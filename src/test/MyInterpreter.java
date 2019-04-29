package test;

import interpreter.ConsoleLexer;
import interpreter.ConsoleParser;

public class MyInterpreter {

	public static int interpret(String[] lines) {
		ConsoleLexer lexer = new ConsoleLexer();
		ConsoleParser parser = new ConsoleParser();
		int solution = 0;
		try {
			solution = parser.parse(lexer.tokenize(lines));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return solution;
	}
}
