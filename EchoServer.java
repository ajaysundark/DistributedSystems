import java.net.*;
import java.io.*;

public class EchoServer extends Thread {
  protected Socket s;

  EchoServer (Socket s) {
    System.out.println ("New client.");
    this.s = s;
  }

  public void run () {
    try {
      InputStream istream = s.getInputStream ();
      OutputStream ostream = s.getOutputStream ();
    //  new PrintStream (ostream).println ("Welcome to the multithreaded echo server.");
      ostream.write("Welcome to the multithreaded echo server.");
      byte buffer[] = new byte[16];
      int read;
      while ((read = istream.read (buffer)) >= 0) {
        ostream.write (buffer, 0, read);
        // print received data on server's console
        System.out.write (buffer, 0, read);
        System.out.flush();
      }
      System.out.println ("Client exit.");
    } catch (IOException ex) {
      ex.printStackTrace ();
    } finally {
      try {
        System.out.println("closing client connection...");
        s.close ();
      } catch (IOException ex) {
        ex.printStackTrace ();
      }
    }
  }

  public static void main (String args[]) throws IOException {

    if (args.length != 1)
         throw new RuntimeException ("Syntax: EchoServer port-number");

    System.out.println ("Starting on port " + args[0]);
    ServerSocket server = new ServerSocket (Integer.parseInt (args[0]));

    while (true) {
      System.out.println ("Waiting for a client request");
      Socket client = server.accept ();
      System.out.println ("Received request from " + client.getInetAddress ());
      EchoServer c = new EchoServer (client);
      c.start ();
    }
  }
}
