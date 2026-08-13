package org.itpc_advanced.viewmodel;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.itpc_advanced.model.TemperatureStats;
import org.itpc_advanced.model.TemperatureStatsRepository;
import org.itpc_advanced.service.DataParser;
import org.itpc_advanced.service.LocalManager;
import org.itpc_advanced.service.LocalTextBinder;
import org.itpc_advanced.service.ReportBuilder;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class MainViewModel {

	private TemperatureStatsRepository temperatureStatsRepository;
	private ObservableList<TemperatureStats> temperatureStatsList = FXCollections.observableArrayList();
	private ObjectProperty<TemperatureStats> selectedTemperatureStatsProperty = new SimpleObjectProperty<TemperatureStats>();
	
	
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
	
	
	private final ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private final DoubleProperty yAxisLowerBoundProperty = new SimpleDoubleProperty();
	private final DoubleProperty yAxisUpperBoundProperty = new SimpleDoubleProperty();
	
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private StringProperty manualInputProperty = new SimpleStringProperty();

	public MainViewModel(TemperatureStatsRepository temperatureStatsRepository){	
		this.temperatureStatsRepository = temperatureStatsRepository;
	//	LocalTextBinder.bindText(typeValueProperty, "types.k");
		 
	//	temperatureStatsList = temperatureStatsRepository.getTemperatureStatsList();
	//	selectedTemperatureStats = temperatureStatsRepository.getSelectedTemperatureStats();
		selectedTemperatureStatsProperty.addListener((obs, oldVal, newVal) -> {
			System.out.println("selectedDataFile is updated");
			updateElements();
		});

	}

	private void updateElements() {
		TemperatureStats tempStats = selectedTemperatureStatsProperty.getValue();
		
		if (tempStats == null) {
			return;
		}
		LocalTextBinder.bindText(typeValueProperty, tempStats.getRawTemperatureRecord().getTcType());
		
		LocalDateTime timeStamp = tempStats.getRawTemperatureRecord().getTimeStamp();
 
		timeStampValueProperty.set(timeStamp.format(dateTimeFormatter));
		timeStepValueProperty.set(tempStats.getRawTemperatureRecord().getTimeStep().toString());
		pointsCountValueProperty.set(tempStats.getRawTemperatureRecord().getPointsCount().toString());
		
		averageMaxValueProperty.set(tempStats.getAverageMax().toString());
		averageMinValueProperty.set(tempStats.getAverageMin().toString());
		relativeMaxValueProperty.set(tempStats.getRelativeMax().toString());
		relativeMinValueProperty.set(tempStats.getRelativeMin().toString());
		
		linearOffsetValueProperty.set(tempStats.getLinearOffset().toString());
		tempSetValueProperty.set(tempStats.getTargetTemperature().toString());
		
	//	chartData.set(selectedTemperatureStats.getValue().getChartData());
		chartData.clear();
		chartData.addAll(tempStats.getChartData());
		yAxisLowerBoundProperty.set(tempStats.getChartBounds()[1]);
		yAxisUpperBoundProperty.set(tempStats.getChartBounds()[0]);
		
		manualInputProperty.set(ReportBuilder.getTextFromRawValues(tempStats.getMaxTemperaturePoints()));		
		

	}

	public void calculateManual(String text) {
	//	selectedTemperatureStatsProperty.set(DataParser.parseFromText(text));
	//	DataProcessor.calculate(selectedDataFile.get());
	//	updateAttributes();
		
	}
	
	public void calculateManual() {
	System.out.println("Calculating from manual field");
	String text = manualInputProperty.getValue();
	
	//selectedTemperatureStatsProperty.set(DataParser.parseFromText(text));
		
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
		selectedTemperatureStatsProperty.set(temperatureStats);
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