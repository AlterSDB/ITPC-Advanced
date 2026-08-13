package org.itpc_advanced;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Properties;

import org.itpc_advanced.model.TemperatureStatsDatabase;
import org.itpc_advanced.view.InputController;
import org.itpc_advanced.view.LayoutController;
import org.itpc_advanced.view.OutputController;
import org.itpc_advanced.viewmodel.MainViewModel;


public class App extends Application {

	public static void main(String[] args) {
		launch(args);	
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		MainViewModel viewModel = new MainViewModel(new TemperatureStatsDatabase());
		
		FXMLLoader loaderLayout = new FXMLLoader(getClass().getResource("/fxml/layout.fxml"));	
		FXMLLoader loaderInput = new FXMLLoader(getClass().getResource("/fxml/input.fxml"));	
		FXMLLoader loaderOutput = new FXMLLoader(getClass().getResource("/fxml/output.fxml"));
		
		setViewModel(loaderLayout, viewModel);
		setViewModel(loaderInput, viewModel);
		setViewModel(loaderOutput, viewModel);
		
		Parent root = loaderLayout.load();		
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/version.properties"));
		mainStage.setTitle("ITPC Advanced v" + properties.getProperty("version"));
		mainStage.getIcons().add(new Image("/images/logo.png"));
		mainStage.setScene(new Scene(root));
		mainStage.setResizable(true);
		mainStage.show();
	}

	private void setViewModel(FXMLLoader loaderLayout, MainViewModel viewModel) {
		loaderLayout.setControllerFactory((controllerClass) -> {
			if (controllerClass == LayoutController.class) return new LayoutController(viewModel);
			if (controllerClass == InputController.class) return new InputController(viewModel);
			if (controllerClass == OutputController.class) return new OutputController(viewModel);
			return null;
		});
	}
	
	
	
	
	
	
	

}