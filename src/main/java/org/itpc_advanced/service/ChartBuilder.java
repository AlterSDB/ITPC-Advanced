package org.itpc_advanced.service;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class ChartBuilder {
	
	@FXML static LineChart<Number, Number> lineChart;
	@FXML static NumberAxis x;
	@FXML static NumberAxis y;
	
	public static LineChart<Number, Number> createChart() {
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

}
