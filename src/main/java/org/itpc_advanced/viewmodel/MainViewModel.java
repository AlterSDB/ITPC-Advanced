package org.itpc_advanced.viewmodel;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.itpc_advanced.model.TemperatureRecord;
import org.itpc_advanced.model.TemperatureStats;
import org.itpc_advanced.model.TemperatureStatsDatabase;
import org.itpc_advanced.service.DataParser;
import org.itpc_advanced.service.DeviceScanner;
import org.itpc_advanced.service.LocalManager;
import org.itpc_advanced.service.LocalTextBinder;
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
	private StringProperty typeValueProperty = new SimpleStringProperty();
	private StringProperty timeStampValueProperty = new SimpleStringProperty();
	private StringProperty timeStepValueProperty = new SimpleStringProperty();
	private StringProperty pointsCountValueProperty = new SimpleStringProperty();
	
	// outputs
	private StringProperty averageMaxValueProperty = new SimpleStringProperty();
	private StringProperty averageMinValueProperty = new SimpleStringProperty();
	private StringProperty relativeMaxValueProperty = new SimpleStringProperty();
	private StringProperty relativeMinValueProperty = new SimpleStringProperty();
	
	// offsets
	private StringProperty linearOffsetValueProperty = new SimpleStringProperty();
	private StringProperty tempSetValueProperty = new SimpleStringProperty();
	
	private final ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private final DoubleProperty yAxisLowerBoundProperty = new SimpleDoubleProperty(0.0);
	private final DoubleProperty yAxisUpperBoundProperty = new SimpleDoubleProperty(10.0);
	
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private StringProperty manualInputProperty = new SimpleStringProperty();
	
	private TemperatureStatsDatabase statsDatabase;
	private ObjectProperty<TemperatureStats> selectedStatsProperty = new SimpleObjectProperty<TemperatureStats>();
	private ObservableList<TemperatureStats> statsList = FXCollections.observableArrayList();
	

	public MainViewModel(TemperatureStatsDatabase statsDatabase){
		
		this.statsDatabase = statsDatabase;
		
		selectedStatsProperty.addListener((obs, oldValue, newValue) -> {
			manualInputProperty.set(ReportBuilder.getTextFromRawValues(newValue.getRawTemperatureRecord().getPoints()));
			statsDatabase.setSelectedStats(newValue);
			updateInterface();
		});
		
		
	}
	
	public void scanFromDevice() {
		System.out.println("scan pressed: adding records...");
		statsList.clear();
	//	statsDatabase.scanFromDevice();
		statsDatabase.demoScanFromDevice();
		statsList.addAll(statsDatabase.getStatsList());
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
		if (selectedStatsProperty.getValue() == null) {
			System.out.println("selectedStats is null");
			return;
		}

		TemperatureRecord tempRecord = selectedStatsProperty.getValue().getRawTemperatureRecord();
		
		if (tempRecord == null) {
			System.out.println("TempRecord is null");
			return;
		}
		
		LocalTextBinder.bindText(typeValueProperty, tempRecord.getTcType());
		timeStampValueProperty.set(tempRecord.getTimeStamp().format(dateTimeFormatter));
		timeStepValueProperty.set(tempRecord.getTimeStep().toString());
		pointsCountValueProperty.set(tempRecord.getPointsCount().toString());

		TemperatureStats tempStats = statsDatabase.getSelectedStats();
		
		averageMaxValueProperty.set(tempStats.getAverageMax().toString());
		averageMinValueProperty.set(tempStats.getAverageMin().toString());
		relativeMaxValueProperty.set(tempStats.getRelativeMax().toString());
		relativeMinValueProperty.set(tempStats.getRelativeMin().toString());
		
		linearOffsetValueProperty.set(tempStats.getLinearOffset().toString());
		tempSetValueProperty.set(tempStats.getTargetTemperature().toString());
		
		chartData.clear();
		chartData.addAll(tempStats.getChartData());
		yAxisLowerBoundProperty.set(tempStats.getChartBounds()[0]);
		yAxisUpperBoundProperty.set(tempStats.getChartBounds()[1]);

	}

	
	public void calculateFromManualInput() {
	String text = manualInputProperty.getValue();
	statsDatabase.getSelectedStats().setRawTemperatureRecord(DataParser.parseFromText(text));
	selectedStatsProperty.set(statsDatabase.getSelectedStats());
	updateInterface();		
	}

	public void updateSelectedItem(TemperatureStats temperatureStats) {
		selectedStatsProperty.set(temperatureStats);
	}

	public void saveSelectedToFile() {
		System.out.println("save pressed");
		LocalManager localManager = LocalManager.getInstance();
		
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
		ReportBuilder.buildReport(statsDatabase.getSelectedStats());
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
		return statsList;
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

}