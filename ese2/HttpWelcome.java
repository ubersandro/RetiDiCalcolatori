package it.unical.dimes.reti.ese2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
public class HttpWelcome {
  private static int port = 8188;
  private static String HtmlWelcomeMessage () {
    return "<html>\n"+
               "  <head>\n"+
               "    <title>UNICAL Ingegneria Informatica</title>\n"+
               "  </head>\n"+
               "  <body>\n"+
               "    <h2 align=\"center\">\n"+
               "      <font color=\"#0000FF\">Benvenuti al Corso di"+ 
               " Reti di Calcolatori</font>\n"+
               "    </h2>\n"+
               "  </body>\n"+
               "</html>";
  }
  public static void main (String args[]) {
	    try {
	      ServerSocket server = new ServerSocket(port);
	      System.out.println("HTTP server running on port: "+port);
	      while (true) {
	        Socket client = server.accept();
	        BufferedReader in = new BufferedReader
	          (new InputStreamReader(client.getInputStream()));
	        PrintWriter out = new PrintWriter
	          (new OutputStreamWriter(client.getOutputStream()));
	        String request = in.readLine();
	        System.out.println("Request: "+request);
	        StringTokenizer st = new StringTokenizer(request);
	        //Thread.sleep(10000);
	        if ((st.countTokens()>=2) && st.nextToken().equals("GET")) {
	            String message = HtmlWelcomeMessage();
	            // Start of response headers
	            out.println ("HTTP/1.0 200 OK");
	            out.println ("Content-Length: "+message.length());
	            out.println ("Content-Type: text/html");
	            out.println ();
	            // End of response headers
	            out.println (message);
	          } else {
	            out.println("400 Bad Request");
	          }
	          out.flush();
	          client.close();
	        }
	      } catch (Exception e) { System.err.println(e); }
	    } // main
	  } // class
