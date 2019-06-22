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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.PathModel;
import models.SimModel;

public class ViewModel extends Observable implements Observer {
	private double currentLongtitude;
	private double currentLatitude;
	// models
	private PathModel pm;
	private SimModel sm;
	/* ***data members related to view*** */
	// ** simulator **
	// connect button
	public StringProperty simulatorIP;
	public StringProperty simulatorPort;
	// throttle & rudder
	public DoubleProperty throttle;
	public DoubleProperty rudder;
	// joystick arguments
	public StringProperty aileron;
	public StringProperty elevator;
	// script text
	public StringProperty script;
	// plane location & destination -- data from simulator

	public DoubleProperty csv_srcX, csv_srcY, csv_scale;
	public IntegerProperty csv_rows, csv_cols;
	public ObjectProperty<ImageView> plane;

	// ** path **
	public StringProperty solverIP;
	public StringProperty solverPort;
	// data from the view
	public DoubleProperty destX;
	public DoubleProperty destY;
	public ObjectProperty<double[][]> ground;
	public ObjectProperty<String[]> directions;
	public DoubleProperty groundCellW, groundCellH;

	// *** end of variables
	public ViewModel(PathModel pm, SimModel sm) {
		this.pm = pm;
		this.sm = sm;
		simulatorIP = new SimpleStringProperty();
		simulatorPort = new SimpleStringProperty();
		throttle = new SimpleDoubleProperty();
		rudder = new SimpleDoubleProperty();
		aileron = new SimpleStringProperty();
		elevator = new SimpleStringProperty();
		script = new SimpleStringProperty();

		csv_srcX = new SimpleDoubleProperty();
		csv_srcY = new SimpleDoubleProperty();
		csv_scale = new SimpleDoubleProperty();
		csv_rows = new SimpleIntegerProperty();
		csv_cols = new SimpleIntegerProperty();

		groundCellH = new SimpleDoubleProperty();
		groundCellW = new SimpleDoubleProperty();

		solverIP = new SimpleStringProperty();
		solverPort = new SimpleStringProperty();
		destX = new SimpleDoubleProperty();
		destY = new SimpleDoubleProperty();
		plane = new SimpleObjectProperty<>(new ImageView());
		plane.get().setImage(new Image("/resources/airplane.png"));
		plane.get().setVisible(false);
		ground = new SimpleObjectProperty<>();
		String[] s = { "" };
		directions = new SimpleObjectProperty<>(s);

		openServer();
		System.out.println("opened server");
	}

	private void openServer() {
		sm.openDataServer(5400, 10);
	}

	public void connectToSimulator() {
		sm.connectToSimulator(simulatorIP.get(), Integer.parseInt(simulatorPort.get()));
		sm.dumpPosition(simulatorIP.get(), Integer.parseInt(simulatorPort.get()));
	}

	public void sendScriptToSimulator() {
		sm.sendScript(script.get());
	}

	public void setThrottle() {
		sm.setThrottle(throttle.get());
	}

	public void setRudder() {
		sm.setRudder(rudder.get());
	}

	public void setJoystickChanges() {
		sm.setAileron(Double.parseDouble(aileron.get()));
		sm.setElevator(Double.parseDouble(elevator.get()));
	}

	public void connectToSolverServer() {
		pm.connectToSolverServer(solverIP.get(), Integer.parseInt(solverPort.get()));
	}

	public void calcShortestPath() {
		int destX_index = (int) (destX.get() / groundCellW.get());
		int destY_index = (int) (destY.get() / groundCellH.get());
		int planeX_index = (int) (plane.get().getX() / groundCellW.get());
		int planeY_index = (int) (plane.get().getY() / groundCellH.get());
		pm.calcShortestPath(ground.get(), planeX_index, planeY_index, destX_index, destY_index);
	}

	private void updatePlanePosition() {
		/*
		 * array indexes: longitude_deg x | latitude_deg y | altitude_ft | ground_elev_m
		 * | ground_elev_ft
		 */
		double[] position = sm.getPlaneLocation();
		double longitude_deg = position[0];
		double latitude_deg = position[1];
//		double altitude_ft=position[2];
//		double ground_elev_m=position[3];
//		double ground_elev_ft=position[4];
		 
		int currentIndexX = (int) (((longitude_deg - csv_srcX.get())) / csv_scale.get() * groundCellW.get());
		int currentIndexY = (int) (((csv_srcY.get() - latitude_deg) / csv_scale.get()) * groundCellH.get());
		
		plane.get().setX(currentIndexX);
		plane.get().setY(currentIndexY);
		plane.get().setRotate(45);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == pm) {
			directions.set(pm.getShortestPath());
			setChanged();
			notifyObservers();
		}
		if (o == sm) {
			updatePlanePosition();
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
