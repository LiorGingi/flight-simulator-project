package interpreter;

import java.util.Arrays;
import java.util.HashMap;

import algorithms.ShuntingYard;
import commands.CommandMap;
import expression.CommandExpression;
import expression.Expression;

public class ConsoleParser implements Parser {

	CommandMap map;
	HashMap<String, Double> symbolTable;//Holds the variables values.
	
	public ConsoleParser() {
		map = new CommandMap();
		symbolTable = new HashMap<>();
	}
	
	@Override
	public void parse(String[] strArr) throws Exception {
		int index=0;
		while(index<strArr.length){
			Expression resultExp= map.get(strArr[index]);
			if(resultExp!=null) {
				CommandExpression ce=(CommandExpression)resultExp;//resultExp contains CommandExpression
				int numOfArgs=ce.getC().getNumOfParameters();
				ce.getC().setParameters(Arrays.copyOfRange(strArr, index, index+numOfArgs));//send parameters including command name.
				index+=resultExp.calculate();
			}
		}
	}

}
