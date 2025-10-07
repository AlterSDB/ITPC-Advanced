package org.itpc_advanced.viewmodel;

import org.itpc_advanced.model.Settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettingsViewModel {

	private BooleanProperty shuffleValuesProperty = new SimpleBooleanProperty();
	private BooleanProperty demoModeProperty = new SimpleBooleanProperty();
	private StringProperty maxDeviationProperty = new SimpleStringProperty();
	private StringProperty connTimeoutProperty = new SimpleStringProperty();
	
	private Settings settings;
	
	public SettingsViewModel() {
		settings = Settings.getInstance();
		maxDeviationProperty.set(settings.getMaxDeviation().toString());
		connTimeoutProperty.set(settings.getConnectionTimeout().toString());
		shuffleValuesProperty.set(settings.isShuffleValues());
		demoModeProperty.set(settings.isDemoMode());
		
		shuffleValuesProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		connTimeoutProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		maxDeviationProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		demoModeProperty.addListener((obs, oldVal, newVal) -> saveSettings());
	}
	
	public void loadSettings() {
		// load from file
	}

	public void saveSettings() {

		if (maxDeviationProperty.getValue().equals("")) {
			settings.setMaxDeviation(0);
		} else {
			settings.setMaxDeviation(Integer.parseInt(maxDeviationProperty.getValue()));
		}
		
		if (connTimeoutProperty.getValue().equals("")) {
			settings.setConnectionTimeout(0);
		} else {
			settings.setConnectionTimeout(Integer.parseInt(connTimeoutProperty.getValue()));
		}
		
		settings.setShuffleValues(shuffleValuesProperty.getValue());
		settings.setDemoMode(demoModeProperty.getValue());
		// add save in file
	}

	public BooleanProperty shuffleValuesProperty() {
		return shuffleValuesProperty;
	}

	public void setShuffleValues(Boolean shuffleValues) {
		this.shuffleValuesProperty.set(shuffleValues);
	}

	public StringProperty maxDeviationProperty() {
		return maxDeviationProperty;
	}

	public void setMaxDeviation(Integer maxDeviation) {
		this.maxDeviationProperty.set(maxDeviation.toString());
	}

	public StringProperty timeoutProperty() {
		return connTimeoutProperty;
	}

	public void setTimeout(Integer timeout) {
		this.connTimeoutProperty.set(timeout.toString());
	}

	public BooleanProperty demoModeProperty() {
		return demoModeProperty;
	}

	public void setDemoMode(Boolean demoMode) {
		this.demoModeProperty.set(demoMode);
	}

}
