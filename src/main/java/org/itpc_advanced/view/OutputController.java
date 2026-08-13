package org.itpc_advanced.view;

import org.itpc_advanced.service.LocalTextBinder;
import org.itpc_advanced.viewmodel.MainViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OutputController {

    @FXML
    private Text tempText;

    @FXML
    private Text averageMaxText;

    @FXML
    private Text averageMinText;

    @FXML
    private Text relativeMaxText;

    @FXML
    private Text relativeMinText;

    @FXML
    private TextField averageMaxField;

    @FXML
    private TextField averageMinField;

    @FXML
    private TextField relativeMaxField;

    @FXML
    private TextField relativeMinField;

    @FXML
    private Button copyResultBtn;

    @FXML
    private AreaChart<Number, Number> areaChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Text linearOffsetText;

    @FXML
    private Text tempSetText;

    @FXML
    private TextField tempSetField;

    @FXML
    private TextField linearOffsetField;

	private MainViewModel viewModel;
	
	private XYChart.Series<Number, Number> series;
	
	public OutputController(MainViewModel viewModel) {
    	this.viewModel = viewModel;
    }
    
    @FXML 
    void initialize() {
    	bindTextElements();
    	bindLocalizedElements();
    	initLineChart();
    }
    
	@FXML
    void onCopyResultsBtnAction(ActionEvent event) {
    	viewModel.getReport();
    }
	
	private void initLineChart() {
		series = new XYChart.Series<Number, Number>();
		series.setData(viewModel.getChartData());
		areaChart.getData().add(series);
 
		areaChart.setCreateSymbols(false);
		areaChart.setLegendVisible(false);
		areaChart.setAnimated(false);
		areaChart.setLegendSide(Side.LEFT);

		xAxis.setUpperBound(15);
		xAxis.setMinorTickCount(2);
		yAxis.setAutoRanging(true);
		yAxis.setTickUnit(1);
		yAxis.setMinorTickCount(0);
		//yAxis.lowerBoundProperty().bind(viewModel.yAxisLowerBoundProperty());
		//yAxis.upperBoundProperty().bind(viewModel.yAxisUpperBoundProperty());
		
	}
    
    private void bindLocalizedElements() {
    	LocalTextBinder.bindText(tempSetText.textProperty(), "field.set.target");
		LocalTextBinder.bindText(linearOffsetText.textProperty(), "field.linear.offset");
		LocalTextBinder.bindText(averageMaxText.textProperty(), "field.average.max");
		LocalTextBinder.bindText(averageMinText.textProperty(), "field.average.min");
		LocalTextBinder.bindText(relativeMaxText.textProperty(), "field.relative.max");
		LocalTextBinder.bindText(relativeMinText.textProperty(), "field.relative.min");
		LocalTextBinder.bindText(copyResultBtn.textProperty(), "button.get.report");
		LocalTextBinder.bindText(xAxis.labelProperty(), "chart.x.axis");
		LocalTextBinder.bindText(yAxis.labelProperty(), "chart.y.axis");
		LocalTextBinder.bindText(areaChart.titleProperty(), "chart.label");
		
	}
    
    private void bindTextElements() {   	
    	averageMaxField.textProperty().bind(viewModel.averageMaxValueProperty());
    	averageMinField.textProperty().bind(viewModel.averageMinValueProperty());
    	relativeMaxField.textProperty().bind(viewModel.relativeMaxValueProperty());
    	relativeMinField.textProperty().bind(viewModel.relativeMinValueProperty());   
    	linearOffsetField.textProperty().bind(viewModel.linearOffsetValueProperty());
    	tempSetField.textProperty().bind(viewModel.tempSetValueProperty());
		
	}

}
