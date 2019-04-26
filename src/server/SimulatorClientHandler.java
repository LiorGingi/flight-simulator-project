package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import interpreter.BindingTable;

public class SimulatorClientHandler implements ClientHandler {

	int frequency; //not sure what should be done with the frequency
	
	public SimulatorClientHandler(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public void handleClient(InputStream in, OutputStream out) throws IOException {
		BufferedReader clientInputBuffer = new BufferedReader(new InputStreamReader(in));
		PrintWriter responseToClient = new PrintWriter(out);
		
		String inputFromClient;
		String[] simValuesInput;
		HashMap <String, Double> simVarValues = new HashMap<>();
		boolean clientIsConnected = true;
		
		while(clientIsConnected) {
			inputFromClient = clientInputBuffer.readLine().toString();
			if(inputFromClient != "-1") {
				simValuesInput = inputFromClient.split(","); 
				
				for (int i=0; i<3; i++) {
					String var = convertIndexToVar(i);
					double newVal = Double.parseDouble(simValuesInput[i]);
					if (simVarValues.containsKey(var)) {
						if (simVarValues.get(var) != newVal) {
							simVarValues.put(var, newVal);
						}
					} else {
						simVarValues.put(var, newVal);
					}
					
					if(BindingTable.checkIfBind(var)) {
						BindingTable.updateVarValue(var, newVal);
					}
				}
			} else {
				clientIsConnected = false;
			}
		}

	}
	
	private String convertIndexToVar(int index) {
		switch (index) {
		case 0:
			return "simX";
		case 1:
			return "simY";
		case 2:
			return "simZ";
		default:
			return "";
		}
	}

}
