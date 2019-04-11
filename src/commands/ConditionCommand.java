package commands;

import java.util.concurrent.ConcurrentHashMap;
import interpreter.SymbolTable;

public class ConditionCommand implements Command {

	boolean answer;
	ConcurrentHashMap<String, Double> symbolTable;
	
	public ConditionCommand() {
		symbolTable = SymbolTable.getInstance();
	}
	
	@Override
	public int execute(String[] args, int index) throws Exception {
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
		return 3;
	}
	
	private double checkValue(String argToCheck) {
		if (symbolTable.containsKey(argToCheck))
			return symbolTable.get(argToCheck);
		
		return Double.parseDouble(argToCheck);
	}
	
	public boolean getAnswer() {
		return answer;
	}

}
