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
	private final Table tableModel = new Table(fileList);
	private StringProperty averageMaxProperty = new SimpleStringProperty();
	private StringProperty averageMinProperty = new SimpleStringProperty();
	private StringProperty relativeMaxProperty = new SimpleStringProperty();
	private StringProperty relativeMinProperty = new SimpleStringProperty();
	private StringProperty tempSetProperty = new SimpleStringProperty();
	
	public MainViewModel(){	
		relativeMaxProperty.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
			//	VisualFX.changeText(relativeMaxProperty, newValue);
				
			}
			
		});
		selectedDataFile.addListener(new ChangeListener<DataFile>() {
			@Override
			public void changed(ObservableValue<? extends DataFile> observable,
					DataFile oldDataFile, DataFile newDataFile) {
				if(newDataFile != null) {
					System.out.println("CHANGED");
					averageMaxProperty.bind(Bindings.concat(newDataFile.getAverageMax()));
					averageMinProperty.bind(Bindings.concat(newDataFile.getAverageMin()));
					relativeMaxProperty.bind(Bindings.concat(newDataFile.getRelativeMax()));
					relativeMinProperty.bind(Bindings.concat(newDataFile.getRelativeMin()));
					
				//	VisualFX.changeText(averageMaxProperty, newDataFile.getAverageMax());
				//	VisualFX.changeText(averageMinProperty, newDataFile.getAverageMin());
				//	VisualFX.changeText(relativeMaxProperty, newDataFile.getRelativeMax());
				//	VisualFX.changeText(relativeMinProperty, newDataFile.getRelativeMin());
					if(!newDataFile.getTargetTemperature().getValue().equals(tempSetProperty.getValue())) {
					tempSetProperty.setValue(newDataFile.getTargetTemperature().getValue().toString());
					}
				//	Bindings.bindBidirectional(averageMinProperty, newDataFile.getAverageMin(), new NumberStringConverter());
				}
			}
		});
		tempSetProperty.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				System.out.println("CHANGED TARGET");
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
		tableModel.clearFiles();
		tableModel.addFiles(MockyDataFiles.mock());
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
