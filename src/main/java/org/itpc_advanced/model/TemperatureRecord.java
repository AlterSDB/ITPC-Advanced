package org.itpc_advanced.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemperatureRecord {

	private String tcType;
	private LocalDateTime timeStamp;
	private double timeStep;
	private List<Double> points;
	private int id;
	private TemperatureStats stats;
	
	
	public TemperatureRecord(int id, String tcType, LocalDateTime timeStamp, Double timeStep, List<Double> points) {
		this.id = id;
		this.tcType = tcType;
		this.timeStamp = timeStamp;
		this.timeStep = timeStep;
		this.points = points;
		this.stats = new TemperatureStats(this.points, this.timeStep);
	}

	public TemperatureRecord() {
		this.id = 0;
		this.tcType = "types.unknown";
		this.timeStamp = LocalDateTime.now();
		this.timeStep = 0.0;
		this.points = new ArrayList<Double>();
	}

	public TemperatureRecord(List<Double> points) {
		this.id = 0;
		this.tcType = "types.unknown";		
		this.timeStamp = LocalDateTime.now();
		this.timeStep = 15.0;
		this.points = points;
		this.stats =new TemperatureStats(this.points, this.timeStep);
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
		return points.size();
	}

	public Integer getId() {
		return id;
	}

	public TemperatureStats getStats() {
		return stats;
	}

}