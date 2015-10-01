package udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;

public class ServidorUDP {
	
	private Collection<String> pacotes;
	private Collection<String> cabecalhos;
	
	public ServidorUDP() {
		pacotes = new ArrayList<String>();
		cabecalhos = new ArrayList<String>();
	}
	
	
	private static void receberPacote() throws SocketException {
		byte[] buffer = new byte[1024];
		String pacote;
		int cabecalho = 0;// definir limite para quantidade de numeros do
							// cabecalho,ou seja, so pode receber 10 pacotes por
							// "sessao"
		int receveidPacket = 0;
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		DatagramSocket socket = new DatagramSocket(6791);
		try {
			socket.receive(packet);
			System.out.println(packet.getAddress());
			pacote = new String(buffer, 0, packet.getLength());
			System.out.println(pacote);
			extrairPacoteACK(buffer); 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		/*
		for (int i = 0; i < 5; i++) {
			try {
				socket.receive(packet);
			
				if () {
					cabecalho++;
					dado = new String(buffer, 0, packet.getLength());
					System.out.println("From: "
							+ packet.getAddress().getHostName() + ":"
							+ packet.getPort() + ":" + dado);
				}

				if (cabecalho <= 10) {
					listaCabecalho = cabecalho;
					listaDado = dado;
					receivedPacket++;

				}
				listaReceivedPacket = recevidePacket; // para o cliente
														// saber quais
														// pacotes precisa
														// reenviar
				System.out.println("Pacote recebido com sucesso"
						+ receivedPacket);

			} catch (InterruptedIOException e) {
				System.out.println("TimeOut: " + e);
				System.out
						.println("Tempo de resposta excedido, reenvie os pacotes perdidos listados a seguir"
								+ listaReceivedPacket);
			}
			*/
	}
	public static void main(String[] args) throws SocketException {
		receberPacote();
	}
	private static byte[] extrairPacoteACK(byte[] pacote) {
		Byte ackId = pacote[0];
		System.out.println(pacote);
		return null;
	}
} 