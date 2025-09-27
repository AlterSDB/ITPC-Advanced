package org.itpc_advanced.service;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LocalTextBinder {
	
	public static void bindLabel(Label label, String key) {
		StringBinding binding = Bindings.createStringBinding(
				() -> LocalManager.getInstance().getString(key), 
				LocalManager.getInstance().resourceBundleProperty());
		label.textProperty().bind(binding);
	}
	
	public static void bindText(Text text, String key) {
		StringBinding binding = Bindings.createStringBinding(
				() -> LocalManager.getInstance().getString(key), 
				LocalManager.getInstance().resourceBundleProperty());
		text.textProperty().bind(binding);
	}
	
	public static void bindButton(Button button, String key) {
		StringBinding binding = Bindings.createStringBinding(
				() -> LocalManager.getInstance().getString(key), 
				LocalManager.getInstance().resourceBundleProperty());
		if(button != null) {
			button.textProperty().bind(binding);
		}
	}

}
