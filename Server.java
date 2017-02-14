import java.util.*;


public class Server{
	Hashtable accounts = new Hashtable();
	int base = 0;

	int CreateAcount(){
		Account account  = new Account(base);
		account.add(base, account);
		base++;
		return base -1;
	}
	string Deposit(int amount, int uid){
		Account account = accounts.get(uid);
		if(account.deposit(amount)){
			return "OK";
		}
		return "FAILED";
	}

}