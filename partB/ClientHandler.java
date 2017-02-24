import org.apache.thrift.TException;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.* ;

public class ClientHandler implements BankService.Iface {

	Hashtable<Integer, Account> accounts= new Hashtable<>();
	int BASE=1;
	Lock serverLock = new ReentrantLock();

	private static final String serverLog = "serverLogFile";
	private FileWriter fw;
	
	public ClientHandler() {
		try {
			fw = new FileWriter(serverLog);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int CreateAccount() throws TException {
		sLog("Received Create request from client ");
		
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
		sLog("Received Deposit request from client for " + account.getUID());
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
		sLog("Received GetBalance request from client for " + account.getUID());
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
		
		sLog("Received Transfer request from client for transfer from " + sourceAccount.getUID() + " to " + depositAccount.getUID());

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
			sLog("Fund transfer from " + source + " to destn : " + destination + " success");
			return BankServiceConstants.SUCCESS;
		}
		sLog("Fund transfer from " + source + " to destn : " + destination + " failed");
		sourceAccount.lock.unlock();
		depositAccount.lock.unlock();
		return BankServiceConstants.FAIL;
	}

	public void sLog(String string) {
		try { fw.append(string + '\n');fw.flush(); }
		catch(IOException e) {e.printStackTrace();}
	}

}
