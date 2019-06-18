package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import interpreter.Interpreter;
import interpreter.MyInterpreter;

public class SimModel extends Observable {
	private class ActiveScriptSender {
		private BlockingQueue<Runnable> queue;
		private AtomicBoolean stop;
		private Thread scriptThread;
		private Interpreter inter;

		public ActiveScriptSender() {
			inter = new MyInterpreter();
			queue = new LinkedBlockingQueue<>();
			stop = new AtomicBoolean();
			scriptThread = new Thread(() -> {
				while (!stop.get()) {
					try {
						queue.take().run();
					} catch (InterruptedException e) {
					}
				}
			}, "script_thread");
		}

		public void sendScript(String script) {
			queue.add(() -> {
				inter.interpret(script);
			});
		}

		public void start() {
			scriptThread.start();
		}

		public void stop() {
			queue.add(() -> stop.set(true));
		}
	}

	private ActiveScriptSender activeSender;
	private ScheduledExecutorService scheduledDumper;
	private Socket socketForPosition = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	double[] posArray;

	public SimModel() {
		posArray = new double[5];
		activeSender = new ActiveScriptSender();
		activeSender.start();
	}

	public void sendScript(String script) {
		activeSender.sendScript(script);
	}

	public void openDataServer(int port, int frequency) {
		activeSender.sendScript("openDataServer " + port + " " + frequency);
	}

	public void connectToSimulator(String ip, int port) {
		activeSender.sendScript("connect " + ip + " " + port);
	}

	public void dumpPosition(String ip, int port) {// transfer to active object
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
		activeSender.sendScript("VM_Throttle = " + value);
	}

	public void setRudder(double value) {
		activeSender.sendScript("VM_Rudder = " + value);
	}

	public void setAileron(double value) {
		activeSender.sendScript("VM_Aileron = " + value);
	}

	public void setElevator(double value) {
		activeSender.sendScript("VM_Elevator = " + value);
	}

	public double[] getPlaneLocation() {
		return posArray;
	}
}
