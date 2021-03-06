package commands;

import java.util.ListIterator;

import interpreter.MyInterpreter;

public class BindCommand implements Command {
	private String localVarName;
	private String simVarName;

	@Override
	public void execute() throws Exception {
		MyInterpreter.getBindingTable().addBinding(localVarName, simVarName);
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		it.previous();
		localVarName = it.previous();
		if (localVarName.equals("=")) {
			localVarName = it.previous();
			it.next();
		}
		it.next();
		it.next();
		simVarName = fixSimVarName(it.next());
	}

	private static String fixSimVarName(String name) {
		if (name != null) {
			String[] str = name.split("\"");
			if (str.length > 1)
				return str[1];
		}
		return name;
	}
}
