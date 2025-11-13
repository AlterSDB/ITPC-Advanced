package org.itpc_advanced.model;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.itpc_advanced.service.FileManager;


public class Settings {
	private String absolutePath = new File(".").getAbsolutePath() + "\\settings.ini";
	private static Settings instance = null;
	private String  port = "";
	private Boolean shuffleValues;
	private Boolean demoMode = true;
	private Integer maxDeviation = 50;
	private Integer connectionTimeout;

	private Settings() {
		File file = new File(absolutePath);

		if (file.exists()) {
			System.out.println("YES!");
			setSettingsFromFile(absolutePath);		
		} else {
			System.out.println("NO");
			setDefaultSettings();
			FileManager.saveFile(file, getSettingsAsText());
		}

	}

	private void setSettingsFromFile(String path) {
		try {
			List<String> strs = FileManager.readFile(path);	
			for (String str : strs) {
				str.replaceAll(" ", "");
				String[] str2 = str.split("=");
				String name = str2[0].intern();
				String value = str2[1].intern();
				
				switch(name) {
				case "demoMode":
					this.demoMode = Boolean.valueOf(value);
					break;
				case "maxDeviation":
					this.maxDeviation = Integer.parseInt(value);
					break;
				default:
					break;
				}
				System.out.println("variable " + name + " changed to " + value);
			}
			port = "COM1";
			shuffleValues = false;
			connectionTimeout = 5000;
			
		} catch(IOException e) {
			setDefaultSettings();
		}
		
	}
	
	public void saveToFile() {
		FileManager.saveFile(absolutePath, getSettingsAsText());
		System.out.println("Successful settings save");
	}

	private String getSettingsAsText() {
		 StringBuffer result = new StringBuffer();
		    result.append("port = " + port + System.lineSeparator());
		    result.append("shuffleValues = " + shuffleValues + System.lineSeparator());
		    result.append("demoMode = " + demoMode + System.lineSeparator());
		    result.append("maxDeviation = " + maxDeviation + System.lineSeparator());
		    result.append("connectionTimeout = " + connectionTimeout + System.lineSeparator());
		    
			return result.toString();
	}

	private void setDefaultSettings() {
		port = "COM1";
		shuffleValues = false;
		demoMode = true;
		maxDeviation = 50;
		connectionTimeout = 5000;
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

}