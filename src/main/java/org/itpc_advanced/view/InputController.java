package org.itpc_advanced.view;


import org.itpc_advanced.model.TemperatureRecord;
import org.itpc_advanced.model.TemperatureStats;
import org.itpc_advanced.service.Localizator;
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
    private TableView<TemperatureRecord> table;

    @FXML
    private TableColumn<TemperatureRecord, Number> column;

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
		
		Localizator.bindText(label.textProperty(), "table.placeholder");		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
			System.out.println("Item updated");
			viewModel.updateSelectedItem(newItem);
		});
		table.setItems(viewModel.getRecords());
		
		bindTextElements();  
		bindLocalizedElements();
    }

	private void bindLocalizedElements() {
		Localizator.bindText(scanBtn.textProperty(), "button.scan");
		Localizator.bindText(saveBtn.textProperty(), "button.save");
		Localizator.bindText(calculateBtn.textProperty(), "button.calculate");
		Localizator.bindText(timeStampText.textProperty(), "df.timestamp");
		Localizator.bindText(typeText.textProperty(), "df.tc.type");
		Localizator.bindText(timeStepText.textProperty(), "df.timestep");
		Localizator.bindText(pointsCountText.textProperty(), "df.points.count");
	}

	private void bindTextElements() {
		typeValueText.textProperty().bind(viewModel.typeProperty());
		timeStampValueText.textProperty().bind(viewModel.timeStampProperty());
		timeStepValueText.textProperty().bind(viewModel.timeStepProperty());
		pointsCountValueText.textProperty().bind(viewModel.pointsCountProperty());		
		manualInputTextArea.textProperty().bindBidirectional(viewModel.manualInputProperty());
	}	

}

