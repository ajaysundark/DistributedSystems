import java.io.*;
import java.net.*;
import java.util.*;

public class BankClient {
	private static String serverIp = "127.0.0.1";
	private static int serverPort = 5892;
	private static String clientLog = "clientLogFile";
	private static int threadCount = 100;
	private static int iterationCount = 100;
	private static FileWriter fw;
	
	public static void main(String[] args) {
		List<Socket> openSocketList = new ArrayList<>();
		try {
			fw = new FileWriter(clientLog);
		
			// create 100 accounts
			Client creator = new Client(new NewAccountCreationRequest(), 100);
			creator.setServerName(serverIp);
			creator.setPort(serverPort);
			
			Thread t = new Thread(creator);
			t.start();
			t.join();
			
			System.out.println("Finished creating 100 accounts");
			
			// deposit to 100 accounts
			Response response = null;
			for(int i=1; i<=100; ++i) {
				Socket ssocket = new Socket(serverIp, serverPort);
				if(ssocket.isConnected()) {
					System.out.println("connected to Server.");
					ObjectOutputStream os = new ObjectOutputStream(ssocket.getOutputStream());
					Request depositRequest = new DepositRequest(i, 100);
					os.writeObject(depositRequest);
					System.out.println("Deposit request sent to server..");

					ObjectInputStream is = new ObjectInputStream(ssocket.getInputStream());
					response = (DepositResponse) is.readObject();
		            System.out.println("Server says " + response );
		            
					cLog(response.toString());
				}
				ssocket.close();
				System.out.println("Closed connection at Client side");
			}
			response = null;
			System.out.println("Finished depositing 100 accounts");
			
			// Get balance
			int totalBalance = 0;
				for(int i=1; i<=100; ++i) {
					Socket ssocket = new Socket(serverIp, serverPort);
					if(ssocket.isConnected()) {
						System.out.println("connected to Server.");
						ObjectOutputStream os = new ObjectOutputStream(ssocket.getOutputStream());
						Request balanceRequest = new BalanceRequest(i);
						os.writeObject(balanceRequest);
						
						System.out.println("Balance request sent to server..");
						ObjectInputStream is = new ObjectInputStream(ssocket.getInputStream());
						response = (BalanceResponse) is.readObject();

			            System.out.println("Server says " + response );
						totalBalance+= ((BalanceResponse)response).getAccountBalance();
						cLog(response.toString());
					}
					ssocket.close();
					System.out.println("Closed connection at Client side");
				}
			
			System.out.println("Finished balance query on 100 accounts  : Total balance now is " + totalBalance);
			
			List<Thread> threadList = new ArrayList<>(threadCount);
			for(int i=0; i<threadCount; ++i) {
				Client randomizer = new Client(iterationCount);
				randomizer.setServerName(serverIp);
				randomizer.setPort(serverPort);
				
				Thread rThread = new Thread(randomizer); 
				rThread.start();
				
				threadList.add(rThread);
			}
			
			// Wait for all the account transfers to complete
			for(Thread each : threadList) {
				each.join();
			}
			
			System.out.println("Finished random money transfer on 100 accounts.");
			
			totalBalance = 0;
			for(int i=1; i<=100; ++i) {
				Socket ssocket = new Socket(serverIp, serverPort);
				if(ssocket.isConnected()) {
					System.out.println("connected to Server.");
					ObjectOutputStream os = new ObjectOutputStream(ssocket.getOutputStream());
					Request balanceRequest = new BalanceRequest(i);
					os.writeObject(balanceRequest);
					
					System.out.println("Balance request sent to server..");
					ObjectInputStream is = new ObjectInputStream(ssocket.getInputStream());
					response = (BalanceResponse) is.readObject();

		            System.out.println("Server says " + response );
					totalBalance+= ((BalanceResponse)response).getAccountBalance();
					cLog(response.toString());
				}
				ssocket.close();
				System.out.println("Closed connection at Client side");
			}
		
		System.out.println("Finished balance query on 100 accounts  : Final total balance now is " + totalBalance);
			
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	} // main ends
	
	private static void cLog(String log) throws IOException {
		fw.append(log).append('\n');
	}
}