package org.itpc_advanced.controller;

import java.io.IOException;

import org.itpc_advanced.ITPC_Advanced;
import org.itpc_advanced.model.DataFileOld;
import org.itpc_advanced.service.ReportBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;


public class FXMLMainController {
	@FXML private   TableView                     table;
	@FXML
	void initialize() {
		
	}

	@FXML
	void copyResultBtnAction() {
		System.out.println("copyResultsButton pressed");
		if(table.getSelectionModel().getSelectedItem() == null) {
			System.out.println("Ошибка: Файл в таблице не выбран.");
			return;
		}

		Clipboard clipboard = Clipboard.getSystemClipboard();
		DataFileOld df = (DataFileOld) table.getSelectionModel().getSelectedItem();
		clipboard.setContent(ReportBuilder.getReport(null));
		System.out.println("Результаты скопированы");
	}

	@FXML
	void settingsBtnAction() throws IOException {
		System.out.println("SettingsButton pressed");
		ITPC_Advanced.callSettingsWindow();
	}
	
	@FXML
	void scanBtnAction(ActionEvent event) {
		System.out.println("Scan pressed");
	}

}
