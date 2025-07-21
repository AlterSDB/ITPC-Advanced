package org.itpc_advanced.model;

import java.util.List;

import org.itpc_advanced.service.DataProcessor;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

@SuppressWarnings("rawtypes")
public class ProcessedDataFile {
	
	private static int counter = 1;
	private DataFile dataFile = null;
	private Integer targetTemperature = null;
	private ObservableList<XYChart.Data> chartData = null;
	private List<Double> maxTemps = null;
	private List<Double> minTemps = null;
	private Double averageMax = null;
	private Double averageMin = null;
	private Double relativeMax = null;
	private Double relativeMin = null;
	private String fileName = null;
	private double[] chartBounds = null;
	
	
	public ProcessedDataFile(DataFile dataFile) {
		this.dataFile = dataFile;		
	}


	public Integer getTargetTemperature() {
		return targetTemperature;
	}
	
	public DataFile getDataFile() {
		return this.dataFile;
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


	public String getFileName() {
		if(fileName == null) {
			return "";
		}
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;		
	}	


	public double[] getChartBounds() {
		return chartBounds;
	}


	public void setChartBounds(double[] chartBounds) {
		this.chartBounds = chartBounds;
	}


	public static int getCounter() {
		return counter;
	}


	public static void resetCounter(int counter) {
		ProcessedDataFile.counter = 1;
	}


	public Double getRelativeMax() {
		return relativeMax;
	}


	public void setRelativeMax(Double relativeMax) {
		this.relativeMax = relativeMax;
	}


	public Double getRelativeMin() {
		return relativeMin;
	}


	public void setRelativeMin(Double relativeMin) {
		this.relativeMin = relativeMin;
	}


	public void updateTargetTemperature(String textValue) {
		double newValue = Double.parseDouble(textValue);
		setTargetTemperature((int)newValue);
		setRelativeMax(DataProcessor.findRelative(newValue, averageMax));
		setRelativeMin(DataProcessor.findRelative(newValue, averageMin));		
	}

}
