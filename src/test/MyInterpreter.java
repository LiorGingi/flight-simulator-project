package test;

import interpreter.BindingTable;
import interpreter.ConsoleLexer;
import interpreter.ConsoleParser;
import interpreter.SymbolTableStack;

public class MyInterpreter {

	public static int interpret(String[] lines) {
		ConsoleLexer lexer = new ConsoleLexer();
		ConsoleParser parser = new ConsoleParser();
		int solution = 0;
		try {
			String[][] console = lexer.tokenize(lines);
			solution = parser.parse(console);
			BindingTable.clearTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solution;
	}
}
