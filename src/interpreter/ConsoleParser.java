package interpreter;

import commands.CommandMap;
import commands.ConditionCommand;
import expression.CommandExpression;
import expression.Expression;

public class ConsoleParser implements Parser {

	CommandMap map;// holds CommandExpressions

	public ConsoleParser() {
		map = new CommandMap();
	}

	@Override
	public int parse(String[][] script) throws Exception {
		// create main stack
		SymbolTableStack.addScope();
		int index;
		int line;
		int retVal = 0;
		for (line = 0; line < script.length; line++) {// main loop
			index = 0;
			String[] fixedLine = getParameters(script[line]);

			while (index < fixedLine.length) {

				Expression resultExp = map.getCommand(fixedLine[index]);

				if (resultExp != null) {
					CommandExpression ce = (CommandExpression) resultExp;// resultExp contains CommandExpression
					index++;// set the index to the first parameter
					ce.initialize(fixedLine, index);
					// check if it is a new scope
					if (map.isScopeCommand(fixedLine[index - 1])) {
						line += ce.calculate();
						//fill the scope
						while (!script[line][0].equals("}")) {
							ce.initialize(script[line], index);
							line += ce.calculate();
						}
						ce.initialize(script[line], index);
						ce.calculate();
					}
					// return command
					if (map.isEndOfScript(fixedLine[0])) {
						retVal = (int) ce.calculate();
						index += fixedLine.length;
					} else
						index += ce.calculate();
				} else {
					index++;
				}
			}
		}
		SymbolTableStack.exitScope();
		return retVal;
	}

	private String[] getParameters(String[] arr) throws Exception {
		ExpressionSeparator separator = new ExpressionSeparator(arr);
		return new ExpressionCalculator(separator.separate()).calculateExpressions();
	}
}
