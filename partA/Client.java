import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {

	private Request request = null;
	private Response response = null;
	private int iterations = 1;
	private int operation = MessageType.UNKNOWN.getType();

    private String serverName = "127.0.0.1";
    private int port = 5892;
	
    public Client(int itr) {
    	this.iterations = itr;
    }
    
	public Client(Request req) {
		this.request= req;
	}
	
	public Client(Request req, int iter) {
		this.request = req;
		this.iterations = iter;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void run () {
        
        Socket client = null;

        try {
         System.out.println("Connecting to " + serverName + " on port " + port);

     		operation = (null==request) ? 0 : request.getMessageType().getType(); 
     		// System.out.println("Operation is " + operation);
     		
            while(iterations>0) {         		
	         	if (operation==0) {
	         		int fromAcc = (int) ((Math.random() * 100) + 1);
	         		int toAcc = (int) ((Math.random() * 100) + 1);
	         		request = new TransferRequest(fromAcc, toAcc, 10);
	         	}
	         	
	         	client = new Socket(serverName, port);
	            if(client.isConnected()) {
	            	print("connected to " + client.getRemoteSocketAddress());

		            OutputStream outToServer = client.getOutputStream();
		            ObjectOutputStream sender = new ObjectOutputStream(outToServer);
		            sender.writeObject(request);
		
		            System.out.println("Request sent to server..");
		               
		            InputStream inFromServer = client.getInputStream();
		            ObjectInputStream in = new ObjectInputStream(inFromServer);
		            
		            response = ( (Response)in.readObject() );
		            System.out.println("Server says " +  response);
		            
		            if (request.getMessageType() == MessageType.Transfer) {
		            	BankClient.cLog("Request to server " + request.getMessageType().toString() + " " + response.getStatusString());
		            }
	            }
	            client.close();
	         	
	            --iterations;	         	
         	}
      } catch(IOException e) {
         e.printStackTrace();
      } catch(ClassNotFoundException cnfe) {
          cnfe.printStackTrace();
      } catch (NumberFormatException nfe) {
         nfe.printStackTrace();
      } finally {
          try { client.close(); }
          catch (IOException e) {}
      }
   }

	public  static void print(String input){
		System.out.println(input);
	}

	public  static void print(int input){
		System.out.println(input);
	}
}