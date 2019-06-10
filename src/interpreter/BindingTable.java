package interpreter;

import java.util.concurrent.ConcurrentHashMap;

public class BindingTable {
	private volatile ConcurrentHashMap<String, String> bindTable;// local name-> simulator name
	private Object lock = new Object();

	public BindingTable() {
		getTable();
	}

	private ConcurrentHashMap<String, String> getTable() {
		ConcurrentHashMap<String, String> result = bindTable;
		if (result == null) {
			synchronized (lock) {
				result = bindTable;
				if (result == null) {
					bindTable = result = new ConcurrentHashMap<>();
				}
			}
		}
		return bindTable;
	}

	public boolean isBindToSimulator(String localVarName) {
		return getTable().containsKey(localVarName);
	}

	public String getNameInSimulator(String localVarName) {
		return getTable().get(localVarName);
	}

	public void addBinding(String localVarName, String simVarName) {
		if (!isBindToSimulator(localVarName))
			getTable().put(localVarName, simVarName);
	}

	public void removeVar(String localVarName) {
		getTable().remove(localVarName);
	}

	public void clearBindTable() {
		getTable().clear();
	}
}
