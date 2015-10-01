package udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;

import httplike.arquivos.Parser;

public class ClienteUDP {

	private Parser parser;
	private Collection<String> confirmados;

	public ClienteUDP() {
		parser = new Parser();
		confirmados = new ArrayList<String>();
	}

	public void enviarPacote(String host, int porta, byte[] dados) throws IOException {
		InetAddress end = InetAddress.getByName(host);
		DatagramPacket packet = new DatagramPacket(dados, dados.length, end, porta);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		socket.close();
	}

	public void enviarArquivo(String path, String host, int porta) {
		try {
			Collection<String> arquivoLido = parser.lerArquivoPorLinha(path);
			Object[] arquivo = arquivoLido.toArray();
			for (int sequencia = 1; sequencia < arquivo.length; sequencia++) {
				String linha = String.valueOf(arquivo[sequencia - 1]); 
				byte[] pacote = adicionarCabecalhoPacote(linha.getBytes(), sequencia);
				enviarPacote(host, porta, pacote);
				/*
				try {
					receberPacote(porta);
				} catch (InterruptedIOException e) {
					String ultimoPacote = (String) confirmados.toArray()[confirmados.size() - 1];
					sequencia = Integer.valueOf(ultimoPacote).intValue();
				} */

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void receberPacote(int porta) throws IOException {
		byte[] buffer = new byte[1024];
		String pacote;
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		DatagramSocket socket = new DatagramSocket(porta);

		socket.setSoTimeout(5000);
		try {
			socket.receive(packet);
			pacote = new String(buffer, 0, packet.getLength());
			confirmados.add(pacote);
			System.out.println("ACK: " + pacote + "Confirmado!");
		} catch (InterruptedIOException e) {
			System.out.println("Falha! Último ACK: " + (String) confirmados.toArray()[confirmados.size() - 1]);
			throw e;
		}
	}

	public byte[] adicionarCabecalhoPacote(byte[] pacote , int valor) {
		byte[] sequence = Integer.toBinaryString(valor).getBytes();
		byte[] pacoteComCabecalho = new byte[pacote.length + sequence.length];
		System.arraycopy(sequence, 0, pacoteComCabecalho, 0, sequence.length);
		System.arraycopy(pacote, 0, pacoteComCabecalho, sequence.length, pacote.length);
		return pacoteComCabecalho;
	}


}