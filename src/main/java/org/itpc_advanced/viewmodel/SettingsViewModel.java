package org.itpc_advanced.viewmodel;

import java.util.Locale;

import org.itpc_advanced.model.Settings;
import org.itpc_advanced.service.LocalManager;
import org.itpc_advanced.service.SettingsManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettingsViewModel {

	private final BooleanProperty shuffleValuesProperty = new SimpleBooleanProperty();
	private final BooleanProperty demoModeProperty = new SimpleBooleanProperty();
	private final StringProperty maxDeviationProperty = new SimpleStringProperty();
	private final StringProperty connTimeoutProperty = new SimpleStringProperty();
	private final StringProperty selectedPortProperty = new SimpleStringProperty();
	private final Settings settings = Settings.getInstance();
	private final LocalManager localization = LocalManager.getInstance();

	public SettingsViewModel() {
		SettingsManager.initializeSettings(settings);
		maxDeviationProperty.set(settings.getMaxDeviation().toString());
		connTimeoutProperty.set(settings.getConnectionTimeout().toString());
		shuffleValuesProperty.set(settings.isShuffleValues());
		demoModeProperty.set(settings.isDemoMode());
		selectedPortProperty.set(settings.getPort());

		shuffleValuesProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		connTimeoutProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		maxDeviationProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		demoModeProperty.addListener((obs, oldVal, newVal) -> saveSettings());
		selectedPortProperty.addListener((obs, oldVal, newVal) -> saveSettings());
	}

	public void saveSettings() {
		if (maxDeviationProperty.getValue().isEmpty()) {
			settings.setMaxDeviation(0);
		} else {
			settings.setMaxDeviation(Integer.parseInt(maxDeviationProperty.getValue()));
		}

		if (connTimeoutProperty.getValue().isEmpty()) {
			settings.setConnectionTimeout(0);
		} else {
			settings.setConnectionTimeout(Integer.parseInt(connTimeoutProperty.getValue()));
		}
		settings.setShuffleValues(shuffleValuesProperty.getValue());
		settings.setDemoMode(demoModeProperty.getValue());
		settings.setPort(selectedPortProperty.getValue());
		SettingsManager.saveToFile(settings);
	}

	public void changeLanguage() {
		if (localization.isEnglish()) {
			localization.setLocale(new Locale("ru"));
		} else {
			localization.setLocale(Locale.ENGLISH);
		}
	}

	public BooleanProperty shuffleValuesProperty() {
		return shuffleValuesProperty;
	}

	public StringProperty maxDeviationProperty() {
		return maxDeviationProperty;
	}

	public StringProperty timeoutProperty() {
		return connTimeoutProperty;
	}
	
	public StringProperty selectedPortProperty() {
		return selectedPortProperty;
	}

	public BooleanProperty demoModeProperty() {
		return demoModeProperty;
	}

}