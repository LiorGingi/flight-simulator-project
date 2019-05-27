package algorithms;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import expression.Div;
import expression.Expression;
import expression.Minus;
import expression.Mul;
import expression.Number;
import expression.Plus;
import expression.StringExpression;
import expression.VariableExpression;
import interpreter.MyInterpreter;
import interpreter.Property;

public class ShuntingYard { // update the algorithm to work with commands
//	private String exp;

//	public ShuntingYard(String exp) {
//		this.exp = exp;
//	}

	public static Expression calc(String exp) throws Exception {
		if (exp.startsWith("-"))
			return useAlg("0" + exp);
		return useAlg(exp);
	}

	private static Expression useAlg(String expression) throws Exception {// think about alg return value
		Queue<String> queue = new LinkedList<String>();
		Stack<String> stack = new Stack<String>();
		Stack<Expression> stackExp = new Stack<Expression>();
//		ExpressionCommand c = null;
//		ExpressionCommandFactory cf = new ExpressionCommandFactory();

		String[] split = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
		for (String s : split) {
			if (isDouble(s) || isVariable(s)) {
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
				default:
//					if((c=cf.createCommand(s))!=null)
//						stack.push(s);
				}
			}
		}
		while (!stack.isEmpty()) {
			queue.add(stack.pop());
		}

		for (String str : queue) {
			Property p = null;
			if (isDouble(str)) {
				stackExp.push(new Number(Double.parseDouble(str)));
			} else if ((p = getVariable(str)) != null) {
				stackExp.push(new VariableExpression(p));
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
				default:
//					if ((c=cf.createCommand(str))!=null) {
//						stackExp.push(c);
//					}
//					else{//string parameter
					stackExp.push(left);
					stackExp.push(right);
					stackExp.push(new StringExpression(str));
//					}
				}
			}
		}
		return stackExp.pop();
	}

	private static Property getVariable(String str) {
		if (MyInterpreter.getSymbolTable().isExist(str))
			try {
				return MyInterpreter.getSymbolTable().getVariable(str);
			} catch (Exception e) {
			}
		if (MyInterpreter.getBindingTable().isBind(str))
			return MyInterpreter.getBindingTable().getBindedVar(str);
		return null;
	}

	private static boolean isVariable(String str) {
		return MyInterpreter.getSymbolTable().isExist(str) || MyInterpreter.getBindingTable().isBind(str);
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
