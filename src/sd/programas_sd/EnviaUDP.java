package sd.programas_sd;

import java.io.*;   import java.net.*;
public class EnviaUDP
{ public static void main(String[] args)throws Exception
  { InetAddress end = InetAddress.getByName("192.168.97.218");
    String s = "Saudacoes";
    byte[] msg = new byte[s.length()];
    msg = s.getBytes();
    DatagramPacket packet = new DatagramPacket(msg,
                            msg.length, end, 1234);
    DatagramSocket socket = new DatagramSocket();
    socket.send(packet);
    socket.close();
  }
}
