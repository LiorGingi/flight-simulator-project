package interpreter;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import commands.CommandMap;
import expression.CommandExpression;
import expression.Expression;

public class ConsoleParser implements Parser {

	CommandMap map;// holds CommandExpressions
	ConcurrentHashMap<String, Double> symbolTable;// holds variables.

	public ConsoleParser() {
		map = new CommandMap();
		symbolTable = SymbolTableStack.getTopScope();
	}

	@Override
	public void parse(String[][] script) throws Exception {
		// create main stack
		SymbolTableStack.getInstance();
		SymbolTableStack.addScope();
		int index;
		for (String[] line : script) {// main loop
			index = 0;
			String[] fixedLine=getParameters(line);
			
			while (index < fixedLine.length) {

				Expression resultExp = map.get(fixedLine[index]);

				if (resultExp != null) {
					CommandExpression ce = (CommandExpression) resultExp;// resultExp contains CommandExpression

					ce.initialize(fixedLine, index + 1);
					index += ce.calculate();
				}
//				else {
//
//				}

			}
		}
	}

	private String[] getParameters(String[] arr) throws Exception {
		ExpressionSeparator separator = new ExpressionSeparator(arr);
		return new ExpressionCalculator(separator.separate()).calculateExpressions();
	}

}
