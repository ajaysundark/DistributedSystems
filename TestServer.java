import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
public class TestServer {
   ServerSocket myServerSocket;
   boolean ServerOn = true;
   public TestServer() { 
      try {
         myServerSocket = new ServerSocket(1579);
      } catch(IOException ioe) { 
         System.out.println("Could not create server socket on port 8888. Quitting.");
         System.exit(-1);
      } 
		
      Calendar now = Calendar.getInstance();
      SimpleDateFormat formatter = new SimpleDateFormat(
         "E yyyy.MM.dd 'at' hh:mm:ss a zzz");
      System.out.println("It is now : " + formatter.format(now.getTime()));
      
      while(ServerOn) { 
         try { 
            Socket clientSocket = myServerSocket.accept();
            ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
            cliThread.start(); 
         } catch(IOException ioe) { 
            System.out.println("Exception found on accept. Ignoring. Stack Trace :"); 
            ioe.printStackTrace(); 
         }  
      } 
      try { 
         myServerSocket.close(); 
         System.out.println("Server Stopped"); 
      } catch(Exception ioe) { 
         System.out.println("Error Found stopping server socket"); 
         System.exit(-1); 
      } 
   }
	
   public static void main (String[] args) { 
      new TestServer();        
   } 
	
   class ClientServiceThread extends Thread { 
      Socket myClientSocket;
      boolean m_bRunThread = true; 
      public ClientServiceThread() { 
         super(); 
      } 
		
      ClientServiceThread(Socket s) { 
         myClientSocket = s; 
      } 
		
      public void run() { 
         BufferedReader in = null; 
         PrintWriter out = null; 
         System.out.println(
            "Accepted Client Address - " + myClientSocket.getInetAddress().getHostName());
         try { 
            in = new BufferedReader(
               new InputStreamReader(myClientSocket.getInputStream()));
            out = new PrintWriter(
               new OutputStreamWriter(myClientSocket.getOutputStream()));
            
            while(m_bRunThread) { 
               String clientCommand = in.readLine(); 
               System.out.println("Client Says :" + clientCommand);
               
               if(!ServerOn) { 
                  System.out.print("Server has already stopped"); 
                  out.println("Server has already stopped"); 
                  out.flush(); 
                  m_bRunThread = false;
               } 
               if(clientCommand.equalsIgnoreCase("quit")) {
                  m_bRunThread = false;
                  System.out.print("Stopping client thread for client : ");
               } else if(clientCommand.equalsIgnoreCase("end")) {
                  m_bRunThread = false;
                  System.out.print("Stopping client thread for client : ");
                  ServerOn = false;
               } else {
                  out.println("Server Says : " + clientCommand);
                  out.flush(); 
               } 
            } 
         } catch(Exception e) { 
            e.printStackTrace(); 
         } 
         finally { 
            try { 
               in.close(); 
               out.close(); 
               myClientSocket.close(); 
               System.out.println("...Stopped"); 
            } catch(IOException ioe) { 
               ioe.printStackTrace(); 
            } 
         } 
      } 
   } 
}

// import java.net.*;
// import java.io.*;

// class TestServer {
//     ServerSocket serverSocket;
//     int serverPortNumber = 8870;
//     boolean serverRunning = true;
//     public TestServer(){
//     	print("Starting Server");
//     	try {
//             serverSocket = new ServerSocket(serverPortNumber);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         while (serverRunning) {
//         	print("In while loop");
//             try {
//                 final Socket clientSocket = serverSocket.accept();
//                 Thread thread = new Thread(new Runnable() {
//                 	Socket clientSocketInsideThread = clientSocket;
// 				    @Override
// 				    public void run() {
// 				    	print("in run func");
// 				    	BufferedReader inputStream = null; 
//         				PrintWriter outputStream = null; 
// 				    	try{

// 				    		inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//             				outputStream = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//             				if (inputStream == null || outputStream == null) {
//             					print ("streams are null");
//             				}
//             				while(true){
//             					print("listening to data stream");
//             					String clientCommand = inputStream.readLine();
//             					System.out.println("Client Says :" + clientCommand);
//             					outputStream.print("Oka");
//             				}
// 				    	}catch (Exception e) {
// 				    		e.printStackTrace();
// 				    	}
// 				    }
				            
// 				});
// 				thread.start();
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//     }
//     public static void main(String[] args) {
// 		new  TestServer();
//     }

//     public  static void print(String input){
		
// 		System.out.println(input);

// 	}

// 	public  static void print(int input){
		
// 		System.out.println(input);
		
// 	}
// }