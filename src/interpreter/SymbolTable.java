package interpreter;

import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class SymbolTable {
	private volatile LinkedBlockingDeque<ConcurrentHashMap<String, Property>> symbolTableStack;
	private Object lock = new Object();

	public SymbolTable() {
		getTableStack();
		addScope();
	}

	private LinkedBlockingDeque<ConcurrentHashMap<String, Property>> getTableStack() {
		LinkedBlockingDeque<ConcurrentHashMap<String, Property>> result = symbolTableStack;
		if (result == null) {
			synchronized (lock) {
				result = symbolTableStack;
				if (result == null) {
					symbolTableStack = result = new LinkedBlockingDeque<ConcurrentHashMap<String, Property>>();
				}
			}
		}
		return symbolTableStack;
	}

	private boolean isInCurrentScope(String name) throws Exception {
		try {
			return getTableStack().peekLast().containsKey(name);
		} catch (NullPointerException e) {
			throw new Exception("table not initialized");
		}
	}

	public void addNewVar(String name) throws Exception {
		if (!isInCurrentScope(name))
			getTableStack().peekLast().put(name, new Property(0.0));
		else
			throw new Exception("Variable exists");
	}

	public synchronized boolean isExist(String varName) {
		Stack<ConcurrentHashMap<String, Property>> stack = new Stack<>();
		boolean answer = false;
		while ((!getTableStack().isEmpty()) && !answer) {
			stack.push(getTableStack().pollLast());
			if (stack.peek().containsKey(varName))
				answer = true;
		}
		while (!stack.empty())
			try {
				getTableStack().putLast(stack.pop());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		return answer;
	}

	public synchronized Property getVariable(String varName) throws Exception {
		Stack<ConcurrentHashMap<String, Property>> stack = new Stack<>();
		Property retVal = null;

		while ((!getTableStack().isEmpty()) && retVal == null) {
			stack.push(getTableStack().pollLast());
			if (stack.peek().containsKey(varName))
				retVal = stack.peek().get(varName);
		}
		while (!stack.empty())
			try {
				getTableStack().putLast(stack.pop());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		if (retVal == null)
			throw new Exception("var " + varName + " doesn't exists");
		return retVal;
	}

	public void addScope() {
		try {
			getTableStack().putLast(new ConcurrentHashMap<>());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeScope() {
		try {
			getTableStack().removeLast();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}
}
