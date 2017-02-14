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
		if(account.deposit(amount)){
			return "OK";
		}
		return "FAILED";
	}
	int Getbalance(int uid){
		Account account =(Account) accounts.get(uid);
		return account.getBalance();
		// return 0;
	}

	String Transfer(int source, int dest, int amount){
		Account account  =(Account) accounts.get(source);
		Account depositAccount  =(Account) accounts.get(dest);
		if (account.withdral(amount)) {
			depositAccount.deposit(amount);
			return "OK";
		}
		return "FAIL";
	}
	public  static void print(String input){
		System.out.println(input);
	}

}