package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers;

import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.ScoreEntry;

import java.util.Comparator;

public class ScoreEntryComparator implements Comparator<ScoreEntry> {

    @Override
    public int compare(final ScoreEntry o1, final ScoreEntry o2) {
        return Integer.compare(o2.getScore(), o1.getScore());
    }
}
