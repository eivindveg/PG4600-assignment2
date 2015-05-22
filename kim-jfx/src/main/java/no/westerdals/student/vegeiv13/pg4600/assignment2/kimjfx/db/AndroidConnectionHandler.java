package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafxports.android.FXActivity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AndroidConnectionHandler extends ConnectionHandler {

    private final FXActivity context;
    private Map<Class, DatabaseHelper> helpers = new HashMap<>();

    public AndroidConnectionHandler() {
        context = FXActivity.getInstance();
    }

    @Override
    public <T, ID> Dao<T, ID> getDao(final Class<T> clazz) {
        if(helpers.get(clazz) == null) {
            helpers.put(clazz, new DatabaseHelper<>(clazz, context));
        }
        try {
            return helpers.get(clazz).getDao(clazz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private class DatabaseHelper<T> extends OrmLiteSqliteOpenHelper {

        private Class<T> clazz;

        public DatabaseHelper(Class<T> clazz, Context context) {
            super(context, "kimjfx.db", null, 1);

            this.clazz = clazz;
        }

        @Override
        public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, clazz);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
            try {
                TableUtils.dropTable(connectionSource, clazz, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
