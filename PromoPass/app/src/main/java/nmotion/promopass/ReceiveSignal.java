package nmotion.promopass;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

/**
 * Created by Anna on 11/11/2015.
 */
public class ReceiveSignal extends Service {

    private BeaconManager beaconManager;
    private BeaconEventListener beaconEventListener;
    private Notification notification = null;

    @Override
    public IBinder onBind(Intent intent) {
        // We don't want to bind; return null.
        return null;
    }

    @Override
    public void onCreate() {

        Gimbal.setApiKey(this.getApplication(), "0be38462-57de-40d9-8f24-451f4d4c2b5c");

        final Context self = this;

        beaconEventListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);
                String factoryID = beaconSighting.getBeacon().getIdentifier();
                if(InternetCheck.isNetworkConnectedForService(self)){
                    if(notification == null){
                        notification = new Notification(self);
                    }
                    notification.addBusiness(factoryID);
                }
            }
        };

        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconEventListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Register the listener here.
        beaconManager.startListening();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        beaconManager.stopListening();
    }
}