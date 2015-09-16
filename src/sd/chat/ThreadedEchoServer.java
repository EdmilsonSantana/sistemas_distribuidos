/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 * @author EdmilsonS
 */

package sd.chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

/* TODO: Decrementar quantidade de usuários quando um usuario sair, fazer um login */
public class ThreadedEchoServer {

	public static void main(String[] args) {

		int i = 1;
		try {
			ServerSocket serverSocket = new ServerSocket(8150);
			Collection<PrintWriter> usuarios = new ArrayList<PrintWriter>();
			for (;;) {
				Socket incoming = serverSocket.accept();
				System.out.println("Spawning " + i);
				PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
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

	private Collection<PrintWriter> usuarios;

	private int counter;

	private Socket incoming;

	private PrintWriter out;

	public ThreadedEchoHandler(PrintWriter out, Socket i, Collection<PrintWriter> lista, int c) {
		usuarios = lista;
		counter = c;
		incoming = i;
		this.out = out;
	}

	public void run() {

		try {
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(incoming.getInputStream(), "UTF-8"));

			out.println("Hello! Enter BYE to exit.");
			String apelido;
			boolean valido = true;
			boolean entrou = true;
			do {
				out.println("Apelido: ");
				apelido = in.readLine();
				apelido = apelido.replaceAll("[^(\\x28-\\x7F)]", "").trim();

				if ("BYE".equals(apelido)) {
					entrou = false;
					break;
				}
				valido = validaLogin(apelido);
				if (!valido) {
					out.println("Apelido invalido!");
				}
			} while (!valido);
			
			if (entrou) {
				
				for (PrintWriter usuario : usuarios) {
					usuario.println(apelido + " entrou!!");
				}

				boolean done = false;
				while (!done) {
					String str = in.readLine();
					System.out.println(incoming + " - " + apelido + " - " + str);
					for (PrintWriter usuario : usuarios) {

						if (str == null) done = true;
						else {
							usuario.println(apelido + "(" + counter + "):" + str);

							if (str.trim().equals("BYE")) done = true;
						}
					}
				}

				for (PrintWriter usuario : usuarios) {
					usuario.println(apelido + " saiu!!");
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
