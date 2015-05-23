package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app;

import android.os.Bundle;
import com.google.android.gms.maps.MapFragment;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.concurrent.UnescoMapWorker;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.helpers.HeritageSiteHelper;

public class MainActivity extends BaseActivity {

    private MapFragment mapFragment;
    private UnescoMapWorker worker;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, mContentFrame);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        populateMap();
    }

    private void populateMap() {
        worker = new UnescoMapWorker(mapFragment.getMap(), getProgressBar(), this);
        worker.execute();
    }

    @Override
    public void refresh() {
        if(worker != null) {
            worker.cancel(true);
        }
        new HeritageSiteHelper(this).deleteAll();
        mapFragment.getMap().clear();
        populateMap();
    }
}
