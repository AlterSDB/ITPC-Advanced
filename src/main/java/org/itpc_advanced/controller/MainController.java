package org.itpc_advanced.controller;

import java.io.IOException;

import org.itpc_advanced.ITPC_Advanced;
import org.itpc_advanced.model.ProcessedDataFile;
import org.itpc_advanced.model.Table;
import org.itpc_advanced.service.ChartBuilder;
import org.itpc_advanced.service.DeviceScanner;
import org.itpc_advanced.service.ReportBuilder;
import org.itpc_advanced.utils.MockyDataFiles;
import org.itpc_advanced.utils.VisualFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class MainController {
	
	@FXML 
	public TableView<ProcessedDataFile> tableView;
	
	@FXML
	public TableColumn<ProcessedDataFile, String> tableColumn;
	
    @FXML
    private LineChart<Number, Number> lineChart;
    
    @FXML
    private NumberAxis x;

    @FXML
    private NumberAxis y;

	@FXML 
	public TextField tempSetField;
	
    @FXML
    public TextField averageMaxField;

    @FXML
    public TextField averageMinField;

    @FXML
    public TextField relativeMaxField;

    @FXML
    public TextField relativeMinField;
    
    private final Table tableModel = new Table();

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	void initialize() {	
		lineChart = ChartBuilder.createChart(lineChart, x, y);
		tableView.setItems(tableModel.getFiles());
		tableColumn.setCellValueFactory(new PropertyValueFactory<ProcessedDataFile, String>("FileName"));
		
		tempSetField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					tempSetField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if(tableView.getSelectionModel().getSelectedItem() == null || newValue.isEmpty()) {
					return;
				}
				
				ProcessedDataFile dataFile = tableView.getSelectionModel().getSelectedItem();
				dataFile.updateTargetTemperature(tempSetField.getText());
				updateRelativeFields(dataFile);
		}
			
			
		});
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if(tableView.getSelectionModel().getSelectedItem() != null) {
					ProcessedDataFile dataFile = (ProcessedDataFile) tableView.getSelectionModel().getSelectedItem();
					System.out.println("Выбран " + dataFile.getFileName());
				    updateFields(dataFile);
				    ChartBuilder.changeChart(dataFile.getChartData(), dataFile.getChartBounds(), dataFile.getFileName());
				}
			}
		});
	}

	@FXML
	void copyResultBtnAction() {
		System.out.println("copyResultsButton pressed");
		ReportBuilder.buildReport(tableView);
	}

	@FXML
	void settingsBtnAction() throws IOException {
		System.out.println("SettingsButton pressed");
		ITPC_Advanced.callSettingsWindow();
	}
	
	@FXML
	void scanBtnAction(ActionEvent event) {
		System.out.println("Scan pressed");
		tableModel.clearFiles();
		tableModel.addFiles(MockyDataFiles.mock());
		//tableModel.addFiles(DeviceScanner.readDataFiles());
	}
	
	@FXML
	void switchLanguageAction(ActionEvent event) {
		System.out.println("Language pressed");
	}

	void updateFields(ProcessedDataFile dataFile) {
		tempSetField.setText(dataFile.getTargetTemperature().toString());
		VisualFX.changeText(averageMaxField, dataFile.getAverageMax().toString());
		VisualFX.changeText(averageMinField, dataFile.getAverageMin().toString());
		updateRelativeFields(dataFile);
	}
	void updateRelativeFields(ProcessedDataFile dataFile) {
		VisualFX.changeText(relativeMaxField, dataFile.getRelativeMax().toString());
		VisualFX.changeText(relativeMinField, dataFile.getRelativeMin().toString());
	}
	
}

