package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view_model.ViewModel;

public class MainWindowController implements Observer {
	private ViewModel viewModel;
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private Circle destCircle;

	// MVVM Variables
	private DoubleProperty destX, destY;
	private StringProperty aileronV, elevatorV;
	private DoubleProperty csv_srcX, csv_srcY, csv_scale;
	private IntegerProperty csv_rows, csv_cols;
	private ObjectProperty<ImageView> plane;
	private ObjectProperty<String[]> directions;
	private ObjectProperty<double[][]> ground;
	private DoubleProperty gridCellH, gridCellW;

	// Autopilot mode
	@FXML
	private TextArea simScript;
	@FXML
	private RadioButton autopilotMode;

	// Topographic Map
	@FXML
	private Button openConnectWindow;
	@FXML
	private Button calculatePathBtn;
	@FXML
	private Button loadMapBtn;
	@FXML
	private TopographicMapDisplayer topographicMapDisplayer;
	@FXML
	private TopographicColorRangeDisplayer topographicColorRangeDisplayer;
	@FXML
	private Group mapGroup;
	@FXML
	private Label minHeight;
	@FXML
	private Label maxHeight;

	// Connect to server/path solver window
	@FXML
	private Button backToMain;
	@FXML
	private Button connectServerBtn;
	@FXML
	private TextField connectionIp;
	@FXML
	private TextField connectionPort;
	@FXML
	private TextField simServerIp;
	@FXML
	private TextField simServerPort;
	@FXML
	private TextField pathServerIp;
	@FXML
	private TextField pathServerPort;
	@FXML
	private Label connectDataErrorMsg;

	// Manual mode objects (slider + joystick)
	@FXML
	private Slider rudderSlider;
	@FXML
	private Slider throttleSlider;
	@FXML
	private Circle joystick;
	@FXML
	private Circle frameCircle;
	@FXML
	private RadioButton manualMode;

	// Objects for manual mode data panel
	@FXML
	private Label aileronValue;
	@FXML
	private Label elevatorValue;
	@FXML
	private Label throttleValue;
	@FXML
	private Label rudderValue;

	public MainWindowController() {
		openConnectWindow = new Button();
		calculatePathBtn = new Button();
		loadMapBtn = new Button();
		topographicMapDisplayer = new TopographicMapDisplayer();
		topographicColorRangeDisplayer = new TopographicColorRangeDisplayer();
		simScript = new TextArea();
		connectDataErrorMsg = new Label();
		minHeight = new Label();
		maxHeight = new Label();
		mapGroup = new Group();
		backToMain = new Button();
		connectServerBtn = new Button();
		simServerIp = new TextField();
		simServerPort = new TextField();
		pathServerIp = new TextField();
		pathServerPort = new TextField();
		directions = new SimpleObjectProperty<>();
		ground = new SimpleObjectProperty<>();
		rudderSlider = new Slider();
		throttleSlider = new Slider();
		joystick = new Circle();
		frameCircle = new Circle();
		manualMode = new RadioButton();
		aileronValue = new Label();
		elevatorValue = new Label();
		aileronV = new SimpleStringProperty();
		elevatorV = new SimpleStringProperty();
		throttleValue = new Label();
		rudderValue = new Label();
		autopilotMode = new RadioButton();

		csv_srcX = new SimpleDoubleProperty();
		csv_srcY = new SimpleDoubleProperty();
		csv_scale = new SimpleDoubleProperty();
		csv_rows = new SimpleIntegerProperty();
		csv_cols = new SimpleIntegerProperty();

		gridCellH = new SimpleDoubleProperty();
		gridCellW = new SimpleDoubleProperty();

		plane = new SimpleObjectProperty<>();
		destX = new SimpleDoubleProperty();
		destY = new SimpleDoubleProperty();
	}

	public void setViewModel(ViewModel vm) {
		viewModel = vm;
		// *** sim model***
		// connection to simulator
//		viewModel.simulatorIP.bind(simServerIp.textProperty());
//		viewModel.simulatorPort.bind(simServerPort.textProperty());
		// autopilot
		viewModel.script.bind(simScript.textProperty());
		// manual
		viewModel.throttle.bind(throttleSlider.valueProperty());
		viewModel.rudder.bind(rudderSlider.valueProperty());
		viewModel.aileron.bind(aileronV);
		viewModel.elevator.bind(elevatorV);

		// current location of plane.
		viewModel.csv_srcX.bind(csv_srcX);
		viewModel.csv_srcY.bind(csv_srcY);
		viewModel.csv_scale.bind(csv_scale);
		viewModel.csv_rows.bind(csv_rows);
		viewModel.csv_cols.bind(csv_cols);
		plane.bind(viewModel.plane);
		mapGroup.getChildren().add(plane.get());
		// ***path model***
		viewModel.destX.bind(destX);
		viewModel.destY.bind(destY);
		directions.bind(viewModel.directions);
		viewModel.groundCellH.bind(gridCellH);
		viewModel.groundCellW.bind(gridCellW);
	}

