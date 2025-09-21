package org.itpc_advanced.model;

import java.time.LocalDateTime;
import java.util.List;

import org.itpc_advanced.service.DataProcessor;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

@SuppressWarnings("rawtypes")
public class DataFile {

	private static int counter = 1;
	
	// Raw
	private final String tcType;
	private final LocalDateTime timeStamp;
	private final Double timeStep;
	private final List<Double> values;
	
	// Derivatives
	private ObservableList<XYChart.Data<Number, Number>> chartData = null;
	private List<Double> maxTemps = null;
	private List<Double> minTemps = null;
	private Integer fileId = null;
	private double[] chartBounds = null;
	private DoubleProperty targetTemperature = new SimpleDoubleProperty();
	private DoubleProperty averageMax = new SimpleDoubleProperty();
	private DoubleProperty averageMin = new SimpleDoubleProperty();
	private DoubleProperty relativeMax = new SimpleDoubleProperty();
	private DoubleProperty relativeMin = new SimpleDoubleProperty();
	
	
	public DataFile(String tcType, LocalDateTime timeStamp, Double timeStep, List<Double> values) {
		this.tcType = tcType;
		this.timeStamp = timeStamp;
		this.timeStep = timeStep;
		this.values = values;		
	}
	
	public DataFile() {
		this.tcType = null;
		this.timeStamp = null;
		this.timeStep = null;
		this.values = null;	
	}
	
	public static int getCounter() {
		return counter;
	}

	public static void resetCounter(int counter) {
		DataFile.counter = 1;
	}
	
	public Integer getFileId() {
		if(fileId == null) {
			return 0;
		}
		return fileId;
	}
	
	public List<Double> getValues() {
		return values;
	}

	public String getTcType() {
		return tcType;
	}

	public Double getTimeStep() {
		return timeStep;
	}

	public ObservableList<XYChart.Data<Number, Number>> getChartData() {
		return chartData;
	}

	public double[] getChartBounds() {
		return chartBounds;
	}

	public DoubleProperty getTargetTemperature() {
		return targetTemperature;
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	public DoubleProperty getAverageMax() {
		return averageMax;
	}

	public DoubleProperty getAverageMin() {
		return averageMin;
	}

	public DoubleProperty getRelativeMax() {
		return relativeMax;
	}

	public DoubleProperty getRelativeMin() {
		return relativeMin;
	}

	public List<Double> getMaxTemps() {
		return maxTemps;
	}

	public List<Double> getMinTemps() {
		return minTemps;
	}


	public void setFileId(Integer fileId) {
		this.fileId = fileId;		
	}

	public void setChartData(ObservableList<XYChart.Data<Number, Number>> chartData) {
		this.chartData = chartData;
	}

	public void setChartBounds(double[] chartBounds) {
		this.chartBounds = chartBounds;
	}

	public void setTargetTemperature(Double targetTemperature) {
		if(targetTemperature == null) {
			targetTemperature = 0.0;
		}
		this.targetTemperature.set(targetTemperature);
		setRelativeMax(DataProcessor.findRelative(targetTemperature, averageMax.get()));
		setRelativeMin(DataProcessor.findRelative(targetTemperature, averageMin.get()));		
	}

	public void setAverageMax(Double averageMax) {
		this.averageMax.set(averageMax);
	}

	public void setAverageMin(Double averageMin) {
		this.averageMin.set(averageMin);
	}

	public void setRelativeMax(Double relativeMax) {
		this.relativeMax.set(relativeMax);
	}

	public void setRelativeMin(Double relativeMin) {
		this.relativeMin.set(relativeMin);
	}

	public void setMaxTemps(List<Double> maxTemps) {
		this.maxTemps = maxTemps;
	}

	public void setMinTemps(List<Double> minTemps) {
		this.minTemps = minTemps;
	}

}
