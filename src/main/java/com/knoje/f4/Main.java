package com.knoje.f4;

import java.util.Arrays;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	final int maxWidth = 500;
	final int maxHeight = 520;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Fantastic Four !!!");
		BorderPane root = new BorderPane();

		ImageView iv1 = new ImageView(new Image("resources/f1.png"));
		Pane f1 = new Pane(iv1);
		f1.setMinWidth(220);
		f1.setMinHeight(220);

		f1.setPadding(new Insets(10, 10, 10, 10));
		f1.setMaxWidth(maxWidth);

		iv1.setFitWidth(100);
		iv1.setX(50);
		iv1.setY(35);
		// iv1.layoutXProperty().bind(f1.widthProperty().subtract(iv1.fitWidthProperty()).divide(2));
		// iv1.layoutYProperty().bind(f1.heightProperty().subtract(iv1.fitHeightProperty()).divide(6));
		iv1.setPreserveRatio(true);
		iv1.setSmooth(true);
		iv1.setCache(true);
		iv1.maxHeight(maxHeight-40);
		
		Wrapper<Point2D> mouseLocation = new Wrapper<>();

		setUpDragging(iv1, mouseLocation);

		iv1.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getY() > (iv1.getFitHeight() - 5)) {
					iv1.setCursor(Cursor.SE_RESIZE);
				} else {
					iv1.setCursor(Cursor.DEFAULT);
				}
			}
		});

		iv1.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				// double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newMaxX = iv1.getX() + iv1.getFitWidth() + deltaX;
				if (newMaxX >= iv1.getX() && newMaxX <= iv1.getParent().getBoundsInLocal().getWidth()) {
					iv1.setFitWidth(iv1.getFitWidth() + deltaX);
				}
				// double newMaxY = iv1.getY() + iv1.getFitHeight() + deltaY ;
				// if (newMaxY >= iv1.getY() && newMaxY <=
				// iv1.getParent().getBoundsInLocal().getHeight()) {
				// iv1.setFitHeight(iv1.getFitHeight() + deltaY);
				// }
				//System.out.println("F1: " + f1.getWidth() + " " + f1.getHeight());
				//System.out.println("F1: " + iv1.getFitWidth() + " " + iv1.getFitHeight());
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		// resizes the image to have width of 100 while preserving the ratio and using
		// higher quality filtering method; this ImageView is also cached to
		// improve performance
		ImageView iv2 = new ImageView(new Image("resources/f2.png"));
		iv2.setFitWidth(100);
		iv2.setPreserveRatio(true);
		iv2.setSmooth(true);
		iv2.setCache(true);

		// defines a viewport into the source image (achieving a "zoom" effect) and
		// displays it rotated
		ImageView iv3 = new ImageView(new Image("resources/f3.png"));
		iv3.setFitWidth(100);
		iv3.setPreserveRatio(true);
		iv3.setSmooth(true);
		iv3.setCache(true);

		ImageView iv4 = new ImageView(new Image("resources/f4.png"));
		iv4.setFitWidth(100);
		iv4.setPreserveRatio(true);
		iv4.setSmooth(true);
		iv4.setCache(true);

		StackPane f2 = new StackPane(iv2);
		f2.setMinWidth(220);

		StackPane f3 = new StackPane(iv3);
		f3.setMinWidth(220);

		StackPane f4 = new StackPane(iv4);
		f4.setMinWidth(220);

		TilePane center = new TilePane(Orientation.HORIZONTAL, 0, 0);
		center.setPrefColumns(2);
		center.setMaxWidth(maxWidth);

		center.getChildren().addAll(f1, f2, f3, f4);

		root.setCenter(center);

		HBox top = new HBox();
		top.setMinWidth(250);
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(5, 5, 5, 5));
		top.setSpacing(10);
		Button btnStrch = new Button();
		btnStrch.setText("< Stretch >");
		btnStrch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScaleTransition st = new ScaleTransition(Duration.millis(100), iv1);
				st.setByX(1.5f);
				st.setByY(1.5f);
				st.setCycleCount(2);
				st.setAutoReverse(true);
				st.play();
				st.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						createDraggableRectangle(110, 220, 1190, -320);
						iv1.setFitWidth(100);
					}
				});

			}
		});
		Button btnVsbl = new Button();
		btnVsbl.setText("< Visible >");
		btnVsbl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FadeTransition iv2ft = new FadeTransition(Duration.millis(1000), iv2);
				iv2ft.setFromValue(1.0);
				iv2ft.setToValue(0.0);
				iv2ft.setCycleCount(2);
				iv2ft.setAutoReverse(true);

				iv2ft.play();

			}
		});

		Button btnFly = new Button();
		btnFly.setText("< Fly >");
		btnFly.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Path path = new Path();
				path.getElements().add(new MoveTo(iv3.getLayoutX() - 10, iv3.getLayoutY()));
				path.getElements().add(new LineTo(iv3.getLayoutX(), iv3.getLayoutY() - 350));

				// path.getElements().add (new MoveTo(0f, 50f));
				// path.getElements().add(new LineTo(460, 320));;
				// path.getElements().add (new CubicCurveTo(450f, -450f, 45f, 45f, 0, -450f));

				PathTransition pathTransition = new PathTransition();
				pathTransition.setDuration(Duration.millis(5000));
				pathTransition.setNode(iv3);
				pathTransition.setPath(path);
				pathTransition.setOrientation(OrientationType.NONE);
				pathTransition.setCycleCount(2);
				pathTransition.setAutoReverse(true);

				pathTransition.play();

				pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {

					}
				});
			}
		});

		Button btnWalk = new Button();
		btnWalk.setText("< Walk >");
		btnWalk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Path path = new Path();
				path.getElements().add(new MoveTo(iv4.getLayoutX(), iv4.getLayoutY() + 10));
				path.getElements().add(new LineTo(iv4.getLayoutX() - 350, iv4.getLayoutY() + 10));

				// path.getElements().add (new MoveTo(0f, 50f));
				// path.getElements().add (new CubicCurveTo(40f, 50f, -50f, -50f, -1150, -50f));

				PathTransition pathTransition = new PathTransition();
				pathTransition.setDuration(Duration.millis(3000));
				pathTransition.setNode(iv4);
				pathTransition.setPath(path);
				pathTransition.setOrientation(OrientationType.NONE);
				pathTransition.setCycleCount(2);
				pathTransition.setAutoReverse(true);

				pathTransition.play();

				pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {

					}
				});
			}
		});

		top.getChildren().addAll(btnStrch, btnVsbl, btnFly, btnWalk);
		root.setTop(top);

		stage.setWidth(maxWidth);
		stage.setMaxWidth(maxWidth);
		stage.setHeight(maxHeight);
		stage.setMaxHeight(maxHeight);
		stage.setResizable(false);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	static class Wrapper<T> {
		T value;
	}

	private Rectangle createDraggableRectangle(double x, double y, double width, double height) {
		final double handleRadius = 2;

		Rectangle rect = new Rectangle(x, y, width, height);

		// top left resize handle:
		Circle resizeHandleNW = new Circle(handleRadius, Color.RED);
		// bind to top left corner of Rectangle:
		resizeHandleNW.centerXProperty().bind(rect.xProperty().divide(2));
		resizeHandleNW.centerYProperty().bind(rect.yProperty().divide(2));

		// bottom right resize handle:
		Circle resizeHandleSE = new Circle(handleRadius, Color.GREEN);
		// bind to bottom right corner of Rectangle:
		resizeHandleSE.centerXProperty().bind(rect.xProperty().add(rect.widthProperty()));
		resizeHandleSE.centerYProperty().bind(rect.yProperty().add(rect.heightProperty()));

		// move handle:
		Circle moveHandle = new Circle(handleRadius, Color.GOLD);
		// bind to bottom center of Rectangle:
		moveHandle.centerXProperty().bind(rect.xProperty().add(rect.widthProperty().divide(2)));
		moveHandle.centerYProperty().bind(rect.yProperty().add(rect.heightProperty()));

		// force circles to live in same parent as rectangle:
		rect.parentProperty().addListener((obs, oldParent, newParent) -> {
			for (Circle c : Arrays.asList(resizeHandleNW, resizeHandleSE, moveHandle)) {
				Pane currentParent = (Pane) c.getParent();
				if (currentParent != null) {
					currentParent.getChildren().remove(c);
				}
				((Pane) newParent).getChildren().add(c);
			}
		});

		Wrapper<Point2D> mouseLocation = new Wrapper<>();

		setUpDragging(resizeHandleNW, mouseLocation);
		setUpDragging(resizeHandleSE, mouseLocation);
		setUpDragging(moveHandle, mouseLocation);

		resizeHandleNW.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newX = rect.getX() + deltaX;
				if (newX >= handleRadius && newX <= rect.getX() + rect.getWidth() - handleRadius) {
					rect.setX(newX);
					rect.setWidth(rect.getWidth() - deltaX);
				}
				double newY = rect.getY() + deltaY;
				if (newY >= handleRadius && newY <= rect.getY() + rect.getHeight() - handleRadius) {
					rect.setY(newY);
					rect.setHeight(rect.getHeight() - deltaY);
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		resizeHandleSE.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newMaxX = rect.getX() + rect.getWidth() + deltaX;
				if (newMaxX >= rect.getX()
						&& newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleRadius) {
					rect.setWidth(rect.getWidth() + deltaX);
				}
				double newMaxY = rect.getY() + rect.getHeight() + deltaY;
				if (newMaxY >= rect.getY()
						&& newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleRadius) {
					rect.setHeight(rect.getHeight() + deltaY);
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		moveHandle.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newX = rect.getX() + deltaX;
				double newMaxX = newX + rect.getWidth();
				if (newX >= handleRadius && newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleRadius) {
					rect.setX(newX);
				}
				double newY = rect.getY() + deltaY;
				double newMaxY = newY + rect.getHeight();
				if (newY >= handleRadius && newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleRadius) {
					rect.setY(newY);
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}

		});

		return rect;
	}

	private void setUpDragging(Circle circle, Wrapper<Point2D> mouseLocation) {

		circle.setOnDragDetected(event -> {
			circle.getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
		});

		circle.setOnMouseReleased(event -> {
			circle.getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}

	private void setUpDragging(ImageView iv, Wrapper<Point2D> mouseLocation) {

		iv.setOnDragDetected(event -> {
			iv.getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
		});

		iv.setOnMouseReleased(event -> {
			iv.getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}

}