package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.BorderPane;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.KimGame;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers.Controller;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.Difficulty;

public class HomeController extends Controller<KimGame> {

    @FXML
    private BorderPane root;
    @FXML
    private ListView<Difficulty> difficulties;

    @FXML
    private Button scores;

    @FXML
    public void play() {
        MultipleSelectionModel<Difficulty> selectionModel = difficulties.getSelectionModel();
        if(!selectionModel.isEmpty()) {
            getApplication().startGame(selectionModel.getSelectedItem());
        }
    }

    @FXML
    public void showScores() {
        System.out.println("Scores called");
    }

    @Override
    public void setApplication(KimGame application) {
        super.setApplication(application);

        Difficulty[] difficulties = Difficulty.values();
        this.difficulties.setItems(FXCollections.observableArrayList(difficulties));
        scores.setOnAction(event -> {
            application.startLeaderBoard();
        });
    }

    @Override
    public Node getRoot() {
        return root;
    }

}
