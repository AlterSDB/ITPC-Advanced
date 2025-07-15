package org.itpc_advanced.model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Table {
	
	@FXML 
	public static TableView table;
	@FXML 
	public static TableColumn<DataFile, String> filesColumn;
	@FXML 
	public static TextField targetTemperatureField;
	

	public static void initialize() {
		if(table == null) {
			table = new TableView();
		}
		if(filesColumn == null) {
			filesColumn = new TableColumn<DataFile, String>();
		}
		table.setPlaceholder(new Label("Список файлов пуст"));
		filesColumn.setCellValueFactory(new PropertyValueFactory<DataFile, String>("FileName"));
		filesColumn.setMaxWidth(194);
		filesColumn.setResizable(false);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					ProcessedDataFile df = (ProcessedDataFile) table.getSelectionModel().getSelectedItem();
					System.out.println("Выбран ");
					targetTemperatureField.clear();
					targetTemperatureField.setText(Integer.toString(df.getTargetTemperature()));
					System.out.println("Установлена автоматическая настройка ТЗ. Целевое значение: " + Integer.toString(df.getTargetTemperature()));
					
				}
			}
		});
	}
	
	public static TableView getTable() {
		return table;
	}

	public static TableColumn<DataFile, String> getFilesColumn() {
		return filesColumn;
	}
	
	
}

