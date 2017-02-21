import java.net.*;
import java.io.*;

public class Client {

	public static void main(String [] args) {
      String serverName = "127.0.0.1";
      int port = 5890;
      int funds = 0;
      int fromAccount = -1;
      int toAccount = -1;
      Request request;
      MessageType msg;

      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         print("connected to " + client.getRemoteSocketAddress());
         Scanner keyboard = new Scanner(System.in);

         while (true) {
         	int op = 0;
         	System.out.println("Select your operation" + 
         						"\n\t1. Create an Account" +
         						"\n\t2. Deposit funds" +
         						"\n\t3. Check Balance" +
         						"\n\t4. Transfer funds" +
         						"\n\t5. Quit.");
         	
         		op = Integer.parseInt(keyboard.readLine());
         		switch (MessageType.fromInt(op)) {

         			case MessageType.CREATE: //create account
                     request = new NewAccountCreationRequest();
         				break;

         			case MessageType.DEPOSIT: //deposit
                     System.out.println("Please specify the account number for depositing funds..");
                     int account = Integer.parseInt(keyboard.readLine());
                     System.out.println("How much would you like to deposit?");
                     int amount = Integer.parseInt(keyboard.readLine());
                     request = new DepositRequest(account, amount);
         				break;

         			case MessageType.BALANCE: //balance
                     System.out.println("Please specify the account number for checking balance..");
                     int account = Integer.parseInt(keyboard.readLine());
                     request = new BalanceRequest(account);
         				break;

         			case MessageType.TRANSFER: //transfer
                     System.out.println("Please specify the account number to transfer from ");
                     int fromAccount = Integer.parseInt(keyboard.readLine());
                     System.out.println("Please specify the account number to transfer to ");
                     int toAccount = Integer.parseInt(keyboard.readLine());
                     System.out.println("How much would you like to transfer?");
                     int amount = Integer.parseInt(keyboard.readLine());
                     request = new DepositRequest(fromAccount, toAccount, amount);
         				break;

         			case 5: return;
         			default: op = 0;
         		}

         	if (op==0) {
         		System.out.println("Illegal selection. Please select a valid input operation. \n");
         	}
            else {
               OutputStream outToServer = client.getOutputStream();
               ObjectOutputStream sender = new ObjectOutputStream(outToServer);
               sender.writeObject(request);

               InputStream inFromServer = client.getInputStream();
               ObjectInputStream in = new ObjectInputStream(inFromServer);

               System.out.println("Server says " + ( (Response)in.readObject() ) );
            }
         }

         client.close();
      } catch(IOException e) {
         e.printStackTrace();
      } catch (NumberFormatException nfe) {
         nfe.printStackTrace();
      }
   }

	public  static void print(String input){
		System.out.println(input);
	}

	public  static void print(int input){
		System.out.println(input);
	}
}