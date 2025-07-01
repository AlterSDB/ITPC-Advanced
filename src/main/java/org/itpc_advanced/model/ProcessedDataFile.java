package org.itpc_advanced.model;

import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class ProcessedDataFile {
	
	private DataFile dataFile = null;
	private Integer targetTemperature = null;
	private ObservableList<XYChart.Data> chartData = null;
	private List<Double> maxTemps = null;
	private List<Double> minTemps = null;
	private Double averageMax = null;
	private Double averageMin = null;
	
	
	ProcessedDataFile(DataFile dataFile) {
		this.dataFile = dataFile;		
	}


	public Integer getTargetTemperature() {
		return targetTemperature;
	}


	public void setTargetTemperature(Integer targetTemperature) {
		this.targetTemperature = targetTemperature;
	}


	public ObservableList<XYChart.Data> getChartData() {
		return chartData;
	}


	public void setChartData(ObservableList<XYChart.Data> chartData) {
		this.chartData = chartData;
	}


	public List<Double> getMaxTemps() {
		return maxTemps;
	}


	public void setMaxTemps(List<Double> maxTemps) {
		this.maxTemps = maxTemps;
	}


	public List<Double> getMinTemps() {
		return minTemps;
	}


	public void setMinTemps(List<Double> minTemps) {
		this.minTemps = minTemps;
	}


	public Double getAverageMax() {
		return averageMax;
	}


	public void setAverageMax(Double averageMax) {
		this.averageMax = averageMax;
	}


	public Double getAverageMin() {
		return averageMin;
	}


	public void setAverageMin(Double averageMin) {
		this.averageMin = averageMin;
	}

	

}
