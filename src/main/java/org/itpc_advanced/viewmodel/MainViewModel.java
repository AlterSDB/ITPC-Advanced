package org.itpc_advanced.viewmodel;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Table;
import org.itpc_advanced.service.DataParser;
import org.itpc_advanced.service.DeviceScanner;
import org.itpc_advanced.utils.MockyDataFiles;
import org.itpc_advanced.utils.VisualFX;
import org.itpc_advanced.view.ChartViewModel;
import org.itpc_advanced.view.MainView;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.util.converter.NumberStringConverter;

public class MainViewModel {
	private final ObservableList<DataFile> fileList = FXCollections.observableArrayList();   
	private final ObjectProperty<DataFile> selectedDataFile = new SimpleObjectProperty<DataFile>();
	private StringProperty averageMaxProperty = new SimpleStringProperty();
	private StringProperty averageMinProperty = new SimpleStringProperty();
	private final StringProperty relativeMaxProperty = new SimpleStringProperty();
	private StringProperty relativeMinProperty = new SimpleStringProperty();
	private StringProperty tempSetProperty = new SimpleStringProperty();
	
	private final ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private final StringProperty chartTitle = new SimpleStringProperty();
	private final StringProperty xAxisLabel = new SimpleStringProperty();
	private final StringProperty yAxisLabel = new SimpleStringProperty();
	private final DoubleProperty yAxisLowerBoundProperty = new SimpleDoubleProperty();
	private final DoubleProperty yAxisUpperBoundProperty = new SimpleDoubleProperty();
	
	public MainViewModel(){	
		selectedDataFile.addListener(new ChangeListener<DataFile>() {
			@Override
			public void changed(ObservableValue<? extends DataFile> observable,
					DataFile oldDataFile, DataFile newDataFile) {
				if(newDataFile != null) {
					System.out.println("CHANGED");
					tempSetProperty.setValue(newDataFile.getTargetTemperature().getValue().toString());
					newDataFile.setTargetTemperature(Double.parseDouble(tempSetProperty.getValue()));
					relativeMaxProperty.setValue(newDataFile.getRelativeMax().getValue().toString());
					relativeMinProperty.setValue(newDataFile.getRelativeMin().getValue().toString());
					averageMaxProperty.setValue(newDataFile.getAverageMax().getValue().toString());
					averageMinProperty.setValue(newDataFile.getAverageMin().getValue().toString());
					
					yAxisLowerBoundProperty.set(newDataFile.getChartBounds()[0]);
					yAxisUpperBoundProperty.set(newDataFile.getChartBounds()[1]);
					chartData.clear();
					chartData.setAll(newDataFile.getChartData());
				
					
					
					//	relativeMaxProperty.bind(newDataFile.getRelativeMax().asString());
					//	relativeMinProperty.bind(newDataFile.getRelativeMin().asString());
					//	averageMaxProperty.bind(newDataFile.getAverageMax().asString());
					//	averageMinProperty.bind(newDataFile.getAverageMin().asString());					
				}
			}
		});
		tempSetProperty.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				System.out.println("CHANGED TARGET to " + Double.parseDouble(newValue));
				if(selectedDataFile.getValue() != null && !newValue.isEmpty()) {		
				selectedDataFile.getValue().setTargetTemperature(Double.parseDouble(newValue));
				}
			}
			
		});
		
		
		this.chartTitle.set("Температурная характеристика");
		this.xAxisLabel.set("Время, мин.");
		this.yAxisLabel.set("Температура, Т°С");
		this.yAxisLowerBoundProperty.set(0);
		this.yAxisUpperBoundProperty.set(10);		
	}

	public ObservableList<DataFile> getFileList() {
		return fileList;
	}

	public ObjectProperty<DataFile> selectedDataFileProperty() {
		return selectedDataFile;
	}

	public void readDataFiles() {
		fileList.clear();
		fileList.addAll(MockyDataFiles.mock());
		//fileList.addAll(DeviceScanner.readDataFiles());
	}

	public StringProperty averageMaxProperty() {
		return averageMaxProperty;
	}

	public StringProperty averageMinProperty() {
		return averageMinProperty;
	}

	public StringProperty relativeMaxProperty() {
		return relativeMaxProperty;
	}

	public StringProperty relativeMinProperty() {
		return relativeMinProperty;
	}

	public StringProperty tempSetProperty() {
		return tempSetProperty;
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

	public DoubleProperty yAxisLowerBoundProperty() {
		return yAxisLowerBoundProperty;
	}
	public DoubleProperty yAxisUpperBoundProperty() {
		return yAxisUpperBoundProperty;
	}

	/*public void setData(ObservableList<XYChart.Data<Number, Number>> data) {
		y.setLowerBound(bounds[0]);
		y.setUpperBound(bounds[1]);
		newSeries.setData(newChartData);
		
        lineChart.getData().clear();
		lineChart.getData().add(newSeries);
		VisualFX.slideTransition(newSeries.getNode());
	} */

	
}
