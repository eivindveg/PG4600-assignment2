package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models.HeritageSite;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnescoInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final ExecutorService executor;
    private View convertView;
    private LayoutInflater inflater;
    private List<HeritageSite> sites;

    public UnescoInfoWindowAdapter(final LayoutInflater inflater, final List<HeritageSite> sites) {
        this.inflater = inflater;
        this.sites = sites;
        executor = Executors.newSingleThreadExecutor();
    }


    @SuppressLint("InflateParams")
    @Override
    public View getInfoWindow(final Marker marker) {
        return null;
    }

    private HeritageSite findSiteByMarkerId(final String id) {
        for (final HeritageSite site : sites) {
            if (site.getMarkerId().equals(id)) {
                return site;
            }
        }
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.map_info_window, null);
        }
        HeritageSite site = findSiteByMarkerId(marker.getId());
        if (site == null) {
            return null;
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.info_title);
        titleView.setText(site.getTitle());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.info_image);
        ImageDownloadWorker task = new ImageDownloadWorker(site.getImage(), imageView);
        task.execute();

        TextView descriptionView = (TextView) convertView.findViewById(R.id.info_description);
        descriptionView.setText(site.getDescription());

        return convertView;
    }

}
