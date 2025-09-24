package org.itpc_advanced.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.DataFile;
@SuppressWarnings({ "unchecked", "unused", "rawtypes"})

public class DataProcessor {
	
	private static final AtomicInteger fileCounter = new AtomicInteger(1);

	public static void calculate(DataFile dataFile) {		
		dataFile.setFileId(fileCounter.getAndIncrement());
		
		if (dataFile.getValues().size() < 20) {
			dataFile.setTargetTemperature(0);
			dataFile.setChartData(FXCollections.observableArrayList());
			dataFile.setMaxTemps(new ArrayList<Double>());
			dataFile.setMinTemps(new ArrayList<Double>());
			dataFile.setAverageMax(0.0);
			dataFile.setAverageMin(0.0);
			dataFile.setRelativeMax(0.0);
			dataFile.setRelativeMin(0.0);
			dataFile.setChartBounds(new double[] { 0.0, 10.0 });
			
			return;
		}
		int target = findTargetValue(dataFile.getValues());
		ObservableList<XYChart.Data<Number,Number>> chartData = getChartData(dataFile);
		List<Double> sortedValues = new ArrayList<Double>(dataFile.getValues());
		Collections.sort(sortedValues);

		List<Double> maxTemps = new ArrayList<Double>();
		List<Double> minTemps = new ArrayList<Double>();
		
		for(int i = 0; i < 10; i++) {
		//	System.out.println(sortedValues.size() + "     " + i);
			maxTemps.add(sortedValues.get(sortedValues.size() - 1 - i));
			minTemps.add(minTemps.size() - i, sortedValues.get(i));
		}
		
		double averageMax = findAverage(maxTemps);
		double averageMin = findAverage(minTemps);
		double relativeMax = findRelative(target, averageMax);
		double relativeMin = findRelative(target, averageMin);
		double[] chartBounds = findChartBounds(sortedValues);
		
		dataFile.setTargetTemperature(target);
		dataFile.setChartData(chartData);
		dataFile.setMaxTemps(maxTemps);
		dataFile.setMinTemps(minTemps);
		dataFile.setAverageMax(averageMax);
		dataFile.setAverageMin(averageMin);
		dataFile.setRelativeMax(averageMax);
		dataFile.setRelativeMin(averageMin);
		dataFile.setChartBounds(chartBounds);
		
		return;
	}
	
	public static double findRelative(double target, double average) {
		double relative = average - target;
		double result = Math.ceil(relative * 10) / 10;
		return result;
	}

	public static double findAverage(List<Double> maxTemps) {
		double average = 0.0;
		for (double value : maxTemps) {
			average += value;
		}
		average /= maxTemps.size();

		return Math.floor(average * 10) / 10;
	}

	private static ObservableList<XYChart.Data<Number,Number>> getChartData(DataFile df) {
		ObservableList<XYChart.Data<Number,Number>> chartData = FXCollections.observableArrayList();
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
	

	public static void resetFilesCounter() {
		fileCounter.set(1);	
	}

	public static List<Double> setLinearOffset(List<Double> values, Integer linearOffset) {
		List<Double> result = new ArrayList<Double>(values);
		for (int i = 0; i < values.size(); i++) {
			result.set(i, values.get(i) + linearOffset); 
		}
		return result;
	}

}