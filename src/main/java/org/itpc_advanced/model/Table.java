package org.itpc_advanced.model;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Table {
	
	private final ObservableList<ProcessedDataFile> files = FXCollections.observableArrayList();
	private final AtomicInteger fileCounter = new AtomicInteger(1);
	
	public ObservableList<ProcessedDataFile> getFiles() {
		return files;
	}
	
	public void addFile(ProcessedDataFile file) {
		file.setFileName("Файл " + fileCounter.getAndIncrement());
		files.add(file);
	}
	
	
	public void clearFiles() {
		if(!files.isEmpty()) {
			files.clear();
		    fileCounter.set(1);
		}
	}
	
	public void initialize(TableView<ProcessedDataFile> table) {
		table.getColumns().clear();
		final TableColumn<ProcessedDataFile, String> filesColumn = new TableColumn<ProcessedDataFile, String>();
		table.setPlaceholder(new Label("Список файлов пуст"));
		filesColumn.setCellValueFactory(new PropertyValueFactory<ProcessedDataFile, String>("FileName"));
		filesColumn.setMaxWidth(194);
		filesColumn.setResizable(false);
	}

	public void addFiles(ObservableList<ProcessedDataFile> files) {
		for(ProcessedDataFile dataFile : files) {
			addFile(dataFile);
		}
		
	}
	
	
}

