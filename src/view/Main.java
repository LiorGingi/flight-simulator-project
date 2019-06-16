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
	public void start(Stage _primaryStage) {
		primaryStage = _primaryStage;
		
		//***create models and view-model connections***
		//models
		PathModel pathModel=new PathModel();
		SimModel simModel=new SimModel();
		//view model
		
		ViewModel viewModel=new ViewModel(pathModel, simModel);
		pathModel.addObserver(viewModel);
		simModel.addObserver(viewModel);
		
		try {
			FXMLLoader fxml=new FXMLLoader();
			BorderPane root = fxml.load(getClass().getResource("MainWindow.fxml").openStream());
			MainWindowController mainWindowC=fxml.getController();//view
			mainWindowC.setViewModel(viewModel);
			viewModel.addObserver(mainWindowC);
			Scene scene = new Scene(root,1000,500);

//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
