package interpreter;

import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable {
	private volatile ConcurrentHashMap<String, Property> symbolTable;// varName+ '-' + scopeName -> property
	private Object lock = new Object();
	private int scopeNum = 0;

	private ConcurrentHashMap<String, Property> getTable() {
		ConcurrentHashMap<String, Property> result = symbolTable;
		if (result == null) {
			synchronized (lock) {
				result = symbolTable;
				if (result == null) {
					symbolTable = result = new ConcurrentHashMap<>();
				}
			}
		}
		return symbolTable;
	}

	public void addNewVar(String name) throws Exception {
		if (!isExist(name))
			getTable().put(setVarName(name, scopeNum), new Property(0.0));
		else
			throw new Exception("Variable exists");
	}

	public boolean isExist(String varName) {
		for (int i = scopeNum; i >= 0; i--) {
			if (getTable().containsKey(setVarName(varName, i)))
				return true;
		}
		return false;
	}

	public Property getVariable(String nameInScript) throws Exception {
		Property retVal = null;
		for (int i = scopeNum; i >= 0; i++) {
			if ((retVal = getTable().get(setVarName(nameInScript, i))) != null)
				return retVal;
		}
		throw new Exception("var " + nameInScript + " doesn't exists");
	}

	private String setVarName(String name, int scope) {
		return name + "-" + scope;
	}
}
