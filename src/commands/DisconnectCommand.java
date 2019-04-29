package commands;

import java.util.Observable;

public class DisconnectCommand extends Observable implements Command {

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (args.length != 1)
			throw new Exception("too many arguments");
		else {
			notifyObservers();
			return 1;
		}
	}
}
