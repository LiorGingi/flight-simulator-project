package test;

import interpreter.ConsoleLexer;
import interpreter.ConsoleParser;
import interpreter.Lexer;
import interpreter.Parser;

public class MainTest {
	public static void main(String[] args) {
		Lexer<String> l = new ConsoleLexer();
		Parser p = new ConsoleParser();
		String[] line = { "var x", "x=5", "var y=x + 3", "return y*x +   5" };
		try {
			System.out.println(p.parse(l.tokenize(line)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
