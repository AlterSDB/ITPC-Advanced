package org.itpc_advanced.service;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class LocalTextBinder {

	public static void bindText(ObjectProperty<String> text, String key) {
		StringBinding binding = Bindings.createStringBinding(
				() -> LocalManager.getInstance().getString(key), 
				LocalManager.getInstance().resourceBundleProperty());
		if (text != null) {
			text.bind(binding);
		}
	}

	public static void bindText(StringProperty text, String key) {
		StringBinding binding = Bindings.createStringBinding(
				() -> LocalManager.getInstance().getString(key), 
				LocalManager.getInstance().resourceBundleProperty());
		if (text != null) {
			text.bind(binding);
		}
	}

}