package betServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class BetServer_1 extends Thread{
    private boolean betAcceptable;
    private int winner;
    private Semaphore check;
    private Semaphore mutex;
    private long fine;
    private Map<String, int[]> scommesse; /*< IP , [cavallo, puntata] >*/
    /**
     * Il thread si occupa dell'invio dei risultati ai Client connessi. L'invio procede con il Multicast UDP.
     *
     */
    private void extractWinner(){
        winner =1+(int)(Math.random()*12);
        System.out.println("VINCITORE ESTRATTO..." + winner);
    }

    private class Sender extends Thread{
        public void run(){
            try{
                mutex.acquire();
                StringBuilder sb = new StringBuilder(300);
                scommesse.forEach((IP, v) -> {
                    if(v[0]== winner){
                        System.out.println(Arrays.toString(v));
                        sb.append("<"+IP+">"+" <"+ v[1]*12+">\n");
                    }
                });

                System.out.println();
                System.out.println("RISULTATI");
                System.out.println(sb);
                System.out.println();
                mutex.release();
                byte [] msg = sb.toString().getBytes(StandardCharsets.UTF_8);
                DatagramPacket dp = new DatagramPacket(msg, msg.length, InetAddress.getByName(ServerUtils.MULTICAST_ADDRESS),ServerUtils.MULTICAST_PORT);
                MulticastSocket mcs = new MulticastSocket(ServerUtils.MULTICAST_PORT);
                int i=5;
                while(i>0){
                    mcs.send(dp);
                    i--;
                }
                mcs.close();
            }catch (InterruptedException iex){
                iex.printStackTrace();
            }
            catch(UnknownHostException uhex){
                uhex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class BetManager extends Thread{
        @Override
        public void run(){
            System.out.println("Manager avviato");
            ServerSocket s = null;
            try {
                s = new ServerSocket(ServerUtils.SERVER_PORT);
            }catch (IOException e){
                e.printStackTrace();
            }
            while(true){
                try {
                    Socket client = s.accept();
                    new Handler(client).start();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * La connessione aperta è gestita da un oggetto handler che verifica la correttezza della scommessa sottomessa
         * dal Client e poi la aggiunge alla collezione delle scommesse.
         */
        private class Handler extends Thread{
            Socket handle;
            boolean ok;
            Handler(Socket s){
                handle=s;
            }

            public void run(){
                try {
                    check.acquire();
                    if(betAcceptable) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(handle.getInputStream()));
                        String bet = br.readLine();
                        ok = bet.matches(ServerUtils.betPattern);
                        if (ok) {
                            mutex.acquire();
                            int horse = Integer.parseInt(bet.substring(bet.indexOf('<') + 1, bet.indexOf('>')));
                            String tmp = bet.substring(bet.indexOf(' '));
                            int money = Integer.parseInt(tmp.substring(tmp.indexOf('<') + 1, tmp.indexOf('>')));
                            scommesse.put(handle.getInetAddress().toString()+":"+handle.getPort(), new int[]{horse, money});
                            System.out.format("Scommessa ricevuta da "+handle.getRemoteSocketAddress()+" VALORE: "+money+" CAVALLO: "+horse+'\n');
                            mutex.release();
                        } else {
                            PrintWriter pw = new PrintWriter(handle.getOutputStream(), true);
                            pw.println("Scommessa non accettata. Formato non rispettato -> " + bet);
                            pw.close();
                        }
                        br.close();
                        handle.close();
                    }else{
                        PrintWriter pw = new PrintWriter(handle.getOutputStream(), true);
                        pw.println("Scommessa non accettabile. Tempo scaduto.");
                        pw.close();
                    }
                    check.release();
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }catch(IOException ioex){
                        ioex.printStackTrace();
                    }

            }
        }

    }


    /**
     *
     * @param oraLimite in millisecondi --> prossima versione utilizzerà Calendar.
     */
    public BetServer_1(long oraLimite){
        mutex = new Semaphore(1);
        scommesse = new HashMap<>();
        check = new Semaphore(1);
        betAcceptable = true;
        if(oraLimite - System.currentTimeMillis() > 0)fine = oraLimite;
        else throw new RuntimeException("Ora specificata non valida. Abortion...");
    }

    @Override
    public void run(){
        /**
         * Ogni secondo controlla se è finito il tempo delle scommesse.
         */
        Thread timeChecker = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    long currentTime = System.currentTimeMillis();
                    try{
                        if(currentTime>=fine){
                            System.out.println("Tempo scaduto (timeChecker)");
                            mutex.acquire();
                            betAcceptable=false;
                            mutex.release();
                            break;
                        }
                        else Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        timeChecker.start();
        Thread betManager = new BetManager();
        betManager.setDaemon(true);
        betManager.start();
        try {
            timeChecker.join(); //termina quando finisce il tempo
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.extractWinner();

        /*try{
            Thread.sleep(10000);
        }catch (InterruptedException ex){}
*/
        Thread sender = new Sender();
        sender.start();

        try {
            sender.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ARRIVEDERCI ALL'ITALIA CHE PRODUCE");
    }

    public static void main(String[] args) {
        /*String bet ="<90> <12>";
        if(bet.matches(ServerUtils.betPattern)) System.out.println("YES");
        else System.out.println("NO");
        int horse = Integer.parseInt(bet.substring(bet.indexOf('<')+1,bet.indexOf('>')));
        String tmp = bet.substring(bet.indexOf(' '));
        int money = Integer.parseInt(tmp.substring(tmp.indexOf('<')+1,tmp.indexOf('>')));
        System.out.println("HORSE "+horse+ " MONEY "+money);*/
        new BetServer_1(System.currentTimeMillis()+10000).start();
    }

}


