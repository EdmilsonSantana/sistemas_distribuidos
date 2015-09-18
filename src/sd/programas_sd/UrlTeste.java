package sd.programas_sd;

/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 */

import java.io.*;
import java.net.*;

public class UrlTeste
{  public static void main(String[] args)
   {  try
      {

          URL url = new URL("http://www.google.com.br");

          BufferedReader in = new BufferedReader
            (new InputStreamReader(url.openStream()));


 String linha;
 while( (linha = in.readLine()) != null)
   System.out.println(linha);

      }
      catch (IOException e)
      {  System.out.println("Error" + e);
      }
   }
}
