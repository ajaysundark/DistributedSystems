import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.* ;
import java.net.*;
import java.io.*;

public class Server {
	Hashtable<Integer, Account> accounts= new Hashtable<>();
	int BASE=0;
	Lock serverLock = new ReentrantLock();
	private static final String serverLog = "serverLogFile";

	private void testServer(Server server) {
		// Test Server code
		print("\n\n\n\nCreating Account\n\n\n\n");
		for (int i = 0;i < 100 ;i++ ) {
			print(server.CreateAccount());
		}
		print("\n\n\n\nDepositing Money\n\n\n\n\n");
		int sum  = 0;
		for (int i = 0;i < 100 ;i++ ) {
			print(server.Deposit(i,100));
		}

		for (int i = 0;i < 100 ;i++ ) {
			sum+=(server.GetBalance(i));
		}

		print("\n\n\n\nSumming Deposits\n\n\n\n");
		print (sum);
		int numberofThreads = 100;
		int numberofIterations = 5;
		ArrayList<Thread> threadsList = new ArrayList<Thread>();
		print("\n\n\n\nStarting Thread\n\n\n\n");
		for (int i = 0;i <numberofThreads ;i++ ) {
			Thread thread = new Thread(new Runnable() {

			    @Override
			    public void run() {
			    	for (int j = 0;j<numberofIterations ;j++ ) {
				        Random rand = new Random(); 
				        int  sourceAccount = (int) rand.nextInt(99);
				        int depositAccount = (int) rand.nextInt(99);
				        print(server.Transfer(sourceAccount, depositAccount, 10));
				    }

			    }
			            
			});
			        
			thread.start();
			threadsList.add(thread);
		}

		for (Thread thread : threadsList ) {
			try{
				thread.join();
			}catch (Exception e) {
				System.out.println(e);
			}
		}

		print("All Done");
		sum =0;
		for (int i = 0;i < 100 ;i++ ) {
			sum+=(server.GetBalance(i));
		}
		print("\n\n\n\nFinal Sum\n\n\n\n\n");
		print (sum);
	}

	public void serverProcess() throws IOException, InterruptedException, ExecutionException {
		ServerSocket serverSocket = new ServerSocket (5890);
		Socket client;

		ObjectInputStream oin;
		ObjectOutputStream oos;
		Request request = null;
		Response response = null;

		ExecutorService es = Executors.newFixedThreadPool(2);
		Future<Response> f;
		FileWriter fw = new FileWriter(serverLog);
		// List<Future<Response>> execList = new ArrayList<>(100);

		while(true) {
			client = serverSocket.accept();

			System.out.println("Received request from client : " + client.getInetAddress());

			try {
				oin = new ObjectInputStream (client.getInputStream());
				request = (Request) oin.readObject();
				fw.append(request.toString()).append('\n');
				f = es.submit(new ProcessThread(request));
				if (f.isDone()) {
					oos = new ObjectOutputStream(client.getOutputStream());
					response = f.get();
					oos.writeObject(response);
					fw.append(response.toString());
					fw.flush();
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} /*finally {
				client.close();
			}*/
		}
	}
	public static void main(String[] args) {
		System.out.println("In main");
		Server server =   new Server();

		// testServer(server);
		try { server.serverProcess(); }
		catch (IOException | InterruptedException | ExecutionException ex) {
			ex.printStackTrace();
		}
	}

	int CreateAccount() {
		while(serverLock.tryLock() == false) {}
		Account account  = new Account(BASE);
		accounts.put(BASE, account);
		int accountUID = BASE;
		BASE++;
		serverLock.unlock();
		return accountUID;
	}

	int Deposit(int uid, int amount) {
		Account account = (Account) accounts.get(uid);

		while (account.lock.tryLock() == false ){}
		if(account.deposit(amount)){
			account.lock.unlock();
			return Response.SUCCESS;
		}
		account.lock.unlock();
		return Response.FAIL;
	}

	int GetBalance(int uid) {
		Account account = (Account) accounts.get(uid);
		while (account.lock.tryLock() == false ){}
		int balance = account.getBalance();
		account.lock.unlock();
		return balance;
	}

	int Transfer(int source, int dest, int amount) {
		Account sourceAccount  =(Account) accounts.get(source);
		Account depositAccount  =(Account) accounts.get(dest);

		if (sourceAccount == null) {
			System.out.println("source account is null");
			return Response.SUCCESS;
		}

		if (depositAccount == null) {
			System.out.println("depositAccount is null");
			return Response.FAIL;
			
		}

		while (sourceAccount.lock.tryLock() == false || depositAccount.lock.tryLock() == false) {
			if (sourceAccount.lock.isLocked()) {
				sourceAccount.lock.unlock();
			}
			if (depositAccount.lock.isLocked()) {
				depositAccount.lock.unlock();
			}
		}

		if (sourceAccount.withdraw(amount)) {
			depositAccount.deposit(amount);
			sourceAccount.lock.unlock();
			depositAccount.lock.unlock();
			return Response.SUCCESS;
		}
		sourceAccount.lock.unlock();
		depositAccount.lock.unlock();
		return Response.FAIL;
	} // Transfer ends

	public  static void print(String input){
		System.out.println(input);
	}

	public  static void print(int input){
		System.out.println(input);
	}


	class ProcessThread implements Callable<Response> {
		Request request = null;
		Response response = null;

		public ProcessThread(Request _req) {
			this.request = _req;
		}

		public Response call() {
			if (request!=null) {
				Response response = null;
				int fromAcc = -1;
				int toAcc = -1;
				int funds = 0;


				switch(request.getMessageType()) {
					case Create:
						// NewAccountCreationRequest ; nothing to do with the request object
						fromAcc = CreateAccount();
						response = new CreateAccountResponse(fromAcc);
						break;
					case Deposit:
						// DepositRequest
						fromAcc = ((DepositRequest) request).getAccountNumber();
						funds = ((DepositRequest)request).getFundToDeposit();
						response = new DepositResponse(Deposit(fromAcc, funds));
						break;
					case Balance:
						fromAcc = ((BalanceRequest)request).getAccountNumber();
						response = new BalanceResponse(GetBalance(fromAcc));
						break;
					case Transfer:
						fromAcc = ((TransferRequest)request).transferFrom();
						toAcc = ((TransferRequest)request).transferTo();
						funds = ((TransferRequest)request).getAmountToTransfer();
						response = new TransferResponse(Transfer(fromAcc, toAcc, funds));
						break;
				}
			}
			return response;
		} // call ends
	} // Thread ends
	
}