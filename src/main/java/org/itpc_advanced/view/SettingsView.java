package org.itpc_advanced.view;

import org.itpc_advanced.service.TextFormatterFactory;
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
import javafx.scene.text.Text;
import jssc.SerialPortList;

public class SettingsView {

    @FXML private Text portText;
    @FXML private Text shuffleValuesText;
    @FXML private Text maxDeviationText;
    @FXML private Text connTimeoutText;
    @FXML private MenuButton selectPortMenuBtn;
    @FXML private CheckBox shuffleValuesCheckBox;
    @FXML private TextField maxDeviationTextField;
    @FXML private TextField connTimeoutTextField;
    @FXML private Button saveSettingsBtn;
	private SettingsViewModel viewModel;

	public void setViewModel(SettingsViewModel viewModel) {
		this.viewModel = viewModel;
		this.maxDeviationTextField.textProperty().bindBidirectional(this.viewModel.maxDeviationProperty());
		this.connTimeoutTextField.textProperty().bindBidirectional(this.viewModel.timeoutProperty());
		this.shuffleValuesCheckBox.selectedProperty().bindBidirectional(this.viewModel.shuffleValuesProperty());
		maxDeviationTextField.setTextFormatter(TextFormatterFactory.getOnlyDigitsTextFormatter(5));
		connTimeoutTextField.setTextFormatter(TextFormatterFactory.getOnlyDigitsTextFormatter(5));
	}
	
	@FXML
	void initialize() {
		System.out.println("Available ports: ");
		for (String port : SerialPortList.getPortNames()) {
			System.out.println(port);
			MenuItem item = new MenuItem(port);
			selectPortMenuBtn.getItems().add(item);
		}

		for (MenuItem item : selectPortMenuBtn.getItems()) {
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					selectPortMenuBtn.setText(item.getText());
				}
			});
		}
		saveSettingsBtn.setOnAction((event) -> viewModel.saveSettings());
	//	maxDeviationTextField.setOnKeyPressed(saveOnEnterKey());
	//	connTimeoutTextField.setOnKeyPressed(saveOnEnterKey());
	//	connTimeoutTextField.getParent().setOnKeyPressed(saveOnEnterKey());
	}
	
	private EventHandler<KeyEvent> saveOnEnterKey() {
		return (key) -> {
				if (key.getCode().equals(KeyCode.ENTER)) {
					viewModel.saveSettings();
					System.out.println("ZZZ");
				}
		};
	}

}