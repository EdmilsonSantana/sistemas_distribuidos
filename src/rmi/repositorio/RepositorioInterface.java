package rmi.repositorio;

import java.io.IOException;
import java.rmi.Remote;

public interface RepositorioInterface extends Remote {

	byte[] getBytesArquivoDoDiretorio(String nomeArquivo) throws IOException;
	
	String getListaDeArquivosDiretorio();
	
	int getQuantidadeDeArquivos();
}
