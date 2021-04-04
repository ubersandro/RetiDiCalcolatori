package betServer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

/**
 * Versione 1.0
 * Prossima versione --> introdurre costruttore con parametri server (IP e porta) variabili
 */
public class BetClient extends Thread{
    private int bet = 1 + (int) (Math.random()*12);
    private int horse = 1 + (int) (Math.random()*12);
    private String SERVER_NAME = "localhost";
    private int SERVER_PORT = ServerUtils.SERVER_PORT;
    private String group = ServerUtils.MULTICAST_ADDRESS;

    @Override
    public void run(){
        Socket s = null;
        try{
            s = new Socket(SERVER_NAME, SERVER_PORT);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
            pw.println("<"+horse+">"+" <"+bet+">");
            System.out.println(this.getId()+" HA SCOMMESSO <"+horse+">"+" <"+bet+">");
            pw.close();
            s.close();
            MulticastSocket mcs = new MulticastSocket(ServerUtils.MULTICAST_PORT);
            mcs.joinGroup(InetAddress.getByName(group));
            int i = 2;
            byte [] buffer = new byte[256];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            mcs.receive(dp);
            String st = new String(dp.getData());
            System.out.println(st.substring(0,st.indexOf('\0'))); //--> attenzione
            mcs.close();
        }catch(IOException ioex){
            ioex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int i = 10;
        while(i-->=0) new BetClient().start();
    }
}
