package commands;

import java.util.ListIterator;

import interpreter.MyInterpreter;
import interpreter.Property;

public class BindCommand implements Command {
	private Property localVar;
	private String simVarName;

	@Override
	public void execute() throws Exception {
		MyInterpreter.getBindingTable().addBinding(simVarName, localVar);
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		it.previous();
		String localVarName = it.previous();
		if (localVarName.equals("=")) {
			localVarName = it.previous();
			it.next();
		}
		localVar = MyInterpreter.getSymbolTable().getVariable(localVarName);
		it.next();
		it.next();
		simVarName = it.next();
	}
}
