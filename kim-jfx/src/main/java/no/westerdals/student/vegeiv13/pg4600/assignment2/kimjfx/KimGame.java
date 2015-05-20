package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class KimGame extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World!");
        Label label = new Label("Hello again!");

        StackPane root = new StackPane();
        root.getChildren().add(label);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
