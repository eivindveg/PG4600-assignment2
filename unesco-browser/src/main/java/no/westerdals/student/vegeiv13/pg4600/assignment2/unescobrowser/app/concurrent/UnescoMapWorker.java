package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.concurrent;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.UnescoInfoWindowAdapter;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.helpers.HeritageSiteHelper;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models.HeritageSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class UnescoMapWorker extends AsyncTask<Void, Integer, List<HeritageSite>> {

    private static final String GEORSS_URL = "http://whc.unesco.org/en/list/georss/";
    private final HeritageSiteHelper helper;
    private GoogleMap googleMap;
    private ProgressBar progress;
    private Activity context;

    public UnescoMapWorker(GoogleMap googleMap, ProgressBar progress, Activity context) {
        this.googleMap = googleMap;
        this.progress = progress;
        this.context = context;
        this.helper = new HeritageSiteHelper(context);
    }

    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<HeritageSite> doInBackground(final Void... params) {
        List<HeritageSite> heritageSites = helper.getAll();
        if(heritageSites.isEmpty()) {
            downloadSites(heritageSites);
        } else {
            int progress = 0;
            this.progress.setMax(heritageSites.size());
            for (final HeritageSite heritageSite : heritageSites) {
                try {
                    addMarker(heritageSite);
                    publishProgress(progress++);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return heritageSites;
    }

    private void downloadSites(final List<HeritageSite> heritageSites) {
        Document parse;
        try {
            parse = Jsoup.parse(new URL(GEORSS_URL), 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element body = parse.body().getElementsByTag("channel").first();
        Elements items = body.getElementsByTag("item");
        int progress = 0;
        clearMap();
        this.progress.setMax(items.size());
        for (final Element item : items) {
            String title = item.getElementsByTag("title").first().text();

            // Extract CDATA content
            Elements descriptionElements = item.getElementsByTag("description");
            Element descriptionContent = Jsoup.parse(descriptionElements.text()).body();
            String description = descriptionContent.text();
            String image = descriptionContent.getElementsByTag("img").first().attr("src");

            String link = item.getElementsByTag("link").first().text();

            Double latitude = Double.valueOf(item.getElementsByTag("geo:lat").first().text());
            Double longitude = Double.valueOf(item.getElementsByTag("geo:long").first().text());

            HeritageSite site = new HeritageSite(title, description, image, link, latitude, longitude);
            try {
                addMarker(site);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            heritageSites.add(site);
            try {
                helper.save(site);
            } catch (SQLException e) {
                System.err.println("Could not save site. We can however still work in offline-database-mode");
            }
            publishProgress(progress++);
        }
    }

    private void addMarker(final HeritageSite heritageSite) throws InterruptedException {
        final MarkerOptions options = heritageSite.toMarkerOptions();
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Marker marker = googleMap.addMarker(options);
                heritageSite.setMarkerId(marker.getId());
            }
        });
        Thread.sleep(5);
    }

    protected void onProgressUpdate(Integer... progress) {
        this.progress.setProgress(progress[0]);
    }

    protected void clearMap() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                googleMap.clear();
            }
        });
    }

    @Override
    protected void onPostExecute(final List<HeritageSite> heritageSites) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        progress.setVisibility(View.INVISIBLE);

        googleMap.setInfoWindowAdapter(new UnescoInfoWindowAdapter(inflater, heritageSites));
    }
}
