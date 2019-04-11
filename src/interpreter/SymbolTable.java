package interpreter;

import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable {

	private static volatile ConcurrentHashMap<String, Double> symbolTable;//Holds the variables values.
	private static Object lock=new Object();
	public static ConcurrentHashMap<String, Double> getInstance() {
		ConcurrentHashMap<String, Double> result=symbolTable;
		if(result==null) {
			synchronized (lock) {
				result=symbolTable;
				if(result==null)
					symbolTable = result = new ConcurrentHashMap<>();
			}
		}
		return symbolTable;
	}
}
