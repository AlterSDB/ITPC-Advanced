package org.itpc_advanced.App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jssc.SerialPortList;

public class SettingsFXMLController {

	@FXML	private AnchorPane ap;
	@FXML	private MenuButton selectPortMenu;
	@FXML	private CheckBox automaticTargetCheck;
	@FXML	private CheckBox autoLoadCheck;
	@FXML	private CheckBox multipleChartsCheck;
	@FXML	private CheckBox shuffleCheck;
	@FXML	private TextField timeStepField;
	@FXML	private TextField timeoutField;
	@FXML	private Button saveButton;
			private Settings settings;
			private String   timeout;
			private String   timeStep;

	@FXML
	void initialize() {
		settings = Settings.getInstance();
		
		if(settings.isMultipleCharts()) {
			multipleChartsCheck.setSelected(true);
		}
		if(settings.isAutomaticTarget()) {
			automaticTargetCheck.setSelected(true);
		}
		if(settings.isAutoLoad()) {
			autoLoadCheck.setSelected(true);
		}
		if(settings.isShuffleValues()) {
			shuffleCheck.setSelected(true);
		}
		
		timeStep = Double.toString(settings.getTimeStep());
		timeStepField.setText(timeStep);
		timeout = Double.toString(settings.getConnectionTimeout());
		timeoutField.setText(timeout);
		selectPortMenu.setText(settings.getPort());
		System.out.println("Обнаружены порты: ");
		for(String port : SerialPortList.getPortNames()) {
			System.out.println(port);
			MenuItem item = new MenuItem(port);
			selectPortMenu.getItems().add(item);
		}
		for (MenuItem item : selectPortMenu.getItems()) {
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					selectPortMenu.setText(item.getText());
				}
			});
		}
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveSettings();
			}
		});
		
		timeoutField.setOnKeyPressed(saveOnEnterKey());
		timeStepField.setOnKeyPressed(saveOnEnterKey());
	}


	private EventHandler<KeyEvent> saveOnEnterKey() {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					System.out.println("Нажат Enter, настройки закрываются.");
					saveSettings();
				}
			}
		};
		}


	protected void saveSettings() {
		timeStep = timeStepField.getText();
		timeout = timeoutField.getText();
		Double newTimeStep;
		Double newConnectionTimeout;
		
		if(timeStep.isEmpty() || timeout.isEmpty()) {
			System.out.println("Поле \"Таймаут или  поле\" \"Временной шаг\" пусто.");
			return;
		}
		
		if (timeStep.matches(".*\\d.*") && timeout.matches(".*\\d.*")) {
			newTimeStep = Double.parseDouble(timeStepField.getText().replace(",", "."));
			newConnectionTimeout = Double.parseDouble(timeoutField.getText().replace(",", "."));
		}
		else {
			System.out.println("Некорректное значение в поле \"Таймаут или в поле\" \"Временной шаг\"");
			return;
		}
		
		settings.setAutoLoad(autoLoadCheck.isSelected());
		settings.setAutomaticTarget(automaticTargetCheck.isSelected());
		settings.setMultipleCharts(multipleChartsCheck.isSelected());
		settings.setConnectionTimeout(newConnectionTimeout);
		settings.setTimeStep(newTimeStep);
		settings.setShuffleValues(shuffleCheck.isSelected());
		settings.setPort(selectPortMenu.getText());
		Stage stage = (Stage) ap.getScene().getWindow();
		stage.close();
	}

}
