package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers.GameController;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers.LeaderboardController;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers.PostGameController;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db.AndroidConnectionHandler;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db.ConnectionHandler;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db.DesktopConnectionHandler;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.ScoreEntryComparator;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.Difficulty;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.ScoreEntry;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.utils.OSHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class KimGame extends Application {

    private Group root = new Group();
    private ConnectionHandler connectionHandler;
    private Rectangle2D bounds;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        bounds = Screen.getPrimary().getBounds();
        primaryStage.setScene(new Scene(startHome()));
        primaryStage.show();
        rescale(root);
    }

    private void rescale(Node node) {
        System.out.println(bounds);
        if(OSHandler.IS_ANDROID) {
            node.resize(bounds.getWidth(), bounds.getHeight());
        } else {
            node.autosize();
        }
    }

    public Parent startHome() {
        replaceSceneContent("Home.fxml");
        return root;
    }

    public Parent startGame(final Difficulty difficulty) {
        final Controller<KimGame> controller = replaceSceneContent("Game.fxml");
        ((GameController) controller).setDifficulty(difficulty);
        return root;
    }

    private Controller<KimGame> replaceSceneContent(final String fxml) {
        ClassLoader cLoader = KimGame.class.getClassLoader();
        URL resource = cLoader.getResource("no/westerdals/student/vegeiv13/pg4600/assignment2/kimjfx/" + fxml);
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            final Node layout = loader.load();
            root.getChildren().setAll(layout);
            Controller<KimGame> controller = loader.getController();
            rescale(controller.getRoot());
            controller.setApplication(this);
            return controller;
        } catch (IOException e) {
            throw new RuntimeException("Could not load resource" + fxml, e);
        }

    }

    public ConnectionHandler getConnectionHandler() {
        if (connectionHandler == null) {
            if (OSHandler.IS_ANDROID) {
                connectionHandler = new AndroidConnectionHandler();
            } else if (OSHandler.IS_DESKTOP) {
                connectionHandler = new DesktopConnectionHandler();
            }
        }
        return connectionHandler;
    }

    public Parent startPostGame(final int score) {
        PostGameController controller = (PostGameController) replaceSceneContent("PostGame.fxml");
        controller.setScore(score);
        return root;
    }

    public Parent startLeaderBoard(final List<ScoreEntry> scoreEntries) {
        LeaderboardController controller = (LeaderboardController) replaceSceneContent("LeaderBoard.fxml");
        controller.setEntries(scoreEntries);
        return root;
    }

    @SuppressWarnings("unchecked")
    public Parent startLeaderBoard() {
        try {
            List<ScoreEntry> scoreEntries = getConnectionHandler().getDao(ScoreEntry.class).queryForAll();
            Collections.sort(scoreEntries, new ScoreEntryComparator());
            startLeaderBoard(scoreEntries);
            return root;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
