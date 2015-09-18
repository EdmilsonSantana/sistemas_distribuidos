package httplike.arquivos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {

	public static final int CHAVE_CRIPTOGRAFIA = 3;
	
	public String lerArquivo(String path) throws IOException {
		FileInputStream stream = new FileInputStream(path);
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String arquivo = br.readLine();
		String str;
		while (arquivo != null) {
			str = br.readLine();
			if (str == null) {
				break;
			}
			arquivo = arquivo + str;
		}
		br.close();
		return arquivo;
	}

	public String[][] parse(String recurso) {
		
		String[][] conteudo = new String[2][];
		String delimitador = "[-]";
		String tags = recurso.replaceAll(">.*?</.*?>", "-");
		tags = tags.replaceAll("<", "-").trim();
		String texto = recurso.replaceAll("<.*?>", "-");
		texto = texto.replaceAll("--", "-");
		tags = tags.replaceAll("--", "-");
		conteudo[0] = texto.split(delimitador);
		conteudo[1] = tags.split(delimitador);
		
		return conteudo;

	}
	
	public String criptografa(String recurso, int chave) {
		char[] caracteres  = recurso.toCharArray();
		for ( int i = 0; i < caracteres.length; i++ ) {
			int decimal = (int)caracteres[i];
			decimal += chave;
 			if ( decimal > 255 ) {
 				decimal = decimal%255 - 1;
 			}
			caracteres[i] = (char)decimal;
		}
		return String.copyValueOf(caracteres);
	}
	
	public String descriptografa(String recurso, int chave) {
		
		char[] caracteres  = recurso.toCharArray();
		for ( int i = 0; i < caracteres.length; i++ ) {
			int decimal = (int)caracteres[i];
			decimal -= chave;
			if ( decimal < 0 ) {
				decimal = ( (255 -  decimal) + 1 ) ;
			}
			caracteres[i] = (char)((int)caracteres[i] - chave);
		}
		return String.copyValueOf(caracteres);
	}

}
