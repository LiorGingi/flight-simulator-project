package interpreter;

import java.util.HashMap;
import commands.Command;
import commands.CommandMap;

public class LineParser implements Parser {

	CommandMap map;
	HashMap<String, Double> symbolTable;
	
	public LineParser() {
		map = new CommandMap();
		symbolTable = new HashMap<>();
	}
	
	@Override
	public void parse(String[] strArr) throws Exception {
		int index = 0;
		int length = strArr.length;
		while (index < length) {
			Command cmd = map.get(strArr[index]);
			if (cmd == null) {
				throw new Exception("no command");
			}

		}

	}

}
