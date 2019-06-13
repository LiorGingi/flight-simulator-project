package view_model;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.PathModel;
import models.SimModel;

public class ViewModel extends Observable implements Observer {
	
	private PathModel pm;
	private SimModel sm;
	public StringProperty ip;
	public IntegerProperty port;
	
	public ViewModel(PathModel pm, SimModel sm) {
		this.pm=pm;
		this.sm=sm;
		ip=new SimpleStringProperty();
		port=new SimpleIntegerProperty();
	}
	
	public void ConnectToSimulator() {
		sm.ConnectToSimulator(ip.get(),port.get());
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
