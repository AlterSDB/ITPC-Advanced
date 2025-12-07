package org.itpc_advanced.model;

public class Settings {
	private static Settings instance = null;
	private String  port = "COM1";
	private Boolean shuffleValues = true;
	private Boolean demoMode = true;
	private Integer maxDeviation = 50;
	private Integer connectionTimeout = 5000;
	private String language = "en";

	private Settings() {

	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}

		return instance;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getMaxDeviation() {
		if (maxDeviation != null) {
			return maxDeviation;
		}
		return 500;
	}

	public void setMaxDeviation(Integer maxDeviation) {
		this.maxDeviation = maxDeviation;
	}

	public Boolean isShuffleValues() {
		return shuffleValues;
	}

	public void setShuffleValues(Boolean shuffleValues) {
		this.shuffleValues = shuffleValues;
	}

	public Boolean isDemoMode() {
		return demoMode;
	}

	public void setDemoMode(Boolean demoMode) {
		this.demoMode = demoMode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}