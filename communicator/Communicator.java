package communicator;


import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Communicator extends Thread{
    private static final int MC_PORT = 2000;
    private static final String MC_IP ="230.0.0.1";
    protected String TCP_PORT = (59000 + (int) (Math.random()*1000))+ "";

    protected class SocketListener extends Thread{
        public void run(){
            try {
                ServerSocket s = new ServerSocket(Integer.parseInt(TCP_PORT));
                while(true){
                    Socket client = s.accept();
                    new Thread(()->{
                        try{
                            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            String line = null;
                            do{
                                line = br.readLine();
                                if(line!=null)System.out.println(line);
                            }while(line!=null);
                            OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                            osw.write("Ti spacco a due come finisco di lavorare. Gubay da "+this.getId());
                            osw.flush();
                            osw.close();
                            br.close(); //chiudere il flusso equivale a chiudere il socket a quanto pare --> PORCODIO
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected class MulticastListener extends Thread{
        public void run(){
            try {
                MulticastSocket mcs = new MulticastSocket(MC_PORT);
                mcs.joinGroup(InetAddress.getByName(MC_IP));
                while(true) {
                    byte[] buffer = new byte[256];
                    DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                    mcs.receive(dp);
                    String msg = new String(dp.getData());
                    int R_PORT = Integer.parseInt(msg.substring(0, msg.indexOf('\0')));
                    InetAddress receiver = dp.getAddress();
                    Socket s = null;
                    if(InetAddress.getLocalHost()!=receiver ||  !Communicator.this.TCP_PORT.equals(R_PORT+"") ) {
                        s = new Socket(receiver, R_PORT);
                        Socket finalS = s;
                        new Thread(() -> {
                            try {
                                System.out.println("Gestisco la connessione "+finalS);
                                PrintWriter pw = new PrintWriter(new OutputStreamWriter(finalS.getOutputStream()),true);
                                pw.println(TCP_PORT);
                                pw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected void sendMCastDatagram(){
        new Thread(()->{
            while(true) {
                try {
                    InetAddress grp_add = InetAddress.getByName(MC_IP);
                    byte[] buffer = TCP_PORT.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket dp = new DatagramPacket(buffer, buffer.length, grp_add, MC_PORT);
                    MulticastSocket mcs = new MulticastSocket(MC_PORT); //--> è meglio crearlo nel costruttore e poi condividerlo con i Lock
                    mcs.send(dp);
                    System.out.format("%d > INVIO MC\n", this.getId());
                    Thread.sleep(5000);
                } catch (IOException  e) {
                    e.printStackTrace();
                }catch (InterruptedException iex){
                    break;
                }
            }
        }).start();
    }

    public void run(){
        new SocketListener().start();
        new MulticastListener().start();
        sendMCastDatagram();

    }

    public static void main(String[] args) {
        new Communicator().start();
        new Communicator().start();
        new Communicator().start();
    }
}

