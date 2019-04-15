package interpreter;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SymbolTableStack {
	
	private static volatile CopyOnWriteArrayList<HashMap<String, Double>> scopes;
	private static Object lock=new Object();
	
	public static CopyOnWriteArrayList<HashMap<String, Double>> getInstance() {
		CopyOnWriteArrayList<HashMap<String, Double>> result=scopes;
		if(result==null) {
			synchronized (lock) {
				result=scopes;
				if(result==null) {
					scopes = result = new CopyOnWriteArrayList<>();
					scopes.add(new HashMap<>());
				}
			}
		}
		return scopes;
	}
	
	public static void addScope() { //adds the new scope to the end of the list
		getInstance().add(new HashMap<String, Double>());
	}
	
	public static HashMap<String, Double> getTopScope() {
		return getInstance().get(getInstance().size()-1);
	}
	
	public static boolean varExistence(String var) {
		int scopeSize = getInstance().size();

		for (int i=scopeSize-1; i>=0; i--) {
			if(getInstance().get(i).get(var) != null) { //get from ArrayList -> get from HashMap
				return true;
			}
		}

		return false;
	}
	
	public static void addVar(String var, Double val) throws Exception {
		if(!varExistence(var)) {
			getTopScope().put(var, val);
		} else {
			throw new Exception("var already exists in scope");
		}
	}
	
	public static void exitScope() { //deletes top scope in scopes ArrayList
		getInstance().remove(getInstance().size()-1);
	}
	
	public static Double getVarValue(String var) throws Exception {
		int scopeSize = getInstance().size();

		if(varExistence(var)) {
			for (int i=scopeSize-1; i>=0; i--) {
				if(getInstance().get(i).get(var) != null) { //get from ArrayList -> get from HashMap
					return getInstance().get(i).get(var);
				}
			}
		} else {
			throw new Exception("var doesn't exist");
		}		
		return null;
	}
}
