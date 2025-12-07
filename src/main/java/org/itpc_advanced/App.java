package org.itpc_advanced;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Properties;

import org.itpc_advanced.view.MainView;
import org.itpc_advanced.viewmodel.MainViewModel;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);	
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
		Parent root = mainLoader.load();

		MainView mainController = mainLoader.getController();
		MainViewModel viewModel = new MainViewModel();
		mainController.setViewModel(viewModel);

		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/version.properties"));
		mainStage.setTitle("ITPC Advanced v" + properties.getProperty("version"));
		mainStage.getIcons().add(new Image("/images/logo.png"));
		mainStage.setScene(new Scene(root));
		mainStage.setResizable(false);
		mainStage.show();
	}

}