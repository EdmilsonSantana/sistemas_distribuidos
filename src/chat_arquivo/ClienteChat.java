
package chat_arquivo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
			out.writeBoolean(Boolean.FALSE);
			out.writeUTF(mensagem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(arquivo);
			byte[] buffer = new byte[1024];
			int bytesRead;
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeBoolean(Boolean.TRUE);
			out.writeUTF(arquivo.getName());
			out.writeLong(arquivo.length());
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				socket.getOutputStream().write(buffer, 0, bytesRead);
				socket.getOutputStream().flush();
			}
			System.out.println("Arquivo " + arquivo.getName() + "enviado");
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	class LeituraMensagemThread extends Thread {

		private Socket socket;

		private String downloadPath = System.getProperty("user.dir")+"\\src\\chat_arquivo\\arquivos recebidos\\";
		
		public LeituraMensagemThread(Socket socket) {
			this.socket = socket;
			start();
		}

		@Override
		public void run() {

			DataInputStream in;
			try {
				in = new DataInputStream(socket.getInputStream());
				while (Boolean.TRUE) {
					try {
						boolean tipoEntrada = in.readBoolean();
						String msg = in.readUTF();
						if ( tipoEntrada ) {
							Long tamanho = in.readLong();
							System.out.println("Cliente iniciou a escrita");
							FileOutputStream file = new FileOutputStream(new File(downloadPath.concat(msg)));
							byte[] buffer = new byte[1024];
							int bytesRead;
							long bytesTotaisLido = 0;
							while (bytesTotaisLido < tamanho) {
								System.out.println("Disponiveis: " + socket.getInputStream().available());
								bytesRead = socket.getInputStream().read(buffer);
								System.out.println("Lidos: " + bytesRead);
								bytesTotaisLido += bytesRead;
								file.write(buffer, 0, bytesRead);
								file.flush();	

							}
							file.close();
							System.out.println("Cliente acabou a escrita");
						} else {
							ui.escreverMensagemAreaTexto(msg);
						}
						
						
					} catch (IOException e) {
					}

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	


	public static void main(String[] args) {

		ClienteChat cliente = new ClienteChat();
		//System.out.println(System.getProperty("user.dir")+"\\chat_arquivo\\arquivos recebidos");
	}
}
