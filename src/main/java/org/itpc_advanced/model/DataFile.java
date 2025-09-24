package org.itpc_advanced.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.itpc_advanced.service.DataProcessor;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class DataFile {

	private static int counter = 1;
	
	// Raw
	private final String tcType;
	private final LocalDateTime timeStamp;
	private final Double timeStep;
	private final List<Double> values;
	
	// Derivatives
	private List<Double> processedValues = new ArrayList<Double>();
	private List<XYChart.Data<Number, Number>> chartData = new ArrayList<XYChart.Data<Number, Number>>();
	private List<Double> maxTemps = null;
	private List<Double> minTemps = null;
	private Integer fileId = null;
	private Integer targetTemperature = 0;
	private Integer linearOffset = 0;
	private Double averageMax = 0.0;
	private Double averageMin = 0.0;
	private Double relativeMax = 0.0;
	private Double relativeMin = 0.0;
	private double[] chartBounds = null;
	
	
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

	public List<XYChart.Data<Number, Number>> getChartData() {
		return chartData;
	}

	public double[] getChartBounds() {
		return chartBounds;
	}

	public Integer getTargetTemperature() {
		return targetTemperature;
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	public Double getAverageMax() {
		return averageMax;
	}

	public Double getAverageMin() {
		return averageMin;
	}

	public Double getRelativeMax() {
		return relativeMax;
	}

	public Double getRelativeMin() {
		return relativeMin;
	}

	public List<Double> getMaxTemps() {
		return maxTemps;
	}

	public List<Double> getMinTemps() {
		return minTemps;
	}


	public Integer getLinearOffset() {
		return linearOffset;
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

	public void setTargetTemperature(Integer targetTemperature) {
		if(targetTemperature == null) {
			targetTemperature = 0;
		}
		this.targetTemperature = targetTemperature;
		setRelativeMax(DataProcessor.findRelative(targetTemperature, averageMax));
		setRelativeMin(DataProcessor.findRelative(targetTemperature, averageMin));		
	}
	
	public void setLinearOffset(Integer linearOffset) {
		if (linearOffset == null) {
			this.linearOffset = 0;
		} else {
			this.linearOffset = linearOffset;
		}
		
		processedValues = DataProcessor.setLinearOffset(values, this.linearOffset);
		this.maxTemps.clear();
		this.minTemps.clear();
		
		if (processedValues.size() == 0) {
			return;
		}
		
		for(int i = 0; i < 10; i++) {
				this.maxTemps.add(processedValues.get(processedValues.size() - 1 - i));
				this.minTemps.add(this.minTemps.size() - i, processedValues.get(i));
			}
		setAverageMax(DataProcessor.findAverage(this.maxTemps));
		setAverageMin(DataProcessor.findAverage(this.minTemps));
		setRelativeMax(DataProcessor.findRelative(this.targetTemperature, averageMax));
		setRelativeMin(DataProcessor.findRelative(this.targetTemperature, averageMin));					
	}

	public void setAverageMax(Double averageMax) {
		this.averageMax = averageMax;
	}

	public void setAverageMin(Double averageMin) {
		this.averageMin = averageMin;
	}

	public void setRelativeMax(Double relativeMax) {
		this.relativeMax = relativeMax;
	}

	public void setRelativeMin(Double relativeMin) {
		this.relativeMin = relativeMin;
	}

	public void setMaxTemps(List<Double> maxTemps) {
		this.maxTemps = maxTemps;
	}

	public void setMinTemps(List<Double> minTemps) {
		this.minTemps = minTemps;
	}

}
