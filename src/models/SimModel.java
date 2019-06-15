package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import interpreter.Interpreter;
import interpreter.MyInterpreter;

public class SimModel extends Observable {

	private Interpreter inter;
	private ScheduledExecutorService scheduledDumper;
	private Socket socketForPosition = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	double[] posArray;

	public SimModel() {
		inter = new MyInterpreter();
		posArray = new double[5];
	}

	public void sendScript(String script) {
		inter.interpret(script);
	}

	public void connectToSimulator(String ip, int port) {
		inter.interpret("connect " + ip + " " + port);
	}

	public void dumpPosition(String ip, int port) {
		try {
			socketForPosition = new Socket(ip, port);
			out = new PrintWriter(socketForPosition.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socketForPosition.getInputStream()));
			scheduledDumper = Executors.newSingleThreadScheduledExecutor();
			scheduledDumper.scheduleAtFixedRate(() -> {
				out.println("dump position");
				out.flush();
				StringBuilder builder = new StringBuilder();
				try {
					in.readLine();
					in.readLine();
					in.readLine();
					builder.append(in.readLine());
					builder.append(in.readLine());
					builder.append(in.readLine());
					builder.append(in.readLine());
					builder.append(in.readLine());
					in.readLine();
					in.readLine();
					String[] str = builder.toString().split("[<>]");
					for (int i = 0, j = 2; i < 5; i++, j += 4)
						posArray[i] = Double.parseDouble(str[j]);
					setChanged();
					notifyObservers();
				} catch (IOException e) {
//					System.err.println("communication error. disconnecting");
					scheduledDumper.shutdownNow();
				}
			}, 0, 250, TimeUnit.MILLISECONDS);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setThrottle(double value) {
		inter.interpret("VM_Throttle = " + value);
	}

	public void setRudder(double value) {
		inter.interpret("VM_Rudder = " + value);
	}

	public void setAileron(double value) {
		inter.interpret("VM_Aileron = " + value);
	}

	public void setElevator(double value) {
		inter.interpret("VM_Elevator = " + value);
	}

	public double[] getPlaneLocation() {
		return posArray;
	}
}
