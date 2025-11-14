package org.itpc_advanced.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.itpc_advanced.model.Settings;

public class SettingsManager {

	private static String absolutePath = new File(".").getAbsolutePath() + "\\settings.ini";	
	
	public static void initializeSettings(Settings settings) {
		File file = new File(absolutePath);

		if (file.exists()) {
			System.out.println("Settings exists!");
			setSettingsFromFile(absolutePath, settings);		
		} else {
			System.out.println("Settings not exists");
			setDefaultSettings(settings);
			FileManager.saveFile(file, getSettingsAsText(settings));
		}
		
	}

	public static void setSettingsFromFile(String path, Settings settings) {
		try {
			List<String> strs = FileManager.readFile(path);	
			for (String str : strs) {
				String[] str2 = str.split("=");
				String name = str2[0];
				String value = str2[1];
				System.out.println(name.contains("demoMode"));

				if (name.contains("demoMode")) {
					settings.setDemoMode(Boolean.valueOf(value));
					System.out.println(value + " - demomode now");
					System.out.println("variable " + name + " changed to " + value);
					continue;
				}
				if (name.contains("maxDeviation")) {
					settings.setMaxDeviation(Integer.parseInt(value.replaceAll(" ", "")));
					System.out.println(value + " - maxdev now");
					System.out.println("variable " + name + " changed to " + value);
					continue;
				}
				if (name.contains("port")) {
					settings.setPort(value.replaceAll(" ", ""));
					System.out.println(value + " - port now");
					System.out.println("variable " + name + " changed to " + value);
					continue;
				}
				if (name.contains("shuffleValues")) {
					settings.setShuffleValues(Boolean.parseBoolean(value.replaceAll(" ", "")));
					System.out.println(value + " - shuffleValues now");
					System.out.println("variable " + name + " changed to " + value);
					continue;
				}
				if (name.contains("connectionTimeout")) {
					settings.setConnectionTimeout(Integer.parseInt(value.replaceAll(" ", "")));
					System.out.println(value + " - connectionTimeout now");
					System.out.println("variable " + name + " changed to " + value);
					continue;
				}
			}
		} catch(IOException e) {
			System.err.println("Error parsing settings file. Default settings was restored.");
			setDefaultSettings(settings);
		}
		
	}
	
	public static void saveToFile(Settings settings) {
		FileManager.saveFile(absolutePath, getSettingsAsText(settings));
		System.out.println("Successful settings save");
	}

	public static String getSettingsAsText(Settings settings) {
		 StringBuffer result = new StringBuffer();
		    result.append("port = " + settings.getPort() + System.lineSeparator());
		    result.append("shuffleValues = " + settings.isShuffleValues() + System.lineSeparator());
		    result.append("demoMode = " + settings.isDemoMode() + System.lineSeparator());
		    result.append("maxDeviation = " + settings.getMaxDeviation() + System.lineSeparator());
		    result.append("connectionTimeout = " + settings.getConnectionTimeout() + System.lineSeparator());
		//    result.append("language = " + settings.getLanguage() + System.lineSeparator());
		    
			return result.toString();
	}

	public static void setDefaultSettings(Settings settings) {
		settings.setPort("COM1");
		settings.setShuffleValues(false);
		settings.setDemoMode(true);
		settings.setMaxDeviation(50);
		settings.setConnectionTimeout(5000);
	}

}
