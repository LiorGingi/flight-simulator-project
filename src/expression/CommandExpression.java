package expression;

import commands.Command;

public class CommandExpression implements Expression {
	Command c;
	
	public CommandExpression(Command c) {
		this.c = c;
	}


	public Command getC() {
		return c;
	}

	@Override
	public int calculate(String[] args, int index) throws Exception {
		return c.execute(args, index);
	}
	

}
