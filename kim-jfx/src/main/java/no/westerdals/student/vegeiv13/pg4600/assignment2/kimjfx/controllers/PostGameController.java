package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers;

import com.j256.ormlite.dao.Dao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.KimGame;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.ScoreEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostGameController extends Controller<KimGame> {

    @FXML
    private Label scoreLabel;

    @FXML
    private TextField name;

    @FXML
    private Button enter;
    private Dao<ScoreEntry, Integer> scoreDao;

    @Override
    public void setApplication(final KimGame application) {
        super.setApplication(application);
        scoreDao = application.getConnectionHandler().getDao(ScoreEntry.class);
        EventHandler<ActionEvent> actionEventEventHandler = event -> {
            if (name.getText().replaceAll(" ", "").equals("")) {
                return;
            }
            ScoreEntry scoreEntry = new ScoreEntry(name.getText().trim(), Integer.parseInt(scoreLabel.getText()));
            application.startLeaderBoard(PostGameController.this.handleScore(scoreEntry));
        };
        enter.setOnAction(actionEventEventHandler);
        name.setOnAction(actionEventEventHandler);
    }

    private List<ScoreEntry> handleScore(final ScoreEntry scoreEntry) {
        try {
            List<ScoreEntry> scoreEntries = scoreDao.queryForAll();
            Integer lowest = scoreEntries.stream()
                    .mapToInt(ScoreEntry::getScore)
                    .min().orElse(0);
            if (scoreEntries.size() < 7 && scoreEntry.getScore() > lowest) {
                scoreDao.create(scoreEntry);
                scoreEntries.add(scoreEntry);
            }
            if (scoreEntries.size() > 7) {
                new ArrayList<>(scoreEntries.subList(7, scoreEntries.size())).forEach(entry -> {
                    scoreEntries.remove(entry);
                    try {
                        scoreDao.delete(entry);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            scoreEntries.sort((o1, o2) -> Integer.compare(o1.getScore(), o2.getScore()));

            return scoreEntries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScore(final int score) {
        scoreLabel.setText(String.valueOf(score));
    }
}
