import java.util.*;
import java.util.concurrent.locks.* ;
import java.net.*;
import java.io.*;

public class Server {
	Hashtable<Integer, Account> accounts= new Hashtable<>();
	int base=0;
	Lock serverLock = new ReentrantLock();
	ServerSocket serverSocket;

	private void testServer() {
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
 
	public static void main(String[] args) {
		System.out.println("In main");
		Server server =   new Server();

		testServer();
		
		serverSocket = new ServerSocket (5890);
		DataInputStream dis = serverSocket.getInputStream();

		
	}

	int CreateAccount() {
		while(serverLock.tryLock() == false) {}
		Account account  = new Account(base);
		accounts.put(base, account);
		int accountUID = base;
		base++;
		serverLock.unlock();
		return accountUID;
	}

	String Deposit(int uid, int amount) {
		Account account = (Account) accounts.get(uid);

		while (account.lock.tryLock() == false ){}
		if(account.deposit(amount)){
			account.lock.unlock();
			return "OK";
		}
		account.lock.unlock();
		return "FAILED";
	}

	int GetBalance(int uid) {
		Account account = (Account) accounts.get(uid);
		while (account.lock.tryLock() == false ){}
		int balance = account.getBalance();
		account.lock.unlock();
		return balance;
	}

	String Transfer(int source, int dest, int amount) {
		Account sourceAccount  =(Account) accounts.get(source);
		Account depositAccount  =(Account) accounts.get(dest);

		if (sourceAccount == null) {
			System.out.println("source account is null");
			return "FAIL";
		}

		if (depositAccount == null) {
			System.out.println("depositAccount is null");
			return "FAIL";
			
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
			return "OK";
		}
		sourceAccount.lock.unlock();
		depositAccount.lock.unlock();
		return "FAIL";
	}
	public  static void print(String input){
		
		System.out.println(input);

	}

	public  static void print(int input){
		
		System.out.println(input);
		
	}

}