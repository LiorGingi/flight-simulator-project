package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import interpreter.MyInterpreter;

public class SimulatorClientHandler implements ClientHandler {

	private class SimDataGetter extends TimerTask {
		private String inputFromClient;

		@Override
		public void run() {
			if (inputFromClient != null) {
				try {
					String[] str = inputFromClient.split(",");
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/airspeed-indicator/indicated-speed-kt")
							.setValue(Double.parseDouble(str[0]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/altimeter/indicated-altitude-ft")
							.setValue(Double.parseDouble(str[1]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/altimeter/pressure-alt-ft")
							.setValue(Double.parseDouble(str[2]));
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/attitude-indicator/indicated-pitch-deg")
							.setValue(Double.parseDouble(str[3]));
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/attitude-indicator/indicated-roll-deg")
							.setValue(Double.parseDouble(str[4]));
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/attitude-indicator/internal-pitch-deg")
							.setValue(Double.parseDouble(str[5]));
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/attitude-indicator/internal-roll-deg")
							.setValue(Double.parseDouble(str[6]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/encoder/indicated-altitude-ft")
							.setValue(Double.parseDouble(str[7]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/encoder/pressure-alt-ft")
							.setValue(Double.parseDouble(str[8]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/gps/indicated-altitude-ft")
							.setValue(Double.parseDouble(str[9]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/gps/indicated-ground-speed-kt")
							.setValue(Double.parseDouble(str[10]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/gps/indicated-vertical-speed")
							.setValue(Double.parseDouble(str[11]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/heading-indicator/offset-deg")
							.setValue(Double.parseDouble(str[12]));
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/magnetic-compass/indicated-heading-deg")
							.setValue(Double.parseDouble(str[13]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/slip-skid-ball/indicated-slip-skid")
							.setValue(Double.parseDouble(str[14]));
					MyInterpreter.getBindingTable().getBindedVar("/instrumentation/turn-indicator/indicated-turn-rate")
							.setValue(Double.parseDouble(str[15]));
					MyInterpreter.getBindingTable()
							.getBindedVar("/instrumentation/vertical-speed-indicator/indicated-speed-fpm")
							.setValue(Double.parseDouble(str[16]));
					MyInterpreter.getBindingTable().getBindedVar("/controls/flight/aileron")
							.setValue(Double.parseDouble(str[17]));
					MyInterpreter.getBindingTable().getBindedVar("/controls/flight/elevator")
							.setValue(Double.parseDouble(str[18]));
					MyInterpreter.getBindingTable().getBindedVar("/controls/flight/rudder")
							.setValue(Double.parseDouble(str[19]));
					MyInterpreter.getBindingTable().getBindedVar("/controls/flight/flaps")
							.setValue(Double.parseDouble(str[20]));
//					MyInterpreter.getBindingTable().getBindedVar("/controls/engines/current-engine/throttle")
//							.setValue(Double.parseDouble(str[21]));
					MyInterpreter.getBindingTable().getBindedVar("/engines/engine/rpm")
							.setValue(Double.parseDouble(str[22]));
				} catch (NullPointerException e) {
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	private int Hz;
	private SimDataGetter dataGetter;

	public SimulatorClientHandler(int Hz) {
		this.Hz = Hz;
		dataGetter = new SimDataGetter();
	}

	@Override
	public void handleClient(InputStream in, OutputStream out) throws IOException {
		System.out.println("simulator connected");
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(in));
		int frequency = 1000 / Hz;
		Timer t = new Timer();
		t.scheduleAtFixedRate(dataGetter, 0, frequency);
		while ((dataGetter.inputFromClient = clientInput.readLine()) != null)
			;
		dataGetter.cancel();
		t.cancel();
	}

}
