package commands;

import java.util.ListIterator;

import algorithms.ShuntingYard;

public abstract class ConditionCommand extends ScopeCommand {
	protected String conditionLine;

	protected void setConditionLine(ListIterator<String> it) {
		conditionLine = it.next();
		it.next();// pass {
	}

	protected boolean checkCondition() throws Exception {// only one condition is handled, fix to handle multiple
															// conditions
		if (conditionLine == null)
			throw new Exception("no condition");
		String[] args = conditionLine.split("(?<=(!=)|(==)|(<=)|(>=)|[<>])|(?=(!=)|(==)|(<=)|(>=)|[<>])");
		String operator = args[1];
		Double rightArg = getargValue(args[0]);
		Double leftArg = getargValue(args[2]);
		switch (operator) {
		case "<":
			return (rightArg < leftArg);
		case "<=":
			return (rightArg <= leftArg);
		case ">":
			return (rightArg > leftArg);
		case ">=":
			return (rightArg >= leftArg);
		case "==":
			return (rightArg == leftArg);
		case "!=":
			return (rightArg != leftArg);
		default:
			throw new Exception("No condition");
		}
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		this.setConditionLine(it);
		this.loadScope(it);
	}

	private Double getargValue(String arg) throws NumberFormatException, Exception {
		return Double.parseDouble(ShuntingYard.calc(arg).calculate());
	}

}
