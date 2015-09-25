package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import httplike.arquivos.Parser;

public class ClienteUDP {

	private Parser parser;

	public ClienteUDP() {
		parser = new Parser();
	}

	public void enviarPacote(String host, int porta, byte[] dados) throws IOException {
		InetAddress end = InetAddress.getByName(host);
		DatagramPacket packet = new DatagramPacket(dados, dados.length, end, porta);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		socket.close();
	}

	public void receberPacote(int porta) throws IOException {
		byte[] buffer = new byte[1024];
		String s;
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		DatagramSocket socket = new DatagramSocket(porta);
		socket.setSoTimeout(5000);
		socket.receive(packet);
		s = new String(buffer, 0, packet.getLength());
		System.out.println("From: " + packet.getAddress().getHostName() + ":" + packet.getPort() + ":" + s);
		

	}

}