package org.itpc_advanced.viewmodel;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Table;
import org.itpc_advanced.utils.MockyDataFiles;
import org.itpc_advanced.utils.VisualFX;
import org.itpc_advanced.view.MainView;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.NumberStringConverter;

public class MainViewModel {
	private final ObservableList<DataFile> fileList = FXCollections.observableArrayList();   
	private final ObjectProperty<DataFile> selectedDataFile = new SimpleObjectProperty<DataFile>();
	private StringProperty averageMaxProperty = new SimpleStringProperty();
	private StringProperty averageMinProperty = new SimpleStringProperty();
	private final StringProperty relativeMaxProperty = new SimpleStringProperty();
	private StringProperty relativeMinProperty = new SimpleStringProperty();
	private StringProperty tempSetProperty = new SimpleStringProperty();
	
	public MainViewModel(){	
		selectedDataFile.addListener(new ChangeListener<DataFile>() {
			@Override
			public void changed(ObservableValue<? extends DataFile> observable,
					DataFile oldDataFile, DataFile newDataFile) {
				if(newDataFile != null) {
					System.out.println("CHANGED");
					tempSetProperty.bind(newDataFile.getTargetTemperature().asString());
					newDataFile.setTargetTemperature(Double.parseDouble(tempSetProperty.getValue()));
					relativeMaxProperty.bind(newDataFile.getRelativeMax().asString());
					relativeMinProperty.bind(newDataFile.getRelativeMin().asString());
					averageMaxProperty.bind(newDataFile.getAverageMax().asString());
					averageMinProperty.bind(newDataFile.getAverageMin().asString());
				}
			}
		});
		tempSetProperty.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				System.out.println("CHANGED TARGET to " + Double.parseDouble(newValue));
				if(selectedDataFile.getValue() != null && !newValue.isEmpty()) {		
				selectedDataFile.getValue().setTargetTemperature(Double.parseDouble(newValue));
				}
			}
			
		});
		
	}

	public ObservableList<DataFile> getFileList() {
		return fileList;
	}

	public ObjectProperty<DataFile> selectedDataFileProperty() {
		return selectedDataFile;
	}

	public void readDataFiles() {
		fileList.clear();
		fileList.addAll(MockyDataFiles.mock());
		//tableModel.clearFiles();
		//tableModel.addFiles(MockyDataFiles.mock());
	}

	public StringProperty averageMaxProperty() {
		return averageMaxProperty;
	}

	public StringProperty averageMinProperty() {
		return averageMinProperty;
	}

	public StringProperty relativeMaxProperty() {
		return relativeMaxProperty;
	}

	public StringProperty relativeMinProperty() {
		return relativeMinProperty;
	}

	public StringProperty tempSetProperty() {
		return tempSetProperty;
	}

	
}
