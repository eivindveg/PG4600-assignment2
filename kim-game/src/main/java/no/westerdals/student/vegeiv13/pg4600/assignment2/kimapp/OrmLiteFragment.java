package no.westerdals.student.vegeiv13.pg4600.assignment2.kimapp;

import android.app.Fragment;
import android.database.sqlite.SQLiteOpenHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * Concept ripped from http://stackoverflow.com/questions/13015826/using-ormlite-with-fragments
 */
public class OrmLiteFragment extends Fragment {

    private OrmLiteSqliteOpenHelper helper;

    protected SQLiteOpenHelper getHelper() {
        if (helper == null) {
            helper = OpenHelperManager.getHelper(getActivity(), OrmLiteSqliteOpenHelper.class);

        }
        return helper;
    }

    @Override
    public void onDestroy() {
        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }
    }
}
