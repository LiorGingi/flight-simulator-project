package commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import interpreter.BindingTable;

public class ConnectCommand implements Command, Observer {
//	private DisconnectCommand c;
	private PrintWriter out;
	private Socket server;

	public ConnectCommand(DisconnectCommand c) {
//		this.c = c;
		c.addObserver(this);
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		// TODO ***Written by Lior: When connecting to a server, set an outputStream
		// PrintWriter to BindingTable.***
		if (args.length != 3)
			throw new Exception("wrong amount of arguments");
		else {
			String address = args[index];
			int port = Integer.parseInt(args[index + 1]);
			try {
				server = new Socket(address, port);
				this.out = new PrintWriter(server.getOutputStream());
				BindingTable.setServerOutStream(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 2;
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		diconnectFromServer();
	}

	private void diconnectFromServer() {
		BindingTable.setServerOutStream(null);
		if (out != null) {
			out.write("bye");
			out.close();
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
