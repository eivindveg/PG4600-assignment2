package no.westerdals.student.vegeiv13.pg4600.assignment2.kim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Round {

    public static final int NUMBER_OF_WRONG_ALTERNATIVES = 2;
    public static final int MINIMUM_NUMBER_OF_WORDS = Difficulty.values()[0].getWords();
    private final String answer;
    private final List<String> selectedWords;
    private final List<String> alternatives;
    private Random random;

    protected Round(List<String> wordObjects, int numberOfWords) {
        checkParameters(wordObjects, numberOfWords);
        random = new Random();

        selectedWords = new ArrayList<>();
        alternatives = new ArrayList<>();
        answer = wordObjects.remove(random.nextInt(wordObjects.size()));

        setupSelection(wordObjects, numberOfWords);
        setupAlternatives(wordObjects);
    }

    private void checkParameters(final List<String> wordObjects, final int numberOfWords) {
        if (numberOfWords < MINIMUM_NUMBER_OF_WORDS) {
            throw new UnsupportedOperationException("Cannot generate a round with less than "
                    + MINIMUM_NUMBER_OF_WORDS + " words");
        }
        if (wordObjects.size() < numberOfWords + NUMBER_OF_WRONG_ALTERNATIVES) {
            throw new UnsupportedOperationException("WordObject list is not large enough to generate a round of size "
                    + numberOfWords);
        }
    }

    private void setupAlternatives(final List<String> wordObjects) {
        alternatives.add(answer);

        while (alternatives.size() < NUMBER_OF_WRONG_ALTERNATIVES + 1) {
            int index = random.nextInt(wordObjects.size());
            String wordToAdd = wordObjects.remove(index);
            alternatives.add(wordToAdd);
        }

        Collections.shuffle(alternatives);
    }

    private void setupSelection(final List<String> wordObjects, final int numberOfWords) {
        selectedWords.add(answer);

        while (selectedWords.size() < numberOfWords) {
            int index = random.nextInt(wordObjects.size());
            String wordToAdd = wordObjects.remove(index);
            selectedWords.add(wordToAdd);
        }

        Collections.shuffle(selectedWords);
    }

    public List<String> displayWords() {
        return Collections.unmodifiableList(selectedWords);
    }

    public List<String> displayAlternatives() {
        return Collections.unmodifiableList(alternatives);
    }

    public List<String> displayWordsWithoutAnswer() {
        List<String> words = new ArrayList<>(selectedWords);

        // Checks and balances
        if(words.remove(answer)) {
            return words;
        } else {
            throw new NullPointerException("Could not prepare answer free list");
        }
    }
}
