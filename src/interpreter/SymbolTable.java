package interpreter;

import java.util.HashMap;

public class SymbolTable {

	private static volatile HashMap<String, Double> symbolTable;//Holds the variables values.
	private static Object lock=new Object();
	public static HashMap<String, Double> getInstance() {
		HashMap<String, Double> result=symbolTable;
		if(result==null) {
			synchronized (lock) {
				result=symbolTable;
				if(result==null)
					symbolTable = result = new HashMap<>();
			}
		}
		return symbolTable;
	}
}
