package commands;

import interpreter.ConsoleParser;

public class IfCommand extends ConditionCommand {

	public IfCommand() {
		super();
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		if (this.checkCondition(getCondition(args))) {
			parser = new ConsoleParser();
			parseScope();
		}
		return args.length - index;
	}

}
