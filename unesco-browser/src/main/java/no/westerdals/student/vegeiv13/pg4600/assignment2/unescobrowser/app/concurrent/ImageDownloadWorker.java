package no.westerdals.student.vegeiv13.pg4600.assignment2.unescobrowser.app.concurrent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloadWorker extends AsyncTask<Void, Void, Drawable> {

    private static final String LOG_TAG = ImageDownloadWorker.class.getSimpleName();
    private final String url;
    private final Context context;
    private final ImageView viewToBind;

    public ImageDownloadWorker(final Context context, final String url, final ImageView viewToBind) {
        this.context = context;
        this.url = url;
        this.viewToBind = viewToBind;
    }

    @Override
    protected Drawable doInBackground(final Void... params) {
        try (InputStream inputStream = new URL(url).openStream()) {
            return Drawable.createFromStream(inputStream, null);
        } catch (IOException e) {
            Log.w(LOG_TAG, e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable result) {
        if (result == null) {
            Toast.makeText(context, "Could not download image", Toast.LENGTH_LONG).show();
        } else {
            viewToBind.setImageDrawable(result);
        }
    }
}
