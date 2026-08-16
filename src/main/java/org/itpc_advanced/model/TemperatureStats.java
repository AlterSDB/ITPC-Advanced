package org.itpc_advanced.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.chart.XYChart;

import org.itpc_advanced.service.DataProcessor;

public class TemperatureStats {
	
	private double targetTemperature;
	private double linearOffset;
	
	private List<Double> filteredPoints = new ArrayList<>();
	private List<Double> minTemperaturePoints = new ArrayList<>();
	private List<Double> maxTemperaturePoints = new ArrayList<>();
	
	private double averageMin;
	private double averageMax;
	private double relativeMin;
	private double relativeMax;
	
	private List<XYChart.Data<Number, Number>> chartData = new ArrayList<>();
	private double[] chartBounds;
	private double yAxisLowerBound;
	private double yAxisUpperBound;
	


	public TemperatureStats(List<Double> points, double timeStep) {

		filteredPoints = DataProcessor.removeParasiticValues(points);
		
		List<Double> sortedTermperaturePoints = new ArrayList<Double>(filteredPoints);
		Collections.sort(sortedTermperaturePoints);
		chartBounds = DataProcessor.findChartBounds(sortedTermperaturePoints);
		chartData = DataProcessor.getChartData(points, timeStep);		
		yAxisLowerBound = chartBounds[0];
		yAxisUpperBound = chartBounds[1];
		
		setLinearOffset(0.0);
		if (sortedTermperaturePoints.size() > 20) {
			minTemperaturePoints.addAll(sortedTermperaturePoints.subList(0, 10));
			Collections.reverse(sortedTermperaturePoints);
			maxTemperaturePoints.addAll(sortedTermperaturePoints.subList(0, 10));
			targetTemperature = (double) DataProcessor.findTargetValue(sortedTermperaturePoints);
			averageMin = DataProcessor.findAverage(minTemperaturePoints);
			averageMax = DataProcessor.findAverage(maxTemperaturePoints);		
			relativeMin = DataProcessor.findRelative(targetTemperature, averageMin);
			relativeMax = DataProcessor.findRelative(targetTemperature, averageMax);
		} else {
			targetTemperature = 0.0;
			averageMin = 0.0;
			averageMax = 0.0;
			relativeMin = 0.0;
			relativeMax = 0.0;
		}
	}
	
	
	public TemperatureStats(TemperatureStats stats) {
		this.targetTemperature = stats.getTargetTemperature();
		this.linearOffset = stats.getLinearOffset();
		this.filteredPoints = stats.getFilteredPoints();
		this.maxTemperaturePoints = stats.getMaxTemperaturePoints();
		this.minTemperaturePoints = stats.getMinTemperaturePoints();
		this.averageMax = stats.getAverageMax();
		this.averageMin = stats.getAverageMin();
		this.relativeMax = stats.getRelativeMax();
		this.relativeMin = stats.getRelativeMin();
		this.chartData = stats.getChartData();
		this.chartBounds = stats.getChartBounds();
		this.yAxisLowerBound = stats.getyAxisLowerBound();
		this.yAxisUpperBound = stats.getyAxisUpperBound();
	}
	
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Target: " + this.targetTemperature + "\n");
		sb.append("Linear Offset: " + this.linearOffset + "\n");
		sb.append("Avg Max: " + this.averageMax + "\n");
		sb.append("Avg Min: " + this.averageMin + "\n");
		sb.append("Rel Max: " + this.relativeMax + "\n");
		sb.append("Rel Min: " + this.relativeMin + "\n");
		sb.append("Lower Bound: " + this.yAxisLowerBound + "\n");
		sb.append("Upper Bound: " + this.yAxisUpperBound + "\n");
		
		return sb.toString();
		
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


	@Deprecated
	public double[] getChartBounds() {
		return chartBounds;
	}


	public List<Double> getFilteredPoints() {
		return filteredPoints;
	}


	public double getyAxisLowerBound() {
		return yAxisLowerBound;
	}


	public double getyAxisUpperBound() {
		return yAxisUpperBound;
	}

}