package org.itpc_advanced.viewmodel;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.itpc_advanced.model.TemperatureRecord;
import org.itpc_advanced.model.TemperatureStats;
import org.itpc_advanced.model.RecordsDatabase;
import org.itpc_advanced.service.DataParser;
import org.itpc_advanced.service.DeviceScanner;
import org.itpc_advanced.service.LocalizationManager;
import org.itpc_advanced.service.Localizator;
import org.itpc_advanced.service.ReportBuilder;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class MainViewModel {

	// inputs
	private StringProperty typeProperty = new SimpleStringProperty();
	private StringProperty timeStampProperty = new SimpleStringProperty();
	private StringProperty timeStepProperty = new SimpleStringProperty();
	private StringProperty pointsCountProperty = new SimpleStringProperty();
	
	// outputs
	private StringProperty averageMaxProperty = new SimpleStringProperty();
	private StringProperty averageMinProperty = new SimpleStringProperty();
	private StringProperty relativeMaxProperty = new SimpleStringProperty();
	private StringProperty relativeMinProperty = new SimpleStringProperty();
	
	// offsets
	private StringProperty linearOffsetProperty = new SimpleStringProperty();
	private StringProperty targetTemperatureProperty = new SimpleStringProperty();
	
	private ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private DoubleProperty yAxisLowerBoundProperty = new SimpleDoubleProperty(0.0);
	private DoubleProperty yAxisUpperBoundProperty = new SimpleDoubleProperty(10.0);
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private StringProperty manualInputProperty = new SimpleStringProperty();
	
	private RecordsDatabase recordsDatabase;
	private ObjectProperty<TemperatureRecord> selectedRecordProperty = new SimpleObjectProperty<TemperatureRecord>();
	private ObservableList<TemperatureRecord> records = FXCollections.observableArrayList();
	

	public MainViewModel(RecordsDatabase recordsDatabase){
		
		this.recordsDatabase = recordsDatabase;
		
		selectedRecordProperty.addListener((obs, oldRecord, newRecord) -> {
			System.out.println("selected new record");
			manualInputProperty.set(ReportBuilder.getTextFromRawValues(newRecord.getPoints()));
			updateInterface();
		});
		
		linearOffsetProperty.addListener((obs, oldOffset, newOffset) -> {
			System.out.println("new linear offset");
			// добавить дебаунсер и пересчет всех точек (ред. существующий статс)
		});
		
		targetTemperatureProperty.addListener((obs, oldTarget, newTarget) -> {
			System.out.println("new target temperature");
			// добавить проверку на только числа + символ точки, дебаунсер и пересчет только относительных макс и мин (ред. существующий статс)
		});
		
		
	}
	
	public void scanFromDevice() {
		System.out.println("scan pressed: adding records...");
		getRecords().clear();
	//	statsDatabase.scanFromDevice();
		recordsDatabase.demoGetRecordsFromDevice();
		getRecords().addAll(recordsDatabase.getRecords());
		updateInterface();
	}
	
	
	
	public boolean validateInput(String input) {
		if (input.length() > 4) {
			return false;
		}

		if (!input.matches("\\d+")) {
			return false;
		}

		try {
			int value = Integer.parseInt(input);
			if (value <= 99999) {
				return true;
			}

		} catch (NumberFormatException e) {
			return false;
		}

		return false;
		}


	private void updateInterface() {
		if (selectedRecordProperty.getValue() == null) {
			System.out.println("selectedStats is null");
			return;
		}

		TemperatureRecord tempRecord = selectedRecordProperty.getValue();
		
		if (tempRecord == null) {
			System.out.println("TempRecord is null");
			return;
		}
		
		Localizator.bindText(typeProperty, tempRecord.getTcType());
		timeStampProperty.set(tempRecord.getTimeStamp().format(dateTimeFormatter));
		timeStepProperty.set(tempRecord.getTimeStep().toString());
		pointsCountProperty.set(tempRecord.getPointsCount().toString());

		TemperatureStats tempStats = tempRecord.getStats();
		if (tempStats != null) {
		
		averageMaxProperty.set(tempStats.getAverageMax().toString());
		averageMinProperty.set(tempStats.getAverageMin().toString());
		relativeMaxProperty.set(tempStats.getRelativeMax().toString());
		relativeMinProperty.set(tempStats.getRelativeMin().toString());
		
		linearOffsetProperty.set(tempStats.getLinearOffset().toString());
		targetTemperatureProperty.set(tempStats.getTargetTemperature().toString());
		
		chartData.clear();
		chartData.addAll(tempStats.getChartData());
		yAxisLowerBoundProperty.set(tempStats.getyAxisLowerBound());
		yAxisUpperBoundProperty.set(tempStats.getyAxisUpperBound());
		
		} else {
			System.out.println("The file is empty");
			String zero = "0.0";
			averageMaxProperty.set(zero);
			averageMinProperty.set(zero);
			relativeMaxProperty.set(zero);
			relativeMinProperty.set(zero);
			linearOffsetProperty.set(zero);
			targetTemperatureProperty.set(zero);
			chartData.clear();
			yAxisLowerBoundProperty.set(0.0);
			yAxisUpperBoundProperty.set(10.0);
		}

	}

	
	public void calculateFromManualInput() {
		String text = manualInputProperty.getValue();
		selectedRecordProperty.setValue(DataParser.parseFromText(text));
		updateInterface();		
	}

	public void updateSelectedItem(TemperatureRecord temperatureRecord) {
		selectedRecordProperty.set(temperatureRecord);
	}

	public void saveSelectedToFile() {
		System.out.println("save pressed");
		LocalizationManager localManager = LocalizationManager.getInstance();
		
		if(localManager.isEnglish()) { 
			localManager.setLocale(new Locale("ru", "RU"));	
		} else {
			localManager.setLocale(Locale.ENGLISH);
		}
	}
	
	public void openSettingsWindow() {
		System.out.println("Settings btn pressed");
	}

	public void getReport() {
		ReportBuilder.buildReport(selectedRecordProperty.getValue());
	}
	
	public StringProperty typeProperty() {
		return typeProperty;
	}
	
	public StringProperty timeStampProperty() {
		return timeStampProperty;
	}
	
	public StringProperty timeStepProperty() {
		return timeStepProperty;
	}
	
	public StringProperty pointsCountProperty() {
		return pointsCountProperty;
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
	
	public StringProperty linearOffsetProperty() {
		return linearOffsetProperty;
	}
	
	public StringProperty tempSetProperty() {
		return targetTemperatureProperty;
	}

	public ObservableList<XYChart.Data<Number, Number>> getChartData() {
		return chartData;
	}

	public DoubleProperty yAxisLowerBoundProperty() {
		return yAxisLowerBoundProperty;
	}
	
	public DoubleProperty yAxisUpperBoundProperty() {
		return yAxisUpperBoundProperty;
	}

	public StringProperty manualInputProperty() {
		return manualInputProperty;
	}

	public ObservableList<TemperatureRecord> getRecords() {
		return records;
	}


}