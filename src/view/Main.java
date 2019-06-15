package view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.PathModel;
import models.SimModel;
import view_model.ViewModel;


public class Main extends Application {
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		//create models and view-model
		PathModel pathModel=new PathModel();
		SimModel simModel=new SimModel();
		ViewModel viewModel=new ViewModel(pathModel, simModel);
		pathModel.addObserver(viewModel);
		simModel.addObserver(viewModel);
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root,1000,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
