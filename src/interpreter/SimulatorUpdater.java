package interpreter;

import java.io.PrintWriter;

public interface SimulatorUpdater {
	public void update(PrintWriter out, String name, Property p);
}
