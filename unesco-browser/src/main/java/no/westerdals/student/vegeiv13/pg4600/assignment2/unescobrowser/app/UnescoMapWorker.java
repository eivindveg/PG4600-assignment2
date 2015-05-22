package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.models.HeritageSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UnescoMapWorker extends AsyncTask<Void, Integer, List<HeritageSite>> {


    public static final String GEORSS_URL = "http://whc.unesco.org/en/list/georss/";
    private GoogleMap googleMap;
    private ProgressBar progress;
    private Context context;

    public UnescoMapWorker(GoogleMap googleMap, ProgressBar progress, Context context) {
        this.googleMap = googleMap;
        this.progress = progress;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<HeritageSite> doInBackground(final Void... params) {
        List<HeritageSite> heritageSites = new ArrayList<>();
        Document parse;
        try {
            parse = Jsoup.parse(new URL(GEORSS_URL), 5000);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Element body = parse.body().getElementsByTag("channel").first();
        Elements items = body.getElementsByTag("item");
        int i = 0;
        progress.setMax(items.size());
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

            heritageSites.add(new HeritageSite(title, description, image, link, latitude, longitude));
            publishProgress(i++);
        }
        return heritageSites;
    }

    protected void onProgressUpdate(Integer... progress) {
        this.progress.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(final List<HeritageSite> heritageSites) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        progress.setVisibility(View.INVISIBLE);
        googleMap.clear();

        googleMap.setInfoWindowAdapter(new UnescoInfoWindowAdapter(inflater, heritageSites));
        for (final HeritageSite heritageSite : heritageSites) {
            MarkerOptions options = heritageSite.toMarkerOptions();
            Marker marker = googleMap.addMarker(options);
            heritageSite.setMarkerId(marker.getId());
        }
    }
}
