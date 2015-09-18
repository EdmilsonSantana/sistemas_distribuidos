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

	public void solicitarRecurso(String host, int porta, String pathRecurso) {
		
		Socket socket = conectar(host, porta);
		DataInputStream in;
		DataOutputStream out;
		try {
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(pathRecurso);
			receberRecurso(in, pathRecurso);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void receberRecurso(DataInputStream in, String pathRecurso) {
		 String recurso;
		try {
			recurso = in.readUTF();
			recurso = parser.descriptografa(recurso, Parser.CHAVE_CRIPTOGRAFIA);
			ui.apresentarRecurso(parser.parse(recurso), pathRecurso);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	
}