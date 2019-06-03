package interpreter;

import java.io.PrintWriter;

public class MyUpdater implements SimulatorUpdater {

	@Override
	public void update(PrintWriter out, String name, Property p) {
		String command = "set " + name + " " + p.getValue().doubleValue();
		if(name.equals("/controls/engines/current-engine/throttle"))
			System.out.println(command);
		out.println(command);
		out.flush();
	}
}
