package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class MySerialServer implements Server {
	private volatile boolean stop;
	private ClientHandler ch;
	private int port;

	public MySerialServer() {
		this.stop = false;
	}

	@Override
	public void start(int port, ClientHandler c) {
		this.port = port;
		this.ch = c;

		new Thread(() -> runServer()).start();
	}

	@Override
	public void stop() {
		stop = true;
	}

	private void runServer() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(1000);
			Socket aClient = null;
			while (!stop) {
				try {
					aClient = server.accept();// awaits for client.
					try {
						ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());

					} catch (IOException e) {// handles exception thrown from handleClient
						e.printStackTrace();
					}
				} catch (SocketTimeoutException e) {
					if (server != null)
						server.close();
					this.runServer();
				} finally {
					if (aClient != null)
						aClient.close();
					TimeUnit.MILLISECONDS.sleep(100);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {// closing the server
			try {
				if (server != null) {///
					server.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
