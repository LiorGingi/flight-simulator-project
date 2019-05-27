package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySerialServer implements Server {
	private volatile boolean stop;
	private ClientHandler ch;
	private int port;
	private ExecutorService es;

	public MySerialServer() {
		this.stop = false;
		es = Executors.newFixedThreadPool(5);
	}

	@Override
	public void start(int port, ClientHandler c) {
		this.port = port;
		this.ch = c;

		es.execute(() -> runServer());
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
//					TimeUnit.MILLISECONDS.sleep(100);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {// closing the server
			try {
				es.shutdown();
				if (server != null) {
					server.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
