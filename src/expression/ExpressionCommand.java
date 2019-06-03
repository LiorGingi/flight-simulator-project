package expression;

import java.util.ListIterator;

import commands.Command;

public class ExpressionCommand implements Expression {
	private Command c;

	public ExpressionCommand(Command c) {
		super();
		this.c = c;
	}

	public void setParameters(ListIterator<String> it) {
		try {
			c.setParameters(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String calculate() throws Exception {
		c.execute();
		return null;
	}
}
