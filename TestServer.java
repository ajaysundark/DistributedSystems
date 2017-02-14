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
                Socket clientSocket = ServerSocket.accept();
                // then do something witht the socket
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
		new  TestServer();
    }
}