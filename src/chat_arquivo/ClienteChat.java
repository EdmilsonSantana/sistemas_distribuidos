
package chat_arquivo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteChat {

	private ChatUI ui;

	private Socket socket;

	public ClienteChat() {
		ui = new ChatUI(this);
		conectar("127.0.0.1", 6789);
	}

	private Socket conectar(String host, int porta) {

		try {
			socket = new Socket(host, porta);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return socket;
	}

	public void enviarMensagem(String mensagem) {

		DataOutputStream out;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(mensagem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receberMensagem() {

	}

	public boolean autenticarEntradaNoChat(String apelido) {

		enviarMensagem(apelido);
		DataInputStream in;
		boolean apelidoValido = Boolean.TRUE;
		try {
			in = new DataInputStream(socket.getInputStream());
			apelidoValido = in.readBoolean();
			if (apelidoValido) {
				new LeituraMensagemThread(this.socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return apelidoValido;

	}

	public void enviarArquivoDoDiretorio(File arquivo) {

		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(arquivo);
			byte[] buffer = new byte[(int) arquivo.length()];
			inputStream.read(buffer);
			inputStream.close();
			socket.getOutputStream().write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class LeituraMensagemThread extends Thread {

		private Socket socket;

		public LeituraMensagemThread(Socket socket) {
			this.socket = socket;
			start();
		}

		@Override
		public void run() {

			DataInputStream in;
			try {
				in = in = new DataInputStream(socket.getInputStream());
				while (Boolean.TRUE) {
					try {
						String msg = in.readUTF();
						ui.escreverMensagemAreaTexto(msg);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	class LeituraArquivoThread extends Thread {

		private Socket socket;

		private String downloadPath = "C:\\MyDownloads\\";

		public LeituraArquivoThread(Socket socket) {
			this.socket = socket;
			start();
		}

		@Override
		public void run() {

		}

	}

	public static void main(String[] args) {

		ClienteChat cliente = new ClienteChat();

	}
}
