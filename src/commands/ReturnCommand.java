package commands;

import interpreter.SymbolTableStack;

public class ReturnCommand implements Command {

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (args.length == 2) {
			return (int) getReturnValue(args[index]);
		}
		else
			throw new Exception("Invalid return value");
	}

	private double getReturnValue(String val) throws Exception {
		if (SymbolTableStack.isVarExist(val))
			return SymbolTableStack.getVarValue(val).doubleValue();
		else
			try {
				return Double.parseDouble(val);
			} catch (NumberFormatException e) {
				throw new Exception("Invalid return value");
			}
	}
}
