package nmotion.promopass;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anna on 11/11/2015.
 */
public class Notification {

    private Context context;
    private int count;
    private String firstBusinessName;
    private NotificationCompat.InboxStyle inboxStyle;

    public Notification(Context context){
        this.context = context;
    }

    public void addBusiness(String gimbalID){

        String businessID = getBusinessID(gimbalID);
        if(businessID == null) return;    // Gimbal device is not registered with PromoPass

        String adID = getAdID(businessID);
        if(adID == null) return;    // Business has not created an ad yet

        String consumerID = DeviceIdentifier.id(context);

        if(receivedAdExists(adID, consumerID)) return;

        if(isBlocked(businessID, consumerID)) return;

        Reader.insert("http://fendatr.com/api/v1/received/ad",
                "{\"AdID\" : \"" + adID + "\", \"ConsumerID\" : \"" + consumerID + "\", \"BusinessID\" : \"" + businessID + "\"}");

        getInboxStyle(consumerID);

        create();


    }

    private void getInboxStyle(String consumerID){

        JSONArray results = Reader.getResults("http://fendatr.com/api/v1/received/ad/" + consumerID + "/unseen");

        inboxStyle = new NotificationCompat.InboxStyle();
        count = 0;

        for(int i=0; i<results.length(); i++){
            String businessName = getBusinessName(results, i);
            if(count == 0) firstBusinessName = businessName;

            inboxStyle.addLine(businessName);
            count++;
        }

        inboxStyle.setBigContentTitle(count + " businesses are nearby.");
    }

    private boolean isBlocked(String businessID, String consumerID){

        String block = null;
        JSONObject json;

        try {

            JSONArray results = Reader.getResults("http://fendatr.com/api/v1/preferences/consumer/"+consumerID
                    +"/business/"+businessID+"/check/block");
            json = results.getJSONObject(0);
            block = json.getString("IsBlocked");
            if(block.equals("1"))
                return true;
        } catch (JSONException e) { }


        return false;
    }

    private String getBusinessName(JSONArray businessIDs, int index){
        String businessName = null;
        JSONObject json;

        try {
            json = businessIDs.getJSONObject(index);
            String businessID = json.getString("BusinessID");

            JSONArray results = Reader.getResults("http://fendatr.com/api/v1/business/" + businessID + "/name");
            json = results.getJSONObject(0);
            businessName = json.getString("Name");
        } catch (JSONException e) { }

        return businessName;

    }

    private String getBusinessID(String gimbalID){

        String businessID = null;

        JSONArray results = Reader.getResults("http://fendatr.com/api/v1/business/" + gimbalID + "/getBusinessID");

        if(results.length() > 0) {
            try {
                JSONObject json = results.getJSONObject(0);
                businessID = json.getString("BusinessID");
            } catch (JSONException e) { }
        }

        return businessID;
    }

    private String getAdID(String businessID){

        String adID = null;

        JSONArray results = Reader.getResults("http://fendatr.com/api/v1/business/"+businessID+"/current-ad/id");

        if(results.length() > 0) {
            try {
                JSONObject json = results.getJSONObject(0);
                adID = json.getString("AdID");
            } catch (JSONException e) { }
        }

        return adID;
    }

    private boolean receivedAdExists(String adID, String consumerID){

        JSONArray results = Reader.getResults("http://fendatr.com/api/v1/received/ad/"+adID+"/"+consumerID+"/getReceivedAd");

        if(results.length() > 0) return true;      // Received Ad already exists
        return false;
    }

    private void create(){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(count == 1 ? firstBusinessName + " is nearby." : count + " businesses are nearby.")
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
