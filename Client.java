import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

	public static void main(String [] args) {
      String serverName = "127.0.0.1";
      int port = 5890;
        int fromAccount = -1;
        int toAccount = -1;
        int funds = 0;
        Request request=null;
      MessageType msg;

        Socket client = null;

        try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         client = new Socket(serverName, port);
         
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

         		op = Integer.parseInt(keyboard.nextLine());
         		switch (op) {

         			case 1: //create account
                     request = new NewAccountCreationRequest();
         				break;

         			case 2: //deposit
                     System.out.println("Please specify the account number for depositing funds..");
                     fromAccount = Integer.parseInt(keyboard.nextLine());
                     System.out.println("How much would you like to deposit?");
                     funds = Integer.parseInt(keyboard.nextLine());
                     request = new DepositRequest(fromAccount, funds);
         				break;

         			case 3: //balance
                     System.out.println("Please specify the account number for checking balance..");
                     fromAccount = Integer.parseInt(keyboard.nextLine());
                     request = new BalanceRequest(fromAccount);
         				break;

         			case 4: //transfer
                     System.out.println("Please specify the account number to transfer from ");
                     fromAccount = Integer.parseInt(keyboard.nextLine());
                     System.out.println("Please specify the account number to transfer to ");
                     toAccount = Integer.parseInt(keyboard.nextLine());
                     System.out.println("How much would you like to transfer?");
                     funds = Integer.parseInt(keyboard.nextLine());
                     request = new TransferRequest(fromAccount, toAccount, funds);
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