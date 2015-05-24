package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.helpers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.R;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.concurrent.ImageDownloadWorker;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models.HeritageSite;

import java.util.List;

public class UnescoInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View convertView;
    private LayoutInflater inflater;
    private List<HeritageSite> sites;
    private ImageDownloadWorker task;

    public UnescoInfoWindowAdapter(final LayoutInflater inflater, final List<HeritageSite> sites) {
        this.inflater = inflater;
        this.sites = sites;
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

    @SuppressWarnings("Annotator")
    @Override
    public View getInfoContents(final Marker marker) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.map_info_window, null);
        }
        if(task != null) {
            task.cancel(true);
        }
        HeritageSite site = findSiteByMarkerId(marker.getId());
        if (site == null) {
            return null;
        }
        TextView titleView = (TextView) convertView.findViewById(R.id.info_title);
        titleView.setText(site.getTitle());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.info_image);
        task = new ImageDownloadWorker(inflater.getContext(), site.getImage(), imageView);
        task.execute();

        TextView descriptionView = (TextView) convertView.findViewById(R.id.info_description);
        descriptionView.setText(site.getDescription());

        return convertView;
    }

}
