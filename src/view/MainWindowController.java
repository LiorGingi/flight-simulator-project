package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainWindowController {
	@FXML
	private Button backToMain;
	@FXML
	private Button connectServerBtn;
	@FXML
	private Button openConnectWindow;
	
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
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
			
	}
}
