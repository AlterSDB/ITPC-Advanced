package org.itpc_advanced.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.ProcessedDataFile;
@SuppressWarnings({ "unchecked", "unused", "rawtypes"})

public class DataProcessor {
	
	public static ProcessedDataFile process(DataFile dataFile) {
		ProcessedDataFile processedDf = new ProcessedDataFile(dataFile);
		int target = findTargetValue(dataFile.getValues());
		ObservableList<XYChart.Data> chartData = getChartData(dataFile);
		List<Double> sortedValues = new ArrayList<Double>(dataFile.getValues());
		Collections.sort(sortedValues);

		List<Double> maxTemps = new ArrayList<Double>();
		List<Double> minTemps = new ArrayList<Double>();

		for(int i = 0; i < 10; i++) {
			maxTemps.add(sortedValues.get(sortedValues.size() - 1 - i));
			minTemps.add(minTemps.size() - i, sortedValues.get(i));
		}
		
		double averageMax = findAverage(maxTemps);
		double averageMin = findAverage(minTemps);
		double relativeMax = findRelative(target, averageMax);
		double relativeMin = findRelative(target, averageMin);
		double[] chartBounds = findChartBounds(sortedValues);
		
		processedDf.setTargetTemperature(target);
		processedDf.setChartData(chartData);
		processedDf.setMaxTemps(maxTemps);
		processedDf.setMinTemps(minTemps);
		processedDf.setAverageMax(averageMax);
		processedDf.setAverageMin(averageMin);
		processedDf.setRelativeMax(averageMax);
		processedDf.setRelativeMin(averageMin);
		processedDf.setChartBounds(chartBounds);
		
		return processedDf;
	}
	
	public static double findRelative(double target, double average) {
		double relative = average - target;
		double result = Math.ceil(relative * 10) / 10;
		return result;
	}

	private static double findAverage(List<Double> maxTemps) {
		double average = 0.0;
		for (double value : maxTemps) {
			average += value;
		}
		average /= maxTemps.size();

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
	
    private static double[] findChartBounds(List<Double> sortedValues) {
		if(sortedValues.size() < 5) {
			return new double[] {0, 10};
		}

		double[] bounds     = new double[2];
		double   lowerBound = sortedValues.get(0);
		double   upperBound = sortedValues.get(sortedValues.size() - 1);
		upperBound = Math.ceil(upperBound) / 10;
		upperBound = Math.ceil(upperBound) * 10;
		lowerBound = Math.floor(lowerBound) / 10;
		lowerBound = Math.floor(lowerBound) * 10;
		bounds[0]  = lowerBound;
		bounds[1]  = upperBound;
		
		if(bounds[1] - bounds[0] == 20) {
			bounds[0] += 5;
			bounds[1] -= 5;
			while(sortedValues.get(sortedValues.size() - 1) > bounds[1]) {
				bounds[0] += 1;
				bounds[1] += 1;
			}
			while(sortedValues.get(1) < bounds[0]) {
				bounds[0] -= 1;
				bounds[1] -= 1;
			}
		}

		return bounds;
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