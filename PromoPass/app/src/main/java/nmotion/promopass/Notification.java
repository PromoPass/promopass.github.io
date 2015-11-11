package nmotion.promopass;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Anna on 11/11/2015.
 */
public class Notification {

    private Context context;
    private int count;
    private String firstBusinessName;
    private NotificationCompat.InboxStyle inboxStyle;

    public Notification(Context context, String businessName){
        this.context = context;
        count = 1;
        firstBusinessName = businessName;

        inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(businessName);

        create();
    }

    public void addBusiness(String businessName){
        count++;

        inboxStyle.setBigContentTitle(count + " businesses are nearby.");
        inboxStyle.addLine(businessName);

        create();
    }

    private void create(){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(firstBusinessName + " is nearby.")
                .setContentText("PromoPass")
                .setSmallIcon(R.drawable.small_icon)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(context, ListNearbyProviders.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(ListNearbyProviders.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        if(count > 1)
            mBuilder.setStyle(inboxStyle);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }

}
