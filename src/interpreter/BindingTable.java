package interpreter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BindingTable {
	private volatile ConcurrentHashMap<String, Property> bindTable;// simulator name-> value
	private Object lock = new Object();

	public BindingTable() {
		getTable();
	}
	private ConcurrentHashMap<String, Property> getTable() {
		ConcurrentHashMap<String, Property> result = bindTable;
		if (result == null) {
			synchronized (lock) {
				result = bindTable;
				if (result == null) {
					bindTable = result = new ConcurrentHashMap<>();
					bindTable.put("/instrumentation/airspeed-indicator/indicated-speed-kt", new Property(0.0));
					bindTable.put("/instrumentation/altimeter/indicated-altitude-ft", new Property(0.0));
					bindTable.put("/instrumentation/altimeter/pressure-alt-ft", new Property(0.0));
					bindTable.put("/instrumentation/attitude-indicator/indicated-pitch-deg", new Property(0.0));
					bindTable.put("/instrumentation/attitude-indicator/indicated-roll-deg", new Property(0.0));
					bindTable.put("/instrumentation/attitude-indicator/internal-pitch-deg", new Property(0.0));
					bindTable.put("/instrumentation/attitude-indicator/internal-roll-deg", new Property(0.0));
					bindTable.put("/instrumentation/encoder/indicated-altitude-ft", new Property(0.0));
					bindTable.put("/instrumentation/encoder/pressure-alt-ft", new Property(0.0));
					bindTable.put("/instrumentation/gps/indicated-altitude-ft", new Property(0.0));
					bindTable.put("/instrumentation/gps/indicated-ground-speed-kt", new Property(0.0));
					bindTable.put("/instrumentation/gps/indicated-vertical-speed", new Property(0.0));
					bindTable.put("/instrumentation/heading-indicator/indicated-heading-deg", new Property(0.0));
					bindTable.put("/instrumentation/magnetic-compass/indicated-heading-deg", new Property(0.0));
					bindTable.put("/instrumentation/slip-skid-ball/indicated-slip-skid", new Property(0.0));
					bindTable.put("/instrumentation/turn-indicator/indicated-turn-rate", new Property(0.0));
					bindTable.put("/instrumentation/vertical-speed-indicator/indicated-speed-fpm", new Property(0.0));
					bindTable.put("/controls/flight/aileron", new Property(0.0));
					bindTable.put("/controls/flight/elevator", new Property(0.0));
					bindTable.put("/controls/flight/rudder", new Property(0.0));
					bindTable.put("/controls/flight/flaps", new Property(0.0));
					bindTable.put("/controls/engines/current-engine/throttle", new Property(0.0));
					bindTable.put("/engines/engine/rpm", new Property(0.0));

				}
			}
		}
		return bindTable;
	}

	public boolean isBind(String varName) {
		return getTable().containsKey(varName);
	}

//	public void setVar(String name, Double value) {
//		getTable().get(name).setValue(value);
//	}

//	public boolean isBind(Property var) {
//		return getTable().containsValue(var);
//	}

	public Property getBindedVar(String varName) {
		return getTable().get(varName);
	}

//	public Collection<Property> getBindedVars() {
//		return getTable().values();
//
//	}
//
//	public Set<Entry<String, Property>> getBindVarSet() {
//		return getTable().entrySet();
//	}

	public String getVarName(Property var) {
		Set<Map.Entry<String, Property>> set = getTable().entrySet();
		Iterator<Entry<String, Property>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Property> entry = it.next();
			if (entry.getValue() == var)
				return entry.getKey();
		}
		return null;
	}

	public void addBinding(String simVarName, Property localVar) {
		if (!isBind(simVarName))
			getTable().put(simVarName, new Property(localVar.getValue()));
		localVar.bindBi(getBindedVar(simVarName));
	}

	public void removeVar(String varName) {
		if (isBind(varName)) {
			getBindedVar(varName).deleteObservers();
			getTable().remove(varName);
		}
	}

	public void clearBindTable() {
		getTable().keySet().forEach((varName) -> removeVar(varName));
	}
}
