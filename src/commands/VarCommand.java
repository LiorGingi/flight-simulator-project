package commands;

import java.util.ListIterator;

import interpreter.MyInterpreter;

public class VarCommand implements Command {
	private String varName;

	@Override
	public void execute() throws Exception {
		MyInterpreter.getSymbolTable().addNewVar(varName);
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		varName = it.next();
	}

}
