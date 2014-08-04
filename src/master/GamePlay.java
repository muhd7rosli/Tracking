package master;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * GamePlay Class
 * Created by muhd7rosli on 8/5/2014.
 */
public class GamePlay extends Application {

    private void move(ImageView car, String direction, double orientation){
        double currentX = car.getX(), currentY = car.getY();
        double currentOrientation = car.getRotate();

        double newOrientation = Math.toRadians(currentOrientation + orientation);

        double newX, newY;
        if(direction.equals("FORWARD")) {
            newX = currentX - currentOrientation * Math.cos(newOrientation);
            newY = currentY - currentOrientation * Math.sin(newOrientation);
        }
        else {
            newX = currentX + currentOrientation * Math.cos(newOrientation);
            newY = currentY + currentOrientation * Math.sin(newOrientation);
        }

        car.setX(newX);
        car.setY(newY);
    }

    /**
     * This class
     * @param stage
     */
    public void start(Stage stage){
        // create whole container
        VBox container = new VBox();
        container.setSpacing(10.0);

        // create canvas
        double canvasWidth = 800, canvasHeight = 600;

        Pane canvas = new BorderPane();
        canvas.setPrefSize(canvasWidth, canvasHeight); // set the size

        // create textfield to display car position
        Label displayPositionLabel = new Label("Position");
        final TextField displayPosition = new TextField();
        displayPosition.setDisable(true);
        displayPosition.setPrefWidth(100);
        // create textfield to display car orientation
        Label displayOrientationLabel = new Label("Orientation");
        final TextField displayOrientation = new TextField();
        displayOrientation.setDisable(true);
        displayOrientation.setPrefWidth(100);
        // create textfield to display current speed
        Label displaySpeedLabel = new Label("Speed");
        final TextField displaySpeed = new TextField();
        displaySpeed.setDisable(true);
        displaySpeed.setPrefWidth(100);

        HBox infoBar = new HBox();
        infoBar.setSpacing(10);
        infoBar.getChildren().addAll(displayPositionLabel, displayPosition,
                displayOrientationLabel, displayOrientation, displaySpeedLabel, displaySpeed);

        // add all elements
        container.getChildren().addAll(infoBar, canvas);

        Scene scene = new Scene(container);

        Image carSourceImage = new Image("car_small.png");
        final ImageView car = new ImageView(carSourceImage);
        canvas.getChildren().add(car);
        car.setX(canvasWidth/2.0 - car.getFitWidth());
        car.setY(canvasHeight/2.0 - car.getFitHeight());
        car.setRotate(90.0);

        final DoubleProperty movementUnit = new SimpleDoubleProperty(10.0);
        final StringProperty coordinate = new SimpleStringProperty();

        // binding
        displayPosition.textProperty().bind(coordinate);
        displayOrientation.textProperty().bind(car.rotateProperty().asString());
        displaySpeed.textProperty().bind(movementUnit.asString());

        final MultiplePressedKeysEventHandler keyHandler =
                new MultiplePressedKeysEventHandler(new MultiplePressedKeysEventHandler.MultiKeyEventHandler() {
                    @Override
                    public void handle(MultiplePressedKeysEventHandler.MultiKeyEvent ke) {

                        // check if both LEFT and A keys are pressed
                        if (ke.isPressed(KeyCode.LEFT)  && ke.isPressed(KeyCode.A)) {
                            System.out.println("LEFT and A");
                        }
                        // check if only LEFT is pressed
                        else if(ke.isPressed(KeyCode.LEFT) && !ke.isPressed(KeyCode.A)){
                            System.out.println("LEFT");
                        }
                        // check if only A is pressed
                        else if(!ke.isPressed(KeyCode.LEFT) && ke.isPressed(KeyCode.A)){
                            System.out.println("A");
                        }

                        // check if both RIGHT and D keys are pressed
                        if (ke.isPressed(KeyCode.RIGHT) && ke.isPressed(KeyCode.D)) {
                            System.out.println("RIGHT and D");
                        }
                        // check if only RIGHT is pressed
                        else if(ke.isPressed(KeyCode.RIGHT) && !ke.isPressed(KeyCode.D)){
                            System.out.println("RIGHT");
                        }
                        // check if only D is pressed
                        else if(!ke.isPressed(KeyCode.RIGHT) && ke.isPressed(KeyCode.D)){
                            System.out.println("D");
                        }

                        // check if both UP and M keys are pressed
                        if (ke.isPressed(KeyCode.UP) && ke.isPressed(KeyCode.W)) {
                            System.out.println("UP and W");
                        }
                        // check if only UP is pressed
                        else if(ke.isPressed(KeyCode.UP) && !ke.isPressed(KeyCode.W)){
                            System.out.println("UP");
                            move(car, "FORWARD", 0.0);
                        }
                        // check if only W is pressed
                        else if(!ke.isPressed(KeyCode.UP) && ke.isPressed(KeyCode.W)){
                            System.out.println("W");
                        }

                        // check if both DOWN and S keys are pressed
                        if (ke.isPressed(KeyCode.DOWN) && ke.isPressed(KeyCode.S)) {
                            System.out.println("DOWN and S");
                        }
                        // check if only DOWN is pressed
                        else if(ke.isPressed(KeyCode.DOWN) && !ke.isPressed(KeyCode.S)){
                            System.out.println("DOWN");
                            move(car, "BACKWARD", 0.0);
                        }
                        // check if only S is pressed
                        else if(!ke.isPressed(KeyCode.DOWN) && ke.isPressed(KeyCode.S)){
                            System.out.println("S");
                        }
                    }
                });

        scene.setOnKeyPressed(keyHandler);
        scene.setOnKeyReleased(keyHandler);

        // give title to window
        stage.setTitle("Tracking");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main application access
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

}
