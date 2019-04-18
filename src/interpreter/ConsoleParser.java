package interpreter;

import java.util.concurrent.ConcurrentHashMap;

import commands.CommandMap;
import commands.ShuntingYard;
import expression.CommandExpression;
import expression.Expression;
import javafx.scene.shape.Line;

public class ConsoleParser implements Parser {

	CommandMap map;// holds CommandExpressions
	ConcurrentHashMap<String, Double> symbolTable;// holds variables.

	public ConsoleParser() {
		map = new CommandMap();
		symbolTable = SymbolTableStack.getTopScope();
	}

	@Override
	public void parse(String[][] script) throws Exception {
		int index;
		for (String[] line : script) {
			index = 0;
			while (index < line.length) {// main loop

				Expression resultExp = map.get(line[index]);

				if (resultExp != null) {
					CommandExpression ce = (CommandExpression) resultExp;// resultExp contains CommandExpression
					index += ce.getC().execute(line, index + 1) + 1;
				}

			}
		}
	}

}
