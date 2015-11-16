package rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ShellClient {
	private Scanner input = new Scanner(System.in);
	private RemoteShell shell;

	public ShellClient() throws RemoteException {

		// shell = (ShellInterface)
		// Naming.lookup("rmi://192.168.0.31:1099/Shell");
		shell = new RemoteShell();
		commandLine();

	}

	public void commandLine() {
		String command = "";
		new ReadShellStderr(shell);
		new ReadShellStdin(shell);
		do {
			System.out.print("<SD:>");
			command = input.next();
			try {
				shell.executeCommmand("cmd /c ".concat(command));
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		} while (Boolean.TRUE);
	}

	private class ReadShellStdin extends Thread {
		private RemoteShell shell;

		public ReadShellStdin(RemoteShell shell) {
			this.shell = shell;
			start();
		}

		@Override
		public void run() {
			while (Boolean.TRUE) {
				System.out.print(shell.getStdin());
				if ( !shell.getStdin().isEmpty() ) {
					shell.setStdin("");
				}
			}
		}

	}

	private class ReadShellStderr extends Thread {
		private RemoteShell shell;

		public ReadShellStderr(RemoteShell shell) {
			this.shell = shell;
			start();
		}

		@Override
		public void run() {
			while (Boolean.TRUE) {
				System.out.print(shell.getStderr());
				if ( !shell.getStderr().isEmpty() ) {
					shell.setStderr("");
				}
			}
		}

	}
	public static void main(String[] args) throws RemoteException {
		ShellClient client = new ShellClient();
	}
}
