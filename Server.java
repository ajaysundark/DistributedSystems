import java.util.*;


public class Server{
	Hashtable accounts;
	int base;

	public static void main(String[] args) {
		accounts = new Hashtable();
		base = 0;
	}
	int CreateAcount(){
		Account account  = new Account(base);
		account.add(base, account);
		base++;
		return base -1;
	}
	String Deposit(int amount, int uid){
		Account account = accounts.get(uid);
		if(account.deposit(amount)){
			return "OK";
		}
		return "FAILED";
	}
	int Getbalance(int uid){
		Account account = accounts.get(uid);
		return account.getBalance();
	}

	String Transfer(int source, int dest, int amount){
		Account account  = accounts.get(source);
		if (account.withdral(amount)) {
			Account depositAccount =  accounts.get(dest);
			depositAccount.deposit(amount);
			return "OK";
		}
		return "FAIL";
	}

}