package interpreter;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

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
	public void parse(String[][] script) throws Exception {
		// create main stack
		SymbolTableStack.addScope();
		int index;
		for (int line = 0; line < script.length; line++) {// main loop
			index = 0;
			String[] fixedLine = getParameters(script[line]);
//			System.out.println(Arrays.toString(fixedLine));
			while (index < fixedLine.length) {

				Expression resultExp = map.getCommand(fixedLine[index]);

				if (resultExp != null) {
					index++;// set the index to the first parameter
					CommandExpression ce = (CommandExpression) resultExp;// resultExp contains CommandExpression
					// check if it is a new scope
					boolean isScopeCommand = map.isScopeCommand(fixedLine[index]);
					if (isScopeCommand)
						line += loadCommandsToScope(script, line, (ConditionCommand) resultExp)+1;
					ce.initialize(fixedLine, index);
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

	private int loadCommandsToScope(String[][] script, int line, ConditionCommand cc) throws Exception {
		int scopeLines = 0;
		while (!script[line + scopeLines][0].equals("}")) {
			cc.addToEndOfScope(script[line + scopeLines]);
			scopeLines++;
		}
		return scopeLines;
	}
}
