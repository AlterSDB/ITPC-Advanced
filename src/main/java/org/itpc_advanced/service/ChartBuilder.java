package org.itpc_advanced.service;

import org.itpc_advanced.utils.VisualFX;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class ChartBuilder {
	
	private static LineChart<Number, Number> lineChart;
	private static NumberAxis x;
	private static NumberAxis y;
	
	public static LineChart<Number, Number> createChart(LineChart<Number, Number> lineChart, NumberAxis x, NumberAxis y) {
		ChartBuilder.lineChart = lineChart;
		ChartBuilder.x = x;
		ChartBuilder.y = y;
		x.setUpperBound(15);
		x.setMinorTickCount(2);
		x.setLabel("Время, мин.");
		y.setAutoRanging(false);
		y.setLowerBound(0);
		y.setUpperBound(10);
		y.setTickUnit(1);
		y.setMinorTickCount(0);
		y.setLabel("Температура, Т°С");
		lineChart.setTitle("Температурная характеристика");
		lineChart.setCreateSymbols(false);
		lineChart.setLegendVisible(false);
		lineChart.setAnimated(false);
		lineChart.setLegendSide(Side.LEFT);
		
		return lineChart;
	}
	

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void changeChart(ObservableList<Data> newChartData, double[] bounds, String fileName) {
		XYChart.Series newSeries = new XYChart.Series();
		y.setLowerBound(bounds[0]);
		y.setUpperBound(bounds[1]);
		newSeries.setData(newChartData);
		newSeries.setName(fileName);

			lineChart.getData().clear();

		lineChart.getData().add(newSeries);
		VisualFX.slideTransition(newSeries.getNode());
	}

}
