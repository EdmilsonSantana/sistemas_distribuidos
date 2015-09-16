
package httplike.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import httplike.arquivos.Parser;

public class Servidor {

	private static int PORTA = 6791;

	private static ServerSocket serverSocket;

	private static Parser parser = new Parser();

	private Servidor() {
		try {
			serverSocket = new ServerSocket(PORTA);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		accept();
	}

	private void accept() {

		try {
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket);
				new ServidorThread(socket).start();
			}	
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public class ServidorThread extends Thread {

		private Socket socket;

		public ServidorThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {

			try {
				String recurso;
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				while (Boolean.TRUE) {
					recurso = in.readUTF();
					recurso = recurso.replaceAll("[^(\\x28-\\x7F)]", "").trim();
					System.out.println(recurso);
					buscarRecurso(recurso, out);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			super.run();
		}

		public void buscarRecurso(String caminhoRecurso, DataOutputStream cliente) {
			
			try {
				String arquivo = parser.lerArquivo(caminhoRecurso);
				System.out.println(arquivo);
				cliente.writeUTF(arquivo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Servidor getInstancia() {

		return new Servidor();
	}

	public static void main(String[] args) {

		Servidor.getInstancia();
	}

}