package org.itpc_advanced.service;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.ProcessedDataFile;
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class DataProcessor {
	
	
	public static ProcessedDataFile process(DataFile dataFile) {
		ProcessedDataFile processedDf = new ProcessedDataFile(dataFile);
		int target = findTargetValue(dataFile.getValues());
		ObservableList<XYChart.Data> chartData = getChartData(dataFile);
		int[] maxTemps = null;
		int[] minTemps = null;
		double averageMax = findAverage(maxTemps);
		double averageMin = findAverage(minTemps);
		
		processedDf.setTargetTemperature(target);
		processedDf.setChartData(chartData);
		processedDf.setMaxTemps(maxTemps);
		processedDf.setMinTemps(minTemps);
		processedDf.setAverageMax(averageMax);
		processedDf.setAverageMin(averageMin);
		
		return processedDf;
	}
	
	private static double findAverage(double[] array) {
		double average = 0.0;
		for(double value : array) {
			average += value;
		}
		average /= array.length;

		return Math.floor(average * 10) / 10;
	}

	private static ObservableList<XYChart.Data> getChartData(DataFile df) {
		ObservableList<XYChart.Data> chartData = FXCollections.observableArrayList();
		double time = 0.0;
		
		for (int i = 0; i < df.getValues().size(); i++){
			chartData.add(new XYChart.Data(time, df.getValues().get(i)));
			time += df.getTimeStep();
		}

		return chartData;
	}
	
	private static int findTargetValue(List<Double> values) {
		double sum = 0;
		for(double value : values) {
			value = Math.round(value) / 10.0;
			value = Math.round(value) * 10.0;
			sum += value;
		}

		double target = sum / values.size();
		target = Math.round(target) / 10.0;
		target = Math.round(target) * 10.0;

		return (int) target;
	}


}