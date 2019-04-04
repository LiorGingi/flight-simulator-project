package interpreter;

import java.util.ArrayList;
import java.util.Scanner;

public class LineLexer implements Lexer<String> {
	Scanner sc;

	@Override
	public String[] tokenize(String input) {
		sc=new Scanner(input);
		ArrayList<String> wordsList = new ArrayList<>();
		while(sc.hasNext()) {
			wordsList.add(sc.next());
		}
		return (String[]) wordsList.toArray();
	}

}
