package org.itpc_advanced.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.itpc_advanced.model.Settings;

public class SettingsManager {

	private static final String absolutePath = new File(".").getAbsolutePath() + "\\settings.ini";
	
	public static void initializeSettings(Settings settings) {
		File file = new File(absolutePath);

		if (file.exists()) {
			setSettingsFromFile(absolutePath, settings);		
		} else {
			setDefaultSettings(settings);
			FileManager.saveFile(file, getSettingsAsText(settings));
		}
		
	}

	public static void setSettingsFromFile(String path, Settings settings) {
		try {
			List<String> lines = FileManager.readFile(path);
			for (String line : lines) {
				String[] subline = line.split("=");
				String name = subline[0];
				String value = subline[1];

				if (name.contains("demoMode")) {
					settings.setDemoMode(Boolean.valueOf(value));
					continue;
				}
				if (name.contains("maxDeviation")) {
					settings.setMaxDeviation(Integer.parseInt(value.replaceAll(" ", "")));
					continue;
				}
				if (name.contains("port")) {
					settings.setPort(value.replaceAll(" ", ""));
					continue;
				}
				if (name.contains("shuffleValues")) {
					settings.setShuffleValues(Boolean.parseBoolean(value.replaceAll(" ", "")));
					continue;
				}
				if (name.contains("connectionTimeout")) {
					settings.setConnectionTimeout(Integer.parseInt(value.replaceAll(" ", "")));
					continue;
				}
				if (name.contains("language")) {
					settings.setLanguage(value.replaceAll(" ", ""));
				}
			}
		} catch(IOException e) {
			System.err.println("Error parsing settings file. Default settings was restored.");
			setDefaultSettings(settings);
		}
		
	}
	
	public static void saveToFile(Settings settings) {
		FileManager.saveFile(absolutePath, getSettingsAsText(settings));
	}

	public static String getSettingsAsText(Settings settings) {
		return "port = " + settings.getPort() + System.lineSeparator() +
                "shuffleValues = " + settings.isShuffleValues() + System.lineSeparator() +
                "demoMode = " + settings.isDemoMode() + System.lineSeparator() +
                "maxDeviation = " + settings.getMaxDeviation() + System.lineSeparator() +
                "connectionTimeout = " + settings.getConnectionTimeout() + System.lineSeparator() +
                "language = " + settings.getLanguage() + System.lineSeparator();
	}

	public static void setDefaultSettings(Settings settings) {
		settings.setPort("COM1");
		settings.setShuffleValues(false);
		settings.setDemoMode(true);
		settings.setMaxDeviation(50);
		settings.setConnectionTimeout(5000);
		settings.setLanguage("en");
	}

}
