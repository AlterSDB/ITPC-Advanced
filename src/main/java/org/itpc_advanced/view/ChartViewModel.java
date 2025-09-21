package org.itpc_advanced.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class ChartViewModel {
	private final ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private final StringProperty chartTitle = new SimpleStringProperty();
	private final StringProperty xAxisLabel = new SimpleStringProperty();
	private final StringProperty yAxisLabel = new SimpleStringProperty();
	
	public ChartViewModel() {
		this.chartTitle.set("Температурная характеристика");
		this.xAxisLabel.set("Время, мин.");
		this.yAxisLabel.set("Температура, Т°С");
	}

	public ObservableList<XYChart.Data<Number, Number>> getChartData() {
		return chartData;
	}

	public StringProperty chartTitleProperty() {
		return chartTitle;
	}

	public StringProperty xAxisLabelProperty() {
		return xAxisLabel;
	}

	public StringProperty yAxisLabelProperty() {
		return yAxisLabel;
	}
	
	public String getChartTitle() {
		return chartTitle.get();
	}

	public String getxAxisLabel() {
		return xAxisLabel.get();
	}

	public String getyAxisLabel() {
		return yAxisLabel.get();
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle.set(chartTitle);;
	}

	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel.set(xAxisLabel);
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel.set(yAxisLabel);
	}
	
	public void addDataPoint(Number x, Number y) {
		chartData.add(new XYChart.Data<>(x, y));
	}
	
	public void clearData() {
		chartData.clear();
		
	}

	public void setData(ObservableList<XYChart.Data<Number, Number>> data) {
		chartData.setAll(data);
	}
	

}
