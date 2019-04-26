package commands;

import interpreter.SymbolTableStack;

public class IfCommand extends ConditionCommand {

	public IfCommand() {
		super();
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (this.checkCondition(getCondition(args))) {
			parseScope();
			SymbolTableStack.exitScope();					
		}
		return args.length - index;
	}

}
