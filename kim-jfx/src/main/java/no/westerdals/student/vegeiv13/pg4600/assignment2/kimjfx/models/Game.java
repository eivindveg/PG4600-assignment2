package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models;

import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.GameOverException;

import java.util.List;

public class Game {

    private final Difficulty difficulty;
    private Round currentRound;
    private int score;
    private List<KimWord> availableWords;
    private boolean gameOver;

    public Game(final Difficulty difficulty, List<KimWord> availableWords) {
        this.availableWords = availableWords;
        this.difficulty = difficulty;
        currentRound = generateRound();
    }

    private Round generateRound() {
        final int numWords = difficulty.getWords();
        return new Round(availableWords, numWords);
    }

    public Round getRound() throws GameOverException {
        return currentRound = generateRound();
    }

    public boolean guessAnswer(final KimWord guess) throws GameOverException {
        final boolean correct = currentRound.guessAnswer(guess);
        if(correct) {
            score += difficulty.getWords();
        }
        return correct;
    }

    public int getScore() {
        return score;
    }
}
