import java.util.*;
import java.util.concurrent.locks.ReentrantLock ;
import java.net.*;
import java.io.*;

public class Server{
	Hashtable accounts= new Hashtable();
	int base=0;
	ReentrantLock serverLock = new ReentrantLock();
		 ServerSocket serverSocket;
    int serverPortNumber = 8888;
    boolean serverRunning = true;

	public static void main(String[] args) {
		new Server();
	}
	
    Server(){
    	print("Creating server");
    	try {
            serverSocket = new ServerSocket(serverPortNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (serverRunning) {
            try {
                final Socket clientSocket = serverSocket.accept();
                print("got connection");
                Thread thread = new Thread(new Runnable() {
                	Socket clientSocketInsideThread = clientSocket;
				    @Override
				    public void run() {
				    	BufferedReader inputStream = null; 
        				PrintWriter outputStream = null; 
				    	print("TestServer");
				    	try{

				    		 inputStream = new BufferedReader(
               new InputStreamReader(clientSocketInsideThread.getInputStream()));
            outputStream = new PrintWriter(
               new OutputStreamWriter(clientSocketInsideThread.getOutputStream()));
            				while(true){
            					
            					String clientCommand = inputStream.readLine();
            					print("Client Command " + clientCommand);
            				}
				    	}catch (Exception e) {
				    		e.printStackTrace();
				    	}
				    }
				            
				});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
		while (sourceAccount.lock.tryLock() == false || depositAccount.lock.tryLock() == false){
			if (sourceAccount.lock.isLocked()) {
				sourceAccount.lock.unlock();
			}
			if (depositAccount.lock.isLocked()) {
				depositAccount.lock.unlock();
			}
		}
		if (sourceAccount.withdral(amount)) {
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