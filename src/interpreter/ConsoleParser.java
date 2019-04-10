package interpreter;

import java.util.Arrays;
import java.util.HashMap;

import algorithms.ShuntingYard;
import commands.CommandMap;
import expression.CommandExpression;
import expression.Expression;

public class ConsoleParser implements Parser {

	CommandMap map;//holds CommandExpressions
	HashMap<String, Double> symbolTable;//holds variables.
	
	public ConsoleParser() {
		map = new CommandMap();
		symbolTable = SymbolTable.getInstance();
	}
	
	@Override
	public void parse(String[] strArr) throws Exception {
		int index=0;
		while(index<strArr.length){//main loop
			
			Expression resultExp= map.get(strArr[index]);
			
			if(resultExp!=null) {
				CommandExpression ce=(CommandExpression)resultExp;//resultExp contains CommandExpression
				index++;
				ce.getC().setParameters(Arrays.copyOfRange(strArr, index, index+ce.getC().getNumOfParameters()-1));//send parameters not including command name.
				index+=resultExp.calculate();
			}
			
		}
	}

}
