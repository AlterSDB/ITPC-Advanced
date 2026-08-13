package org.itpc_advanced.viewmodel;


import java.util.Locale;

import org.itpc_advanced.model.TemperatureStats;
import org.itpc_advanced.model.TemperatureStatsRepository;
import org.itpc_advanced.service.LocalManager;
import org.itpc_advanced.service.LocalTextBinder;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {

	private TemperatureStatsRepository temperatureStatsRepository;
	private ObservableList<TemperatureStats> temperatureStatsList = FXCollections.observableArrayList();
	private ObjectProperty<TemperatureStats> selectedTemperatureStats = new SimpleObjectProperty<TemperatureStats>();
	
	
	// inputs
	private StringProperty typeValueProperty = new SimpleStringProperty();
	private StringProperty timeStampValueProperty = new SimpleStringProperty();
	private StringProperty timeStepValueProperty = new SimpleStringProperty();
	private StringProperty pointsCountValueProperty = new SimpleStringProperty();
	
	private StringProperty averageMaxValueProperty = new SimpleStringProperty();
	private StringProperty averageMinValueProperty = new SimpleStringProperty();
	private StringProperty relativeMaxValueProperty = new SimpleStringProperty();
	private StringProperty relativeMinValueProperty = new SimpleStringProperty();
	
	private StringProperty linearOffsetValueProperty = new SimpleStringProperty();
	private StringProperty tempSetValueProperty = new SimpleStringProperty();

	public MainViewModel(TemperatureStatsRepository temperatureStatsRepository){	
		this.temperatureStatsRepository = temperatureStatsRepository;
	//	LocalTextBinder.bindText(typeValueProperty, "types.k");
		 
	//	temperatureStatsList = temperatureStatsRepository.getTemperatureStatsList();
	//	selectedTemperatureStats = temperatureStatsRepository.getSelectedTemperatureStats();
		selectedTemperatureStats.addListener((obs, oldVal, newVal) -> {
			System.out.println("selectedDataFile is updated");
			updateElements();
		});
	}

	private void updateElements() {
		//pointsCountProperty.set(selectedDataFile.getValue().getPointsCount().toString());				
		
		typeValueProperty.set(selectedTemperatureStats.getValue().getRawTemperatureRecord().getTcType());
		timeStampValueProperty.set(selectedTemperatureStats.getValue().getRawTemperatureRecord().getTimeStamp().toString());
		timeStepValueProperty.set(selectedTemperatureStats.getValue().getRawTemperatureRecord().getTimeStep().toString());
		pointsCountValueProperty.set(selectedTemperatureStats.getValue().getRawTemperatureRecord().getPointsCount().toString());
		
		averageMaxValueProperty.set(selectedTemperatureStats.getValue().getAverageMax().toString());
		averageMinValueProperty.set(selectedTemperatureStats.getValue().getAverageMin().toString());
		relativeMaxValueProperty.set(selectedTemperatureStats.getValue().getRelativeMax().toString());
		relativeMinValueProperty.set(selectedTemperatureStats.getValue().getRelativeMin().toString());
		
		linearOffsetValueProperty.set(selectedTemperatureStats.getValue().getLinearOffset().toString());
		tempSetValueProperty.set(selectedTemperatureStats.getValue().getTargetTemperature().toString());

	}

	public void calculateManual(String text) {
		
	}

	public void readDataFiles() {
		
	}
	
	public StringProperty typeValueProperty() {
		return typeValueProperty;
	}
	
	public StringProperty timeStampValueProperty() {
		return timeStampValueProperty;
	}
	
	public StringProperty timeStepValueProperty() {
		return timeStepValueProperty;
	}
	
	public StringProperty pointsCountValueProperty() {
		return pointsCountValueProperty;
	}

	public void updateSelectedItem(TemperatureStats temperatureStats) {
		selectedTemperatureStats.set(temperatureStats);
	}

	public void calculate() {
		System.out.println("Calculate pressed");
	}

	public void saveToFile() {
		System.out.println("save pressed");
		LocalManager localManager = LocalManager.getInstance();
		
		if(localManager.isEnglish()) { 
			localManager.setLocale(new Locale("ru", "RU"));	
		} else {
			localManager.setLocale(Locale.ENGLISH);
		}
	}

	public void scanFromDevice() {
		System.out.println("scan pressed");
		temperatureStatsRepository.addStatsFile();
		temperatureStatsList.clear();
		temperatureStatsList.addAll(temperatureStatsRepository.getTemperatureStatsList());
	}
	
	public void openSettingsWindow() {
		System.out.println("Settings btn pressed");
	}

	public void getReport() {
		System.out.println("copyResults pressed");
	}

	public StringProperty averageMaxValueProperty() {
		return averageMaxValueProperty;
	}
	
	public StringProperty averageMinValueProperty() {
		return averageMinValueProperty;
	}

	public StringProperty relativeMaxValueProperty() {
		return relativeMaxValueProperty;
	}
	
	public StringProperty relativeMinValueProperty() {
		return relativeMinValueProperty;
	}
	
	public StringProperty linearOffsetValueProperty() {
		return linearOffsetValueProperty;
	}
	
	public StringProperty tempSetValueProperty() {
		return tempSetValueProperty;
	}

	public ObservableList<TemperatureStats> temperatureStatsList() {
		return temperatureStatsList;
	}


}