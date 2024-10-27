package org.itpc_advanced.App;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class FXMLMainController {
	@FXML private LineChart<Number, Number> lineChart;
	@FXML private NumberAxis x;
	@FXML private NumberAxis y;
	@FXML private TableView table;
 	@FXML private TableColumn<DataFile, String> filesColumn;
    @FXML private Text status;
    @FXML private AnchorPane circlePane;
    @FXML private AnchorPane mainAnchorPane;
    @FXML private AnchorPane resultsPane;
    @FXML private Button copyResultsButton;
    @FXML private TextField targetTemperatureField;
    @FXML private TextField averageMaxField;
    @FXML private TextField averageMinField;
    @FXML private TextField relativeMaxField;
    @FXML private TextField relativeMinField;
    @FXML private Rectangle tableShape;
		  private Settings settings;
		  private Device device;
    

	@FXML
	void initialize() {
		settings = Settings.getInstance();
		initializeTable();
		initializeChart();
		initializeTextFields();
		copyResultsButton.setVisible(false);
		System.out.println("Инициализация интерфейса прошла успешно.");
	}
	
	private void initializeChart() {
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
	}
	
	private void initializeTextFields() {
		averageMaxField.setEditable(false);
		averageMinField.setEditable(false);
		relativeMaxField.setEditable(false);
		relativeMinField.setEditable(false);

		targetTemperatureField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*"))
					targetTemperatureField.setText(newValue.replaceAll("[^\\d]", ""));
				if(table.getSelectionModel().getSelectedItem() == null || newValue.isEmpty())    
					return;
				DataFile selectedDF = (DataFile) table.getSelectionModel().getSelectedItem();
				if(!selectedDF.isFileExists())
					return;
				if(settings.isAutomaticTarget()) {
					selectedDF.setNewTarget(Integer.parseInt(targetTemperatureField.getText()));        
				} else {
					for(DataFile df : device.getDataFiles()) {
						df.setNewTarget(Integer.parseInt(targetTemperatureField.getText()));
					}}
				VisualFX.Text.changeText(relativeMaxField, Double.toString(selectedDF.getRelativeMax()));
		        VisualFX.Text.changeText(relativeMinField, Double.toString(selectedDF.getRelativeMin()));
			}});
	}
	

	private void initializeTable() {      
		table.setPlaceholder(new Label("Список файлов пуст"));
        filesColumn.setCellValueFactory(new PropertyValueFactory<DataFile, String>("FileName"));
        filesColumn.setMaxWidth(194);
        filesColumn.setResizable(false);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
            	if(table.getSelectionModel().getSelectedItem() != null) { 
            		DataFile df = (DataFile) table.getSelectionModel().getSelectedItem();
            		System.out.println("Выбран " + df.getFileName()); 	
            		
            		if(settings.isAutomaticTarget()) {
            			targetTemperatureField.clear();
            			targetTemperatureField.setText(Integer.toString(df.getTargetTemperature()));
            			System.out.println("Установлена автоматическая настройка ТЗ. Таргет: " + Integer.toString(df.getTargetTemperature()));
            		}
            		else {
            			if(!targetTemperatureField.getText().equals("")) {
            				df.setNewTarget(Integer.parseInt(targetTemperatureField.getText()));  
            			}}  		
            
            		VisualFX.Text.changeText(averageMaxField, Double.toString(df.getAverageMax()));
            		VisualFX.Text.changeText(averageMinField, Double.toString(df.getAverageMin()));
            		VisualFX.Text.changeText(relativeMaxField, Double.toString(df.getRelativeMax()));
            		VisualFX.Text.changeText(relativeMinField, Double.toString(df.getRelativeMin()));       		
            		changeChart(df.getChartData(), df.getBounds(), df.getFileName());
            		df.setChartExists();  
            		VisualFX.fadeTransition(copyResultsButton, 0, 1);
        }}});
	}
	
	
	private void changeChart(ObservableList<Data> newChartData, double[] bounds,  String fileName) {
		XYChart.Series newSeries = new XYChart.Series();
		y.setLowerBound(bounds[0]);
		y.setUpperBound(bounds[1]);
	    newSeries.setData(newChartData);
	    newSeries.setName(fileName);
	    if(settings.isMultipleCharts() == false)
	    	lineChart.getData().clear();      
		lineChart.getData().add(newSeries); 	
	    VisualFX.Chart.slideTransition(newSeries.getNode());
	}
	

	@FXML
	void scanBtnAction(ActionEvent event) {
	//	showLoadScreen();
		device = Device.getInstance();
		table.setItems(device.getDataFiles());
	//	VisualFX.makeCircles(circlePane);
		hideLoadScreen();
	}
	
	private void showLoadScreen() {
		VisualFX.fadeTransition(table, 1.0, 0.0);
		VisualFX.fadeTransition(lineChart, 1.0, 0.0);
		VisualFX.fadeTransition(copyResultsButton, 1.0, 0.0);
		VisualFX.fadeTransition(resultsPane, 1.0, 0.0);		
		VisualFX.fadeTransition(tableShape, 1.0, 0.0);
	}
	


	private void hideLoadScreen() {
		
	}

	@FXML
	void copyResultBtnAction() { 
		if(table.getSelectionModel().getSelectedItem() == null) {
			System.out.println("Ошибка: Файл в таблице не выбран.");
			return;
		}
		
		Clipboard clipboard = Clipboard.getSystemClipboard();
		DataFile df = (DataFile) table.getSelectionModel().getSelectedItem();
		clipboard.setContent(df.getFormattedHTML());
		System.out.println("результаты скопированы");
	}
	
	@FXML
	void settingsBtnAction() throws IOException {
		System.out.println("Открываем настройки...");		
		MainApp.callSettingsWindow();
	}
	
	
	@Deprecated 
	void test(DataFile df) {
		System.out.println(
				df.getID() + "\n" + "средниймакс " + 
				df.getAverageMax() + "\n" + "средниймин " + 
				df.getAverageMin() + "\n" + "относительный макс " + 
				df.getRelativeMax() + "\n" + "относительный мин " + 
				df.getRelativeMin() + "\n" + "значения " + 
				df.getValues() + "\n" + "общее количество " + 
				DataFile.getCount()			
				);
	}
	

	
}
