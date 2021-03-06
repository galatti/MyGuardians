package com.example.galatti.myguardians;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Rodrigo Galatti on 09/03/2015.
 */
class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference imageViewReference;

    private static MemoryCache cache = new MemoryCache();

    public ImageDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference(imageView);
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        return downloadBitmap(params[0]);
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = (ImageView) imageViewReference.get();
            if (imageView != null) {

                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageDrawable(imageView.getContext().getResources()
                            .getDrawable(R.drawable.list_placeholder));
                }
            }

        }
    }

    static Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;

        Log.w("ImageDownloader", "Target URL " + url);

        if(url != null) {
            bitmap = cache.get(url);

            if(bitmap == null) {
                final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
                final HttpGet getRequest = new HttpGet(url);
                try {
                    HttpResponse response = client.execute(getRequest);
                    final int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != HttpStatus.SC_OK) {
                        Log.w("ImageDownloader", "Error " + statusCode
                                + " while retrieving bitmap from " + url);
                        return null;
                    }

                    final HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        InputStream inputStream = null;
                        try {
                            inputStream = entity.getContent();
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            cache.put(url, bitmap);
                            Log.w("ImageDownloader", "Buffered: " + url);
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            entity.consumeContent();
                        }
                    }
                } catch (Exception e) {
                    // Could provide a more explicit error message for IOException or
                    // IllegalStateException
                    getRequest.abort();
                    Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
                } finally {
                    if (client != null) {
                        client.close();
                    }
                }
            } else {
                Log.w("ImageDownloader", "Retrieved from buffer: " + url);
            }
        }
        return bitmap;
    }



}