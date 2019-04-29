package interpreter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class BindingTable {
	private static volatile ConcurrentHashMap<String, ArrayList<String>> bindTable;
	private static Object lock = new Object();
	private static PrintWriter outToServer;

	public static ConcurrentHashMap<String, ArrayList<String>> getInstance() {
		ConcurrentHashMap<String, ArrayList<String>> result = bindTable;
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

	public static void addBinding(String var1, String var2) {
		if (getInstance().containsKey(var1)) {
			getInstance().get(var1).add(var2);
		} else {
			ArrayList<String> var1List = new ArrayList<>();
			var1List.add(var2);
			getInstance().put(var1, var1List);
		}

		if (getInstance().containsKey(var2)) {
			getInstance().get(var2).add(var1);
		} else {
			ArrayList<String> var2List = new ArrayList<>();
			var2List.add(var1);
			getInstance().put(var2, var2List);
		}
	}

	public static void updateVarValue(String var, Double newVal) {
		if (getInstance().containsKey(var)) {
			getInstance().get(var).forEach((str) -> {
				if (str.startsWith("sim")) {
					if (outToServer != null) {
						outToServer.println("set " + var + " " + newVal);
						outToServer.flush();
					}

				} else {
					try {
						SymbolTableStack.setVarValue(var, newVal);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static boolean checkIfBind(String var) {
		return getInstance().containsKey(var);
	}

	public static void setServerOutStream(PrintWriter out) {
		outToServer = out;
	}

}
