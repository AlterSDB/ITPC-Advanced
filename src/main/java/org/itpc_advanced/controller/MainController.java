package org.itpc_advanced.controller;

import java.io.IOException;

import org.itpc_advanced.ITPC_Advanced;
import org.itpc_advanced.model.ProcessedDataFile;
import org.itpc_advanced.model.Table;
import org.itpc_advanced.utils.MockyDataFiles;
import org.itpc_advanced.utils.VisualFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;


public class MainController {
	
	@FXML 
	public TableView<ProcessedDataFile> table;
	@FXML 
	public TableColumn<ProcessedDataFile, String> filesColumn;
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

	
	@SuppressWarnings("unchecked")
	@FXML
	void initialize() {	
		table.setPlaceholder(new Label("Список файлов пуст"));
		filesColumn.setCellValueFactory(new PropertyValueFactory<ProcessedDataFile, String>("FileName"));
		filesColumn.setMaxWidth(190);
		filesColumn.setResizable(false);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observableValue, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					ProcessedDataFile df = (ProcessedDataFile) table.getSelectionModel().getSelectedItem();
					tempSetField.clear();
					tempSetField.setText(Integer.toString(df.getTargetTemperature()));
					VisualFX.changeText(averageMaxField,  Double.toString(df.getAverageMax()));
					VisualFX.changeText(averageMinField,  Double.toString(df.getAverageMin()));
					VisualFX.changeText(relativeMaxField, Double.toString(df.getRelativeMax()));
					VisualFX.changeText(relativeMinField, Double.toString(df.getRelativeMin()));
				//	changeChart(df.getChartData(), df.getChartBounds(), df.getFileName());
					//VisualFX.fadeTransition(copyResultsButton, 0, 1);
					
				}
				
				
			}
			
		});
		
		
		tempSetField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					tempSetField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				
			//	if(table.getSelectionModel().getSelectedItem() == null || newValue.isEmpty()) {
			//		return;
			//	}

			//	DataFileOld selectedDF = (DataFileOld) table.getSelectionModel().getSelectedItem();

			//	if(!selectedDF.isFileExists()) {
			//		return;
			//	}
				
			//	if(settings.isAutomaticTarget()) {
			//		selectedDF.setNewTarget(Integer.parseInt(targetTemperatureField.getText()));
			//	}
			//	else {
			//		for(DataFileOld df : deviceScanner.getDataFiles()) {
			//			df.setNewTarget(Integer.parseInt(targetTemperatureField.getText()));
			//		}
			//	}
				
			//	VisualFX.changeText(relativeMaxField, Double.toString(selectedDF.getRelativeMax()));
			//	VisualFX.changeText(relativeMinField, Double.toString(selectedDF.getRelativeMin()));
			}
		});
	}
	

	@FXML
	void copyResultBtnAction() {
		System.out.println("copyResultsButton pressed");
		if(Table.getTable() == null) {
			System.out.println("Ошибка: Таблицы не существует.");
			return;
		}
		if(Table.getTable().getSelectionModel().getSelectedItem() == null) {
			System.out.println("Ошибка: Файл в таблице не выбран.");
			return;
		}

		Clipboard clipboard = Clipboard.getSystemClipboard();
	//	DataFile df = (DataFile) Table.getTable().getSelectionModel().getSelectedItem();
	//	clipboard.setContent(ReportBuilder.getReport(null));
		System.out.println("Результаты скопированы");
	}

	@FXML
	void settingsBtnAction() throws IOException {
		System.out.println("SettingsButton pressed");
		ITPC_Advanced.callSettingsWindow();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	void scanBtnAction(ActionEvent event) {
		System.out.println("Scan pressed");
		// table.setItems(DeviceScanner.readDataFiles());
		   table.setItems(MockyDataFiles.mock());
		
	}
	
	@FXML
	void switchLanguageAction(ActionEvent event) {
		System.out.println("Language pressed");
	}
	
}

