package org.itpc_advanced.App;



import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class VisualFX {
	
	
/*	private static void makeCircle(AnchorPane pane, int id) {
		Color green = Color.LAWNGREEN;
		int offsetY = 5;
		for(int i = 0; i < id; i++) {
			offsetY += 35;
		}
		Circle circle = new Circle(-5, offsetY, 2, green);
		pane.getChildren().add(circle);		
		
		
		//TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), circle);
        //translateTransition.setToX(-20);
        //translateTransition.setAutoReverse(true);
        //translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        //translateTransition.play();
        
	}
	*/
	
	

	public static void makeCircles(AnchorPane pane) {
	/*	Device device = Device.getInstance();
		List<DataFile> dFiles = device.getDataFiles();
		for(DataFile df : dFiles) {
			if(df.isFileExists()) {
			  makeCircle(pane, df.getID());
			}
		} */
	}

	
	


	public static void fadeTransition(Node element, double startValue, double endValue) {
		int duration = 450;
		int delay = 200;
		
		if(startValue == 0.0) {
			element.setOpacity(0.0);
		}
		
		if(!element.isVisible()) {
			element.setVisible(true);
		}
		
		if(element.getOpacity() == endValue) {
			return;
		}
		
		FadeTransition ft = new FadeTransition(Duration.millis(duration), element);
	     ft.setFromValue(startValue);
	     ft.setToValue(endValue);
	     ft.setDelay(Duration.millis(delay));
	     ft.setCycleCount(0);
	     ft.play();
		
	}


	
	static class Chart {
		public static void slideTransition(Node element) {
			Rectangle mask = new Rectangle(600, 200, Color.AQUA);
			mask.setX(-610);
			mask.setY(0);
			element.getParent().setClip(mask);
			TranslateTransition ts = new TranslateTransition(Duration.seconds(0.75), mask);
			ts.setToX(550);
			ts.play();
		}
		
	}
	
	static class Text {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static void changeText(TextField field, String text) {
			Timeline timeline = new Timeline();
			StringBuffer newText = new StringBuffer();	
			StringBuffer oldText = new StringBuffer(field.getText());
			int duration = 0;
			int step = 50;
	        
	        for(int i = 0; i < oldText.length() ; i++) {
					timeline.getKeyFrames().add(new KeyFrame(
							Duration.millis(duration), 
							new EventHandler() {
								@Override
								public void handle(Event event) {
									oldText.deleteCharAt(oldText.length() - 1);
									field.setText(oldText.toString());
								}
							}));
					duration += step - 20;
				} 
	        
	        for(int i = 0; i < text.length() ; i++) {
				timeline.getKeyFrames().add(new KeyFrame(
						Duration.millis(duration), 
						new EventHandler() {
							@Override
							public void handle(Event event) {
								if (newText.length() < text.length()) {
									newText.append(text.charAt(newText.length()));
									field.setText(newText.toString());
								}
							}
						}));
					duration += step;
			} 
	        timeline.setCycleCount(1);
	        timeline.play();  
		}
		
	}
	
	
}
