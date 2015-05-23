package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.KimGame;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.ScoreEntry;

import java.util.List;

public class LeaderboardController extends Controller<KimGame> {

    @FXML
    private ListView<ScoreEntry> scoresList;

    @FXML
    private Button home;

    @Override
    public void setApplication(final KimGame application) {
        super.setApplication(application);
        home.setOnAction(event -> application.startHome());
    }

    public void setEntries(final List<ScoreEntry> scoreEntries) {
        scoresList.getItems().addAll(scoreEntries);
    }
}
