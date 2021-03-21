package datagramSockets;

import jdk.nashorn.internal.runtime.events.RecompilationEvent;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DGSTest {
    private static final int SEND_PORT = 18000, RECEIVE_PORT=20000;
    public static void main(String[] args) {

        try {
            DatagramSocket s = new DatagramSocket();
            System.out.println(s.getPort());
            InetAddress addr = InetAddress. getByName("localhost");
            String mex = "Ti spacco a due come finisco di lavorare";
            byte []bytes = mex.getBytes(StandardCharsets.UTF_8);
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, addr, RECEIVE_PORT);
            int i=4;
            System.out.println("SENDING");
            /*System.out.println(s);
            System.out.println(dp);
            System.out.println("LOCALHOST -> "+ dp.getAddress());
            System.out.println(Arrays.toString(dp.getData()));
            System.out.println(new String(dp.getData()));*/
            while(i-->0)s.send(dp);
            s.close();
        } catch (SocketException e) {
            System.err.println("SocketExc");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
