import java.io.*;
import java.util.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class BankClient {
	private static String serverIp = "127.0.0.1";
	private static int serverPort = 7100;
	private static String clientLog = "clientLogFile";
	private static int threadCount = 100;
	private static int iterationCount = 100;
	private static FileWriter fw;
	
	public static void main(String[] args) {
		try {
			TTransport transport = new TSocket(serverIp, serverPort);
			
			transport.open();
			TProtocol protocol = new  TBinaryProtocol(transport);
			BankService.Client client = new BankService.Client(protocol);
			perform(client);
			transport.close();
			
			List<Thread> threadList = new ArrayList<>(threadCount);
			for(int i=0;i<threadCount;++i) {
				Thread tThread = new Thread(new Runnable() {
					public void run() {
						try {
							TTransport transport = new TSocket(serverIp, serverPort);
							
							transport.open();
							TProtocol protocol = new  TBinaryProtocol(transport);
							BankService.Client tClient = new BankService.Client(protocol);
							int source = (int)(Math.random() * 100 + 1);
							int destination = (int)(Math.random() * 100 + 1);
							tClient.Transfer(source, destination, 10);
							transport.close();
						} catch (TException e) {
							e.printStackTrace();
						}
					}
				});
				threadList.add(tThread);
			}
			
			// Wait for all the account transfers to complete
			for(Thread each : threadList) {
				each.join();
			}

			System.out.println("Finished random money transfer on 100 accounts.");

			// Get balance
			int totalBalance = 0;
			for(int i=1; i<=100; ++i) {
				totalBalance+=client.GetBalance(i);
			}
			System.out.println("Finished balance query on 100 accounts  : Total balance now is " + totalBalance);
		} catch (TException | InterruptedException x) {
			x.printStackTrace();
		}
	} // main ends
		
	private static void perform(BankService.Client client) throws TException {
		
		// create 100 accounts
		for(int i=0; i<100; ++i) {
			client.CreateAccount();
		}
		
		System.out.println("Finished creating 100 accounts");
		
		// deposit to 100 accounts
		for(int i=1; i<=100; ++i) {
			client.Deposit(i, 100);
		}
		
		System.out.println("Finished depositing 100 accounts");
		
		// Get balance
		int totalBalance = 0;
		for(int i=1; i<=100; ++i) {
			totalBalance+=client.GetBalance(i);
		}
		System.out.println("Finished balance query on 100 accounts  : Total balance now is " + totalBalance);
	}
	
	private static void cLog(String log) throws IOException {
		fw.append(log).append('\n');
	}
}