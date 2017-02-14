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
			 out.writeUTF("Hello from " + clientSocket.getLocalSocketAddress());
	         InputStream clientInputStream = clientSocket.getInputStream();
	         DataInputStream clientDataInputStream = new DataInputStream(clientInputStream);
	         
	         System.out.println("Server says " + clientDataInputStream.readUTF());
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