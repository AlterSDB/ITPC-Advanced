package org.itpc_advanced;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Properties;

import org.itpc_advanced.view.MainView;
import org.itpc_advanced.viewmodel.MainViewModel;



public class ITPC_Advanced extends Application {

	private static Stage mainStage;
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
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if(settingsStage != null) {
					settingsStage.close();
				}
			}
		});
		mainStage.show();
		
		
	}
	
	public void callSettingsWindow() throws IOException {
		if(settingsStage != null && settingsStage.isShowing()) {
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

	/*@Override
	public void start(Stage s) throws IOException {
		mainStage = s;
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/version.properties"));
		String name = "ITPC Advanced v" + properties.getProperty("version"); 
		setRoot("main", name);
	}

	static void setRoot(String fxml) throws IOException {
		setRoot(fxml, mainStage.getTitle());
	}

	static void setRoot(String fxml, String title) throws IOException {
		Scene scene = new Scene(loadFXML(fxml));
		mainStage.setTitle(title);
		mainStage.getIcons().add(new Image("/images/logo.png"));
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if(settingsStage != null) {
					settingsStage.close();
				}
			}
		});
		mainStage.show();
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(ITPC_Advanced.class.getResource("/fxml/"+fxml + ".fxml"));
		///Parent) new FXMLLoader(ITPC_Advanced.class.getResource("/fxml/"+fxml + ".fxml")).load();
		return fxmlLoader.load();
	}
	
	*/
	
	

}
