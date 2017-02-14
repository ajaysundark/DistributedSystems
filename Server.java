import java.util.*;


public class Server{
	Hashtable accounts= new Hashtable();
	int base=0;

	public static void main(String[] args) {
		System.out.println("In main");
	}
	int CreateAcount(){
		Account account  = new Account(base);
		accounts.put(base, account);
		base++;
		return base -1;
	}
	String Deposit(int amount, int uid){
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

}