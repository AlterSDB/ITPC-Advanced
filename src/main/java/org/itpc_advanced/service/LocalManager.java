package org.itpc_advanced.service;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LocalManager {
	private static LocalManager instance;
	private final ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();
	private final ObjectProperty<Locale> currentLocale = new SimpleObjectProperty<>();
	
	
	private LocalManager() {
		setLocale(Locale.ENGLISH);
	}
	
	public static LocalManager getInstance() {
		if (instance == null) {
			instance = new LocalManager();
		}
		return instance;
	}


	private void setLocale(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("lang", locale );
		resources.set(bundle);
		currentLocale.set(locale);
		
	}
	
	private String getString(String key) {
		ResourceBundle bundle = resources.get();
		if (bundle != null) {
			try {
				return bundle.getString(key);
			} catch (Exception e) {
				return "!" + key + "!";
			}
		}
		return key;
	}

	public ResourceBundle getResources() {
		return resources.get();
	}

	public Locale getCurrentLocale() {
		return currentLocale.get();
	}

	public ObjectProperty<ResourceBundle> resourceBundleProperty() {
		return resources;
	}

	public ObjectProperty<Locale> currentLocaleProperty() {
		return currentLocale;
	}
	

}
