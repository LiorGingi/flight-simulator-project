package expression;

import commands.Command;

public class CommandExpression implements Expression {
	private Command c;
	private String[] parameters;
	private int index;

	public CommandExpression(Command c) {
		this.c = c;
	}

	public void initialize(String[] arr, int index) throws Exception {
		if (arr == null)
			throw new Exception("missing arguments");
		else {
			this.parameters = arr;
			this.index=index;
		}
	}

	@Override
	public double calculate() throws Exception {
		if (parameters != null)
			return (double) c.execute(parameters, index);
		throw new Exception("array is not initialized");
	}

}
