package org.itpc_advanced.App;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class VisualFX {
	
	public static void fadeTransition(Node element, double startValue, double endValue, int duration, int delay) {
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

	/*
	 * Animation of fast erasing and typing text
	 */

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
	
	public static void slideTransition(Node element) {
		Rectangle mask = new Rectangle(600, 200, Color.AQUA);
		mask.setX(-610);
		mask.setY(0);
		element.getParent().setClip(mask);
		TranslateTransition ts = new TranslateTransition(Duration.seconds(0.75), mask);
		ts.setToX(550);
		ts.play();
	}
	
	public static Animation pulsatingText(Text text) {
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0),    new KeyValue(text.opacityProperty(), 1.0)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1600), new KeyValue(text.opacityProperty(), 0.8)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1100), new KeyValue(text.opacityProperty(), 0.5)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), new KeyValue(text.opacityProperty(), 1.0)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2500), new KeyValue(text.opacityProperty(), 1.0)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		return (Animation)timeline;
	}

	@SuppressWarnings("serial")
	public static List<Animation> addSpinnerAnimation(Circle circle, int speed, int length, int startAngle, int targetRadius) {
		Arc fillerArc = new Arc(circle.getCenterX(), circle.getCenterY(), targetRadius, targetRadius, startAngle, 0.0);
		fillerArc.setType(ArcType.ROUND);
		circle.setClip(fillerArc);
		circle.setRadius(0);
		circle.setVisible(true);

		Timeline birthAnimation = new Timeline();
		birthAnimation.getKeyFrames().add(new KeyFrame(
			Duration.millis(500), 
			new KeyValue(fillerArc.lengthProperty(), -length)
		));
		birthAnimation.getKeyFrames().add(new KeyFrame(
			Duration.millis(200), 
			new KeyValue(circle.radiusProperty(), targetRadius)
		));
		birthAnimation.setDelay(Duration.millis(700));

		Timeline rotationAnimation = new Timeline();
		Rotate   rotationTransform = new Rotate(0, 0, 0);
		KeyFrame keyFrame = new KeyFrame(
			Duration.millis(speed), 
			new KeyValue(rotationTransform.angleProperty(), 360)
		);

		fillerArc.getTransforms().add(rotationTransform);
		rotationAnimation.getKeyFrames().add(keyFrame);
		rotationAnimation.setCycleCount(Animation.INDEFINITE);
		rotationAnimation.play();
		birthAnimation.play();
		List<Animation> animations = new ArrayList<Animation>() {{
			add(rotationAnimation);
			add(birthAnimation);
		}};

		return animations;
	}


	public static void hideSpinnerAnimation(Circle circle) {
		Timeline timeline = new Timeline();
		double duration = 200;
		timeline.getKeyFrames().add(new KeyFrame(
			Duration.millis(duration),
			new KeyValue(circle.radiusProperty(), (circle.getRadius() + 10.0) )
		));
		timeline.getKeyFrames().add(new KeyFrame(
			Duration.millis(duration),
			new KeyValue(circle.radiusProperty(), 0.0)
		));
		timeline.setCycleCount(0);
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				circle.setVisible(false);
				circle.setClip(null);
			}
		});

		timeline.play();
	}


	public static Button setButtonAnimation(Button button) {                             // НЕ ГОТОВО
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent actionEvent) {
				Timeline animation = new Timeline(
				new KeyFrame(Duration.seconds(0),
				new KeyValue(button.translateXProperty(), 1))
				);
				animation.setCycleCount(0);
				animation.setAutoReverse(true);
				animation.play();
			}
		});

		return button;
	}

}
