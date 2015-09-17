package httplike.cliente;

import java.io.*;
import java.net.*;

import httplike.arquivos.Parser;
import httplike.gui.ClienteUI;

public class Cliente {
	
	private static Parser parser = new Parser();
	
	private ClienteUI ui;
	
	public Cliente(ClienteUI ui) {
		
		this.ui = ui;
		
	}

	
	private Socket conectar(String host, int porta) {
		Socket socket;
		
		try {
			socket = new Socket(host, porta);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return socket;
	}

	public void solicitarRecurso(String host, int porta, String recurso) {
		
		Socket socket = conectar(host, porta);
		DataInputStream in;
		DataOutputStream out;
		try {
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(recurso);
			receberRecurso(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void receberRecurso(DataInputStream in) {
		 String recurso;
		try {
			recurso = in.readUTF();
			apresentarRecurso(parser.parse(recurso));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	private void apresentarRecurso(String[][] conteudo) {
		for( int i = 0; i < conteudo[1].length; i++ ) {
			if( "italico".equals(conteudo[1][i])) {
				ui.novoTextoItalico(conteudo[0][i]);
			} else if ( "negrito".equals(conteudo[1][i]) ) {
				ui.novoTextoNegrito(conteudo[0][i]);
			}
		}
	}
	
	
}