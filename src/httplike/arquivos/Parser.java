package httplike.arquivos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;

public class Parser {

	private ArrayList<String> tags = new ArrayList<>();
	
	public Parser() {
		inicializarTags();
		
	}
	
	public String lerArquivo(String path) throws IOException {
		FileInputStream stream = new FileInputStream(path);
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br =  new BufferedReader(reader);
		String arquivo = br.readLine();
		String str;
		while ( arquivo != null ) {
			str = br.readLine();
			if ( str == null ) {
				break;
			}
			arquivo =  arquivo + str;
			System.out.println(arquivo);
		}
		br.close();
		return arquivo;
	}
	

	
	private void inicializarTags() {
		tags.add("italico");
		tags.add("negrito");
	} 
	
	public String getTags(int index) {
		return tags.get(index);
	}
	
	public String[][] parse(String recurso) {
		String[][] conteudo = new String[2][];
		String delimitador = "[-]";
		String tags = recurso.replaceAll(">.*?</.*?>", "-");
		tags = tags.replaceAll("<", "-").trim();
		String texto  = recurso.replaceAll("<.*?>", "-");
		texto = texto.replaceAll("--", "-");
		tags = tags.replaceAll("--", "-");
		
		conteudo[0] = texto.split(delimitador);
		conteudo[1] = tags.split(delimitador);
		return conteudo;
		
	}
	
	
	
}
