package datagramSockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 */

public class DSServer {
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(20000);
            System.out.println("PORT CONNECTED --> " + ds.getPort());
            byte [] buf = new byte[10];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            int i=4;
            while(i-->0){
                ds.receive(dp);
                System.out.println(dp.getAddress());
                String msg = new String(buf);
                System.out.println(msg);
            }
            ds.close();
            System.out.println("BY(T)E");
        }catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


