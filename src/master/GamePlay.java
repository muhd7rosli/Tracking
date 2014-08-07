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
import java.text.DecimalFormat;

/**
 * GamePlay Class
 * Created by muhd7rosli on 8/5/2014.
 */
public class GamePlay extends Application {

    private void move(ImageView car, String direction){
        double currentX = car.getX(), currentY = car.getY();
        double currentOrientation = car.getRotate();

        double newOrientation = Math.toRadians(currentOrientation);

        double newX, newY;
        if(direction.equals("FORWARD")) {
            newX = currentX - 1.0 * Math.cos(newOrientation);
            newY = currentY - 1.0 * Math.sin(newOrientation);
        }
        else {
            newX = currentX + 1.0 * Math.cos(newOrientation);
            newY = currentY + 1.0 * Math.sin(newOrientation);
        }

        car.setX(newX);
        car.setY(newY);
    }

    /**
     *
     * @param car
     * @param direction
     */
    private void move(ImageView car, String direction, double speed){
        double currentX = car.getX(), currentY = car.getY();
        double currentOrientation = car.getRotate();

        double newOrientation = Math.toRadians(currentOrientation);

        double newX, newY;
        if(direction.equals("FORWARD")) {
            newX = currentX - speed * Math.cos(newOrientation);
            newY = currentY - speed * Math.sin(newOrientation);
        }
        else {
            newX = currentX + speed * Math.cos(newOrientation);
            newY = currentY + speed * Math.sin(newOrientation);
        }

        car.setX(newX);
        car.setY(newY);
    }

    /**
     *
     * @param car
     * @param direction
     * @param orientation
     */
    private void move(final ImageView car, String direction, double speed, double orientation){
        double currentX = car.getX(), currentY = car.getY();
        double currentOrientation = car.getRotate();

        double newOrientation = currentOrientation + orientation;
        final double newOrientationRad = Math.toRadians(newOrientation);

        final double newX, newY;
        if(direction.equals("FORWARD")) {
            newX = currentX - speed * Math.cos(newOrientationRad);
            newY = currentY - speed * Math.sin(newOrientationRad);
        }
        else {
            newX = currentX + speed * Math.cos(newOrientationRad);
            newY = currentY + speed * Math.sin(newOrientationRad);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                car.setRotate(Math.toDegrees(newOrientationRad));
                car.setX(newX);
                car.setY(newY);
            }
        });

    }

    private String displayNumber(double number){

        BigDecimal bd = new BigDecimal(number);
        bd.setScale(3, BigDecimal.ROUND_FLOOR);
        DecimalFormat df = new DecimalFormat("##.###");
        return df.format(number);
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

        Label instructionLbl = new Label("Pressed UP key to move forward, " +
                "Pressed DOWN key to move backward, " + "\n" +
                "Pressed LEFT key to move left, " + "\n" +
                "Pressed RIGHT key to move right, " + "\n" +
                "Pressed W key to accelerate, " + "\n" +
                "Pressed S key to decelerate.");
        instructionLbl.setPrefWidth(canvasWidth);

        HBox infoBar = new HBox();
        infoBar.setSpacing(10);
        infoBar.getChildren().addAll(displayPositionLabel, displayPosition,
                displayOrientationLabel, displayOrientation, displaySpeedLabel, displaySpeed);

        // add all elements
        container.getChildren().addAll(infoBar, instructionLbl, canvas);

        Scene scene = new Scene(container);

        Image carSourceImage = new Image("car_small.png");
        final ImageView car = new ImageView(carSourceImage);
        canvas.getChildren().add(car);
        car.setX(canvasWidth/2.0 - car.getFitWidth());
        car.setY(canvasHeight/2.0 - car.getFitHeight());
        car.setRotate(90.0);

        final DoubleProperty movementUnit = new SimpleDoubleProperty(0.0);
        final StringProperty coordinate = new SimpleStringProperty();
        final StringProperty rotation = new SimpleStringProperty(String.valueOf(car.getRotate()));

        // binding
        displayPosition.textProperty().bind(coordinate);
        displayOrientation.textProperty().bind(rotation);
        displaySpeed.textProperty().bind(movementUnit.asString());

        final MultiplePressedKeysEventHandler keyHandler =
                new MultiplePressedKeysEventHandler(new MultiplePressedKeysEventHandler.MultiKeyEventHandler() {
                    @Override
                    public void handle(MultiplePressedKeysEventHandler.MultiKeyEvent ke) {

                        // check if both UP and LEFT keys are pressed
                        if (ke.isPressed(KeyCode.UP)  && ke.isPressed(KeyCode.LEFT)) {
                            System.out.println("UP and LEFT");
                            move(car, "FORWARD", movementUnit.getValue(), -10.0);

                        }
                        // check if only UP and RIGHT keys are pressed
                        else if(ke.isPressed(KeyCode.UP) && ke.isPressed(KeyCode.RIGHT)){
                            System.out.println("UP AND RIGHT");
                            move(car, "FORWARD", movementUnit.getValue(), +10.0);
                        }
                        else if(ke.isPressed(KeyCode.DOWN) && ke.isPressed(KeyCode.LEFT)){
                            System.out.println("DOWN AND LEFT");
                            move(car, "BACKWARD", movementUnit.getValue(), -10.0);
                        }
                        else if(ke.isPressed(KeyCode.DOWN) && ke.isPressed(KeyCode.RIGHT)){
                            System.out.println("DOWN AND RIGHT");
                            move(car, "BACKWARD", movementUnit.getValue(), +10.0);
                        }
                        // check if only UP is pressed
                        else if(ke.isPressed(KeyCode.UP)){
                            move(car, "FORWARD", movementUnit.getValue());
                        }
                        // check if only DOWN is pressed
                        else if(ke.isPressed(KeyCode.DOWN)){
                            move(car, "BACKWARD", movementUnit.getValue());
                        }
                        // check if only LEFT is pressed
                        else if(ke.isPressed(KeyCode.LEFT)){
                            move(car, "FORWARD", 0.0, -10.0);
                        }
                        // check if only RIGHT is pressed
                        else if(ke.isPressed(KeyCode.RIGHT)){
                            move(car, "FORWARD", 0.0, +10.0);
                        }
                        // check if only W is pressed
                        else if(ke.isPressed(KeyCode.W)){
                            movementUnit.setValue(movementUnit.getValue() + 10.0);
                        }
                        // check if only S is pressed
                        else if(ke.isPressed(KeyCode.S)){
                            movementUnit.setValue(movementUnit.getValue() - 10.0);
                        }

                        coordinate.setValue(displayNumber(car.getX()) + ", " + displayNumber(car.getY()));
                        rotation.setValue(displayNumber(car.getRotate()));
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
