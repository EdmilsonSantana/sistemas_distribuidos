package cliente;

import java.io.*;
import java.net.*;

public class Cliente {
	
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
			
		} catch (IOException e) {
			
		}
	}
	
	private void receberRecurso(InputStream in) {
		 //byte[] objectAsByte = new byte[socket.getReceiveBufferSize()];
	}
}