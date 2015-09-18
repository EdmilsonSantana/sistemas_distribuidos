package sd.programas_sd;
import java.io.*;
import java.net.*;
class Multi
{ public static void main(String[] args)
  { try
    { InetAddress grupo = InetAddress.getByName("227.1.2.3");
      MulticastSocket s = new MulticastSocket(1234);
      s.joinGroup(grupo);
      String sMsg = "Saudacoes";
      byte[] msg = sMsg.getBytes();
      DatagramPacket dOut = new DatagramPacket(msg, msg.length,grupo, 1234);
      s.send(dOut);
      byte[] buf = new byte[1000];
            DatagramPacket dIn = new DatagramPacket(buf, buf.length);
            s.receive(dIn);
            System.out.write(buf, 0, dIn.getLength());
            s.leaveGroup(grupo);
          }
          catch(Exception e)
          {
            System.out.println("Erro: " + e);
          }
        }
      }
