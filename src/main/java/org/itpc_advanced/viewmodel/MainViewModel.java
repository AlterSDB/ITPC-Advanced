package org.itpc_advanced.viewmodel;

import java.io.File;
import java.time.format.DateTimeFormatter;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Settings;
import org.itpc_advanced.service.DataParser;
import org.itpc_advanced.service.DataProcessor;
import org.itpc_advanced.service.DeviceScanner;
import org.itpc_advanced.service.FileManager;
import org.itpc_advanced.service.LocalManager;
import org.itpc_advanced.service.ReportBuilder;
import org.itpc_advanced.utils.MockyDataFiles;
import org.itpc_advanced.utils.VisualFX;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;

public class MainViewModel {

	private final ObservableList<DataFile> fileList = FXCollections.observableArrayList();   
	private final ObjectProperty<DataFile> selectedDataFile = new SimpleObjectProperty<DataFile>();
	private Settings settings = Settings.getInstance();

	private final StringProperty averageMaxProperty = new SimpleStringProperty();
	private final StringProperty averageMinProperty = new SimpleStringProperty();
	private final StringProperty relativeMaxProperty = new SimpleStringProperty();
	private final StringProperty relativeMinProperty = new SimpleStringProperty();
	private final StringProperty tempSetProperty = new SimpleStringProperty();
	private final StringProperty linearOffsetProperty = new SimpleStringProperty();

	private final ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private final StringProperty chartTitle = new SimpleStringProperty();
	private final StringProperty xAxisLabel = new SimpleStringProperty();
	private final StringProperty yAxisLabel = new SimpleStringProperty();
	private final DoubleProperty yAxisLowerBoundProperty = new SimpleDoubleProperty();
	private final DoubleProperty yAxisUpperBoundProperty = new SimpleDoubleProperty();

	private final StringProperty tcTypeProperty = new SimpleStringProperty();
	private final StringProperty timeStampProperty = new SimpleStringProperty();
	private final StringProperty timeStepProperty = new SimpleStringProperty();
	private final StringProperty pointsCountProperty = new SimpleStringProperty();
	private final StringProperty manualTextFieldProperty = new SimpleStringProperty();

	public MainViewModel(){	
		selectedDataFile.addListener((observable, oldDataFile, newDataFile) -> {
				if (newDataFile != null) {
					updateAttributes();
				}
		});

		tempSetProperty.addListener((observable, oldValue, newValue) -> {
				if (!validateInput(newValue)) {
					return;
				}

				if (selectedDataFile.getValue() != null && !newValue.isEmpty()) {
					selectedDataFile.getValue().setTargetTemperature(Integer.parseInt(newValue));
					updateFields();
				}
			});	

		linearOffsetProperty.addListener((observable, oldValue, newValue) -> {
				if (selectedDataFile.getValue() == null) {
					return;
				}

				if (newValue == null || newValue.isEmpty() || newValue.equals("-")) {
					selectedDataFile.getValue().setLinearOffset(0);
					updateFields();
					return;
				}

				if (!newValue.matches("-?\\d+")) {
					return;
				}

				int parsed = 0;

				try {
					parsed = Integer.parseInt(newValue);
				} catch (NumberFormatException e) {
					return;
				}

				selectedDataFile.getValue().setLinearOffset(parsed);
				updateAttributes();
		});

		this.yAxisLowerBoundProperty.set(0);
		this.yAxisUpperBoundProperty.set(10);
	}

	public boolean validateInput(String input) {
		if (input.length() > 4) {
			return false;
		}

		if (!input.matches("\\d+")) {
			return false;
		}

		try {
			int value = Integer.parseInt(input);
			if (value <= 99999) {
				return true;
			}

		} catch (NumberFormatException e) {
			return false;
		}

		return false;
		}

	public ObservableList<DataFile> getFileList() {
		return fileList;
	}

	public ObjectProperty<DataFile> selectedDataFileProperty() {
		return selectedDataFile;
	}

	public void readDataFiles() {
		DataFile.resetCounter();
		fileList.clear();

		if (settings.isDemoMode()) {
			fileList.addAll(MockyDataFiles.mock());
		} else {
			fileList.addAll(DeviceScanner.readDataFiles());
		}
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

	public StringProperty linearOffsetProperty() {
		return linearOffsetProperty;
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

	public void copyResults() {
		if (selectedDataFile.get() == null) {
			return;
		}
		ReportBuilder.buildReport(selectedDataFile.get());
	}
	
	public void updateAttributes() {
		tempSetProperty.setValue(selectedDataFile.get().getTargetTemperature().toString());
		selectedDataFile.get().setTargetTemperature(Integer.parseInt(tempSetProperty.getValue()));
		linearOffsetProperty.setValue(selectedDataFile.get().getLinearOffset().toString());
		yAxisLowerBoundProperty.set(selectedDataFile.get().getChartBounds()[0]);
		yAxisUpperBoundProperty.set(selectedDataFile.get().getChartBounds()[1]);
		chartData.clear();
		chartData.setAll(selectedDataFile.get().getChartData());
		
		manualTextFieldProperty.set(ReportBuilder.getTextFromRawValues(selectedDataFile.get().getValues()));

		updateInfo();
		updateFields();
	}

	public void updateFields() {
		if (selectedDataFile.get() != null) {
		VisualFX.changeText(relativeMaxProperty, selectedDataFile.get().getRelativeMax().toString());
		VisualFX.changeText(relativeMinProperty, selectedDataFile.get().getRelativeMin().toString());
		VisualFX.changeText(averageMaxProperty, selectedDataFile.get().getAverageMax().toString());
		VisualFX.changeText(averageMinProperty, selectedDataFile.get().getAverageMin().toString());
		}
	}

	public void updateInfo(){
		if (selectedDataFile.get() != null) {
			VisualFX.changeText(tcTypeProperty, LocalManager.getInstance().getString(selectedDataFile.get().getTcType().toString()));
			System.out.println(selectedDataFile.get().getTcType().toString());
			VisualFX.changeText(timeStepProperty, selectedDataFile.get().getTimeStep().toString());
	//		tcTypeProperty.set(LocalManager.getInstance().getString(selectedDataFile.get().getTcType().toString()));
			timeStampProperty.set(selectedDataFile.get().getTimeStamp().format(DateTimeFormatter.ISO_LOCAL_DATE).toString());
			pointsCountProperty.set(new Integer(selectedDataFile.get().getValues().size()).toString());
		}
	}

	public StringProperty tcTypeProperty() {
		return tcTypeProperty;
	}

	public StringProperty timeStampProperty() {
		return timeStampProperty;
	}

	public StringProperty timeStepProperty() {
		return timeStepProperty;
	}

	public StringProperty pointsCountProperty() {
		return pointsCountProperty;
	}

	public void calculateManual(String text) {
		selectedDataFile.set(DataParser.parseFromText(text));
		DataProcessor.calculate(selectedDataFile.get());
		updateAttributes();
	}

	public StringProperty manualTextProperty() {
		return manualTextFieldProperty;
	}

	public void saveFile(TextArea textArea) {
		int fileId = selectedDataFile.get() != null ? selectedDataFile.get().getFileId() : 0;
		String defaultFileName = "output_" + fileId + ".txt";
		FileChooser fileChooser = FileManager.getFileChooser(defaultFileName);

		File path = fileChooser.showSaveDialog(textArea.getScene().getWindow());
		String data = textArea.getText();

		if (path != null && data != null) {
			FileManager.saveFile(path, data);
		}

	}

}