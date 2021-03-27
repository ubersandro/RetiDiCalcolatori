package multicastSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import static multicastSocket.MCSServer.*;

public class MCSClient {
    public static void main(String[] args) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(group);
            byte[]buffer = new byte[250];
            DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
            MulticastSocket ms = new MulticastSocket(PORT);
            ms.joinGroup(addr);
            ms.receive(dp);
            String r = new String(dp.getData());
            System.out.println(r.substring(0,r.indexOf('\0')));
            ms.leaveGroup(addr);
            ms.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
