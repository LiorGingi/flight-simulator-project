package test;

import interpreter.ConsoleLexer;
import interpreter.ConsoleParser;
import interpreter.Lexer;
import interpreter.Parser;

public class MainTest {
	public static void main(String[] args) {
		Lexer<String> l=new ConsoleLexer();
		Parser p=new ConsoleParser();
		String[] line= {"return 5 +(8+ 22 )*12 4 + 5-(9+0)"};
		try {
			p.parse(l.tokenize(line));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
