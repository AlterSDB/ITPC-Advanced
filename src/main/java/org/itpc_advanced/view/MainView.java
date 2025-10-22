package org.itpc_advanced.view;

import java.io.IOException;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.service.LocalManager;
import org.itpc_advanced.service.LocalTextBinder;
import org.itpc_advanced.service.TextFormatterFactory;
import org.itpc_advanced.utils.VisualFX;
import org.itpc_advanced.viewmodel.MainViewModel;
import org.itpc_advanced.viewmodel.SettingsViewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainView {

    @FXML private Button scanBtn;
    @FXML private Button copyResultBtn;
    @FXML private Button settingsBtn;
    @FXML private Button saveBtn;
    @FXML private ImageView logoImageView;
    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private TextArea manualTextArea;
    @FXML private Button calculateBtn;
	@FXML private TableView<DataFile> tableView;
    @FXML private TableColumn<DataFile, String> tableColumn;
    @FXML private Text tempText;
    @FXML private Text averageMaxText;
    @FXML private Text averageMinText;
    @FXML private Text relativeMaxText;
    @FXML private Text relativeMinText;
    @FXML private Text tempSetText;
    @FXML private Text linearOffsetText;
    @FXML private Text typeText;
    @FXML private Text timeStampText;
    @FXML private Text timeStepText;
    @FXML private Text pointsCountText;
    @FXML private Text typeValueText;
    @FXML private Text timeStampValueText;
    @FXML private Text timeStepValueText;
    @FXML private Text pointsCountValueText;
    @FXML private TextField averageMaxField;
    @FXML private TextField averageMinField;
    @FXML private TextField relativeMaxField;
    @FXML private TextField relativeMinField;
    @FXML private TextField tempSetField;
    @FXML private TextField linearOffsetField;

    private XYChart.Series<Number, Number> series;
	private MainViewModel viewModel;
	private Stage settingsStage;

	@FXML
	private void onScanBtnAction() {
		viewModel.readDataFiles();
	}

	@FXML
	private void onCalculateBtnAction() {
		viewModel.calculateManual(manualTextArea.getText());
	}
	
	@FXML
	private void onSaveBtnAction() {
		viewModel.saveFile(manualTextArea);
		
	}

	@FXML
	private void onCopyResultsBtnAction() {
		viewModel.copyResults();
	}

	@FXML
	private void onSettingsBtnAction() {
		showSettings();
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
	
	public Button getSaveBtn() {
		return saveBtn;
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

	@FXML
	@SuppressWarnings("unchecked")
	void initialize() {
		System.out.println("Init...");
		TableColumn<DataFile, Integer> tableColumn = (TableColumn<DataFile, Integer>) tableView.getColumns().get(0);

		LocalTextBinder.bindText(tempSetText.textProperty(), "field.set.target");
		LocalTextBinder.bindText(linearOffsetText.textProperty(), "field.linear.offset");
		LocalTextBinder.bindText(averageMaxText.textProperty(), "field.average.max");
		LocalTextBinder.bindText(averageMinText.textProperty(), "field.average.min");
		LocalTextBinder.bindText(relativeMaxText.textProperty(), "field.relative.max");
		LocalTextBinder.bindText(relativeMinText.textProperty(), "field.relative.min");
		LocalTextBinder.bindText(scanBtn.textProperty(), "button.scan");
		LocalTextBinder.bindText(settingsBtn.textProperty(), "button.settings");
		LocalTextBinder.bindText(saveBtn.textProperty(), "button.save");
		LocalTextBinder.bindText(calculateBtn.textProperty(), "button.calculate");
		LocalTextBinder.bindText(copyResultBtn.textProperty(), "button.get.report");
		LocalTextBinder.bindText(xAxis.labelProperty(), "chart.x.axis");
		LocalTextBinder.bindText(yAxis.labelProperty(), "chart.y.axis");
		LocalTextBinder.bindText(lineChart.titleProperty(), "chart.label");
		LocalTextBinder.bindText(tableColumn.textProperty(), "table.header");

		LocalTextBinder.bindText(timeStampText.textProperty(), "df.timestamp");
		LocalTextBinder.bindText(typeText.textProperty(), "df.tc.type");
		LocalTextBinder.bindText(timeStepText.textProperty(), "df.timestep");
		LocalTextBinder.bindText(pointsCountText.textProperty(), "df.points.count");

		Label label = new Label();
		tableView.setPlaceholder(label);
		LocalTextBinder.bindText(label.textProperty(), "table.placeholder");

		tableColumn.setCellValueFactory(new PropertyValueFactory<>("fileId"));
		tableColumn.setCellFactory((column) -> {
				return new TableCell<DataFile, Integer>() {
					@Override
					protected void updateItem(Integer fileId, boolean empty) {
						super.updateItem(fileId, empty);
						if (empty || fileId == null) {
							setText(null);
						} else {
							String prefix = LocalManager.getInstance().getString("table.file.prefix");
							setText(prefix + " " + fileId);
						}
					}
				};
		});

		LocalManager.getInstance().resourceBundleProperty().addListener((obs, oldVal, newVal) -> {
			tableColumn.setVisible(false);
			tableColumn.setVisible(true);
		});

	}
	
	public void showSettings() {
		if (settingsStage != null && settingsStage.isShowing()) {
			settingsStage.close();
			return;
		}

		try {
			settingsStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
			Parent parent = loader.load();

			SettingsView settingsController = loader.getController();
			SettingsViewModel settingsViewModel = new SettingsViewModel();
			settingsController.setViewModel(settingsViewModel);

			Scene scene = new Scene(parent);
			settingsStage.setScene(scene);
			settingsStage.initOwner((Stage)scanBtn.getScene().getWindow());
			settingsStage.initModality(Modality.WINDOW_MODAL);
			settingsStage.setResizable(false);
			settingsStage.setAlwaysOnTop(true);
			settingsStage.getIcons().add(new Image("/images/logo.png"));
			LocalTextBinder.bindText(settingsStage.titleProperty(), "settings.label");	
			settingsController.setStage(settingsStage);
			settingsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setViewModel(MainViewModel viewModel) {
		this.viewModel = viewModel;
		tableView.setItems(viewModel.getFileList());
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (!updatingFromViewModel) {
				viewModel.selectedDataFileProperty().set(newValue);
			}
		});

		viewModel.selectedDataFileProperty().addListener((obs, oldValue, newValue) -> {
			updatingFromViewModel = true;
			tableView.getSelectionModel().select(newValue);
			VisualFX.slideTransition(series.getNode());
			updatingFromViewModel = false;
		});
		
		viewModel.pointsCountProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				return;
			}

			int count = Integer.parseInt(newValue);
			
			if (count < 20) {
				pointsCountValueText.setFill(Color.CRIMSON);
				return;
			}
			
			if (count < 30) {
				pointsCountValueText.setFill(Color.YELLOW);
				return;
			}
			
			if (count > 30) {
				pointsCountValueText.setFill(Color.LIME);
				return;
			}

		});

		viewModel.linearOffsetProperty().addListener((obs, oldValue, newValue) -> {
			VisualFX.slideTransition(series.getNode());
			this.viewModel.updateFields();
		});

		relativeMaxField.textProperty().bind(this.viewModel.relativeMaxProperty());
		relativeMinField.textProperty().bind(this.viewModel.relativeMinProperty());
		averageMaxField.textProperty().bind(this.viewModel.averageMaxProperty());
		averageMinField.textProperty().bind(this.viewModel.averageMinProperty());
		tempSetField.textProperty().bindBidirectional(this.viewModel.tempSetProperty());
		linearOffsetField.textProperty().bindBidirectional(this.viewModel.linearOffsetProperty());
		manualTextArea.textProperty().bindBidirectional(this.viewModel.manualTextProperty());

		typeValueText.textProperty().bind(this.viewModel.tcTypeProperty());
		timeStampValueText.textProperty().bind(this.viewModel.timeStampProperty());
		timeStepValueText.textProperty().bind(this.viewModel.timeStepProperty());
		pointsCountValueText.textProperty().bind(this.viewModel.pointsCountProperty());

		tempSetField.setTextFormatter(TextFormatterFactory.getOnlyDigitsTextFormatter(7));
		linearOffsetField.setTextFormatter(TextFormatterFactory.getOnlySignedDigitsTextFormatter(7));

		series = new XYChart.Series<Number, Number>();
		series.setData(viewModel.getChartData());
		lineChart.getData().add(series);
		lineChart.setCreateSymbols(false);
		lineChart.setLegendVisible(false);
		lineChart.setAnimated(false);
		lineChart.setLegendSide(Side.LEFT);

		xAxis.setUpperBound(15);
		xAxis.setMinorTickCount(2);
		yAxis.setAutoRanging(false);
		yAxis.setTickUnit(1);
		yAxis.setMinorTickCount(0);
		yAxis.lowerBoundProperty().bind(this.viewModel.yAxisLowerBoundProperty());
		yAxis.upperBoundProperty().bind(this.viewModel.yAxisUpperBoundProperty());
	}

}