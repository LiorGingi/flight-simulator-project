package algorithms;

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

public class ShuntingYard {
	private String exp;

	public ShuntingYard(String exp) {
		this.exp = exp;
	}

	public double calc() throws Exception {
		return useAlg(this.exp);
	}

	private double useAlg(String expression) throws Exception {
		Queue<String> queue = new LinkedList<String>();
		Stack<String> stack = new Stack<String>();
		Stack<Expression> stackExp = new Stack<Expression>();

		String[] split = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
		for (String s : split) {
			if (isDouble(s)) {
				queue.add(s);
			}
			if (SymbolTableStack.isVarExist(s)) {// replace variable with its value
				queue.add(SymbolTableStack.getVarValue(s).toString());
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

	private boolean isDouble(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
