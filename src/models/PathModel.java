package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;

public class PathModel extends Observable {

	private Socket solverServer = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String[] directions;

	private String serverIp;
	private int serverPort;

	public void connectToSolverServer(String ip, int port) {
		serverIp = ip;
		serverPort = port;
		try {
			solverServer = new Socket(serverIp, serverPort);
			out = new PrintWriter(solverServer.getOutputStream());
			in = new BufferedReader(new InputStreamReader(solverServer.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void calcShortestPath(double[][] field, int srcX, int srcY, int dstX, int dstY) {
		// verify connection to solver server
		try {
			// send problem according to solver server protocol
			int i, j;
			for (i = 0; i < field.length; i++) {
				for (j = 0; j < field[i].length - 1; j++) {
					out.print(field[i][j] + ",");
				}
				out.println(field[i][j]);
			}
			out.println("end");
			out.println(srcY + "," + srcX);
			out.println(dstY + "," + dstX);
			out.flush();
			// get the result from solver server (directions delimited by ,)
			String response = in.readLine();
			directions = response.split(",");
			setChanged();
			notifyObservers();
		} catch (SocketException e) {
			try {
				if (solverServer != null) {
					solverServer.close();
					solverServer = null;
				}
				solverServer = new Socket(serverIp, serverPort);
				out = new PrintWriter(solverServer.getOutputStream());
				in = new BufferedReader(new InputStreamReader(solverServer.getInputStream()));
				calcShortestPath(field, srcX, srcY, dstX, dstY);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getShortestPath() {
		return directions;
	}
}
