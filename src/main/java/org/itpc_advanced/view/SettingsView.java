package org.itpc_advanced.view;

import org.itpc_advanced.model.Settings2;

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

	@FXML	private AnchorPane pane;
	@FXML	private MenuButton selectPortMenu;
	@FXML	private CheckBox automaticTargetCheck;
	@FXML	private CheckBox autoLoadCheck;
	@FXML	private CheckBox multipleChartsCheck;
	@FXML	private CheckBox shuffleCheck;
	@FXML	private TextField timeStepField;
	@FXML	private TextField timeoutField;
	@FXML	private Button saveButton;

	private final Settings2 settings = new Settings2(pane);

	@FXML
	void initialize() {
		multipleChartsCheck.setSelected(settings.getMultipleCharts().getValue());
		automaticTargetCheck.setSelected(settings.getAutomaticTarget().getValue());
		autoLoadCheck.setSelected(settings.getAutoLoad().getValue());
		shuffleCheck.setSelected(settings.getShuffleValues().getValue());
		selectPortMenu.setText(settings.getPort().getValue());
		timeStepField.setText(settings.getTimeStep().getValue().toString());
		timeoutField.setText(settings.getConnectionTimeout().getValue().toString());
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
		settings.setTimeStep(timeStepField.getText());
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
		settings.setPort(selectPortMenu.getText()); */
	}

}