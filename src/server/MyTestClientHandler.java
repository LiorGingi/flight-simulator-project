package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyTestClientHandler implements ClientHandler {

	Solver<String, String> solver = stringToReverse -> new StringBuilder(stringToReverse).reverse().toString();
	CacheManager<String, String> cm;

	public MyTestClientHandler(CacheManager<String, String> cm) {
		super();
		this.cm = cm;
	}

	@Override
	public void handleClient(InputStream in, OutputStream out) {
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(in));
		PrintWriter responseToClient = new PrintWriter(out);

		try {
			String clientRequest;
			String newSolution;
			while (!(clientRequest = clientInput.readLine()).equals("end") && clientRequest != null) {
				if (cm.isExist(clientRequest)) {
					responseToClient.println(cm.get(clientRequest));
				} else {
					newSolution = solver.solve(clientRequest);
					cm.save(clientRequest, newSolution);
					responseToClient.println(newSolution);
				}
				responseToClient.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			clientInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		responseToClient.close();

	}
}
