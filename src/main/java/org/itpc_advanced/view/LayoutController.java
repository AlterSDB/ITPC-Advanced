package org.itpc_advanced.view;

import org.itpc_advanced.service.Localizator;
import org.itpc_advanced.viewmodel.MainViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class LayoutController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button settingsBtn;

	private MainViewModel viewModel;
	
    public LayoutController(MainViewModel viewModel) {
    	this.viewModel = viewModel;
    }
    
    @FXML
    void initialize() {
		Localizator.bindText(settingsBtn.textProperty(), "button.settings");
		
    }

    @FXML
    void onSettingsBtnAction(ActionEvent event) {
    	viewModel.openSettingsWindow();
    }

}
