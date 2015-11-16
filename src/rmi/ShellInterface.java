package rmi;

import java.io.IOException;
import java.rmi.Remote;

public interface ShellInterface extends Remote {

	void executeCommmand(String command) throws IOException, InterruptedException;
	
	String getStderr();
	
	String getStdin();
}
