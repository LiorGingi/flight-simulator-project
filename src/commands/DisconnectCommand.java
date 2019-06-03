package commands;

import java.util.ListIterator;

public class DisconnectCommand implements Command {

	@Override
	public void execute() throws Exception {
		System.out.println("disconnecting");
		ConnectCommand.disconnectFromSimulator();
		OpenServerCommand.closeServer();
		System.out.println("disconnected");
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
	}

}
