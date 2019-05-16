package commands;

import interpreter.ConsoleParser;

public class IfCommand extends ConditionCommand {

	public IfCommand() {
		super();
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		
		if (!scopeLoaded) {
			loadLineToScope(args);
			return 1;
		} else if (this.checkCondition(getCondition(getConditionLine()))) {
			parser = new ConsoleParser();
			parseScope();
		}
		return args.length - index;
	}

}
