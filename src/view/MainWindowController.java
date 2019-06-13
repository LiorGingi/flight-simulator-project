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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import view_model.ViewModel;

public class MainWindowController implements Observer {
	ViewModel viewModel;
	// local variables for joystick position
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;

	@FXML
	private Button backToMain;
	@FXML
	private Button connectServerBtn;
	@FXML
	private Button openConnectWindow;
	@FXML
	private GroundConditionDisplayer groundConditionDisplayer;
	@FXML
	private PathDisplayer pathDisplayer;
	@FXML
	private Circle joystick;
	@FXML
	private Circle frameCircle;

	public void setViewModel( ViewModel vm) {
		viewModel=vm;
	}
	
	@FXML
	private void openConnectWindow(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConnectPopup.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	@FXML
	private void closeConnectWindow(ActionEvent event) throws IOException {
		Stage stage = (Stage) backToMain.getScene().getWindow();
		stage.close();
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
//				first 2 lines
//				line = br.readLine(); 
//				line = br.readLine();
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

			HashMap<String, double[][]> retValues = new HashMap<>();
			double[][] minMax = new double[1][2];
			minMax[0][0] = min;
			minMax[0][1] = max;
			retValues.put("table", valuesInDouble);
			retValues.put("minMax", minMax);
			groundConditionDisplayer.setGroundField(retValues);
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

		float normalX = (float) ((((newTranslateX - contractionsCenterX) / (maxX - contractionsCenterX)) * 2) - 1); // normalize
																													// to
																													// range
																													// of
																													// [-1,1]
		float normalY = (float) ((((newTranslateY - contractionsCenterY) / (maxY - contractionsCenterY)) * 2) - 1); // normalize
																													// to
																													// range
																													// of
																													// [-1,1]
		System.out.println("" + normalX + " " + normalY);
	}

	@FXML
	private void joystickReleased(MouseEvent me) {
		((Circle) (me.getSource()))
				.setTranslateX(frameCircle.getTranslateX() + frameCircle.getRadius() - joystick.getRadius());
		((Circle) (me.getSource()))
				.setTranslateY(frameCircle.getTranslateY() - frameCircle.getRadius() - joystick.getRadius());
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
