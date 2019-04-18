package commands;

import interpreter.SymbolTableStack;

public abstract class ConditionCommand implements Command {
	//add container for commands
	
	protected boolean checkCondition(String[] args, int index) throws Exception{
		boolean answer;
		double rightArg = checkValue(args[index]);
		double leftArg = checkValue(args[index+2]);
		
		switch(args[index + 1]) {
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
	
	private double checkValue(String argToCheck) throws Exception {
		return SymbolTableStack.getVarValue(argToCheck);
	}

}
