package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import interpreter.MyInterpreter;

public class SimulatorClientHandler  implements ClientHandler {
	
	private class SimDataGetter extends TimerTask{
		private String inputFromClient;

		@Override
		public void run() {
			String[] str=inputFromClient.split(",");
			MyInterpreter.getBindingTable().setVar("/instrumentation/airspeed-indicator/indicated-speed-kt",Double.parseDouble(str[0]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/altimeter/indicated-altitude-ft",Double.parseDouble(str[1]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/altimeter/pressure-alt-ft",Double.parseDouble(str[2]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/attitude-indicator/indicated-pitch-deg",Double.parseDouble(str[3]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/attitude-indicator/indicated-roll-deg",Double.parseDouble(str[4]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/attitude-indicator/internal-pitch-deg",Double.parseDouble(str[5]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/attitude-indicator/internal-roll-deg",Double.parseDouble(str[6]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/encoder/indicated-altitude-ft",Double.parseDouble(str[7]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/encoder/pressure-alt-ft",Double.parseDouble(str[8]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/gps/indicated-altitude-ft",Double.parseDouble(str[9]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/gps/indicated-ground-speed-kt",Double.parseDouble(str[10]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/gps/indicated-vertical-speed",Double.parseDouble(str[11]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/heading-indicator/indicated-heading-deg",Double.parseDouble(str[12]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/magnetic-compass/indicated-heading-deg",Double.parseDouble(str[13]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/slip-skid-ball/indicated-slip-skid",Double.parseDouble(str[14]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/turn-indicator/indicated-turn-rate",Double.parseDouble(str[15]));
			MyInterpreter.getBindingTable().setVar("/instrumentation/vertical-speed-indicator/indicated-speed-fpm",Double.parseDouble(str[16]));
			MyInterpreter.getBindingTable().setVar("/controls/flight/aileron",Double.parseDouble(str[17]));
			MyInterpreter.getBindingTable().setVar("/controls/flight/elevator",Double.parseDouble(str[18]));
			MyInterpreter.getBindingTable().setVar("/controls/flight/rudder",Double.parseDouble(str[19]));
			MyInterpreter.getBindingTable().setVar("/controls/flight/flaps",Double.parseDouble(str[20]));
			MyInterpreter.getBindingTable().setVar("/controls/engines/engine/throttle",Double.parseDouble(str[21]));
			MyInterpreter.getBindingTable().setVar("/engines/engine/rpm",Double.parseDouble(str[22]));			
		}
	}
	private int Hz;
	private SimDataGetter dataGetter;
	public SimulatorClientHandler(int Hz) {
		this.Hz = Hz;
		dataGetter=new SimDataGetter();
	}


	@Override
	public void handleClient(InputStream in, OutputStream out) throws IOException {
		System.out.println("simulator connected");
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(in));
		int frequency=1000/Hz;
		Timer t=new Timer();
		t.scheduleAtFixedRate(dataGetter, 0, frequency);
		while((dataGetter.inputFromClient=clientInput.readLine())!=null);
		dataGetter.cancel();
		t.cancel();
	}

	

}
