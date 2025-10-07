package org.itpc_advanced.service;

import javafx.scene.control.TextFormatter;

public class TextFormatterFactory {
	
	public static TextFormatter<String> getOnlyDigitsTextFormatter(int maxDigits) {
		return new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			if (!newText.matches("\\d*")) {
				return null;
			}

			if (newText.length() > maxDigits) {
				return null;
			}

			return change;
		});
	}
	
	public static TextFormatter<String> getOnlySignedDigitsTextFormatter(int maxDigits) {
		return new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			if (newText.isEmpty() || newText.equals("-")) {
				return change;
			}

			if (!newText.matches("-?\\d{0," + maxDigits + "}")) {
				return null;
			}

			return change;
		});
	}

}
