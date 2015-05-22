package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers.GameController;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers.HomeController;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db.AndroidConnectionHandler;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db.ConnectionHandler;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db.DesktopConnectionHandler;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.Difficulty;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.utils.OSHandler;

import java.io.IOException;
import java.net.URL;


public class KimGame extends Application {

    private Group root = new Group();
    private ConnectionHandler connectionHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(startHome()));
        primaryStage.show();
    }

    private Parent startHome() {
        HomeController controller = (HomeController) replaceSceneContent("Home.fxml");
        controller.setApplication(this);
        return root;
    }

    public Parent startGame(final Difficulty difficulty) {
        GameController controller = (GameController) replaceSceneContent("Game.fxml");
        controller.setDifficulty(difficulty);
        controller.setApplication(this);
        return root;
    }

    private Controller<KimGame> replaceSceneContent(final String fxml) {
        ClassLoader cLoader = KimGame.class.getClassLoader();
        URL resource = cLoader.getResource("no/westerdals/student/vegeiv13/pg4600/assignment2/kimjfx/" + fxml);
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            final Node layout = loader.load();
            root.getChildren().setAll(layout);
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Could not load resources!", e);
        }

    }

    public ConnectionHandler getConnectionHandler() {
        if(connectionHandler == null) {
            if(OSHandler.IS_ANDROID) {
                connectionHandler = new AndroidConnectionHandler();
            } else if(OSHandler.IS_DESKTOP) {
                connectionHandler = new DesktopConnectionHandler();
            }
        }
        return connectionHandler;
    }

    public void postGame(final int score) {

    }
}
