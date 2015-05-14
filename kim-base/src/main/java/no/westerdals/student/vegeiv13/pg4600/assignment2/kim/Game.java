package no.westerdals.student.vegeiv13.pg4600.assignment2.kim;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Difficulty difficulty;
    private List<Round> rounds;
    private int roundsPlayed;

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

    public Round getCurrentRound() throws GameOverException {
        try {
            return rounds.get(roundsPlayed);
        } catch(IndexOutOfBoundsException e) {
            throw new GameOverException("Game is finished, no more rounds");
        }
    }

    public boolean guessAnswer(final String guess) throws GameOverException {
        Round currentRound = getCurrentRound();
        roundsPlayed += difficulty.getWords();
        return currentRound.guessAnswer(guess);
    }
}
