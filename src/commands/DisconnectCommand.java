package commands;

import java.util.ListIterator;

public class DisconnectCommand implements Command {

	@Override
	public void execute() throws Exception {
		ConnectCommand.disconnectFromSimulator();
		OpenServerCommand.closeServer();
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
	}

}
