package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers;

import com.j256.ormlite.dao.Dao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
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


    public Label label;
    @FXML
    private ListView<KimWord> wordList;

    @FXML
    private VBox root;

    @FXML
    private VBox alternatives;

    @FXML
    private Button readyButton;

    @FXML
    private Button quit;

    @FXML
    private ProgressIndicator progress;

    private Difficulty difficulty;
    private Game game;
    private ObjectProperty<Round> round = new SimpleObjectProperty<>();

    public void setDifficulty(final Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void setApplication(final KimGame application) {
        super.setApplication(application);
        quit.setOnAction(event1 -> handleScore());
        //root.getChildren().remove(alternatives);
        Dao<KimWord, Integer> dao = application.getConnectionHandler().getDao(KimWord.class);

        wordList.getItems().addListener((ListChangeListener<? super KimWord>) c ->
                wordList.setPrefSize(wordList.getPrefWidth(), (wordList.getItems().size() * 24) + 12)
        );

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
        new Thread(task).start();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    private void startGame(final List<KimWord> value) {
        label.setText("Press ready when you've memorized the words!");
        ObservableList<Node> children = root.getChildren();
        progress.setVisible(false);
        if (!children.contains(readyButton)) {
            children.add(readyButton);
        }
        alternatives.getChildren().clear();
        wordList.getItems().setAll(value);
        readyButton.setOnAction(event -> displayAlternatives());
        readyButton.setDisable(false);
    }

    private void displayAlternatives() {
        label.setText("Please select the word you think is missing from the list");
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
                    handleScore();
                } else {
                    System.out.println("Correct");
                    this.round.setValue(game.getRound());
                }
            });
            alternatives.getChildren().add(button);
        }
    }

    private void handleScore() {
        if (game.getScore() <= 0) {
            getApplication().startLeaderBoard();
        } else {
            getApplication().startPostGame(game.getScore());
        }
    }

}
