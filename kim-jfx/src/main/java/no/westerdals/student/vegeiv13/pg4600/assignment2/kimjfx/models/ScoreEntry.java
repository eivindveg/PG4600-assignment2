package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ScoreEntry {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String playerName;
    @DatabaseField
    private Integer score;
    public ScoreEntry() {
    }

    public ScoreEntry(final String playerName, final Integer score) {
        this.playerName = playerName;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(final Integer score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
