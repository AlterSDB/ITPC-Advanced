package org.itpc_advanced;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

import org.itpc_advanced.view.MainView;
import org.itpc_advanced.viewmodel.MainViewModel;

public class ITPC_Advanced extends Application {

	private static Stage settingsStage;

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
		mainStage.setOnCloseRequest((event) -> {
				if(settingsStage != null) {
					settingsStage.close();
				}
		});

		mainStage.show();
	}

	public void callSettingsWindow() throws IOException {
		if (settingsStage != null && settingsStage.isShowing()) {
			settingsStage.close();
			return;
		}

		settingsStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		settingsStage.setScene(scene);
		settingsStage.setTitle("Настройки");
		settingsStage.setResizable(false);
		settingsStage.setAlwaysOnTop(true);
		settingsStage.getIcons().add(new Image("/images/logo.png"));
		settingsStage.show();
	}

}