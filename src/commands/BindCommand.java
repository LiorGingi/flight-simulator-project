package commands;

import interpreter.BindingTable;

public class BindCommand implements Command {

	@Override
	public int execute(String[] args, int index) {
		BindingTable.addBinding(args[index], args[index-3]);
		return 1;
	}

}
