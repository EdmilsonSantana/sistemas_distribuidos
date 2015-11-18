package rmi.repositorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClienteRepositorio  {

	private RepositorioInterface repositorio;
	private Scanner input;
	public ClienteRepositorio() {
		try {
			repositorio = (RepositorioInterface)Naming.lookup("rmi://192.168.0.31:1099/repositorio");
		} catch ( RemoteException | MalformedURLException | NotBoundException e) {
			throw new RuntimeException(e);
		}
		input = new Scanner(System.in);
	}
	
	public void commandLine() {
		String command = "";
		do {
			System.out.print("<SD:>");
			command = input.nextLine();
			try {
				processCommand(command);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} while (Boolean.TRUE);
	}
	
	public static void main(String[] args) {

		ClienteRepositorio c = new ClienteRepositorio();
		c.commandLine();
	}
	public void processCommand(String command) throws IOException {
		String[] parametros = command.split(" ");
		if ( "ls".equals(parametros[0]) ) {
			System.out.println(repositorio.getListaDeArquivosDiretorio());
		} else if ( "pull".equals(parametros[0]) ) {
			byte[] conteudoArquivo = repositorio.getBytesArquivoDoDiretorio(parametros[1]);
			salvarArquivo(conteudoArquivo, parametros[2] + parametros[1]);
			System.out.println("Arquivo salvo no diretorio: " + parametros[2]);
		} else if ( "length".equals(parametros[0]) ) {
			System.out.println(repositorio.getQuantidadeDeArquivos());
		} else {
			System.out.println("Comando inválido!");
		}
	}
	
	public void salvarArquivo(byte[] conteudoArquivo, String path) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(new File(path));
		outputStream.write(conteudoArquivo);
		outputStream.flush();
		outputStream.close();
	}
}
