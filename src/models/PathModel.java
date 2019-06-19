package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class PathModel extends Observable {

	private static Socket solverServer = null;
	private static PrintWriter out = null;
	private static BufferedReader in = null;
	private String[] directions;

	public void connectToSolverServer(String ip, int port) {
		try {
			solverServer = new Socket(ip, port);
			out = new PrintWriter(solverServer.getOutputStream());
			in = new BufferedReader(new InputStreamReader(solverServer.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void calcShortestPath(double[][] field, int srcX, int srcY, int dstX, int dstY) {
		// send problem according to solver server protocol
		int i, j;
		for (i = 0; i < field.length; i++) {
			for (j = 0; j < field[i].length-1; j++) {
				out.print(field[i][j] + ",");
			}
			out.println(field[i][j]);
		}
		out.println("end");
		out.println(srcY + "," + srcX);
		out.println(dstY + "," + dstX);
		out.flush();
		try {
			// get the result from solver server (directions delimited by ,)
			String response=in.readLine();
			System.out.println(response);
			directions = response.split(",");
			setChanged();
			notifyObservers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getShortestPath() {
		return directions;
	}
}
