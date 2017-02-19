import java.net.*;
import java.io.*;

public class Client {

	public static void testClient() {

	}

	public static void main(String [] args) {
      String serverName = "127.0.0.1";
      int port = 5890;
      int funds = 0;
      int fromAccount = -1;
      int toAccount = -1;
      Request req;
      MessageType msg;

      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         print("connected to " + client.getRemoteSocketAddress());
         while (true) {
         	int op = 0;
         	System.out.println("Select your operation:
         						\n\t1. Create an Account.
         						\n\t2. Deposit funds.
         						\n\t3. Check Balance.
         						\n\t4. Transfer funds.
         						\n\t5. Quit.");

         	Scanner scanner = new Scanner();
         	if(scanner.hasNextInt()) {
         		op = scanner.readInt();
         		switch (op) {
         			case 1: //create account

         				break;
         			case 2: //deposit
         				break;
         			case 3: //balance
         				break;
         			case 4: //transfer
         				break;
         			case 5: return;
         			default: op = 0;
         		}
         	}

         	if (op==0) {
         		System.out.println("Illegal selection. Please select a valid input operation. \n");
         	} else {
         		msg = MessageType.fromInt(op);
         	}
         }

         OutputStream outToServer = client.getOutputStream();
		 PrintWriter sender  = new PrintWriter(new OutputStreamWriter(outToServer));
         print("Sending command");
         sender.print()

         print("Data Sent");
         InputStream inFromServer = client.getInputStream();
         System.out.println("Input stream made");
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         client.close();
      } catch(IOException e) {
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