package no.westerdals.student.vegeiv13.pg4600.assignment2.kimapp.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.wordnik.client.api.WordsApi;
import com.wordnik.client.common.ApiException;
import com.wordnik.client.model.WordObject;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimapp.NewGameFragment;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimapp.models.KimWord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordTask extends AsyncTask<Void, Void, List<String>> {

    private Dao<KimWord, Integer> dao;
    private OrmLiteSqliteOpenHelper helper;
    private Context caller;

    public WordTask(final Context caller) throws SQLException {
        this.caller = caller;
        helper = getHelper();
        dao = helper.getDao(KimWord.class);
    }

    protected OrmLiteSqliteOpenHelper getHelper() {
        if (helper == null) {
            helper = OpenHelperManager.getHelper(caller, OrmLiteSqliteOpenHelper.class);
        }
        return helper;
    }

    @Override
    protected final List<String> doInBackground(Void... params) {
        List<KimWord> kimWords = null;
        try {
            kimWords = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (kimWords == null || kimWords.isEmpty()) {
            kimWords = queryFromApi();
            saveWords(dao, kimWords);
        }

        List<String> words = new ArrayList<>();
        for (final KimWord kimWord : kimWords) {
            words.add(kimWord.getWord());
        }
        return words;
    }

    private void saveWords(final Dao<KimWord, Integer> dao, final List<KimWord> kimWords) {
        for (final KimWord kimWord : kimWords) {
            try {
                dao.create(kimWord);
            } catch (SQLException e) {
                throw new RuntimeException("SQL error", e);
            }
        }
    }

    private List<KimWord> queryFromApi() {
        WordsApi api = new WordsApi();
        try {
            List<WordObject> wordList = api.getRandomWords(null, null, null, null, null, null, null, null, null, null, null, 1000);
            List<KimWord> kimWords = new ArrayList<>();
            for (final WordObject wordObject : wordList) {
                kimWords.add(new KimWord(wordObject.getWord()));
            }
            return kimWords;
        } catch (ApiException e) {
            throw new RuntimeException("Not connected?", e);
        }
    }

    @Override
    public void onPostExecute(List<String> result) {
        super.onPostExecute(result);
        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }
        final Intent intent = new Intent(null, NewGameFragment.class);
        caller.startActivity(intent);
    }
}
