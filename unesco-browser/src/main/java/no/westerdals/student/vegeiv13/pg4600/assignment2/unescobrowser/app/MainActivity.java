package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app;

import android.os.Bundle;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, mContentFrame);
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        final UnescoMapWorker worker = new UnescoMapWorker(mapFragment.getMap(), getProgressBar(), this);
        worker.execute();
    }
}
