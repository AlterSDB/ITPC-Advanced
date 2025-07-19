package org.itpc_advanced.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Settings {

	private AnchorPane  pane;
	private StringProperty  port = new SimpleStringProperty();
	private BooleanProperty automaticTarget = new SimpleBooleanProperty();
	private DoubleProperty  connectionTimeout = new SimpleDoubleProperty();
	private DoubleProperty  timeStep = new SimpleDoubleProperty();
	private DoubleProperty  maxDeviation = new SimpleDoubleProperty();
	private BooleanProperty multipleCharts = new SimpleBooleanProperty();
	private BooleanProperty autoLoad = new SimpleBooleanProperty();
	private BooleanProperty shuffleValues = new SimpleBooleanProperty();

	public Settings(AnchorPane pane) {
		this.pane = pane;
		port.setValue("COM1");
		autoLoad.setValue(false);
		multipleCharts.setValue(false);
		automaticTarget.setValue(true);
		connectionTimeout.setValue(650.0);
		timeStep.setValue(0.15);
		maxDeviation.setValue(40.0);
		shuffleValues.setValue(true);
	}
	
	public StringProperty getPort() {
		return port;
	}

	public void setPort(StringProperty port) {
		this.port = port;
	}

	public BooleanProperty getAutomaticTarget() {
		return automaticTarget;
	}

	public void setAutomaticTarget(BooleanProperty automaticTarget) {
		this.automaticTarget = automaticTarget;
	}

	public DoubleProperty getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(String connectionTimeout) {
		if(Double.valueOf(connectionTimeout) != null) {
		this.connectionTimeout.setValue(Double.valueOf(connectionTimeout));
		}
	}

	public DoubleProperty getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(String timeStep) {
		if(timeStep.isEmpty()) return;
		if(Double.valueOf(timeStep) != null) {
		this.timeStep.setValue(Double.valueOf(timeStep));
		}
	}

	public DoubleProperty getMaxDeviation() {
		return maxDeviation;
	}

	public void setMaxDeviation(DoubleProperty maxDeviation) {
		this.maxDeviation = maxDeviation;
	}

	public BooleanProperty getMultipleCharts() {
		return multipleCharts;
	}

	public void setMultipleCharts(BooleanProperty multipleCharts) {
		this.multipleCharts = multipleCharts;
	}

	public BooleanProperty getAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(BooleanProperty autoLoad) {
		this.autoLoad = autoLoad;
	}

	public BooleanProperty getShuffleValues() {
		return shuffleValues;
	}

	public void setShuffleValues(BooleanProperty shuffleValues) {
		this.shuffleValues = shuffleValues;
	}
	
	public void close() {
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
		
	}

}
