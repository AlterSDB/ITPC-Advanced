package org.itpc_advanced.view;


import org.itpc_advanced.model.TemperatureStats;
import org.itpc_advanced.service.LocalTextBinder;
import org.itpc_advanced.viewmodel.MainViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class InputController {

    @FXML
    private TableView<TemperatureStats> table;

    @FXML
    private TableColumn<TemperatureStats, Number> column;

    @FXML
    private TextArea manualInputTextArea;

    @FXML
    private Text typeText;

    @FXML
    private Text timeStampText;

    @FXML
    private Text timeStepText;

    @FXML
    private Text pointsCountText;

    @FXML
    private Text typeValueText;

    @FXML
    private Text timeStampValueText;

    @FXML
    private Text timeStepValueText;

    @FXML
    private Text pointsCountValueText;
    
    @FXML 
    private Button scanBtn;
    
    @FXML 
    private Button saveBtn;
    
    @FXML 
    private Button calculateBtn;

	private MainViewModel viewModel;
	
	public InputController(MainViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
    @FXML
    void onCalculateBtnAction(ActionEvent event) {
    	viewModel.calculateFromManualInput();
    }

    @FXML
    void onSaveBtnAction(ActionEvent event) {
    	viewModel.saveSelectedToFile();
    }

    @FXML
    void onScanBtnAction(ActionEvent event) {
    	viewModel.scanFromDevice();
    }
    
    @FXML
    void initialize() {
    	column.setCellValueFactory(new PropertyValueFactory<>("id"));
    	
    	Label label = new Label();
		table.setPlaceholder(label);
		
		LocalTextBinder.bindText(label.textProperty(), "table.placeholder");		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			System.out.println("Item updated");
			viewModel.updateSelectedItem(newVal);
		});
		table.setItems(viewModel.temperatureStatsList());
		
		bindTextElements();  
		bindLocalizedElements();
    }

	private void bindLocalizedElements() {
		LocalTextBinder.bindText(scanBtn.textProperty(), "button.scan");
		LocalTextBinder.bindText(saveBtn.textProperty(), "button.save");
		LocalTextBinder.bindText(calculateBtn.textProperty(), "button.calculate");
		LocalTextBinder.bindText(timeStampText.textProperty(), "df.timestamp");
		LocalTextBinder.bindText(typeText.textProperty(), "df.tc.type");
		LocalTextBinder.bindText(timeStepText.textProperty(), "df.timestep");
		LocalTextBinder.bindText(pointsCountText.textProperty(), "df.points.count");
	}

	private void bindTextElements() {
		typeValueText.textProperty().bind(viewModel.typeValueProperty());
		timeStampValueText.textProperty().bind(viewModel.timeStampValueProperty());
		timeStepValueText.textProperty().bind(viewModel.timeStepValueProperty());
		pointsCountValueText.textProperty().bind(viewModel.pointsCountValueProperty());		
		manualInputTextArea.textProperty().bindBidirectional(viewModel.manualInputProperty());
	}	

}

