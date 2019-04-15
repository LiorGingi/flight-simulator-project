package commands;

import interpreter.SymbolTableStack;

public class VarCommand implements Command {
	

	@Override
	public int execute(String[] args, int index) throws Exception {
		SymbolTableStack.addVar(args[index], new Double(0));
		return 1;
	}

}