	@FXML
	private void openConnectWindow(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(this);
		Parent root = (Parent) fxmlLoader.load(getClass().getResource("ConnectPopup.fxml").openStream());
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(Main.primaryStage);
		stage.setScene(new Scene(root));
		stage.show();
		if (event.getSource() == openConnectWindow) {
			stage.setTitle("Simulator Server");
		} else if (event.getSource() == calculatePathBtn) {
			stage.setTitle("Path Calculation Server");
		}
	}

	@FXML
	private void closeConnectWindow(ActionEvent event) throws IOException {
		Stage stage = (Stage) backToMain.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleConnect(ActionEvent event) throws IOException {
		String ip = connectionIp.getText();
		String port = connectionPort.getText();
		viewModel.simulatorIP.bind(simServerIp.textProperty());
		viewModel.simulatorPort.bind(simServerPort.textProperty());
		viewModel.solverIP.bind(pathServerIp.textProperty());
		viewModel.solverPort.bind(pathServerPort.textProperty());
		String mode = ((Stage) connectServerBtn.getScene().getWindow()).getTitle();

		if (ip.matches("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$") && port.matches("^(\\d{1,4})")) {

			if (mode == "Simulator Server") {
				// handle connection for connecting to the simulator server
				simServerIp.setText(ip);
				simServerPort.setText(port);
				viewModel.connectToSimulator();
				loadMapBtn.setVisible(true);
			} else if (mode == "Path Calculation Server") {
				// handle connection for calculating path
				pathServerIp.setText(ip);
				pathServerPort.setText(port);
				viewModel.connectToSolverServer();
			}

			// need to handle connect to server
			closeConnectWindow(event);
		} else {
			connectDataErrorMsg.setText("Invalid IP address or port, please try again.");
		}
	}

	@FXML
	private void loadData() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose CSV file");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		fc.getExtensionFilters().add(extFilter);
		File file = fc.showOpenDialog(null);
		if (file != null) {
			String line;
			Double min = null, max = null;
			ArrayList<String[]> valuesTable = new ArrayList<>();
			String[] currentRow;
			double[][] valuesInDouble = null;

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String[] coordinates = br.readLine().split(",");
				csv_srcX.set(Double.parseDouble(coordinates[0])); // x0
				csv_srcY.set(Double.parseDouble(coordinates[1])); // y0
				csv_scale.set(Double.parseDouble(br.readLine().split(",")[0]));
				while ((line = br.readLine()) != null) {
					valuesTable.add(line.split(","));
				}
				csv_rows.set(valuesTable.size());
				csv_cols.set(valuesTable.get(0).length);
				valuesInDouble = new double[csv_rows.get()][csv_cols.get()];
				for (int i = 0; i < csv_rows.get(); i++) {
					currentRow = valuesTable.get(i);
					for (int j = 0; j < csv_cols.get(); j++) {
						double currentVal = Double.parseDouble(currentRow[j]);
						valuesInDouble[i][j] = currentVal;
						if (min == null || currentVal < min) {
							min = currentVal;
						}
						if (max == null || currentVal > max) {
							max = currentVal;
						}
					}
				}
				br.close();
				topographicMapDisplayer.setGroundField(min, max, valuesInDouble);
				gridCellH.set(topographicMapDisplayer.getCellH());
				gridCellW.set(topographicMapDisplayer.getCellW());
				ground.set(topographicMapDisplayer.getGroundField());
				viewModel.ground.bind(ground);
				topographicColorRangeDisplayer.setColorRange(min, max);
				minHeight.setText("" + min);
				maxHeight.setText("" + max);
				calculatePathBtn.setVisible(true);
				plane.get().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@FXML
	private void loadScript() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose CSV file");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fc.getExtensionFilters().add(extFilter);
		File file = fc.showOpenDialog(null);
		String script = "";
		String line;
		if (file != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null) {
					script = script + line + "\n";
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			simScript.setText(script);
		}
	}

	@FXML
	private void runScript() {
		if (simScript.getText().length() != 0)
			viewModel.sendScriptToSimulator();
		simScript.clear();
	}

	@FXML
	private void joystickPressed(MouseEvent me) {
		if (manualMode.isSelected()) {
			orgSceneX = me.getSceneX();
			orgSceneY = me.getSceneY();
			orgTranslateX = ((Circle) (me.getSource())).getTranslateX();
			orgTranslateY = ((Circle) (me.getSource())).getTranslateY();
		}
	}

	@FXML
	private void joystickDragged(MouseEvent me) {
		if (manualMode.isSelected()) {
			double offsetX = me.getSceneX() - orgSceneX;
			double offsetY = me.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;
			double joystickCenterX = frameCircle.getTranslateX() + frameCircle.getRadius() - joystick.getRadius();
			double joystickCenterY = frameCircle.getTranslateY() - frameCircle.getRadius() - joystick.getRadius();
			double frameRadius = frameCircle.getRadius();
			double maxX = joystickCenterX + frameRadius;
			double contractionsCenterX = joystickCenterX - frameRadius;
			double maxY = joystickCenterY - frameRadius;
			double contractionsCenterY = joystickCenterY + frameRadius;

			double slant = Math
					.sqrt(Math.pow(newTranslateX - joystickCenterX, 2) + Math.pow(newTranslateY - joystickCenterY, 2));

			if (slant > frameRadius) {
				double alpha = Math.atan((newTranslateY - joystickCenterY) / (newTranslateX - joystickCenterX));
				if ((newTranslateX - joystickCenterX) < 0) {
					alpha = alpha + Math.PI;
				}
				newTranslateX = Math.cos(alpha) * frameRadius + orgTranslateX;
				newTranslateY = Math.sin(alpha) * frameRadius + orgTranslateY;
			}
			((Circle) (me.getSource())).setTranslateX(newTranslateX);
			((Circle) (me.getSource())).setTranslateY(newTranslateY);
			// normalize to range of [-1,1]
			double normalX = Math
					.round(((((newTranslateX - contractionsCenterX) / (maxX - contractionsCenterX)) * 2) - 1) * 100.00)
					/ 100.00;
			// normalize to range of [-1,1]
			double normalY = Math
					.round(((((newTranslateY - contractionsCenterY) / (maxY - contractionsCenterY)) * 2) - 1) * 100.00)
					/ 100.00;
			// send command only if manual mode is selected
			aileronValue.setText("" + normalX);
			elevatorValue.setText("" + normalY);
			aileronV.set("" + normalX);
			elevatorV.set("" + normalY);

			viewModel.setJoystickChanges();
		}

	}

	@FXML
	private void joystickReleased(MouseEvent me) {
		if (manualMode.isSelected()) {
			((Circle) (me.getSource()))
					.setTranslateX(frameCircle.getTranslateX() + frameCircle.getRadius() - joystick.getRadius());
			((Circle) (me.getSource()))
					.setTranslateY(frameCircle.getTranslateY() - frameCircle.getRadius() - joystick.getRadius());

			aileronValue.setText("" + 0);
			elevatorValue.setText("" + 0);
			aileronV.set("0.0");
			elevatorV.set("0.0");

			// update that the value changed
			viewModel.setJoystickChanges();
		}
	}

	// update the location of the plane on the map
	@Override
	public void update(Observable o, Object arg) {
		paintPath();
	}

	@FXML
	private void mapMouseClick(MouseEvent event) throws IOException {
		if (TopographicMapDisplayer.mapLoaded && event.getX() >= 5 && event.getX() <= 345 // need to think about a
																							// better solution for the
																							// boundaries design bug
				&& event.getY() >= 5 && event.getY() <= 295) {

			Circle circle = new Circle(5, Color.BLACK);
			circle.setCenterX(event.getX()); // coordinates on the map
			circle.setCenterY(event.getY());
			mapGroup.getChildren().remove(destCircle);
			mapGroup.getChildren().add(destCircle = circle);
			destX.set(event.getX()); // cells on grid
			destY.set(event.getY());
			viewModel.calcShortestPath();
		}
	}

	@FXML
	private void radioButtonClicked() {
		if (manualMode.isSelected()) {
			rudderValue.setText("" + (Math.round((rudderSlider.getValue() * 10.00))) / 10.00); // round to the closest
																								// decimal
			throttleValue.setText("" + (Math.round((throttleSlider.getValue() * 10.00))) / 10.00); // round to the
																									// closest decimal
			aileronValue.setText("" + 0);
			elevatorValue.setText("" + 0);
		} else {
			rudderValue.setText("");
			throttleValue.setText("");
			aileronValue.setText("");
			elevatorValue.setText("");
			rudderSlider.setValue(0);
			throttleSlider.setValue(0);
		}
	}

	@FXML
	private void paintPath() {
		if (directions.get() != null)
			topographicMapDisplayer.paintPath(directions.get(), mapGroup, plane.get().getX(), plane.get().getY());
	}

	public void setSliderOnDragEvent() {
		rudderSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				if (manualMode.isSelected()) {
					rudderValue.textProperty().setValue("" + (Math.round((rudderSlider.getValue() * 10.00))) / 10.00);
					viewModel.setRudder();
				}
			}
		});

		throttleSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				if (manualMode.isSelected()) {
					throttleValue.textProperty()
							.setValue("" + (Math.round((throttleSlider.getValue() * 10.00))) / 10.00);
					viewModel.setThrottle();
				}
			}
		});
	}

}
