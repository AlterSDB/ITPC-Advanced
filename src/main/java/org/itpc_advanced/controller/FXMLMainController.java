package org.itpc_advanced.controller;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.itpc_advanced.ITPC_Advanced;
import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Settings;
import org.itpc_advanced.service.DeviceScanner;
import org.itpc_advanced.utils.VisualFX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FXMLMainController {
    @FXML 
    private LineChart<Number, Number> lineChart;
	@FXML 
	private TableColumn<DataFile, String> filesColumn;
	@FXML 
	private NumberAxis x;
	@FXML              
	private NumberAxis y;
	@FXML              
	private TableView  table;
	@FXML              
	private AnchorPane root;
	@FXML              
	private AnchorPane loadingScreen;
	@FXML              
	private AnchorPane resultsPane;
	@FXML              
	private Button copyResultsButton;
	@FXML              
	private Button scanBtn;
	@FXML              
	private TextField targetTemperatureField;
	@FXML              
	private TextField averageMaxField;
	@FXML              
	private TextField averageMinField;
	@FXML              
	private TextField relativeMaxField;
	@FXML              
	private TextField relativeMinField;
	@FXML              
	private Rectangle tableShape;
	@FXML   
	public Text subStatusText;
	@FXML             
	public Text statusText;
	@FXML             
	public AnchorPane spinnerPane;
	@FXML             
	public Circle innerCircle;
	@FXML             
	public Circle middleCircle;
	@FXML             
	public Circle иouterCircle;

	@FXML
	void initialize() {
		
	}


	@FXML
	void copyResultBtnAction() {
		System.out.println("Button pressed");
	}

	@FXML
	void settingsBtnAction() throws IOException {
		System.out.println("Button pressed");
	}
	
	@FXML
	void scanBtnAction(ActionEvent event) {
		System.out.println("Button pressed");
	}

}
