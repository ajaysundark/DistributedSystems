import java.net.*;
import java.io.*;

class TestServer {
    ServerSocket serverSocket;
    int serverPortNumber = 8888;
    boolean serverRunning = true;
    public static void main(String[] args) {

        Thread mainThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(serverPortNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                while (serverRunning) {
                    try {
                        Socket clientSocket = ServerSocket.accept();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        mainThread.start();
    }
}