package org.itpc_advanced.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemperatureRecord {

	// Raw data
	private final String tcType;
	private final LocalDateTime timeStamp;
	private final Double timeStep;
	private final List<Double> points;
	private final Integer pointsCount;


	public TemperatureRecord(String tcType, LocalDateTime timeStamp, Double timeStep, List<Double> points) {
		this.tcType = tcType;
		this.timeStamp = timeStamp;
		this.timeStep = timeStep;
		this.points = points;
		this.pointsCount = points.size();
	}

	public TemperatureRecord() {
		this.tcType = "types.k";
		this.timeStamp = LocalDateTime.now();
		this.timeStep = 0.0;
		this.points = new ArrayList<Double>();
		this.pointsCount = 0;
	}

	public TemperatureRecord(List<Double> points) {
		if (Math.random() > 0.5) {
			this.tcType = "types.l";
		} else {
			if (Math.random() > 0.5) {
				this.tcType = "types.k";
			} else {
				this.tcType = "types.b";
			}
		}
		
		
		this.timeStamp = LocalDateTime.now();
		this.timeStep = 15.0;
		this.points = points;
		this.pointsCount = points.size();
	}

	public List<Double> getPoints() {
		return points;
	}

	public String getTcType() {
		return tcType;
	}

	public Double getTimeStep() {
		return timeStep;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public Integer getPointsCount() {
		return pointsCount;
	}

}