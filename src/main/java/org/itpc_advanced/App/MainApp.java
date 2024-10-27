package org.itpc_advanced.App;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;


public class MainApp extends Application {
    private static Stage mainStage;
    private static Stage settingsStage;
    
    @Override
    public void start(Stage s) throws IOException {
        mainStage = s;
        setRoot("scene1","ITPC Advanced");
    }

    static void setRoot(String fxml) throws IOException {
        setRoot(fxml,mainStage.getTitle());
    }

    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        mainStage.setTitle(title);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				if(settingsStage != null) {
					System.out.println("Закрываю настройки...");
					settingsStage.close();
				}
				
			}
		});
        mainStage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch(args);
        if(Device.thread != null)
        	Device.thread.interrupt();
    }

	public static void callSettingsWindow() throws IOException {
		if(settingsStage != null && settingsStage.isShowing()) {
			settingsStage.close();
			return;
		}
		
		settingsStage = new Stage();
	    Parent parent = loadFXML("settings");
		Scene scene = new Scene(parent);
		settingsStage.setScene(scene);
		settingsStage.setTitle("Настройки");
		settingsStage.setResizable(false);
		settingsStage.setAlwaysOnTop(true);
		settingsStage.show();	
	}

}
