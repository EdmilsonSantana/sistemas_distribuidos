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
import java.util.regex.Pattern;

/* TODO: Decrementar quantidade de usuários quando um usuario sair, fazer um login */
public class ServidorChat {

	public static void main(String[] args) {

		int i = 1;
		try {
			ServerSocket serverSocket = new ServerSocket(6789);
			Collection<DataOutputStream> usuarios = new ArrayList<DataOutputStream>();
			for (;;) {
				Socket incoming = serverSocket.accept();
				System.out.println("Spawning " + i);
				DataOutputStream out = new DataOutputStream(incoming.getOutputStream());
				usuarios.add(out);
				new ThreadedEchoHandler(out, incoming, usuarios, i).start();
				i++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class ThreadedEchoHandler extends Thread {

	private Collection<DataOutputStream> usuarios;

	private int counter;

	private Socket incoming;

	private DataOutputStream out;

	public ThreadedEchoHandler(DataOutputStream out, Socket i, Collection<DataOutputStream> lista, int c) {
		usuarios = lista;
		counter = c;
		incoming = i;
		this.out = out;
	}

	public void run() {

		try {
			DataInputStream in;
			in = new DataInputStream(incoming.getInputStream());
			String apelido;
			boolean valido = true;
			boolean entrou = true;
			do {
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
				
				for (DataOutputStream usuario : usuarios) {
					usuario.writeUTF(apelido + " entrou!!");
				}

				boolean done = false;
				while (!done) {
					String str = in.readUTF();
					System.out.println(incoming + " - " + apelido + " - " + str);
					for (DataOutputStream usuario : usuarios) {

						if (str == null) done = true;
						else {
							usuario.writeUTF(apelido + "(" + counter + "):" + str);

							if (str.trim().equals("BYE")) done = true;
						}
					}
				}

				for (DataOutputStream usuario : usuarios) {
					usuario.writeUTF(apelido + " saiu!!");
				}
			}

			incoming.close();

			if (usuarios.contains(out)) {
				usuarios.remove(out);
			}

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
