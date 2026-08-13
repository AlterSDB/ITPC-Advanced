package org.itpc_advanced.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import org.itpc_advanced.model.TemperatureRecord;
import org.itpc_advanced.model.Settings;

public class DataProcessor {

//	private static final AtomicInteger fileCounter = new AtomicInteger(1);

 /*	public static void calculate(TemperatureFileRecord dataFile) {
		dataFile.setFileId(fileCounter.getAndIncrement());

		int target = findTargetValue(dataFile.getPoints());
		List<Double> maxTemps = new ArrayList<Double>();
		List<Double> minTemps = new ArrayList<Double>();
		double averageMax = 0.0;
		double averageMin = 0.0;
		double relativeMax = 0.0;
		double relativeMin = 0.0;
		double[] chartBounds = new double[]{0, 10};

		if (dataFile.getPoints().size() > 20) {
			target = findTargetValue(dataFile.getPoints());
			ArrayList<Double> clearValues = removeParasiticValues(dataFile.getPoints());
			dataFile.setProcessedValues(new ArrayList<Double>(clearValues));
			List<Double> sortedValues = new ArrayList<Double>(dataFile.getProcessedValues());
			Collections.sort(sortedValues);

			for (int i = 0; i < 10; i++) {
				maxTemps.add(sortedValues.get(sortedValues.size() - 1 - i));
				minTemps.add(minTemps.size() - i, sortedValues.get(i));
			}

			averageMax = findAverage(maxTemps);
			averageMin = findAverage(minTemps);
			relativeMax = findRelative(target, averageMax);
			relativeMin = findRelative(target, averageMin);
			chartBounds = findChartBounds(sortedValues);
		}

		dataFile.setTargetTemperature(target);
		dataFile.setChartData(getChartData(dataFile));
		dataFile.setMaxTemps(maxTemps);
		dataFile.setMinTemps(minTemps);
		dataFile.setAverageMax(averageMax);
		dataFile.setAverageMin(averageMin);
		dataFile.setRelativeMax(relativeMax);
		dataFile.setRelativeMin(relativeMin);
		dataFile.setChartBounds(chartBounds);

		return;
	} */

	public static double findRelative(double target, double average) {
		return ( (average * 10) - (target * 10) ) / 10;
	}

	public static double findAverage(List<Double> maxTemps) {
		double average = 0.0;
		for (double value : maxTemps) {
			average += value;
		}
		average /= maxTemps.size();

		return Math.ceil(average * 10) / 10;
	}

	
	public static List<XYChart.Data<Number, Number>> getChartData(List<Double> points, double timeStep) {
		List<XYChart.Data<Number, Number>> chartData = new ArrayList<>();
		double time = 0.0;
		
		for(int i = 0; i < points.size() - 1; i++) {
			chartData.add(new XYChart.Data<Number, Number>(time, points.get(i)));
			time += timeStep / 60;
		}
		 
		return chartData;
	}

    public static double[] findChartBounds(List<Double> sortedValues) {	
		if (sortedValues.size() < 5) {
			return new double[] {0, 10};
		}

		double[] bounds  = new double[2];
		
		int multiplier = 5;
		// Lower bound
		bounds[0]  = sortedValues.get(0);
		bounds[0]  = Math.floor(bounds[0]) / multiplier;
		bounds[0]  = Math.floor(bounds[0]) * multiplier;

		// Upper bound
		bounds[1]  = sortedValues.get(sortedValues.size() - 1);
		bounds[1]  = Math.ceil(bounds[1]) / multiplier;
		bounds[1]  = Math.ceil(bounds[1]) * multiplier;

		if (bounds[0] == bounds[1]) {
			bounds[0] -= 5;
			bounds[1] += 5;
		}

		while (sortedValues.get(sortedValues.size() - 1) > bounds[1] - 1) {
			bounds[1] += 1;
		}

		while (sortedValues.get(1) < bounds[0] + 1) {
			bounds[0] -= 1;
		}

		return bounds;
    }

	public static int findTargetValue(List<Double> values) {
		double sum = 0;
		for (double value : values) {
			value = Math.round(value) / 10.0;
			value = Math.round(value) * 10.0;
			sum += value;
		}

		double target = sum / values.size();
		target = Math.round(target) / 10.0;
		target = Math.round(target) * 10.0;

		return (int) target;
	}

	public static ArrayList<Double> removeParasiticValues(List<Double> values) {
		if (values.isEmpty()) {
			return (ArrayList<Double>) values;
		}

		ArrayList<Double> resultValues = new ArrayList<Double>(values);
		ArrayList<Double> sortedValues = new ArrayList<Double>(values);
		Collections.sort(sortedValues);

		double middleValue = sortedValues.get( (sortedValues.size()/2) );
		double minValue = sortedValues.get(0);
		double maxValue = sortedValues.get(sortedValues.size() - 1);
		double maxDeviation = Settings.getInstance().getMaxDeviation();
		double currentMaxDeviation = Math.max(maxValue - middleValue, middleValue - minValue);	

		if (currentMaxDeviation > maxDeviation) {
			for (double value : values) {
				if (Math.abs(middleValue - value) > maxDeviation) {
					resultValues.remove(value);
				} 
			} 
		}

		return resultValues;
	}

	//public static void resetFilesCounter() {
//		fileCounter.set(1);
//	}

	public static List<Double> setLinearOffset(List<Double> values, Integer linearOffset) {
		List<Double> result = new ArrayList<Double>(values);
		for (int i = 0; i < values.size(); i++) {
			result.set(i, values.get(i) + linearOffset);
		}

		return result;
	}

}