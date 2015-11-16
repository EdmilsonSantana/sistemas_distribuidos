
package rmi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteShell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 28933328896264764L;

	private Runtime runtime;

	private Process process;

	private String stdin;

	private String stderr;

	public RemoteShell() {
		runtime = Runtime.getRuntime();
		setStdin("");
		setStderr("");
	}
	public void executeCommmand(String command) throws IOException, InterruptedException {
		File dir =  new File("/");
		process = runtime.exec(command, null, dir);
		processData();
	}

	private void processData() throws InterruptedException {

		StringBuffer stdinBuffer = new StringBuffer();
		StringBuffer stderrBuffer = new StringBuffer();

		InputStream stdinStream = process.getInputStream();
		InputStream stderrStream = process.getErrorStream();

		new InputStreamHandler(stdinBuffer, stdinStream);
		new InputStreamHandler(stderrBuffer, stderrStream);

		process.waitFor();

		setStdin(stdinBuffer.toString());
		System.out.print(getStdin());
		setStderr(stderrBuffer.toString());
	}

	private class InputStreamHandler extends Thread {

		private InputStream m_stream;

		private StringBuffer m_captureBuffer;

		InputStreamHandler(StringBuffer captureBuffer, InputStream stream) {
			m_stream = stream;
			m_captureBuffer = captureBuffer;
			start();
		}

		public void run() {

			try {
				
				int nextChar;
				while ((nextChar = m_stream.read()) != -1)
					m_captureBuffer.append((char) nextChar);
			} catch (IOException ioex) {
				System.err.println(ioex);
			}
		}
	}

	public String getStdin() {

		return stdin;
	}

	public void setStdin(String stdin) {

		this.stdin = stdin;
	}
	
	public String getStderr() {
		
		return stderr;
	}

	public void setStderr(String stderr) {

		this.stderr = stderr;
	}
}
