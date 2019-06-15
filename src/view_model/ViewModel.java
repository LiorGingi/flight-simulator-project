package view_model;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.PathModel;
import models.SimModel;

public class ViewModel extends Observable implements Observer {
	// models
	private PathModel pm;
	private SimModel sm;
	/* ***data members related to view*** */
	// ** simulator **
	// connect button
	public StringProperty simulatorIP;
	public StringProperty simulatorPort;
	// throttle & rudder
	public StringProperty throttle;
	public StringProperty rudder;
	// joystick arguments
	public StringProperty aileron;
	public StringProperty elevator;
	// script text
	public StringProperty script;
	// plane location & destination -- data from simulator
	public DoubleProperty longitude_deg;
	public DoubleProperty latitude_deg;
	public DoubleProperty altitude_ft;
	public DoubleProperty ground_elev_m;
	public DoubleProperty ground_elev_ft;

	// ** path **
	public StringProperty solverIP;
	public StringProperty solverPort;
	public IntegerProperty destX;
	public IntegerProperty destY;
	public IntegerProperty srcX;
	public IntegerProperty srcY;
	public ObjectProperty<double[][]> ground;
	public ObjectProperty<String[]> directions;

	// *** end of variables
	public ViewModel(PathModel pm, SimModel sm) {
		this.pm = pm;
		this.sm = sm;
		simulatorIP = new SimpleStringProperty();
		simulatorPort = new SimpleStringProperty();
		throttle = new SimpleStringProperty();
		rudder = new SimpleStringProperty();
		aileron = new SimpleStringProperty();
		elevator = new SimpleStringProperty();
		script = new SimpleStringProperty();

		longitude_deg = new SimpleDoubleProperty();
		latitude_deg = new SimpleDoubleProperty();
		altitude_ft = new SimpleDoubleProperty();
		ground_elev_m = new SimpleDoubleProperty();
		ground_elev_ft = new SimpleDoubleProperty();

		solverIP = new SimpleStringProperty();
		solverPort = new SimpleStringProperty();
		destX = new SimpleIntegerProperty();
		destY = new SimpleIntegerProperty();
		srcX = new SimpleIntegerProperty();
		srcY = new SimpleIntegerProperty();
		ground = new SimpleObjectProperty<>();
		directions = new SimpleObjectProperty<>();
	}

	public void ConnectToSimulator() {
		sm.connectToSimulator(simulatorIP.get(), Integer.parseInt(simulatorPort.get()));
		sm.dumpPosition(simulatorIP.get(), Integer.parseInt(simulatorPort.get()));
	}

	public void sendScriptToSimulator() {
		sm.sendScript(script.get());
	}

	public void setThrottle() {
		sm.setThrottle(Double.parseDouble(throttle.get()));
	}

	public void setRudder() {
		sm.setRudder(Double.parseDouble(rudder.get()));
	}

	public void setJoystickChanges() {
		sm.setAileron(Double.parseDouble(aileron.get()));
		sm.setElevator(Double.parseDouble(elevator.get()));
	}

	public void connectToSolverServer() {
		pm.connectToSolverServer(solverIP.get(), Integer.parseInt(solverPort.get()));
	}

	public void calcShortestPath() {
		pm.calcShortestPath(ground.get(), srcX.get(), srcY.get(), destX.get(), destY.get());
	}

	private void getPosition() {
		/*
		 * array indexes: longitude_deg | latitude_deg | altitude_ft | ground_elev_m |
		 * ground_elev_ft
		 */
		double[] position = sm.getPlaneLocation();
		longitude_deg.set(position[0]);
		latitude_deg.set(position[1]);
		altitude_ft.set(position[2]);
		ground_elev_m.set(position[3]);
		ground_elev_ft.set(position[4]);

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == pm) {
			directions.set(pm.getShortestPath());
		}
		if (o == sm) {
//			get the arguments
			getPosition();
		}
	}

//	public static void main(String[] args) {
//		PathModel pm = new PathModel();
//		SimModel sm = new SimModel();
//		ViewModel vm = new ViewModel(pm, sm);
//
//		vm.script.set("openDataServer (540* 10) 1 +  9");
//		vm.sendScriptToSimulator();
//
//		vm.simulatorIP.set("127.0.0.1");
//		vm.simulatorPort.set("5402");
//		vm.ConnectToSimulator();
//
//		String script = "var breaks = bind \"/controls/flight/speedbrake\"" + System.lineSeparator()
//				+ "var throttle = bind \"/controls/engines/current-engine/throttle\"" + System.lineSeparator()
//				+ "var heading = bind /instrumentation/heading-indicator/indicated-heading-deg" + System.lineSeparator()
//				+ "var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt" + System.lineSeparator()
//				+ "var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg" + System.lineSeparator()
//				+ "var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg" + System.lineSeparator()
//				+ "var rudder = bind /controls/flight/rudder" + System.lineSeparator()
//				+ "var aileron = bind /controls/flight/aileron" + System.lineSeparator()
//				+ "var elevator = bind /controls/flight/elevator" + System.lineSeparator()
//				+ "var alt = bind /instrumentation/altimeter/indicated-altitude-ft" + System.lineSeparator()
//				+ "breaks = 0" + System.lineSeparator() + "throttle = 1" + System.lineSeparator() + "var h0 = heading"
//				+ System.lineSeparator() + "while alt < (200*2+100)*2  {" + System.lineSeparator()
//				+ "rudder = (h0 - heading)/20" + System.lineSeparator() + "aileron = - roll / 70"
//				+ System.lineSeparator() + "elevator = pitch / 50" + System.lineSeparator() + "sleep 50"
//				+ System.lineSeparator() + "}" + System.lineSeparator() + "print done";
//		vm.script.set(script);
//		vm.sendScriptToSimulator();
//		System.out.println("done");
//
//	}

}
