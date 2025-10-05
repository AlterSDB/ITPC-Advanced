package org.itpc_advanced.viewmodel;

import org.itpc_advanced.model.Settings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettingsViewModel {
	
	//private MenuButton selectPortMenu;
	private StringProperty shuffleValues = new SimpleStringProperty();
	private StringProperty timestepProperty = new SimpleStringProperty();
	private StringProperty timeoutProperty = new SimpleStringProperty();
	
	private Settings settings;
	
	public SettingsViewModel() {
		settings = Settings.getInstance();
		timestepProperty.set(settings.getMaxDeviation().toString());
		timeoutProperty.set(settings.getConnectionTimeout().toString());
		
	}

	public StringProperty shuffleValuesProperty() {
		return shuffleValues;
	}

	public void setShuffle(Boolean shuffleValues) {
		this.shuffleValues.set(shuffleValues.toString());;
	}

	public StringProperty timestepProperty() {
		return timestepProperty;
	}

	public void setTimeStep(Integer timestep) {
		this.timestepProperty.set(timestep.toString());
	}

	public StringProperty timeoutProperty() {
		return timeoutProperty;
	}

	public void setTimeout(Integer timeout) {
		this.timeoutProperty.set(timeout.toString());
	}

}
