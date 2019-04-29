package commands;

import interpreter.ConsoleParser;
import interpreter.SymbolTableStack;

public class WhileCommand extends ConditionCommand {

	public WhileCommand() {
		super();
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		while (this.checkCondition(args)) {
			parser=new ConsoleParser();
			parseScope();
			SymbolTableStack.exitScope();
		}
		return args.length - index;
	}

}
