package view;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view_model.ViewModel;

public class MainWindowController implements Observer {
	ViewModel viewModel;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	Circle destCircle;


	@FXML
	private Button openConnectWindow;
	@FXML
	private Button calculatePathBtn;
	@FXML
	private TopographicMapDisplayer topographicMapDisplayer;
	@FXML
	private TopographicColorRangeDisplayer topographicColorRangeDisplayer;
	@FXML
	private PathDisplayer pathDisplayer;
	@FXML
	private TextArea simScript;
	@FXML
	private Label connectDataErrorMsg;
	@FXML
	private Label connectMode;
	@FXML
	private Label minHeight;
	@FXML
	private Label maxHeight;
	@FXML
	private Group mapGroup;
	
	//Connect to server window
	@FXML
	private Button backToMain;
	@FXML
	private Button connectServerBtn;
	@FXML
	private TextField simServerIp;
	@FXML
	private TextField simServerPort;
	
	//Manual mode objects (slider + joystick)
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
	
	//Objects for manual mode data panel
	@FXML
	private Label aileronValue;
	@FXML
	private Label elevatorValue;
	@FXML
	private Label throttleValue;
	@FXML
	private Label rudderValue;

	
	public void setViewModel( ViewModel vm) {
		viewModel=vm;
	}
	
	@FXML
	private void openConnectWindow(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConnectPopup.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(Main.primaryStage);
		stage.setScene(new Scene(root));
		stage.show();
		if(event.getSource() == openConnectWindow) {
			stage.setTitle("Simulator Server");
		} else if (event.getSource() == calculatePathBtn) {
			stage.setTitle("Calculate Path");
		}
		
	}
	
	@FXML
	private void closeConnectWindow(ActionEvent event) throws IOException {
		Stage stage = (Stage) backToMain.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleConnect(ActionEvent event) throws IOException {
		String ip = simServerIp.getText();
		String port = simServerPort.getText();
		String mode = ((Stage) connectServerBtn.getScene().getWindow()).getTitle();

		if(ip.matches("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$") && port.matches("^(\\d{1,4})")) {
			
			if(mode == "Simulator Server") {
				//handle connection for connecting to the simulator server
			} else if(mode == "Calculate Path") {
				//handle connection for calculating path
			}
			
			//need to handle connect to server
			closeConnectWindow(event);
		}
		else {
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
				double startX = Double.parseDouble(coordinates[0]);
				double startY = Double.parseDouble(coordinates[1]);
				double space = Double.parseDouble(br.readLine().split(",")[0]);
				while ((line = br.readLine()) != null) {
					valuesTable.add(line.split(","));
				}
				int rowSize = valuesTable.get(0).length;
				valuesInDouble = new double[valuesTable.size()][rowSize];
				for (int i = 0; i < valuesTable.size(); i++) {
					currentRow = valuesTable.get(i);
					for (int j = 0; j < rowSize; j++) {
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
			} catch (Exception e) {
				e.printStackTrace();
			}

			topographicMapDisplayer.setGroundField(min, max, valuesInDouble);
			topographicColorRangeDisplayer.setColorRange(min, max);
			minHeight.setText(""+min);
			maxHeight.setText(""+max);
		}

	}
	
	@FXML
	private void loadScript() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose CSV file");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fc.getExtensionFilters().add(extFilter);
		File file = fc.showOpenDialog(null);
		String script="";
		String line;
		if(file != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null) {
					script=script+line+"\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			simScript.setText(script);
		}
	}

	@FXML
	private void joystickPressed(MouseEvent me) {
		orgSceneX = me.getSceneX();
		orgSceneY = me.getSceneY();
		orgTranslateX = ((Circle) (me.getSource())).getTranslateX();
		orgTranslateY = ((Circle) (me.getSource())).getTranslateY();
	}

	@FXML
	private void joystickDragged(MouseEvent me) {
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

		double normalX = Math.round(((((newTranslateX - contractionsCenterX) / (maxX - contractionsCenterX)) * 2) - 1)*100.00)/100.00; // normalize to range of [-1,1]
		double normalY = Math.round(((((newTranslateY - contractionsCenterY) / (maxY - contractionsCenterY)) * 2) - 1)*100.00)/100.00; // normalize to range of [-1,1]
		System.out.println("" + normalX + " " + normalY);
		
		if(manualMode.isSelected()) {
			//send command only if manual mode is selected
			aileronValue.setText(""+normalX);
			elevatorValue.setText(""+normalY);
		}
	}

	@FXML
	private void joystickReleased(MouseEvent me) {
		((Circle) (me.getSource()))
				.setTranslateX(frameCircle.getTranslateX() + frameCircle.getRadius() - joystick.getRadius());
		((Circle) (me.getSource()))
				.setTranslateY(frameCircle.getTranslateY() - frameCircle.getRadius() - joystick.getRadius());
		
		if(manualMode.isSelected()) {
			//send command only if manual mode is selected
			aileronValue.setText(""+0);
			elevatorValue.setText(""+0);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	private void mapMouseClick(MouseEvent event) throws IOException {
		if(TopographicMapDisplayer.mapLoaded
				&& event.getX() >= 5 && event.getX() <= 345 //need to think about a better solution for the boundaries design bug
				&& event.getY() >= 5 && event.getY() <= 295) {
			
			Circle circle = new Circle(5, Color.BLACK);
			circle.setCenterX(event.getX());
			circle.setCenterY(event.getY());
			mapGroup.getChildren().remove(destCircle);
			mapGroup.getChildren().add(destCircle = circle);
			topographicMapDisplayer.calculateCellOnMap(event.getX(), event.getY());
		}
	}
	
	@FXML
	private void sliderDrag(MouseEvent me) {
		if(manualMode.isSelected()) {
			if(me.getSource() == rudderSlider) {
				//send command for rudder
				rudderValue.setText(""+(Math.round((rudderSlider.getValue()*10.00)))/10.00); //round to the closest decimal
			} else if(me.getSource() == throttleSlider) {
				//send command for throttle
				throttleValue.setText(""+(Math.round((throttleSlider.getValue()*10.00)))/10.00); //round to the closest decimal
			}
		}
	}
	
	@FXML
	private void radioButtonClicked() {
		if(manualMode.isSelected()) {
			rudderValue.setText(""+(Math.round((rudderSlider.getValue()*10.00)))/10.00); //round to the closest decimal
			throttleValue.setText(""+(Math.round((throttleSlider.getValue()*10.00)))/10.00); //round to the closest decimal
			aileronValue.setText(""+0);
			elevatorValue.setText(""+0);
		} else {
			rudderValue.setText("");
			throttleValue.setText("");
			aileronValue.setText("");
			elevatorValue.setText("");
		}
	}
}
