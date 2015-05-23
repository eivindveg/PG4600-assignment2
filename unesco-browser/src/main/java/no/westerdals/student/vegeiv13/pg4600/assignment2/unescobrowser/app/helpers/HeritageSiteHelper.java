package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models.HeritageSite;

import java.sql.SQLException;
import java.util.List;

public class HeritageSiteHelper extends OrmLiteSqliteOpenHelper {

    public HeritageSiteHelper(final Context context) {
        super(context, "unescobrowser.db", null, HeritageSite.MODEL_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, HeritageSite.class);
        } catch (SQLException e) {
            throw new RuntimeException("Could not create tables for " + HeritageSite.class.getSimpleName(), e);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, HeritageSite.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException("Could not drop tables for " + HeritageSite.class.getSimpleName(), e);
        }
    }

    public void deleteAll() {
        onUpgrade(getWritableDatabase(), getConnectionSource(), HeritageSite.MODEL_VERSION, HeritageSite.MODEL_VERSION);
    }

    public List<HeritageSite> getAll() {
        try {
            return getDao(HeritageSite.class).queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(final HeritageSite site) throws SQLException {
        getDao(HeritageSite.class).create(site);
    }
}
