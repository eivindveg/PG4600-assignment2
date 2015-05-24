package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafxports.android.FXActivity;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.KimWord;
import no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.models.ScoreEntry;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AndroidConnectionHandler extends ConnectionHandler {

    private final Map<Class, Dao<?, ?>> daos = new HashMap<>();
    private final DatabaseHelper helper;

    public AndroidConnectionHandler() {
        helper = new DatabaseHelper(FXActivity.getInstance());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, ID> Dao<T, ID> getDao(final Class<T> clazz) {
        if(daos.get(clazz) == null) {
            try {
                daos.put(clazz, helper.getDao(clazz));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return (Dao<T, ID>) daos.get(clazz);
    }

    private class DatabaseHelper extends OrmLiteSqliteOpenHelper {

        public DatabaseHelper(final Context context) {
            super(context, "kimjfx.db", null, 2);
        }

        @Override
        public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
            try {
                int table = TableUtils.createTable(connectionSource, ScoreEntry.class);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println(table);
                System.out.println("ScoreEntry created");
                int table1 = TableUtils.createTable(connectionSource, KimWord.class);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println(table1);
                System.out.println("KimWord created");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {

        }
    }
}
