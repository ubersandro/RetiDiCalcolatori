package it.unical.dimes.reti.ese8.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class StorageServer {
	private HashMap<String, File> data;

	private final int myPort = 2000;

	private final String indexServerAddress;
	private final int indexServerPort = 3000;

	private ServerSocket server;

	public StorageServer(String indexServerAddress) {
		this.indexServerAddress = indexServerAddress;
		data = new HashMap<String, File>();
		System.out.println("StorageServer in fase di avvio");
		inizia();
	}

	public void inizia() {
		try {
			server = new ServerSocket(myPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			memorizzaFile();
		}
	}

	private void memorizzaFile() {
		try {
			//1  Ricevi File
			System.out.println("In attesa di memorizzare un file...");
			Socket tSocket = server.accept();
			ObjectInputStream ois = new ObjectInputStream(tSocket.getInputStream());
			File file = (File) ois.readObject();
			ois.close();
			tSocket.close();
			//1.1 Memorizza nella struttura dati
			data.put(file.getFilename(), file);
			System.out.println("Ho memorizzato il file "+file);
			
			//2 Invia info all'IndexServer
			System.out.println("Invio datagramma all'IndexServer");
			DatagramSocket uSocket = new DatagramSocket();
					
			//2.1 Prepare la stringa da inviare composta da nomeFile#attributo1,attributo2
			String msg = file.getFilename()+"#";
			for (String key:file.getKeywords()) {
				msg+=key+",";
			}
			//2.2 Preparo il pacchetto
			byte[] buf = msg.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			//2.3 Invio il pacchetto
			InetAddress address = InetAddress.getByName(indexServerAddress);
	        packet = new DatagramPacket(buf, buf.length, address, indexServerPort);
	        uSocket.send(packet);		
	        uSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {		
		StorageServer storageS = new StorageServer("localhost");
	}

}
