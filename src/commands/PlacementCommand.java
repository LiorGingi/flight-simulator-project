package commands;

import interpreter.SymbolTableStack;

public class PlacementCommand implements Command {

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (args[index].equals("bind")) {
			index++;
			index+=new BindCommand().execute(args, index);
			return index;
		}
		if (args.length >= 3 && index > 0 && args.length - index == 1) {
			if (!SymbolTableStack.isVarExist(args[index])) {
				try {
					SymbolTableStack.setVarValue(args[index - 1], Double.parseDouble(args[index + 1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				throw new Exception("incorrect variable name");
			return index + 2;
		} else
			throw new Exception("not enougth arguments");
	}
}
