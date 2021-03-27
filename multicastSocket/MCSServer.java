package multicastSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class MCSServer {
    /*private*/ final static String MSG = "Nato selvaggio per essere libero \0 auuuuu", group = "224.0.0.15";
    /*private*/ final static int PORT = 9000;
    public static void main(String[] args) {
        try {
            MulticastSocket ms = new MulticastSocket(PORT); // -> genera IOE
            InetAddress addr = InetAddress.getByName(group); // -> con getByName si può fornire un indirizzo IP testuale
            DatagramPacket dp = new DatagramPacket(MSG.getBytes(StandardCharsets.UTF_8),MSG.length(),addr, PORT);
            Scanner sc = new Scanner(System.in) ;
            sc.nextLine();
            ms.send(dp);
            ms.close();
            System.out.println("GUBAY");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
