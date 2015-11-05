package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

class Multi_client {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			InetAddress grupo = InetAddress.getByName("227.1.2.3");
			MulticastSocket s = new MulticastSocket(1234);
			s.joinGroup(grupo);
			byte[] buf = new byte[1000];
			System.out.println("Qual o seu apelido ?");
			String apelido = scanner.nextLine();
			LeituraThread leitura = new LeituraThread(s);
			leitura.start();
			String sMsg;
			String pacote;
			do {
				sMsg = scanner.nextLine();
				pacote = apelido + " - " + sMsg;
				byte[] msg = pacote.getBytes();
				DatagramPacket dOut = new DatagramPacket(msg, msg.length,
						grupo, 1234);
				s.send(dOut);
				if ("EXIT".equals(sMsg)) {
					leitura.stop();
					break;
				}
			} while (!"EXIT".equals(sMsg));
			s.leaveGroup(grupo);
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}
}

class LeituraThread extends Thread {
	private MulticastSocket s;
	public LeituraThread(MulticastSocket s) {
		this.s = s;
	}
	
	public void run() {
		
		byte[] buf = new byte[1000];
		
		try {
			while ( Boolean.TRUE ) {
				DatagramPacket dIn = new DatagramPacket(buf, buf.length);
				s.receive(dIn);
				String str = new String(buf, 0, dIn.getLength());
				System.out.println(str);
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
