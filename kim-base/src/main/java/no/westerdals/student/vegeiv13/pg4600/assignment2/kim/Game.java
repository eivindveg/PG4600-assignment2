package no.westerdals.student.vegeiv13.pg4600.assignment2.kim;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Difficulty difficulty;
    private List<Round> rounds;

    public Game(final int rounds, final Difficulty difficulty, List<String> availableWords) {
        this.rounds = new ArrayList<>();
        this.difficulty = difficulty;
        generateRounds(rounds, availableWords);
    }

    private void generateRounds(final int rounds, final List<String> availableWords) {
        final int numWords = difficulty.getWords();
        for(int i = 0; i < rounds; i++) {
            this.rounds.add(new Round(availableWords, numWords));
        }
    }
}
