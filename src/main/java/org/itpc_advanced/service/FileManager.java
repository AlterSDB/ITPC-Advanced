package org.itpc_advanced.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;

public class FileManager {
	
	public static List<String> readFile(String path) {
		List<String> lines = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			while (reader.ready()) {
				lines.add(reader.readLine());
			}
			System.out.println("Successful read!");
		} catch(IOException e) {
			System.err.println("Error while reading file: " + e.getMessage());
		}
		
		return lines;
		
	}
	
	public static void saveFile(File path, String data) {
		if (data != null) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
				writer.write(data);
				System.out.println("Successful save!");

			} catch(IOException e) {
				System.err.println("Error while saving file: " + e.getMessage());

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
