package models;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import interpreter.ActiveUpdater;

public class PathModel extends Observable{
	
	private Thread thread;
	private Socket socketToSimServer;
	private PrintWriter serverOutput;
	private BufferedReader serverInput;
	
	private class PositionDataGetter extends TimerTask{
		private String position;
		@Override
		public void run() {
			String line;
			try {
				XMLEncoder encoder=new XMLEncoder(socketToSimServer.getOutputStream());
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			serverOutput.print("dump position");
			try {
				while((line = serverInput.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {	
				e.printStackTrace();
			}
		}
		
	}
	
	public void run() {
		System.out.println("I'm here");
		thread = new Thread(()-> {
			try {
				socketToSimServer = new Socket("127.0.0.1", 5402);
				serverOutput = new PrintWriter(socketToSimServer.getOutputStream());
				serverInput = new BufferedReader(new InputStreamReader(socketToSimServer.getInputStream()));
				Timer t = new Timer();
				PositionDataGetter positionGetter=new PositionDataGetter();
				t.scheduleAtFixedRate(positionGetter, 0, 1000/4);
				positionGetter.cancel();
				t.cancel();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
