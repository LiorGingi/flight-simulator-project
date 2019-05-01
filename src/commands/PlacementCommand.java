package commands;

import interpreter.SymbolTableStack;

public class PlacementCommand implements Command {

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (args[index].equals("bind")) {
			index++;
			return new BindCommand().execute(args, index) + 1;
		}
		if (args.length >= 3 && index > 0 && args.length - index == 1) {
			if (!SymbolTableStack.isVarExist(args[index])) {
				try {
					SymbolTableStack.setVarValue(args[index - 2], Double.parseDouble(args[index]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				throw new Exception("incorrect variable name");
			return 1;
		} else
			throw new Exception("not enougth arguments");
	}
}
