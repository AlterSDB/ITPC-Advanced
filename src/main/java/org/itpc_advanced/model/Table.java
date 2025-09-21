package org.itpc_advanced.model;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Table {
	
	private final ObservableList<DataFile> files;
	private final AtomicInteger fileCounter = new AtomicInteger(1);
	
	public Table(ObservableList<DataFile> fileList) {
		this.files = fileList;
		
	}

	public ObservableList<DataFile> getFiles() {
		return files;
	}
	
	public void addFile(DataFile file) {
		file.setFileId(fileCounter.getAndIncrement());
		files.add(file);
	}
	
	
	public void clearFiles() {
		if(!files.isEmpty()) {
			files.clear();
		    fileCounter.set(1);
		}
	}
	
	public void initialize(TableView<DataFile> table) {
		table.getColumns().clear();
		System.out.println("Initialising table");
		final TableColumn<DataFile, String> filesColumn = new TableColumn<DataFile, String>();
		table.setPlaceholder(new Label("Files list is empty"));
		filesColumn.setCellValueFactory(new PropertyValueFactory<DataFile, String>("FileId"));
		filesColumn.setMaxWidth(194);
		filesColumn.setResizable(false);
	}

	public void addFiles(ObservableList<DataFile> files) {
		for(DataFile dataFile : files) {
			addFile(dataFile);
		}
		
	}
	
	
}

