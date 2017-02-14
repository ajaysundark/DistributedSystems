import java.net.*;
import java.io.*;



public class Client{
	public static void main(String[] args) {
		String serverAddress = args[0];
		int serverPortNumber = Integer.parseInt(args[1]);
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
	

	public  static void print(String input){
		
		System.out.println(input);

	}

	public  static void print(int input){
		
		System.out.println(input);
		
	}
}




//			 clientDataOutputStream.writeUTF("data"); 
// clientDataInputStream.readUTF()
