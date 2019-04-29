package commands;

import server.MySerialServer;
import server.SimulatorClientHandler;

public class OpenDataServerCommand implements Command {
	int port;
	int frequency;

	public OpenDataServerCommand() {
		this.port = 0;
		this.frequency = 0;
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		//index -> port, index+1 -> frequency
		MySerialServer server = new MySerialServer();
		server.start(Integer.parseInt(args[index]), new SimulatorClientHandler(Integer.parseInt(args[index+1])));
		
		return 2;
	}
}
