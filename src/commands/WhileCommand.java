package commands;

import interpreter.ConsoleParser;

public class WhileCommand extends ConditionCommand {

	public WhileCommand() {
		super();
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		while (this.checkCondition(args)) {
			parser = new ConsoleParser();
			parseScope();
		}
		return args.length - index;
	}

}
