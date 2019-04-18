package commands;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import expression.Div;
import expression.Expression;
import expression.Minus;
import expression.Mul;
import expression.Plus;
import interpreter.SymbolTableStack;
import expression.Number;

public class ShuntingYard implements Expression{
	
	@Override
	public double calculate(String[] args, int index) throws Exception {
			StringBuilder builder=new StringBuilder();
			int i=index;
			while(i<args.length) {
				if(SymbolTableStack.isVarExist(args[i]))
					builder.append(SymbolTableStack.getVarValue(args[i]).toString());
				else
				{
					//check if it +-/* or number
					builder.append(args[i]);
				}
				i++;
			}
		return useAlg(builder.toString());
	}
	public static double useAlg(String exp) {
		Queue<String> queue = new LinkedList<String>();
		Stack<String> stack = new Stack<String>();
		Stack<Expression> stackExp = new Stack<Expression>();

		String[] split = exp.split("(?<=[-+*/()])|(?=[-+*/()])");
		for (String s : split) {
			if (isDouble(s)) {
				queue.add(s);
			} else {
				switch (s) {
				case "/":
				case "*":
				case "(":
					stack.push(s);
					break;
				case "+":
				case "-":
					while (!stack.empty() && (!stack.peek().equals("("))) {
						queue.add(stack.pop());
					}
					stack.push(s);
					break;
				case ")":
					while (!stack.peek().equals("(")) {
						queue.add(stack.pop());
					}
					stack.pop();
					break;
				}
			}
		}
		while (!stack.isEmpty()) {
			queue.add(stack.pop());
		}

		for (String str : queue) {
			if (isDouble(str)) {
				stackExp.push(new Number(Double.parseDouble(str)));
			} else {
				Expression right = stackExp.pop();
				Expression left = stackExp.pop();

				switch (str) {
				case "/":
					stackExp.push(new Div(left, right));
					break;
				case "*":
					stackExp.push(new Mul(left, right));
					break;
				case "+":
					stackExp.push(new Plus(left, right));
					break;
				case "-":
					stackExp.push(new Minus(left, right));
					break;
				}
			}
		}

		return Math.floor((stackExp.pop().calculate() * 1000)) / 1000;
	}

	private static boolean isDouble(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	


}
