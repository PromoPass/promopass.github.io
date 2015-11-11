package nmotion.promopass;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;


public class MainActivity extends ActionBarActivity {

    private BeaconManager beaconManager;
    private BeaconEventListener beaconEventListener;
    private ArrayAdapter<String> listAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);

        listAdapter.add("Setting Gimbal API Key");
        listAdapter.notifyDataSetChanged();
        Gimbal.setApiKey(this.getApplication(), "a70b9646-2c65-4354-ab10-dd52be07a572");

        beaconEventListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);
                String factoryID = beaconSighting.getBeacon().getIdentifier();
                listAdapter.add(String.format("Start Visit for %s", factoryID));
                listAdapter.notifyDataSetChanged();
            }
        };

        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconEventListener);
        beaconManager.startListening();

        //CommunicationManager.getInstance().startReceivingCommunications();
    }

}
