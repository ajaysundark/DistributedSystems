import java.net.*;
import java.io.*;

class TestServer {
    ServerSocket serverSocket;
    int serverPortNumber = 8888;
    boolean serverRunning = true;
    TestServer(){
    	try {
            serverSocket = new ServerSocket(serverPortNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (serverRunning) {
            try {
                final Socket clientSocket = serverSocket.accept();
                // then do something witht the socket

                Thread thread = new Thread(new Runnable() {
                	clientSocketInsideThread = clientSocket;
				    @Override
				    public void run() {
				    	
				    	print("TestServer");
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