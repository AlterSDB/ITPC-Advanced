package org.itpc_advanced.view;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.viewmodel.MainViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MainView {
	
	
	public void setViewModel(MainViewModel viewModel) {
		this.averageMaxField.textProperty().bind(viewModel.averageMaxProperty());
		this.averageMinField.textProperty().bind(viewModel.averageMinProperty());
		this.relativeMaxField.textProperty().bind(viewModel.relativeMaxProperty());
		this.relativeMinField.textProperty().bind(viewModel.relativeMinProperty());
		this.tempSetField.textProperty().bindBidirectional(viewModel.tempSetProperty());
		this.tableView.setItems(viewModel.getFileList());
		this.tableColumn.setCellValueFactory(new PropertyValueFactory<DataFile, String>("FileId"));
		this.tableView.getSelectionModel().selectedItemProperty()
		.addListener(
				new ChangeListener<DataFile>() {
					@Override
					public void changed(
							ObservableValue<? extends DataFile> obs,
							DataFile oldValue, DataFile newValue) {
						viewModel.selectedDataFileProperty().set(newValue);
					}
				} );
		
		this.scanBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				viewModel.readDataFiles();
			}
		});		
	}
		
	@FXML
	void initialize(){
		tempSetField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.matches("[0-9.]+")) {
					tempSetField.setText(newValue.replaceAll("[^\\d]", ""));
				}
		}});
	}
	
    @FXML
    private Tab tabAutomatic;

	@FXML
    private TableView<DataFile> tableView;

    @FXML
    private TableColumn<DataFile, String> tableColumn;

    @FXML
    private Button scanBtn;

    @FXML
    private Tab tabManual;

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
    private ImageView logoImageView;

    @FXML
    private Button copyResultBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button languageBtn;

    @FXML
    private TextField tempSetField;

    @FXML
    private Text tempSetText;

    @FXML
    private LineChart<?, ?> tempLineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    public Tab getTabAutomatic() {
		return tabAutomatic;
	}

	public TableView<DataFile> getTableView() {
		return tableView;
	}

	public TableColumn<?, ?> getTableColumn() {
		return tableColumn;
	}

	public Button getScanBtn() {
		return scanBtn;
	}

	public Tab getTabManual() {
		return tabManual;
	}

	public Text getTempText() {
		return tempText;
	}
	
	public Text getAverageMaxText() {
		return averageMaxText;
	}

	public Text getAverageMinText() {
		return averageMinText;
	}

	public Text getRelativeMaxText() {
		return relativeMaxText;
	}

	public Text getRelativeMinText() {
		return relativeMinText;
	}

	public TextField getAverageMaxField() {
		return averageMaxField;
	}

	public TextField getAverageMinField() {
		return averageMinField;
	}

	public TextField getRelativeMaxField() {
		return relativeMaxField;
	}

	public TextField getRelativeMinField() {
		return relativeMinField;
	}

	public ImageView getLogoImageView() {
		return logoImageView;
	}

	public Button getCopyResultBtn() {
		return copyResultBtn;
	}

	public Button getSettingsBtn() {
		return settingsBtn;
	}

	public Button getLanguageBtn() {
		return languageBtn;
	}

	public TextField getTempSetField() {
		return tempSetField;
	}

	public Text getTempSetText() {
		return tempSetText;
	}

	public LineChart<?, ?> getTempLineChart() {
		return tempLineChart;
	}

	public NumberAxis getxAxis() {
		return xAxis;
	}

	public NumberAxis getyAxis() {
		return yAxis;
	}

}
