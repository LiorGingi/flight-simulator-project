package interpreter;

public class ExpressionSeparator {
	private String[] expressions;

	public ExpressionSeparator(String[] expressions) {
		this.expressions = expressions;
	}

//	Converts this String array to an array of commands and numbered expressions
	public String[] separate() {
		boolean previousIsNumber = false;
		boolean prevCloseParenthesis = false; // is previous token ends with )
		StringBuilder builder = new StringBuilder();
		try {
			for (String token : expressions) {
				// check if it is the end of an expression
				if (isNumber(token) && (prevCloseParenthesis || previousIsNumber))
					builder.append(",");
				builder.append(token);
				// the token is not part of an expression
				if (!isValid(token) && !SymbolTableStack.isVarExist(token))
					builder.append(",");
				previousIsNumber = isNumber(token);
				prevCloseParenthesis = token.endsWith(")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString().split(",");
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
		return (isNumber(str) || isArithmeticFunc(str) || str.equals(")") || str.equals("("));
	}
}
