package interpreter;

import java.util.HashMap;

import algorithms.ShuntingYard;
import commands.CommandMap;
import expression.Expression;

public class LineParser implements Parser {

	CommandMap map;
	HashMap<String, Double> symbolTable;
	
	public LineParser() {
		map = new CommandMap();
		symbolTable = new HashMap<>();
	}
	
	@Override
	public void parse(String[] strArr) throws Exception {
		
		Expression resultExp=new ShuntingYard().execute(strArr);
		resultExp.calculate();
		
	}

}
