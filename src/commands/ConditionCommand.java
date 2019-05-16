package commands;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import interpreter.Parser;
import interpreter.SymbolTableStack;

public abstract class ConditionCommand implements Command {
	private volatile LinkedBlockingDeque<String[]> scopeLines;
	private String[] conditionLine;
	protected Parser parser;
	protected boolean scopeLoaded;

	public ConditionCommand() {
		scopeLines = new LinkedBlockingDeque<>();
		scopeLoaded = false;
		conditionLine = null;
	}

	protected void loadLineToScope(String[] line) {
		if (this.getConditionLine() == null)
			setConditionLine(line);
		else if (line[0].equals("}"))
			scopeLoaded = true;
		else
			scopeLines.addLast(line);
	}

	protected String[] getConditionLine() {
		return conditionLine;
	}

	protected void setConditionLine(String[] conditionLine) {
		this.conditionLine = conditionLine;
	}

	protected boolean checkCondition(String[] args) throws Exception {
		boolean answer;
		double rightArg = checkValue(args[1]);
		double leftArg = checkValue(args[3]);

		switch (args[2]) {
		case "<":
			answer = (rightArg < leftArg);
			break;
		case "<=":
			answer = (rightArg <= leftArg);
			break;
		case ">":
			answer = (rightArg > leftArg);
			break;
		case ">=":
			answer = (rightArg >= leftArg);
			break;
		case "==":
			answer = (rightArg == leftArg);
			break;
		case "!=":
			answer = (rightArg != leftArg);
			break;
		default:
			throw new Exception("No condition");
		}
		return answer;
	}

	protected void parseScope() {
		String[][] scope = new String[scopeLines.size()][];
		Iterator<String[]> it = scopeLines.iterator();
		for (int i = 0; i < scopeLines.size(); i++)
			scope[i] = it.next();
		try {
			parser.parse(scope);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String[] getCondition(String[] args) {
		StringBuilder builder = new StringBuilder();
		for (String str : args)
			if (!(str.equals("(") || str.equals(")") || str.equals("{")))
				builder.append(str + ",");
		return builder.toString().split(",");
	}

	private double checkValue(String argToCheck) throws Exception {
		if (SymbolTableStack.isVarExist(argToCheck))
			return SymbolTableStack.getVarValue(argToCheck);
		else {
			try {
				return Double.parseDouble(argToCheck);
			} catch (NumberFormatException e) {
				throw new Exception("Invalid argument");
			}
		}
	}

}
