package commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ListIterator;

import algorithms.ShuntingYard;
import interpreter.ActiveUpdater;
import interpreter.MyUpdater;
import interpreter.Property;

public class ConnectCommand implements Command {
	private static Socket simulator = null;
	private static PrintWriter out = null;
	private static ActiveUpdater updater = null;
	private String ip;
	private int port;

	@Override
	public void execute() throws Exception {
		try {
			simulator = new Socket(ip, port);
			out=new PrintWriter(simulator.getOutputStream());
			updater = new ActiveUpdater(new MyUpdater());
			//initialize
			updater.update(out, "/controls/switches/master-bat", new Property(1.0));
			updater.update(out, "/controls/gear/brake-parking", new Property(1.0));
			updater.update(out, "/controls/engines/engine/starter", new Property(1.0));
			updater.update(out, "/controls/engines/engine/mixture", new Property(1.0));
			updater.update(out, "/controls/switches/magnetos", new Property(3.0));
		} catch (Exception e) {
			System.out.println("waiting to simulator");
			Thread.sleep(3000);
			execute();
		}
	}

	@Override
	public void setParameters(ListIterator<String> it) throws Exception {
		ip = it.next();
		port = (int) Double.parseDouble(ShuntingYard.calc(it.next()).calculate());
	}

	public static void updateSimulator(String name, Property p) throws Exception {
		updater.update(out, name, p);
	}

	public static void disconnectFromSimulator() {
		if (updater != null) {
			updater.stop();
			updater = null;
		}
		if (simulator != null)
			try {
				out.close();
				simulator.close();
				simulator = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
