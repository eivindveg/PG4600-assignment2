package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers;

import com.j256.ormlite.dao.Dao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.KimGame;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.ScoreEntryComparator;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.ScoreEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostGameController extends Controller<KimGame> {

    @FXML
    private BorderPane root;
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

    @Override
    public Node getRoot() {
        return root;
    }

    private List<ScoreEntry> handleScore(final ScoreEntry scoreEntry) {
        try {
            List<ScoreEntry> scoreEntries = scoreDao.queryForAll();
            Integer lowest = null;
            for (final ScoreEntry entry : scoreEntries) {
                if(lowest == null || entry.getScore() < lowest) {
                    lowest = entry.getScore();
                }
            }
            if (scoreEntries.size() < 7 || lowest == null || scoreEntry.getScore() > lowest) {
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
            Collections.sort(scoreEntries, new ScoreEntryComparator());

            return scoreEntries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScore(final int score) {
        scoreLabel.setText(String.valueOf(score));
    }


}
