package org.itpc_advanced.service;

import org.itpc_advanced.model.Settings;

public class SettingsService {
	
	
	
	public static Settings loadSettings() {
		Settings settings = Settings.getInstance();
	// если путь к файлу существует и файл существует, загружаем его и меняем настройки	if ()
		// если нет, оставляем дефолтные значения и записываем новый файл настроек через savesettgins
		
		
		return settings;
	}
	
	public static void saveSettings() {
		
	}

}
