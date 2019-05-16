package commands;

import java.util.Observable;
import java.util.Observer;

import server.MySerialServer;
import server.Server;
import server.SimulatorClientHandler;

public class OpenDataServerCommand implements Command, Observer {
	int port;
	int frequency;
	private Server server;

	public OpenDataServerCommand(DisconnectCommand c) {
		this.port = 0;
		this.frequency = 0;
		c.addObserver(this);
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		//index -> port, index+1 -> frequency
		server = new MySerialServer();
		server.start(Integer.parseInt(args[index]), new SimulatorClientHandler(Integer.parseInt(args[index+1])));
		
		return 2;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.server.stop();
	}
}
