package test;

import interpreter.Interpreter;
import interpreter.MyInterpreter;

public class MainTest {
	public static void main(String[] args) {
		Interpreter inter=new MyInterpreter();
		String script= 
				"openDataServer (540* 10) 1 +  9"+System.lineSeparator() +
				"connect 127.0.0.1 5402"+System.lineSeparator() +
				"print continue"+System.lineSeparator() +
				"var breaks = bind \"/controls/flight/speedbrake\""+System.lineSeparator() +
				"var throttle = bind \"/controls/engines/current-engine/throttle\""+System.lineSeparator() +
				"var heading = bind /instrumentation/heading-indicator/indicated-heading-deg"+System.lineSeparator() +
				"var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt"+System.lineSeparator() +
				"var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg"+System.lineSeparator() +
				"var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg"+System.lineSeparator() +
				"var rudder = bind /controls/flight/rudder"+System.lineSeparator() +
				"var aileron = bind /controls/flight/aileron"+System.lineSeparator() +
				"var elevator = bind /controls/flight/elevator"+System.lineSeparator() +
				"var alt = bind /instrumentation/altimeter/indicated-altitude-ft"+System.lineSeparator() +
				"breaks = 0"+System.lineSeparator() +
				"throttle = 1"+System.lineSeparator() +
				"var h0 = heading"+System.lineSeparator() +
				"while (alt < ((200*2+100)*2)  )  && (alt < (5000/10)  ){"+System.lineSeparator() +
				"rudder = (h0 - heading)/20"+System.lineSeparator() +
				"aileron = - roll / 70"+System.lineSeparator() +
				"elevator = pitch / 50"+System.lineSeparator() +
				"print alt"+System.lineSeparator() +
				"sleep 50"+System.lineSeparator() +
				"}"+System.lineSeparator() +
				"print done"+System.lineSeparator() +
				"disconnect";
		inter.interpret(script);
		System.out.println("done");
		
	}
}