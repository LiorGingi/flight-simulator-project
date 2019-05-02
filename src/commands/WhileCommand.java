package commands;

import interpreter.ConsoleParser;

public class WhileCommand extends ConditionCommand {

	public WhileCommand() {
		super();
	}

	@Override
	public int execute(String[] args, int index) throws Exception {
		
		if (!scopeLoaded) {
			loadLineToScope(args);
			return 1;
		} else
			while (this.checkCondition(getCondition(getConditionLine()))) {
				parser = new ConsoleParser();
				parseScope();
			}
		return args.length - index;
	}
}
