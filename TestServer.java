import java.net.*;
import java.io.*;

class TestServer {
    ServerSocket serverSocket;
    int serverPortNumber = 8881;
    boolean serverRunning = true;
    public TestServer(){
    	print("Starting Server");
    	try {
            serverSocket = new ServerSocket(serverPortNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (serverRunning) {
        	print("In while loop");
            try {
                final Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(new Runnable() {
                	Socket clientSocketInsideThread = clientSocket;
				    @Override
				    public void run() {
				    	print("in run func");
				    	BufferedReader inputStream = null; 
        				PrintWriter outputStream = null; 
				    	print("TestServer");
				    	try{

				    		inputStream = new BufferedReader(new InputStreamReader(clientSocketInsideThread.getInputStream()));
            				outputStream = new PrintWriter(new OutputStreamWriter(clientSocketInsideThread.getOutputStream()));
            				if (inputStream == null || outputStream == null) {
            					print ("streams are null");
            				}
            				while(true){
            					print("listening to data stream");
            					String clientCommand = inputStream.readLine();
            					print("Client Command " + clientCommand);
            					outputStream.print("Oka");
            				}
				    	}catch (Exception e) {
				    		e.printStackTrace();
				    	}
				    }
				            
				});
				thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
		new  TestServer();
    }

    public  static void print(String input){
		
		System.out.println(input);

	}

	public  static void print(int input){
		
		System.out.println(input);
		
	}
}