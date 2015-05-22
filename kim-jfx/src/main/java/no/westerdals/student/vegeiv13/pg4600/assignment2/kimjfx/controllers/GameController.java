package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers;

import com.j256.ormlite.dao.Dao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.KimGame;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.concurrent.WordsTask;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.Difficulty;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.Game;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.KimWord;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.Round;

import java.util.List;

public class GameController extends Controller<KimGame> {


    @FXML
    public ListView<KimWord> wordList;

    @FXML
    public VBox root;

    @FXML
    public VBox alternatives;

    @FXML
    public Button readyButton;

    @FXML
    private HBox progressBox;

    private Difficulty difficulty;
    private Game game;
    private ObjectProperty<Round> round = new SimpleObjectProperty<>();

    public void setDifficulty(final Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void setApplication(final KimGame application) {
        super.setApplication(application);
        //root.getChildren().remove(alternatives);
        Dao<KimWord, Integer> dao = application.getConnectionHandler().getDao(KimWord.class);
        System.out.println("Hello application");
        round.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("Escaping");
                return;
            }
            startGame(round.getValue().displayWords());
        });
        WordsTask task = new WordsTask(dao);
        task.setOnSucceeded(event -> {
            game = new Game(difficulty, task.getValue());
            round.setValue(game.getRound());
        });
        System.out.println("Starting task");
        new Thread(task).start();
        System.out.println("Task started");
    }

    private void startGame(final List<KimWord> value) {
        ObservableList<Node> children = root.getChildren();
        children.remove(progressBox);
        if(!children.contains(readyButton)) {
            children.add(readyButton);
        }
        alternatives.getChildren().clear();
        System.out.println("Generating round");
        wordList.getItems().setAll(value);
        readyButton.setOnAction(event -> displayAlternatives());
        readyButton.setDisable(false);
    }

    private void displayAlternatives() {
        Round round = this.round.getValue();
        wordList.getItems().setAll(round.displayWordsWithoutAnswer());
        root.getChildren().remove(readyButton);
        List<KimWord> words = round.displayAlternatives();
        for (final KimWord word : words) {
            Button button = new Button(word.getWord());
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(event -> {
                boolean correct = game.guessAnswer(word);
                if (!correct) {
                    getApplication().postGame(game.getScore());
                } else {
                    System.out.println("Correct");
                    this.round.setValue(game.getRound());
                }
            });
            alternatives.getChildren().add(button);
        }
    }

}
