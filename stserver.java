package part2.chapter10;

import java.net.*;
import java.io.*;

public class STServer {
  public static void main (String args[]) throws IOException {
    if (args.length != 1)
      throw new RuntimeException ("Syntax: STServer <port>");

    Socket client = accept (Integer.parseInt (args[0]));

    try {
      InputStream i = client.getInputStream ();
      OutputStream o = client.getOutputStream ();
      new PrintStream (o).println ("You are now connected to the Echo Server.");

      int x;
      while ((x = i.read ()) > -1)
        o.write (x);
    } finally {
      System.out.println ("Closing");
      client.close ();
    }
  }

  static Socket accept (int port) throws IOException {
    System.out.println ("Starting on port " + port);
    ServerSocket server = new ServerSocket (port);

    System.out.println ("Waiting");
    Socket client = server.accept ();
    System.out.println ("Accepted from " + client.getInetAddress ());

    server.close ();
    return client;
  }
}
