
package rmi.repositorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Repositorio extends UnicastRemoteObject implements RepositorioInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4940829778536142684L;

	private File[] arquivos;

	private final String diretorio = "\\repositorio";

	public Repositorio() throws RemoteException {
		String workingDirectoryPath = System.getProperty("user.dir");
		File workingDirectory = new File(workingDirectoryPath.concat(diretorio));
		arquivos = workingDirectory.listFiles();
	}

	public int getQuantidadeDeArquivos() {

		return arquivos.length;
	}

	public static void main(String[] args) throws IOException {

		Repositorio repo = new Repositorio();
		repo.getBytesArquivoDoDiretorio("arquivo.txt");
		System.out.println(repo.getListaDeArquivosDiretorio());
	}

	public byte[] getBytesArquivoDoDiretorio(String nomeArquivo) throws IOException {

		for (File arquivo : arquivos) {
			if (arquivo.getName().equals(nomeArquivo)) {
				return buscarArquivoNoDiretorio(arquivo);
			}
		}
		throw new FileNotFoundException();
	}

	public String getListaDeArquivosDiretorio() {

		StringBuilder builder = new StringBuilder();
		for (File arquivo : arquivos) {
			builder.append(arquivo.getName());
			builder.append("\n");

		}
		return builder.toString();
	}

	private byte[] buscarArquivoNoDiretorio(File arquivo) throws IOException {

		FileInputStream inputStream = new FileInputStream(arquivo);
		byte[] buffer = new byte[(int) arquivo.length()];
		inputStream.read(buffer);
		inputStream.close();
		return buffer;
	}
}
