package org.itpc_advanced.service;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.MenuButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class KeyboardControlsManager {
	
	private final List<Node> focusableElements;
	private int currentIndex = 0;
	private final Stage stage;
	
	public KeyboardControlsManager(Stage stage, List<Node> focusableElements) {
		this.stage = stage;
		this.focusableElements = new ArrayList<>(focusableElements);
		initializeFocus();
	}

	private void initializeFocus() {
		if (!focusableElements.isEmpty()) {
			focusableElements.get(0).requestFocus();
		}
		if (stage.getScene() != null) {
			stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
		}
	}
	
	private void handleKeyPress(KeyEvent event) {
		switch (event.getCode()) {
			case UP: 
				event.consume();
				moveFocusUp();
				break;
			case DOWN: 
				event.consume();
				moveFocusDown();
				break;
			case ENTER:
				event.consume();
				activateElement();
				break;
			case ESCAPE:
				event.consume();
				closeWindow();
				break;
			default:
				break;
		}
	}

	private void closeWindow() {
		this.stage.close();
		
	}

	private void activateElement() {
		if (focusableElements.isEmpty()) {
			return;
		}
		
		Node element = focusableElements.get(currentIndex);

		if (element instanceof ButtonBase) {
			ButtonBase button = (ButtonBase) focusableElements.get(currentIndex);
			button.fire();
		}

		else {
			System.out.println("Enter Key Pressed");
		}
		
	}

	private void moveFocusDown() {
		if (focusableElements.isEmpty()) {
			return;
		}
		
		currentIndex++;
		if (currentIndex >= focusableElements.size()) {
			currentIndex = 0;
		}
		focusCurrentElement();
	}

	private void moveFocusUp() {
		if (focusableElements.isEmpty()) {
			return;
		}
		
		currentIndex--;
		if (currentIndex < 0) {
			currentIndex = focusableElements.size() - 1;
		}
		focusCurrentElement();
	}
	
	private void focusCurrentElement() {
		if (currentIndex >= 0 && currentIndex < focusableElements.size()) {
			Node currentElement = focusableElements.get(currentIndex);
			currentElement.requestFocus();
			System.out.println(currentElement.getClass() + currentElement.getId());
		}
		
	}
	
	public Node getCurrentFocusedElement() {
		if (currentIndex >= 0 && currentIndex <= focusableElements.size()) {
			return focusableElements.get(currentIndex);
		}
		return null;
	}



}
