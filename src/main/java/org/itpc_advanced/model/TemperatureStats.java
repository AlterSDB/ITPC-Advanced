package org.itpc_advanced.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.chart.XYChart;

import org.itpc_advanced.service.DataProcessor;

public class TemperatureStats {

	private final TemperatureRecord rawTemperatureRecord;
	
	private Double targetTemperature;
	private Double linearOffset;
	
	private List<Double> minTemperaturePoints = new ArrayList<>();
	private List<Double> maxTemperaturePoints = new ArrayList<>();
	private List<Double> filteredPoints = new ArrayList<>();
	private List<XYChart.Data<Number, Number>> chartData = new ArrayList<>();
	
	private Double averageMin;
	private Double averageMax;
	private Double relativeMin;
	private Double relativeMax;
	private double[] chartBounds;
	


	public TemperatureStats(TemperatureRecord rawTemperatureRecord) {
		this.rawTemperatureRecord = rawTemperatureRecord;

		filteredPoints = DataProcessor.removeParasiticValues(rawTemperatureRecord.getPoints());
		
		List<Double> sortedTermperaturePoints = new ArrayList<Double>(getFilteredPoints());
		Collections.sort(sortedTermperaturePoints);
		getMinTemperaturePoints().addAll(sortedTermperaturePoints.subList(0, 10));
		Collections.reverse(sortedTermperaturePoints);
		getMaxTemperaturePoints().addAll(sortedTermperaturePoints.subList(0, 10));
		
		targetTemperature = (double) DataProcessor.findTargetValue(sortedTermperaturePoints);
		 
		
		averageMin = DataProcessor.findAverage(getMinTemperaturePoints());
		averageMax = DataProcessor.findAverage(getMaxTemperaturePoints());
		
		relativeMin = DataProcessor.findRelative(getTargetTemperature(), getAverageMin());
		relativeMax = DataProcessor.findRelative(getTargetTemperature(), getAverageMax());
		
		chartData = DataProcessor.getChartData(rawTemperatureRecord.getPoints(), rawTemperatureRecord.getTimeStep());
		chartBounds = DataProcessor.findChartBounds(sortedTermperaturePoints);
		
		setLinearOffset(0.0);
	}

	
	public TemperatureRecord getRawTemperatureRecord() {
		return rawTemperatureRecord;
	}

	
	public Double getTargetTemperature() {
		return targetTemperature;
	}

	
	public void setTargetTemperature(Double targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	
	public Double getLinearOffset() {
		return linearOffset;
	}

	
	public void setLinearOffset(Double linearOffset) {
		this.linearOffset = linearOffset;
	}

	
	public List<Double> getMinTemperaturePoints() {
		return minTemperaturePoints;
	}

	
	public void setMinTemperaturePoints(List<Double> minTemperaturePoints) {
		this.minTemperaturePoints = minTemperaturePoints;
	}

	public List<Double> getMaxTemperaturePoints() {
		return maxTemperaturePoints;
	}

	
	public Double getAverageMin() {
		return averageMin;
	}
	

	public Double getAverageMax() {
		return averageMax;
	}


	public Double getRelativeMin() {
		return relativeMin;
	}


	public Double getRelativeMax() {
		return relativeMax;
	}


	public List<XYChart.Data<Number, Number>> getChartData() {
		return chartData;
	}


	public double[] getChartBounds() {
		return chartBounds;
	}


	public List<Double> getFilteredPoints() {
		return filteredPoints;
	}

}