package commands;

import java.util.ListIterator;

import algorithms.ShuntingYard;
import server.MySerialServer;
import server.Server;
import server.SimulatorClientHandler;

public class OpenServerCommand implements Command {
	private static Server server = null;
	private int port;
	private int Hz;

	public static void closeServer() {
		if (server != null)
			server.stop();
	}

	@Override
	public void execute() throws Exception {
		server = new MySerialServer();
		server.start(port, new SimulatorClientHandler(Hz));
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		port = (int) Double.parseDouble(ShuntingYard.calc(it.next()).calculate());
		Hz = (int) Double.parseDouble(ShuntingYard.calc(it.next()).calculate());
	}
}
