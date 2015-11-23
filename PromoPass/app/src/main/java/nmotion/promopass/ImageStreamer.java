package nmotion.promopass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jessica on 11/19/2015.
 */
public class ImageStreamer extends AsyncTask< String, Void, Bitmap> {
    ImageView Image;
    URL URL;

    public ImageStreamer(ImageView Image) {
        this.Image= Image;
    }
    @Override
    protected Bitmap doInBackground(String... link) {
        Bitmap icon = null;
        try {
            URL = new URL(link[0]);
            InputStream in = new URL(link[0]).openStream();
            icon = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        Image.setImageBitmap(result);
    }
}
