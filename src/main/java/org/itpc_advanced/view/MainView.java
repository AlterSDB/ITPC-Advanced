package org.itpc_advanced.view;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.utils.VisualFX;
import org.itpc_advanced.viewmodel.MainViewModel;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MainView {

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
    private TextField linearOffsetField;

    @FXML
    private Text linearOffsetText;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    private XYChart.Series<Number, Number> series;
	
	private MainViewModel viewModel;

	@FXML
	private void onScanBtnAction() {
		viewModel.readDataFiles();
	}
	
	@FXML
	private void onLanguageBtnAction() {
		viewModel.changeLanguage();
	}

	@FXML
	private void onCopyResultsBtnAction() {
		viewModel.copyResults();
	}

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

	public LineChart<Number, Number> getTempLineChart() {
		return lineChart;
	}

	public NumberAxis getxAxis() {
		return xAxis;
	}

	public NumberAxis getyAxis() {
		return yAxis;
	}

	private boolean updatingFromViewModel = false;

	@SuppressWarnings("unchecked")
	public void setViewModel(MainViewModel viewModel) {
		this.viewModel = viewModel;
		tableView.setItems(viewModel.getFileList());
		tableView.setPlaceholder(new Label("Files list is empty"));
		TableColumn<DataFile, Integer> filesColumn = (TableColumn<DataFile, Integer>) tableView.getColumns().get(0);
		filesColumn.setCellValueFactory(new PropertyValueFactory<>("fileId"));
		filesColumn.setCellFactory((column) -> {
				return new TableCell<DataFile, Integer>() {
					@Override
					protected void updateItem(Integer fileId, boolean empty) {
						super.updateItem(fileId, empty);
						if (empty || fileId == null) {
							setText(null);
						} else {
							setText("Файл " + fileId);
						}
					}
				};
		});

		filesColumn.setMaxWidth(200);
		filesColumn.setResizable(false);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if(!updatingFromViewModel) {
				viewModel.selectedDataFileProperty().set(newValue);
			}
		});

		viewModel.selectedDataFileProperty().addListener((obs, oldValue, newValue) -> {
			updatingFromViewModel = true;
			tableView.getSelectionModel().select(newValue);
			VisualFX.slideTransition(series.getNode());
			updatingFromViewModel = false;
		});

		relativeMaxField.textProperty().bind(this.viewModel.relativeMaxProperty());
		relativeMinField.textProperty().bind(this.viewModel.relativeMinProperty());
		averageMaxField.textProperty().bind(this.viewModel.averageMaxProperty());
		averageMinField.textProperty().bind(this.viewModel.averageMinProperty());
		tempSetField.textProperty().bindBidirectional(this.viewModel.tempSetProperty());
		linearOffsetField.textProperty().bindBidirectional(this.viewModel.linearOffsetProperty());

		TextFormatter<String> digitsMax7Formatter = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			if (!newText.matches("\\d*")) {
				return null;
			}

			if (newText.length() > 7) {
				return null;
			}

			return change;
		});

		tempSetField.setTextFormatter(digitsMax7Formatter);

		TextFormatter<String> signedDigitsMax7 = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			if (newText.isEmpty() || newText.equals("-")) {
				return change;
			}

			if (!newText.matches("-?\\d{0,7}")) {
				return null;
			}

			return change;
		});

		linearOffsetField.setTextFormatter(signedDigitsMax7);
		yAxis.lowerBoundProperty().bind(this.viewModel.yAxisLowerBoundProperty());
		yAxis.upperBoundProperty().bind(this.viewModel.yAxisUpperBoundProperty());		
		series = new XYChart.Series<>();
		series.setData(viewModel.getChartData());
		lineChart.getData().add(series);
		lineChart.titleProperty().bind(viewModel.chartTitleProperty());
		xAxis.labelProperty().bind(viewModel.xAxisLabelProperty());
		yAxis.labelProperty().bind(viewModel.yAxisLabelProperty());

		xAxis.setUpperBound(15);
		xAxis.setMinorTickCount(2);
		yAxis.setAutoRanging(false);
		yAxis.setTickUnit(1);
		yAxis.setMinorTickCount(0);
		lineChart.setCreateSymbols(false);
		lineChart.setLegendVisible(false);
		lineChart.setAnimated(false);
		lineChart.setLegendSide(Side.LEFT);		
	}

}