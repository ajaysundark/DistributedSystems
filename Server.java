import java.util.*;
import java.util.concurrent.locks.ReentrantLock ;

public class Server{
	Hashtable accounts= new Hashtable();
	int base=0;
	ReentrantLock serverLock = new ReentrantLock();
	public static void main(String[] args) {
		System.out.println("In main");
		Server server =   new Server();
		print("\n\n\n\nCreating Account\n\n\n\n");
		for (int i = 0;i < 100 ;i++ ) {
			print(server.CreateAcount());
		}
		print("\n\n\n\nDepositing Money\n\n\n\n\n");
		int sum  = 0;
		for (int i = 0;i < 100 ;i++ ) {
			print(server.Deposit(i,100));
		}

		for (int i = 0;i < 100 ;i++ ) {
			sum+=(server.Getbalance(i));
		}
		print("\n\n\n\nSumming Deposits\n\n\n\n");
		print (sum);
		int numberofThreads = 100;
		int numberofIterations = 100;
		ArrayList<Thread> threadsList = new ArrayList<Thread>();
		print("\n\n\n\nStarting Thread\n\n\n\n");
		for (int i = 0;i <numberofThreads ;i++ ) {
			Thread thread = new Thread(new Runnable() {

			    @Override
			    public void run() {
			    	for (int j = 0;j<numberofIterations ;j++ ) {
				        Random rand = new Random(); 
				        int  sourceAccount =(int) rand.nextInt(99);
				        int depositAccount = (int)rand.nextInt(99);
				        // sourceAccount = j;
				        print("sourceAccount");
				        print(sourceAccount);
				        print("depositAccount");
				        print(depositAccount);
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
			sum+=(server.Getbalance(i));
		}
		print("\n\n\n\nFinal Sum\n\n\n\n\n");
		print (sum);

	}
	int CreateAcount(){
		while(serverLock.tryLock() == false){}
		Account account  = new Account(base);
		accounts.put(base, account);
		int accountUID = base;
		base++;
		serverLock.unlock();
		return accountUID;
	}
	String Deposit(int uid, int amount){
		Account account =(Account) accounts.get(uid);

		while (account.lock.tryLock() == false ){}
		if(account.deposit(amount)){
			account.lock.unlock();
			return "OK";
		}
		account.lock.unlock();
		return "FAILED";
	}
	int Getbalance(int uid){
		Account account =(Account) accounts.get(uid);
		while (account.lock.tryLock() == false ){}
		int balance = account.getBalance();
		account.lock.unlock();
		return balance;
	}

	String Transfer(int source, int dest, int amount){
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
		while(serverLock.tryLock() == false){}
		while (sourceAccount.lock.tryLock() == false && depositAccount.lock.tryLock() == false){}
		if (sourceAccount.withdral(amount)) {
			depositAccount.deposit(amount);
			sourceAccount.lock.unlock();
			depositAccount.lock.unlock();
			serverLock.unlock();
			return "OK";
		}
		sourceAccount.lock.unlock();
		depositAccount.lock.unlock();
		serverLock.unlock();
		return "FAIL";
	}
	public  static void print(String input){
		
		System.out.println(input);

	}

	public  static void print(int input){
		
		System.out.println(input);
		
	}

}