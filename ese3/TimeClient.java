package it.unical.dimes.reti.ese3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeClient {
	public static void main(String[] args) throws IOException {
		String hostname = "localhost";
		int port = 3575;
		DatagramSocket socket = new DatagramSocket();
		// invia la richiesta
		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(hostname);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
		socket.send(packet);
		// riceve la risposta
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		// visualizza la risposta
		String received = new String(packet.getData());
		
		System.out.println("Response: " + received);
		System.out.format("IP:%s port:%s%n", packet.getAddress().toString(), ""+packet.getPort());
		socket.close();
	}
}
