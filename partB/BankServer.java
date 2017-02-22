import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class BankServer {

	public static ClientHandler cHandler; 

	public static BankService.Processor processor;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			cHandler = new ClientHandler();
			processor = new BankService.Processor(cHandler);
			
			Runnable serverParent = new Runnable() {
				public void run() {
					startServer(processor);
				}
			};
			
			new Thread(serverParent).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void startServer(BankService.Processor sProcessor) {
		try {
			TServerTransport serverTransport = new TServerSocket(7100);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(sProcessor));
			System.out.println("Starting the multi-threaded server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
