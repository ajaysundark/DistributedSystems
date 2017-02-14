import java.net.*;
import java.io.*;

class TestServer {
    ServerSocket serverSocket;
    int serverPortNumber = 8889;
    boolean serverRunning = true;
    public TestServer(){
    	try {
            serverSocket = new ServerSocket(serverPortNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (serverRunning) {
            try {
                final Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(new Runnable() {
                	Socket clientSocketInsideThread = clientSocket;
				    @Override
				    public void run() {
				    	BufferedReader inputStream = null; 
        				PrintWriter outputStream = null; 
				    	print("TestServer");
				    	try{

				    		 inputStream = new BufferedReader(
               new InputStreamReader(clientSocketInsideThread.getInputStream()));
            outputStream = new PrintWriter(
               new OutputStreamWriter(clientSocketInsideThread.getOutputStream()));
            				while(true){

            					String clientCommand = inputStream.readLine();
            					print("Client Command " + clientCommand);
            				}
				    	}catch (Exception e) {
				    		e.printStackTrace();
				    	}
				    }
				            
				});
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