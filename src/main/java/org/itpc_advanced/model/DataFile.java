package org.itpc_advanced.model;

import java.time.LocalDateTime;
import java.util.List;

public class DataFile {
	private String tcType = null;
	private LocalDateTime timeStamp = null;
	private Double timeStep = null;
	private List<Double> values = null;
	
	public DataFile(String tcType, LocalDateTime timeStamp, Double timeStep, List<Double> values) {
		this.tcType = tcType;
		this.timeStamp = timeStamp;
		this.timeStep = timeStep;
		this.values = values;
	}
	
	public String getTcType() {
		return tcType;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public Double getTimeStep() {
		return timeStep;
	}

	public List<Double> getValues() {
		return values;
	}
		
}
	
