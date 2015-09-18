package sd.programas_sd;

import java.io.*;

public class EntradaTeclado extends StreamTokenizer
  {static StreamTokenizer in = new StreamTokenizer(new InputStreamReader(System.in));
   public EntradaTeclado ()
  {  super(new InputStreamReader (System.in)); }

  static public double ler_double ()  //método para ler double
   {  double entrada;
      try
      { in.nextToken();
        entrada = (double) in.nval;
      }
      catch(Exception e)
      { System.out.println("Erro na leitura do dado.");
        entrada = 0;
      }
       return entrada;
   }
   static public String ler_string () // método para ler string
   {  String entrada;
      try
      { in.nextToken();
        entrada = new String (in.sval);
      }
      catch(Exception e)
       { System.out.println("Erro na leitura do dado.");
        entrada = "";
      }
       return entrada;
    }
  }
