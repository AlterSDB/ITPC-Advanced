package org.itpc_advanced.view;

import java.util.ArrayList;
import java.util.List;

import org.itpc_advanced.service.KeyboardControlsManager;
import org.itpc_advanced.service.LocalTextBinder;
import org.itpc_advanced.service.TextFormatterFactory;
import org.itpc_advanced.viewmodel.SettingsViewModel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jssc.SerialPortList;

public class SettingsView {

    @FXML private Text portText;
    @FXML private Text shuffleValuesText;
    @FXML private Text maxDeviationText;
    @FXML private Text connTimeoutText;
    @FXML private Text languageText;
    @FXML private MenuButton selectPortMenuBtn;
    @FXML private CheckBox shuffleValuesCheckBox;
    @FXML private TextField maxDeviationTextField;
    @FXML private TextField connTimeoutTextField;
    @FXML private Text demoModeText;
    @FXML private CheckBox demoModeCheckBox;
    @FXML private Button closeBtn;
    @FXML private Button languageBtn;

	private SettingsViewModel viewModel;
	private Stage currentStage;
	
	@FXML
	void initialize() {
		bindTextElements();

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

		closeBtn.setOnAction((event) -> closeWindow());
		languageBtn.setOnAction((event) -> viewModel.changeLanguage());		
	}

	public void setStage(Stage stage) {
		currentStage = stage;
		setupKeyboardNavigation();
	}

	public void setViewModel(SettingsViewModel viewModel) {
		this.viewModel = viewModel;
		selectPortMenuBtn.textProperty().bindBidirectional(this.viewModel.selectedPortProperty());
		maxDeviationTextField.textProperty().bindBidirectional(this.viewModel.maxDeviationProperty());
		connTimeoutTextField.textProperty().bindBidirectional(this.viewModel.timeoutProperty());
		shuffleValuesCheckBox.selectedProperty().bindBidirectional(this.viewModel.shuffleValuesProperty());
		demoModeCheckBox.selectedProperty().bindBidirectional(this.viewModel.demoModeProperty());
		maxDeviationTextField.setTextFormatter(TextFormatterFactory.getOnlyDigitsTextFormatter(5));
		connTimeoutTextField.setTextFormatter(TextFormatterFactory.getOnlyDigitsTextFormatter(5));
	}

	private void bindTextElements() {
		LocalTextBinder.bindText(portText.textProperty(), "settings.port");
		LocalTextBinder.bindText(shuffleValuesText.textProperty(), "settings.shuffle.values");
		LocalTextBinder.bindText(maxDeviationText.textProperty(), "settings.max.deviation");
		LocalTextBinder.bindText(connTimeoutText.textProperty(), "settings.connection.timeout");
		LocalTextBinder.bindText(demoModeText.textProperty(), "settings.demo.mode");
		LocalTextBinder.bindText(languageText.textProperty(), "settings.language");
		LocalTextBinder.bindText(closeBtn.textProperty(), "settings.button.save");
		LocalTextBinder.bindText(languageBtn.textProperty(), "settings.button.set.lang");
	}

	private void setupKeyboardNavigation() {
		List<Node> focusableElements = new ArrayList<>();
		focusableElements.add(selectPortMenuBtn);
		focusableElements.add(shuffleValuesCheckBox);
		focusableElements.add(maxDeviationTextField);
		focusableElements.add(connTimeoutTextField);
		focusableElements.add(demoModeCheckBox);
		focusableElements.add(languageBtn);
		focusableElements.add(closeBtn);
		
		new KeyboardControlsManager(currentStage, focusableElements);
	}

	private void closeWindow() {
		this.viewModel.saveSettings();
		currentStage.close();
	}

}