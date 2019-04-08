package expression;

import commands.Command;

public class CommandExpression implements Expression {
	Command c;
	
	public CommandExpression(Command c) {
		this.c = c;
	}

	@Override
	public int calculate() {
		return c.execute();
	}

	public Command getC() {
		return c;
	}
	

}
