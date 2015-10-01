package udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;

import httplike.arquivos.Parser;

public class ClienteUDP {

	private Parser parser;
	private Collection<String> confirmados;
	private static DatagramSocket socket;
	private static final int PORTA = 6792;
	private static ClienteUDP clienteUDP = null;
	
	private ClienteUDP() {
		parser = new Parser();
		confirmados = new ArrayList<String>();
		try {
			socket = new DatagramSocket(PORTA);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ClienteUDP getInstancia() {
		if ( clienteUDP == null ) {
			clienteUDP = new ClienteUDP();
		}
		return clienteUDP;
	}

	public void enviarPacote(String host, int porta, byte[] dados) throws IOException {
		InetAddress end = InetAddress.getByName(host);
		DatagramPacket packet = new DatagramPacket(dados, dados.length, end, porta);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		socket.close();
	}

	public void enviarArquivo(String path, String host, int porta) {
		byte[] pacote;
		int tentativas = 0;
		boolean falha = false;
		try {
			Collection<String> arquivoLido = parser.lerArquivoPorLinha(path);
			Object[] arquivo = arquivoLido.toArray();
			for (int sequencia = 1; sequencia <= arquivo.length; sequencia++) {
				String linha = String.valueOf(arquivo[sequencia - 1]); 
				pacote = adicionarCabecalhoPacote(linha.getBytes(), sequencia);
				enviarPacote(host, porta, pacote);
				System.out.println("Enviando pacote " + sequencia);
				System.out.println("Conteúdo do pacote: " + linha);
				try {
					receberPacote(porta);
				} catch (InterruptedIOException e) {
					if ( tentativas == 5 ) {
						System.out.println("Não foi possível contatar o Servidor.");
						break;
					}
					int index = (confirmados.size() == 0) ? 0: confirmados.size() - 1;
					if( index == 0 ) {
						sequencia = 0;
					} else {
						String ultimoPacote = (String) confirmados.toArray()[index];
						System.out.println("Falha! Último ACK: " + ultimoPacote);
						sequencia = Integer.valueOf(ultimoPacote).intValue();
					}
				} 
			}
			pacote = new String("").getBytes();
			enviarPacote(host, porta, adicionarCabecalhoPacote(pacote, 0));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void receberPacote(int porta) throws IOException {
		byte[] buffer = new byte[1024];
		String pacote;
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		socket.setSoTimeout(5000);
		try {
			socket.receive(packet);
			pacote = new String(buffer, 0, packet.getLength());
			confirmados.add(pacote);
			System.out.println(pacote + " Confirmado!");
		} catch (InterruptedIOException e) {
			throw e;
		}
	}

	public byte[] adicionarCabecalhoPacote(byte[] pacote , int valor) {
		
		byte[] sequence = Integer.valueOf(valor).toString().getBytes();
		byte[] pacoteComCabecalho = new byte[pacote.length + sequence.length];
		System.arraycopy(sequence, 0, pacoteComCabecalho, 0, sequence.length);
		System.arraycopy(pacote, 0, pacoteComCabecalho, sequence.length, pacote.length);
		return pacoteComCabecalho;
	}


}