package eserc1;
import java.net.*;

public class Test {
    public static void main(String[] args) {
        InetAddress a[]=null;
        InetAddress b = null;
        InetAddress modem[]=null;
        try {
            a = InetAddress.getAllByName("localhost"); /*this pc's addresses (IPv4, IPv6)*/
            b=InetAddress.getLocalHost();
            modem=InetAddress.getAllByName("fritz.box");
        }catch(UnknownHostException e ){
            e.printStackTrace();
        }
        for(InetAddress addr : a) {
            System.out.println(addr);
        }
        System.out.println("Modem");
        for(InetAddress addr : modem) {
            System.out.println(addr);
        }
        //System.out.println(b.getAddress());/*raw ip address */
    }
}
