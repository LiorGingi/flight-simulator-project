package interpreter;

import algorithms.ShuntingYard;

public class ExpressionCalculator {
	private String[] expressions;

	public ExpressionCalculator(String[] expressions) {
		this.expressions = expressions;
	}

//	gets String array and calculates mathematic expressions in it
	public String[] calculateExpressions() throws Exception {
		StringBuilder builder = new StringBuilder();
		for (String exp : expressions) {
			if (isExpression(exp)) {
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
		if (val.matches(".*[+-[\\*]/()].*") || SymbolTableStack.isVarExist(val) || isNumber(val))
			return true;
		return false;
	}

	private boolean isNumber(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
