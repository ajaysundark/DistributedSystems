import org.apache.thrift.TException;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.* ;

public class ClientHandler implements BankService.Iface {

	Hashtable<Integer, Account> accounts= new Hashtable<>();
	int BASE=1;
	Lock serverLock = new ReentrantLock();

//	private static final String serverLog = "serverLogFile";
//	private FileWriter fw;
	
	public int CreateAccount() throws TException {
		while(serverLock.tryLock() == false) {}
		Account account  = new Account(BASE);
		accounts.put(BASE, account);
		int accountUID = BASE;
		BASE++;
		serverLock.unlock();
		return accountUID;
	}

	public int Deposit(int uid, int amount) throws TException {
		Account account = (Account) accounts.get(uid);

		while (account.lock.tryLock() == false ){}
		if(account.deposit(amount)){
			account.lock.unlock();
			return BankServiceConstants.SUCCESS;
		}
		account.lock.unlock();
		return BankServiceConstants.FAIL;
	}

	public int GetBalance(int uid) throws TException {
		Account account = (Account) accounts.get(uid);
		while (account.lock.tryLock() == false ){}
		int balance = account.getBalance();
		account.lock.unlock();
		return balance;
	}

	public int Transfer(int source, int destination, int amount)
			throws TException {
		Account sourceAccount  =(Account) accounts.get(source);
		Account depositAccount  =(Account) accounts.get(destination);

		if (sourceAccount == null) {
			System.out.println("source account is null");
			return BankServiceConstants.SUCCESS;
		}

		if (depositAccount == null) {
			System.out.println("depositAccount is null");
			return BankServiceConstants.FAIL;
			
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
			return BankServiceConstants.SUCCESS;
		}
		sourceAccount.lock.unlock();
		depositAccount.lock.unlock();
		return BankServiceConstants.FAIL;
	}

}
