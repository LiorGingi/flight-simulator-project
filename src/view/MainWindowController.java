package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowController {
	@FXML
	private Button backToMain;
	@FXML
	private Button connectServerBtn;
	@FXML
	private Button openConnectWindow;
	@FXML
	private GroundConditionDisplayer groundConditionDisplayer;
	
	@FXML
	private void handleConnectWindow(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		
		if(event.getSource()==openConnectWindow) {
			stage=(Stage)openConnectWindow.getScene().getWindow();
			root=FXMLLoader.load(getClass().getResource("ConnectPopup.fxml"));
		}else {
			stage=(Stage)backToMain.getScene().getWindow();
			root=FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		}
		
		stage.setScene(new Scene(root));
		stage.show();
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
				while ((line = br.readLine()) != null) {
					valuesTable.add(line.split(","));
				}
				int rowSize = valuesTable.get(0).length;
				valuesInDouble = new double[valuesTable.size()][rowSize];
				for (int i=0; i<valuesTable.size(); i++) {
					currentRow = valuesTable.get(i);
					for(int j=0; j<rowSize; j++) {
						double currentVal = Double.parseDouble(currentRow[j]);
						valuesInDouble[i][j] = currentVal;
						if(min == null || currentVal < min) {
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
			retValues.put("table", valuesInDouble);;
			retValues.put("minMax", minMax);
			groundConditionDisplayer.setGroundField(retValues);
		}
		
	}
}
