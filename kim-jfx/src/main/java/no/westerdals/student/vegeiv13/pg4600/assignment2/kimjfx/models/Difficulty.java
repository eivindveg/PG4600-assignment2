package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models;

public enum Difficulty {
    EASY(5),
    MEDIUM(8),
    //HARD(12),
    //EXPERT(15),
    //IMPOSSIBLE(25),
    ;

    private final int words;

    Difficulty(final int words) {
        this.words = words;
    }

    public int getWords() {
        return words;
    }
}
