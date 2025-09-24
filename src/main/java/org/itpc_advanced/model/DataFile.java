package org.itpc_advanced.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.itpc_advanced.service.DataProcessor;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

@SuppressWarnings("rawtypes")
public class DataFile {

	private static int counter = 1;
	
	// Raw
	private final String tcType;
	private final LocalDateTime timeStamp;
	private final Double timeStep;
	private final List<Double> values;
	
	// Derivatives
	private List<Double> processedValues = new ArrayList<Double>();
	private ObservableList<XYChart.Data<Number, Number>> chartData = null;
	private List<Double> maxTemps = null;
	private List<Double> minTemps = null;
	private Integer fileId = null;
	private double[] chartBounds = null;
	private IntegerProperty targetTemperature = new SimpleIntegerProperty();
	private IntegerProperty linearOffset = new SimpleIntegerProperty();
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

	public Integer getTargetTemperature() {
		return targetTemperature.get();
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	public Double getAverageMax() {
		return averageMax.get();
	}

	public Double getAverageMin() {
		return averageMin.get();
	}

	public Double getRelativeMax() {
		return relativeMax.get();
	}

	public Double getRelativeMin() {
		return relativeMin.get();
	}

	public List<Double> getMaxTemps() {
		return maxTemps;
	}

	public List<Double> getMinTemps() {
		return minTemps;
	}


	public Integer getLinearOffset() {
		return linearOffset.get();
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
		this.targetTemperature.set(targetTemperature);
		setRelativeMax(DataProcessor.findRelative(targetTemperature, averageMax.get()));
		setRelativeMin(DataProcessor.findRelative(targetTemperature, averageMin.get()));		
	}
	
	public void setLinearOffset(Integer linearOffset) {
		if(linearOffset == null) {
			linearOffset = 0;
		}
		this.linearOffset.set(linearOffset);
		processedValues = DataProcessor.setLinearOffset(values, linearOffset);
		this.maxTemps.clear();
		this.minTemps.clear();
		for(int i = 0; i < 10; i++) {
			//	System.out.println(sortedValues.size() + "     " + i);
				this.maxTemps.add(processedValues.get(processedValues.size() - 1 - i));
				this.minTemps.add(this.minTemps.size() - i, processedValues.get(i));
			}
		setAverageMax(DataProcessor.findAverage(this.maxTemps));
		setAverageMin(DataProcessor.findAverage(this.minTemps));
		setRelativeMax(DataProcessor.findRelative(this.targetTemperature.get(), averageMax.get()));
		setRelativeMin(DataProcessor.findRelative(this.targetTemperature.get(), averageMin.get()));	
		System.out.println("DONEd");
				
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
