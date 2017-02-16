import java.net.*;
import java.io.*;



public class Client{

		public static void main(String [] args) {
      String serverName = "127.0.0.1";//args[0];
      int port = 7890;//Integer.parseInt(args[1]);
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         System.out.println("Sending command");
         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         System.out.println("Data Sent");
         InputStream inFromServer = client.getInputStream();
         System.out.println("Input stream made");
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }


		

	public  static void print(String input){
		
		System.out.println(input);

	}

	public  static void print(int input){
		
		System.out.println(input);
		
	}
}




//			 clientDataOutputStream.writeUTF("data"); 
// clientDataInputStream.readUTF()

/*
String serverAddress = "127.0.0.1";//args[0];
		int serverPortNumber = 1589;//Integer.parseInt(args[1]);
		try{
			print("Connecting to " + serverAddress+" port " +String.valueOf(serverPortNumber));
			Socket clientSocket = new Socket( serverAddress, serverPortNumber);
			print("Connected");
			OutputStream clientOutputStream = clientSocket.getOutputStream();
			DataOutputStream clientDataOutputStream = new DataOutputStream(clientOutputStream);
			InputStream clientInputStream = clientSocket.getInputStream();
		    DataInputStream clientDataInputStream = new DataInputStream(clientInputStream);
			print("\n\n\n\nCreating Account\n\n\n\n");
			for (int i = 0;i < 100 ;i++ ) {
				clientDataOutputStream.writeUTF("CreateAccount"); 
				print("sent command");
				print( clientDataInputStream.readUTF());
			}

			print("\n\n\n\nDepositing Money\n\n\n\n\n");
			int sum  = 0;
			for (int i = 0;i < 100 ;i++ ) {
				clientDataOutputStream.writeUTF("Deposit;"+String.valueOf(i)+"100"); 
				print( clientDataInputStream.readUTF());
			}

	         clientSocket.close();
		}catch (Exception e) {
			System.out.println(e);
		}

	}
	
*/