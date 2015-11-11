package nmotion.promopass;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

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

    @Override
    public IBinder onBind(Intent intent) {
        // We don't want to bind; return null.
        return null;
    }

    @Override
    public void onCreate() {

        Gimbal.setApiKey(this.getApplication(), "9c5082b4-ebd6-48bc-86f3-78fcf178c9d1");

        final Context self = this;

        beaconEventListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);
                String factoryID = beaconSighting.getBeacon().getIdentifier();
                Toast.makeText(self, factoryID, Toast.LENGTH_SHORT).show();
            }
        };

        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconEventListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting", Toast.LENGTH_SHORT).show();
        //Register the listener here.
        beaconManager.startListening();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        beaconManager.stopListening();
    }
}