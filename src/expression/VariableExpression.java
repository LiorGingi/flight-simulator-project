package expression;

import interpreter.Property;

public class VariableExpression implements Expression {
	private Property p;

	public VariableExpression(Property p) {
		this.p = p;
	}

	@Override
	public String calculate() throws Exception {
		return "" + p.getValue();
	}

}
