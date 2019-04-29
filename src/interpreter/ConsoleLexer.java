package interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleLexer implements Lexer<String> {
	Scanner sc;

	@Override
	public String[][] tokenize(String[] lines) {
		String[][] script = new String[lines.length][];
		int row = 0;
		for (String line : lines) {
			sc = new Scanner(line);
			ArrayList<String> wordsList = new ArrayList<>();

			while (sc.hasNext()) {
				String[] arr = sc.next().split("(?<=[-+*/()=}])|(?=[-+*/()=}])");

				for (String s : arr) {
					wordsList.add(s);
				}
			}
			script[row] = wordsList.toArray(new String[0]);
			row++;
		}
		return script;
	}
}
