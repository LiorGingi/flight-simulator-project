package interpreter;

public class ExpressionSeparator {
	private String[] expressions;

	public ExpressionSeparator(String[] expressions) {
		this.expressions = expressions;
	}

//	Converts this String array to an array of commands and numbered expressions
	public String[] separate() {
		StringBuilder builder = new StringBuilder();
		builder.append(expressions[0]);
		try {
			for (int i = 1; i < expressions.length; i++) {
				String token = expressions[i];
				String prev = expressions[i - 1];
				// check if it is the beggining of an expression
				if (!isValid(prev) || !isValid(token) || isBeggingOfExp(token, prev)) {
					builder.append(",");
				}
				builder.append(token);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString().split(",");
	}

	private boolean isBeggingOfExp(String token, String prev) {
		if (isNumber(token) || SymbolTableStack.isVarExist(token) || token.startsWith("("))
			if (prev.endsWith(")") || isNumber(prev) || SymbolTableStack.isVarExist(prev))
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

	private boolean isArithmeticFunc(String val) {
		return (val.equals("+") || val.equals("-") || val.equals("*") || val.equals("/"));
	}

	private boolean isValid(String str) {
		return (isNumber(str) || SymbolTableStack.isVarExist(str) || isArithmeticFunc(str) || str.equals(")")
				|| str.equals("("));
	}
}
