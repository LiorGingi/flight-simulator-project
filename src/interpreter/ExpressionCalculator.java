package interpreter;

import algorithms.ShuntingYard;

public class ExpressionCalculator {
	private String[] expressions;

	public ExpressionCalculator(String[] expressions) {
		this.expressions = expressions;
	}

//	gets String array and calculates mathematical expressions in it
	public String[] calculateExpressions() throws Exception {
		StringBuilder builder = new StringBuilder();
		for (String exp : expressions) {
			if (isExpression(exp)) {
				Thread.sleep(100);
				Double value=new ShuntingYard(exp).calc();
				builder.append(value.toString());
			}
			else
				builder.append(exp);
			builder.append(",");
		}
		return builder.toString().split(",");
	}

	private boolean isExpression(String val) {
		if (val.matches(".*[+-[\\*]/()].*"))
			return true;
		return false;
	}

}
