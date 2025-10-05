package org.itpc_advanced.view;

import org.itpc_advanced.model.Settings;
import org.itpc_advanced.viewmodel.MainViewModel;
import org.itpc_advanced.viewmodel.SettingsViewModel;

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
import jssc.SerialPortList;

public class SettingsView {

	@FXML	private MenuButton selectPortMenu;
	@FXML	private CheckBox shuffleCheck;
	@FXML	private TextField timestepField;
	@FXML	private TextField timeoutField;
	@FXML	private Button saveButton;
	private SettingsViewModel viewModel;
	
	
	
	
	public void setViewModel(SettingsViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.timestepField.textProperty().bind(this.viewModel.timestepProperty());
		this.timeoutField.textProperty().bind(this.viewModel.timeoutProperty());
		System.out.println("SETTINGS GETTED");
	//	this.shuffleCheck.g
		
	}
	
	/*

	@FXML
	void initialize() {
		System.out.println("Обнаружены порты: ");
		for (String port : SerialPortList.getPortNames()) {
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
		saveButton.setOnAction((event) -> {
				saveSettings();
		});
		
		timeoutField.setOnKeyPressed(saveOnEnterKey());
		timeStepField.setOnKeyPressed(saveOnEnterKey());
	}

	private EventHandler<KeyEvent> saveOnEnterKey() {
		return (key) -> {
				if (key.getCode().equals(KeyCode.ENTER)) {
					saveSettings();
				}
		};
	}

	protected void saveSettings() {
    //		settings.setTimeStep(timeStepField.getText());
		/*timeout = timeoutField.getText();
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
	}*/
}