package commands;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import interpreter.ConsoleParser;
import interpreter.Parser;
import interpreter.SymbolTableStack;

public abstract class ConditionCommand implements Command {
	private volatile LinkedBlockingDeque<String[]> scopeLines;
	protected Parser parser;

	public ConditionCommand() {
		scopeLines = new LinkedBlockingDeque<>();
	}

	public void addToEndOfScope(String[] line) {
		scopeLines.addLast(line);
	}

	protected boolean checkCondition(String[] args) throws Exception {
		boolean answer;
		double rightArg = checkValue(args[0]);
		double leftArg = checkValue(args[2]);

		switch (args[1]) {
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
		String[][] scope=new String[scopeLines.size()][];
		Iterator<String[]> it=scopeLines.iterator();
		for(int i=0;i<scopeLines.size();i++)
			scope[i]=it.next();
		try {
			parser.parse(scope);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String[] getCondition(String[] args) {
		StringBuilder builder=new StringBuilder();
		for(String str: args)
			if(!(str.equals("(")||str.equals(")")||str.equals("}")))
				builder.append(str+",");
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
