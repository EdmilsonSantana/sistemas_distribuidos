package rmi;

import java.rmi.Naming;

import rmi.repositorio.Repositorio;

public class Server {
	
	public static void main(String[] arg)
	
	  { try
	    { Naming.rebind("repositorio", new Repositorio());
	    }
	    catch(Exception e)
	    { e.printStackTrace();
	    }
	  }
	  
}
