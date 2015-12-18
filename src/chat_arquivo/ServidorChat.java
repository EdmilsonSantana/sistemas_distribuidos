/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 * @author EdmilsonS
 */

package chat_arquivo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;

/* TODO: Decrementar quantidade de usuários quando um usuario sair, fazer um login */
public class ServidorChat {

	public static void main(String[] args) {

		int i = 1;
		try {
			ServerSocket serverSocket = new ServerSocket(6789);
			Collection<OutputStream> usuarios = new ArrayList<OutputStream>();
			for (;;) {
				Socket incoming = serverSocket.accept();
				System.out.println("Spawning " + i);
				usuarios.add(incoming.getOutputStream());
				new ThreadedEchoHandler(incoming, usuarios, i).start();
				i++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class ThreadedEchoHandler extends Thread {

	private Collection<OutputStream> usuarios;

	private int counter;

	private Socket incoming;

	public ThreadedEchoHandler(Socket i, Collection<OutputStream> lista, int c) {
		usuarios = lista;
		counter = c;
		incoming = i;
	}

	public void run() {

		try {
			DataInputStream in;
			DataOutputStream out;
			in = new DataInputStream(incoming.getInputStream());
			out = new DataOutputStream(incoming.getOutputStream());
			String apelido;
			boolean valido = true;
			boolean entrou = true;
			do {
				in.readBoolean();
				apelido = in.readUTF();
				apelido = apelido.replaceAll("[^(\\x28-\\x7F)]", "").trim();
				System.out.println(apelido);
				if ("BYE".equals(apelido)) {
					entrou = false;
					break;
				}
				valido = validaLogin(apelido);
				out.writeBoolean(valido);
			} while (!valido);

			if (entrou) {

				for (OutputStream usuario : usuarios) {
					DataOutputStream outData = new DataOutputStream(usuario);
					outData.writeBoolean(Boolean.FALSE);
					outData.writeUTF(apelido + " entrou!!");
				}

				boolean done = false;
				while (!done) {
					boolean tipoEntrada = in.readBoolean();
					String str = in.readUTF();
					System.out.println(incoming + " - " + apelido + " - " + str);

					byte[] buffer = new byte[1024];
					int bytesRead;
					// Informa o tipo de entrada
					for (OutputStream usuario : usuarios) {
						DataOutputStream outData = new DataOutputStream(usuario);
						outData.writeBoolean(tipoEntrada);
						if (!tipoEntrada) {

							if (str == null) done = true;
							else {
								outData.writeUTF(apelido + ":" + str);

								if (str.trim().equals("BYE")) done = true;
							}
						} else {
							outData.writeUTF(str);
						}
					}
					if (tipoEntrada) {
						Long tamanho = in.readLong();
						out.writeLong(tamanho);
						System.out.println("Iniciando leitura no servidor");
						int bytesTotaisLido = 0;
						//FileOutputStream file = new FileOutputStream(new File("C:\\Users\\EdmilsonS\\Desktop\\".concat(str)));
						while ( bytesTotaisLido < tamanho) {
							System.out.println("Disponiveis: " + incoming.getInputStream().available());
							bytesRead = incoming.getInputStream().read(buffer);					
							System.out.println("Lidos: " + bytesRead);
							bytesTotaisLido += bytesRead;
							for ( OutputStream usuario : usuarios) {
								usuario.write(buffer, 0, bytesRead);
								usuario.flush();
							}		
						}
					//	file.close();

						System.out.println("Acabou a leitura de um arquivo");
					}

				}

				for (OutputStream usuario : usuarios) {
					DataOutputStream outData = new DataOutputStream(usuario);
					outData.writeBoolean(Boolean.FALSE);
					outData.writeUTF(apelido + " saiu!!");
				}
			}

			

			if (usuarios.contains(incoming.getOutputStream())) {
				usuarios.remove(incoming.getOutputStream());
			}
			incoming.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private boolean validaLogin(String login) {

		if ((login == null) || (login.isEmpty())) {
			return false;
		}
		return true;
	}

}
