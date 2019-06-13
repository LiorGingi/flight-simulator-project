package models;

import java.util.Observable;
import java.util.Observer;

import interpreter.Interpreter;
import interpreter.MyInterpreter;

public class SimModel extends Observable implements Observer{

	private Interpreter inter;
	
	public SimModel() {
		inter = new MyInterpreter();
	}
	
	public void sendScript(String script) {
		inter.interpret(script);
	}
	public void ConnectToSimulator(String ip, int port) {
		inter.interpret("connect "+ ip+" " + port);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	

	
	

}
