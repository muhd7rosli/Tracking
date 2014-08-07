package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Class Main
 */
public class Main extends Application {

    /**
     * Start the apps comment
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        // create whole container
        VBox container = new VBox();

        // create canvas
        Pane canvas = new BorderPane();
        canvas.setPrefSize(800, 600); // set the size

        final Rectangle square = new Rectangle(50, 50); // create the square
        square.setX(400-25);
        square.setY(300-25);

        canvas.getChildren().add(square); // add the square to the canvas

        // create textfield to display mouse position
        final TextField mouseStatus = new TextField();
        mouseStatus.setDisable(true);

        final Button thisButton = new Button("Test Button");

        // add all elements
        container.getChildren().addAll(canvas, mouseStatus, thisButton);

        Scene scene = new Scene(container);

        final DoubleProperty movementUnit = new SimpleDoubleProperty(10.0);
        mouseStatus.textProperty().bind(movementUnit.asString());
        // add some action
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                // move left
                if (event.getCode().equals(KeyCode.D)) {
                    double currentX = square.getX();
                    double newX = currentX + movementUnit.getValue();

                    if(newX < 800-50) {
                        square.setX(newX);
                    }
                    else {
                        square.setX(800-50);
                    }

                }
                // move up
                else if (event.getCode().equals(KeyCode.W)) {
                    double currentY = square.getY();
                    double newY = currentY - movementUnit.getValue();

                    if(newY > 0) {
                        square.setY(newY);
                    }
                    else {
                        square.setY(0);
                    }
                }
                // move right
                else if (event.getCode().equals(KeyCode.A)) {
                    double currentX = square.getX();
                    double newX = currentX - movementUnit.getValue();

                    if(newX > 0) {
                        square.setX(newX);
                    }
                    else {
                        square.setX(0.0);
                    }

                }
                // move down
                else if (event.getCode().equals(KeyCode.S)) {
                    double currentY = square.getY();
                    double newY = currentY + movementUnit.getValue();

                    if(newY < 600-50) {
                        square.setY(newY);
                    }
                    else {
                        square.setY(600-50);
                    }
                }
                // gear up
                else if (event.getCode().equals(KeyCode.UP)) {
                    movementUnit.set(movementUnit.getValue() + 10.0);
                }
                // gear down
                else if (event.getCode().equals(KeyCode.DOWN)) {

                    // check if the value is non negative
                    if(movementUnit.getValue() > 0) {
                        movementUnit.set(movementUnit.getValue() - 10.0);
                    }
                }
            }
        });

        // give title to window
        primaryStage.setTitle("Tracking");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
