package sd.programas_sd;

import java.io.*;
import java.net.*;
import java.util.*;
public class InfoURL
{ public static void obtemInfo(String endereco)
                throws MalformedURLException, IOException
  { URL url = new URL(endereco);
    URLConnection con = url.openConnection();
    System.out.println("Tipo: " + con.getContentType());
    System.out.println("Tamanho: " + con.getContentLength());
    System.out.println("Modificado em: " +
                            new Date(con.getLastModified()));
    System.out.println("Válido até: " + con.getExpiration());
    System.out.println("Codificação:" + con.getContentEncoding());
    System.out.println("Primeiras 5 linhas:");

    BufferedReader in = new BufferedReader
  (new InputStreamReader(con.getInputStream()));

    for(int i=0; i<5; i++) System.out.println(in.readLine());
  }
}

