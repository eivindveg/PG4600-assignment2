package no.westerdals.student.vegeiv13.pg4600.assignment2.kimapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class KimWord {

    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField
    private String word;

    public KimWord(final String word) {
        this.word = word;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }
}
