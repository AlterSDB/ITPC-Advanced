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

public class ITPC_Advanced extends Application {

	public static void main(String[] args) {
		launch(args);	
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
		Parent root = loader.load();

		MainView controller = loader.getController();
		MainViewModel viewModel = new MainViewModel();
		controller.setViewModel(viewModel);

		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/version.properties"));
		mainStage.setTitle("ITPC Advanced v" + properties.getProperty("version"));
		mainStage.getIcons().add(new Image("/images/logo.png"));
		mainStage.setScene(new Scene(root));
		mainStage.setResizable(false);
	//	mainStage.setOnCloseRequest((event) -> {
		//		if(settingsStage != null) {
	//				settingsStage.close();
	//			}
	//	});

		mainStage.show();
	}

}