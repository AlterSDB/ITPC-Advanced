package org.itpc_advanced.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.itpc_advanced.service.DataProcessor;

public class TemperatureStats {

	private final TemperatureFileRecord rawTemperatureRecord;
	
	private Double targetTemperature;
	private Double linearOffset;
	
	private List<Double> minTemperaturePoints = new ArrayList<Double>();
	private List<Double> maxTemperaturePoints = new ArrayList<Double>();
	
	private Double averageMin;
	private Double averageMax;
	private Double relativeMin;
	private Double relativeMax;


	public TemperatureStats(TemperatureFileRecord rawTemperatureRecord) {
		this.rawTemperatureRecord = rawTemperatureRecord;
		List<Double> sortedTermperaturePoints = new ArrayList<Double>(rawTemperatureRecord.getPoints());
		Collections.sort(sortedTermperaturePoints);
		getMinTemperaturePoints().addAll(sortedTermperaturePoints.subList(0, 10));
		Collections.reverse(sortedTermperaturePoints);
		getMaxTemperaturePoints().addAll(sortedTermperaturePoints.subList(0, 10));
		
		targetTemperature = (double) DataProcessor.findTargetValue(sortedTermperaturePoints);
		
		averageMin = DataProcessor.findAverage(getMinTemperaturePoints());
		averageMax = DataProcessor.findAverage(getMaxTemperaturePoints());
		
		relativeMin = DataProcessor.findRelative(getTargetTemperature(), getAverageMin());
		relativeMax = DataProcessor.findRelative(getTargetTemperature(), getAverageMax());
		
		setLinearOffset(0.0);
	}

	
	public TemperatureFileRecord getRawTemperatureRecord() {
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
 

}