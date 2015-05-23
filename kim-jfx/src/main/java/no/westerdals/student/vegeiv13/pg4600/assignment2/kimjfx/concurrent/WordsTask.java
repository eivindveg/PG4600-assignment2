package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.concurrent;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import javafx.concurrent.Task;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.KimWord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordsTask extends Task<List<KimWord>> {

    // DO NOT USE IN ANY REAL WORLD SCENARIO: THIS CONTAINS A DEMO KEY!
    private static final String WORDS_URL = "http://api.wordnik.com:80/v4/words.json/randomWords?" +
            "hasDictionaryDef=true" +
            "&minCorpusCount=0" +
            "&minLength=5" +
            "&maxLength=15" +
            "&limit=100" +
            "&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";

    private Dao<KimWord, Integer> wordsDao;

    public WordsTask(final Dao<KimWord, Integer> wordsDao) {
        this.wordsDao = wordsDao;
    }

    @Override
    protected List<KimWord> call() throws Exception {
        System.out.println("Querying");
        List<KimWord> kimWords = wordsDao.queryForAll();
        System.out.println("Queried");
        if (kimWords == null || kimWords.isEmpty()) {
            kimWords = downloadWords();
            System.out.println("Saving");
            for (final KimWord kimWord : kimWords) {
                System.out.println("Saving " + kimWord.getWord());
                wordsDao.create(kimWord);
            }
        }
        System.out.println("Done");
        return kimWords;
    }

    private List<KimWord> downloadWords() {
        final Gson gson = new Gson();
        final List<KimWord> wordList = new ArrayList<>();
        try {
            System.out.println("Downloading");
            URL url = new URL(WORDS_URL);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                System.out.println("Building json");
                KimWord[] kimWords = gson.fromJson(reader, KimWord[].class);
                System.out.println("Json built");
                Collections.addAll(wordList, kimWords);
                System.out.println("Added them all!");
            } catch (IOException e) {
                throw new RuntimeException("Not connected?", e);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return wordList;
    }
}
