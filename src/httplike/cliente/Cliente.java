package httplike.cliente;

import java.io.*;
import java.net.*;

public class Cliente {
	
	private static Cliente cliente = null;

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
			 System.out.println(recurso);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		 
	}
	
	public static Cliente getInstancia() {
		if ( cliente == null ) {
			cliente = new Cliente();
		}
		return cliente;
	}
}