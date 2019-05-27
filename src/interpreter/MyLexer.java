package interpreter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MyLexer implements Lexer {
	@Override
	public List<String> tokenize(String script) {// separetes without bind
		String[] tokens = separateTokens(script);
		Scanner sc;
		StringBuilder builder = new StringBuilder();
		for (String token : tokens) {
			sc = new Scanner(token);
			String str1 = sc.next();
			String str2;
			builder.append(str1);

			while (sc.hasNext()) {// get the next expression
				str2 = sc.next();
				if (isEndOfExpression(str1, str2))// end of expression
					builder.append(",");
				builder.append(str2);
				str1 = str2;
			}
			builder.append(",");
		}
		return Arrays.asList(builder.toString().split(","));

	}

	private boolean isEndOfExpression(String str1, String str2) {
		Pattern end = Pattern.compile(".*[\\w)]");
		Pattern start = Pattern.compile("[\\w(].*");
		return (end.matcher(str1).matches() && start.matcher(str2).matches());
	}

	private String[] separateTokens(String script) {
		return script.split("(?<=([={}])|(bind))|(?=([={}]))");
	}

//	public static void main(String[] args) {
//		MyLexer lexer = new MyLexer();
//		System.out.println(lexer.tokenize("openDataServer 5  *1000 10" + System.lineSeparator()
//				+ "connect 127.0.0.1 5402" + System.lineSeparator() + "var brakes = bind /controls/flight/speedbrake"
//				+ System.lineSeparator() + "var throttle = bind /controls/engines/engine/throttle"
//				+ System.lineSeparator() + "var heading = bind /instrumentation/heading-indicator/offset-deg"
//				+ System.lineSeparator() + "var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt"
//				+ System.lineSeparator() + "var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg"
//				+ System.lineSeparator() + "var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg"
//				+ System.lineSeparator() + "var rudder = bind /controls/flight/rudder" + System.lineSeparator()
//				+ "var aileron=bind /controls/flight/aileron" + System.lineSeparator()
//				+ "var elevator=bind /controls/flight/elevator" + System.lineSeparator()
//				+ "var alt=bind /instrumentation/altimeter/indicated-altitude-ft" + System.lineSeparator()
//				+ "brakes = 0" + System.lineSeparator() + "throttle = 1" + System.lineSeparator() + "var h0 = heading"
//				+ System.lineSeparator() + "while alt < 1000 {" + System.lineSeparator() + "rudder = (h0 – heading)/20"
//				+ System.lineSeparator() + "aileron = - roll / 70" + System.lineSeparator() + "elevator = pitch / 50"
//				+ System.lineSeparator() + "print alt" + System.lineSeparator() + "sleep 250" + System.lineSeparator()
//				+ "}"));
//	}
}
