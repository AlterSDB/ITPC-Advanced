package org.itpc_advanced.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;

public class FileManager {
	
	public static void readFile(String path) {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void saveFile(File path, String data) {
		if (data != null) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
				writer.write(data);
				System.out.println("Successful save!");

			} catch(IOException e) {
				System.err.println("File save error: " + e.getMessage());

			}
		}
	}

	public static FileChooser getFileChooser(String defaultFileName) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as...");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (.txt)", ".txt"));
		fileChooser.setInitialFileName(defaultFileName);
		return fileChooser;
	}
	

}
