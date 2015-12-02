package nmotion.promopass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Anna on 12/1/2015.
 */
public class InternetCheck {

    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        boolean connected = activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;

        if(!connected) {
            context.startActivity(new Intent(context, NoInternet.class));
            ((Activity) context).finish();
        }

        return connected;
    }

    public static boolean isNetworkConnectedForService(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
    }

}
