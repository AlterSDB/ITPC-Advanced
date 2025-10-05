package org.itpc_advanced.viewmodel;

import java.io.IOException;
import java.util.Locale;

import org.itpc_advanced.model.DataFile;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainViewModel {

	private final ObservableList<DataFile> fileList = FXCollections.observableArrayList();   
	private final ObjectProperty<DataFile> selectedDataFile = new SimpleObjectProperty<DataFile>();
	private StringProperty averageMaxProperty = new SimpleStringProperty();
	private StringProperty averageMinProperty = new SimpleStringProperty();
	private final StringProperty relativeMaxProperty = new SimpleStringProperty();
	private StringProperty relativeMinProperty = new SimpleStringProperty();
	private StringProperty tempSetProperty = new SimpleStringProperty();
	private StringProperty linearOffsetProperty = new SimpleStringProperty();

	private final ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
	private final StringProperty chartTitle = new SimpleStringProperty();
	private final StringProperty xAxisLabel = new SimpleStringProperty();
	private final StringProperty yAxisLabel = new SimpleStringProperty();
	private final DoubleProperty yAxisLowerBoundProperty = new SimpleDoubleProperty();
	private final DoubleProperty yAxisUpperBoundProperty = new SimpleDoubleProperty();
	public Stage settingsStage;

	public MainViewModel(){	

		selectedDataFile.addListener((observable, oldDataFile, newDataFile) -> {
				if (newDataFile != null) {
					System.out.println("CHANGED FILE");
					tempSetProperty.setValue(newDataFile.getTargetTemperature().toString());
					newDataFile.setTargetTemperature(Integer.parseInt(tempSetProperty.getValue()));
					linearOffsetProperty.setValue(newDataFile.getLinearOffset().toString());
					
					updateFields();
					
					yAxisLowerBoundProperty.set(newDataFile.getChartBounds()[0]);
					yAxisUpperBoundProperty.set(newDataFile.getChartBounds()[1]);
					chartData.clear();
					chartData.setAll(newDataFile.getChartData());
				}
		});

		tempSetProperty.addListener((observable, oldValue, newValue) -> {
				if (!validateInput(newValue)) {
					return;
				}

				System.out.println("CHANGED TARGET to " + Double.parseDouble(newValue));
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
				relativeMaxProperty.setValue(selectedDataFile.getValue().getRelativeMax().toString());
				relativeMinProperty.setValue(selectedDataFile.getValue().getRelativeMin().toString());
				averageMaxProperty.setValue(selectedDataFile.getValue().getAverageMax().toString());
				averageMinProperty.setValue(selectedDataFile.getValue().getAverageMin().toString());		
				
				
				yAxisLowerBoundProperty.set(selectedDataFile.get().getChartBounds()[0]);
				yAxisUpperBoundProperty.set(selectedDataFile.get().getChartBounds()[1]);
				chartData.clear();
				chartData.setAll(selectedDataFile.get().getChartData());
		});	

		this.yAxisLowerBoundProperty.set(0);
		this.yAxisUpperBoundProperty.set(10);		
	}

	private boolean validateInput(String input) {
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
		fileList.clear();
		fileList.addAll(MockyDataFiles.mock());
	//	fileList.addAll(DeviceScanner.readDataFiles());
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
		if(selectedDataFile.get() == null) {
			System.out.println("File is not choosen");
			return;
		}
		ReportBuilder.buildReport(selectedDataFile.get());
	}

	public void changeLanguage() {
		LocalManager localization = LocalManager.getInstance();
		if (localization.isEnglish()) {
			localization.setLocale(new Locale("ru"));
		} else {
			localization.setLocale(Locale.ENGLISH);
		}	
	}

	public void updateFields() {
		VisualFX.changeText(relativeMaxProperty, selectedDataFile.get().getRelativeMax().toString());
		VisualFX.changeText(relativeMinProperty, selectedDataFile.get().getRelativeMin().toString());
		VisualFX.changeText(averageMaxProperty, selectedDataFile.get().getAverageMax().toString());
		VisualFX.changeText(averageMinProperty, selectedDataFile.get().getAverageMin().toString());
		
	}

	public void showSettings() {
		if (settingsStage != null && settingsStage.isShowing()) {
			settingsStage.close();
			return;
		}

		try {
			settingsStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
			Parent parent;
			parent = loader.load();
			Scene scene = new Scene(parent);
			settingsStage.setScene(scene);
			settingsStage.setTitle("Настройки");
			settingsStage.setResizable(false);
			settingsStage.setAlwaysOnTop(true);
			settingsStage.getIcons().add(new Image("/images/logo.png"));
			settingsStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
