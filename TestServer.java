import java.net.*;
import java.io.*;

class TestServer {
    ServerSocket serverSocket;
    int serverPortNumber = 8888;
    boolean serverRunning = true;
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(serverPortNumber);
        } catch (Exception e) {
           e.printStackTrace();
        }

       
        while (serverRunning) {
            try {
                Socket clientSocket = ServerSocket.accept();
                Thread thread = new Thread(new Runnable(clientSocket) {

			    @Override
			    public void run(Socket s) {
			    	

			    }
			            
			});

                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


       
    }
}