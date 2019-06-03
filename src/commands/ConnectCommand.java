package commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicBoolean;

import algorithms.ShuntingYard;
import interpreter.ActiveUpdater;
import interpreter.MyUpdater;
import interpreter.Property;

public class ConnectCommand implements Command {
	private static Socket simulator = null;
	private static PrintWriter out = null;
	private static ActiveUpdater activeUpdater = null;
	private String ip;
	private int port;

	@Override
	public void execute() throws Exception {
		try {
			simulator = new Socket(ip, port);
			out = new PrintWriter(simulator.getOutputStream());
			activeUpdater = new ActiveUpdater(new MyUpdater());
			startCommunicationWithSimulator();
		} catch (Exception e) {
			System.out.println("waiting for simulator");
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
		activeUpdater.update(out, name, p);
	}

	public static void startCommunicationWithSimulator() {
		try {
			Thread.sleep(80*1000);
			activeUpdater.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void disconnectFromSimulator() {
		if (activeUpdater != null) {
			activeUpdater.stop();
			activeUpdater = null;
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
