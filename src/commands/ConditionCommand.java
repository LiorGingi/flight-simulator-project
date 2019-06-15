package commands;

import java.util.ListIterator;

import algorithms.ShuntingYard;

public abstract class ConditionCommand extends ScopeCommand {
	protected String conditionsLine;

	protected void setConditionLine(ListIterator<String> it) {
		conditionsLine = it.next();
		it.next();// pass {
	}

	protected boolean checkCondition() throws Exception {// only one condition is handled, fix to handle multiple
															// conditions
														//need to handle parenthesis
//		if (conditionsLine == null)
//			throw new Exception("no condition");
//		String[] args = conditionsLine.split("(?<=(!=)|(==)|(<=)|(>=)|[<>])|(?=(!=)|(==)|(<=)|(>=)|[<>])");
//		String operator = args[1];
//		Double rightArg = getargValue(args[0]);
//		Double leftArg = getargValue(args[2]);
//		switch (operator) {
//		case "<":
//			return (rightArg < leftArg);
//		case "<=":
//			return (rightArg <= leftArg);
//		case ">":
//			return (rightArg > leftArg);
//		case ">=":
//			return (rightArg >= leftArg);
//		case "==":
//			return (rightArg == leftArg);
//		case "!=":
//			return (rightArg != leftArg);
//		default:
//			throw new Exception("No condition");
//		}
		return ShuntingYard.calcLogic(conditionsLine);
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		this.setConditionLine(it);
		this.loadScope(it);
	}

//	private Double getargValue(String arg) throws NumberFormatException, Exception {
//		return Double.parseDouble(ShuntingYard.calcArithmetic(arg).calculate());
//	}

}
