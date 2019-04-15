package interpreter;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.ShuntingYard;
import commands.CommandMap;
import expression.CommandExpression;
import expression.Expression;

public class ConsoleParser implements Parser {

	CommandMap map;//holds CommandExpressions
	ConcurrentHashMap<String, Double> symbolTable;//holds variables.
	
	public ConsoleParser() {
		map = new CommandMap();
		symbolTable = SymbolTableStack.getInstance();
	}
	
	@Override
	public void parse(String[] strArr) throws Exception {
		int index=0;
		while(index<strArr.length){//main loop
			
			Expression resultExp= map.get(strArr[index]);
			
			if(resultExp!=null) {
				CommandExpression ce=(CommandExpression)resultExp;//resultExp contains CommandExpression
				index+=ce.getC().execute(strArr, index +1) +1;
			}
			
		}
	}

}
