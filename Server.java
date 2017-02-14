import java.util.*;
import java.util.concurrent.locks.ReentrantLock ;

public class Server{
	Hashtable accounts= new Hashtable();
	int base=0;
	ReentrantLock serverLock = new ReentrantLock();
	public static void main(String[] args) {
		System.out.println("In main");
		Server server =   new Server();
		for (int i = 0;i < 100 ;i++ ) {
			print(server.CreateAcount());
		}
		int sum  = 0;
		for (int i = 0;i < 100 ;i++ ) {
			print(server.Deposit(i,100));
		}

		for (int i = 0;i < 100 ;i++ ) {
			sum+=(server.Getbalance(i));
		}
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
		Account account  =(Account) accounts.get(source);
		Account depositAccount  =(Account) accounts.get(dest);

		while (account.lock.tryLock() == false && depositAccount.lock.tryLock()){}
		if (account.withdral(amount)) {
			depositAccount.deposit(amount);
			account.lock.unlock();
			depositAccount.lock.unlock();
			return "OK";
		}
		account.lock.unlock();
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