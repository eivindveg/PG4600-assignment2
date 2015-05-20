package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloadWorker extends AsyncTask<Void, Void, Drawable> {
    private final String url;
    private ImageView viewToBind;

    public ImageDownloadWorker(final String url, final ImageView viewToBind) {
        this.url = url;
        this.viewToBind = viewToBind;
    }

    @Override
    protected Drawable doInBackground(final Void... params) {
        try(InputStream inputStream = new URL(url).openStream()) {
            return Drawable.createFromStream(inputStream, null);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable result) {
        viewToBind.setImageDrawable(result);
    }
}
