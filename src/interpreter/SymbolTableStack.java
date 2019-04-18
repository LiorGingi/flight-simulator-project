package interpreter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class SymbolTableStack {
	private static volatile LinkedBlockingDeque<ConcurrentHashMap<String, Double>> scopes;
	private static Object lock = new Object();

	public static LinkedBlockingDeque<ConcurrentHashMap<String, Double>> getInstance() {
		LinkedBlockingDeque<ConcurrentHashMap<String, Double>> result = scopes;
		if (result == null) {
			synchronized (lock) {
				result = scopes;
				if (result == null) {
					scopes = result = new LinkedBlockingDeque<>();
				}
			}
		}
		return scopes;
	}

	public static void addScope() {
		getInstance().addLast(new ConcurrentHashMap<String, Double>());
	}

	public static ConcurrentHashMap<String, Double> getTopScope() {
		return getInstance().peekLast();
	}

	public static boolean isVarExist(String var) {
		for (ConcurrentHashMap<String, Double> scope : getInstance())
			if (scope.containsKey(var))
				return true;
		return false;
	}

	public static void addVar(String var, Double val) throws Exception {
		if (isVarExist(var))
			throw new Exception("var " + var + " already exists in scope");
		else
			getTopScope().put(var, val);
	}

	public static void exitScope() {
		getInstance().removeLast();
	}

	public static Double getVarValue(String var) throws Exception {
		for (ConcurrentHashMap<String, Double> scope : getInstance())
			if (scope.containsKey(var))
				return scope.get(var);
		throw new Exception("var doesn't exist");
	}
	
	public static void setVarValue(String var, Double newVal) throws Exception {
		boolean exist=false;
		for(ConcurrentHashMap<String, Double> scope: getInstance())
			if(scope.containsKey(var)) {
				exist=true;
				scope.put(var, newVal);
			}
		if(!exist)
			throw new Exception("var doesn't exist");
	}
}