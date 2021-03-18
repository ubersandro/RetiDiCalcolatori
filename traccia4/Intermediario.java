package it.unical.dimes.reti.traccia4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Intermediario {

    private final static int rPort = 2345;
    private final static int gPort = 6789;

    private static List<Venditore> venditori;

    public Intermediario(List<Venditore> venditori) {
        this.venditori = venditori;
    }

    public static void main(String[] args) {

        // Fase 1: ricevi richiesta
        try {
            ServerSocket server = new ServerSocket(rPort);
            MulticastSocket msocket = new MulticastSocket(gPort);

            while (true) {
                Socket accettaRichiesta = server.accept();
                RichiestaHandler t = new RichiestaHandler(accettaRichiesta, msocket, venditori);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class RichiestaHandler extends Thread {
    private Socket socket;
    private MulticastSocket msocket;
    private List<Venditore> venditori;
    private List<Risposta> risposte;

    public RichiestaHandler(Socket s, MulticastSocket msocket, List<Venditore> venditori) {
        this.socket = s;
        this.msocket = msocket;
        this.venditori = venditori;
        this.risposte = Collections.synchronizedList(new LinkedList<Risposta>());
    }

    public void storeRisposta(Risposta risposta) {

        this.risposte.add(risposta);

    }

    public void run() {
        try {

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Richiesta richiesta = (Richiesta) ois.readObject();

            // invia la richiesta in multicast ai Venditori
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(richiesta);
            oos.flush();
            byte[] buf = bos.toByteArray();

            for (Venditore venditore : venditori) {
                MulticastSocket socketV = new MulticastSocket();
                InetAddress group = InetAddress.getByName(venditore.getAddress());
                socketV.joinGroup(group);
                socketV.setSoTimeout(1000 * 60);
                new RispostaHandler(socketV, this).start();
            }

            Risposta minRisp = new Risposta(richiesta.getIdProdotto(), richiesta.getQuantita(), -1, -1);
            for (Risposta r : risposte) {
                if (r.getPrezzo() < minRisp.getPrezzo()) {
                    minRisp = r;
                }
            }

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(minRisp);
            oos.flush();
            socket.close();


        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

class RispostaHandler extends Thread {
    private MulticastSocket msocket;
    private RichiestaHandler handler;

    public RispostaHandler(MulticastSocket msocket, RichiestaHandler handler) {
        this.msocket = msocket;
        this.handler = handler;
    }

    public void run() {
        try {
            DatagramPacket packet;
            while (true) {
                byte[] buf = new byte[512];
                packet = new DatagramPacket(buf, buf.length);
                msocket.receive(packet);
                String received = new String(packet.getData());
                String[] parts = received.split(",");
                int idProdotto = Integer.parseInt(parts[0]);
                int quantita = Integer.parseInt(parts[1]);
                int prezzo = Integer.parseInt(parts[2]);
                int idVenditore = Integer.parseInt(parts[3]);
                this.handler.storeRisposta(new Risposta(idProdotto, quantita, prezzo, idVenditore));
            }

        } catch (Exception e) {
            this.msocket.close();
            System.out.println("Fine risposte dal venditore");
        }
    }
}
