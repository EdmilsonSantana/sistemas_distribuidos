package sd.programas_sd;

import java.io.*;
import java.net.*;
class Multi_server
{ public static void main(String[] args)
  { try
    { InetAddress grupo = InetAddress.getByName("227.1.2.3");
      MulticastSocket s = new MulticastSocket(1234);
      s.joinGroup(grupo);
      String sMsg = "Saudacoes";
      byte[] msg = sMsg.getBytes();
      DatagramPacket dOut = new DatagramPacket(msg, msg.length,grupo, 1234);
      s.send(dOut);
          }
          catch(Exception e)
          {
            System.out.println("Erro: " + e);
          }
        }
      }
