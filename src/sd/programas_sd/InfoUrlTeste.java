package sd.programas_sd;

/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 */

import java.io.*;
import java.net.*;

public class InfoUrlTeste
{  public static void main(String[] args)
   {
       try
      {
          InfoURL a = new InfoURL ();
          a.obtemInfo("http://www.google.com.br");

      }
      catch (IOException e)
      {  System.out.println("Error" + e);
      }


   }
}
