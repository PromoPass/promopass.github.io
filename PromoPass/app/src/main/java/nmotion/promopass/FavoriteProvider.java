package nmotion.promopass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteProvider extends AppCompatActivity {
    private ListView ProviderListView;
    private ArrayAdapter<String> ProviderList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_provider);


        ProviderListView = (ListView) findViewById(R.id.favoriteProvider);
        ProviderList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ProviderListView.setAdapter(ProviderList);

        String consumerID = DeviceIdentifier.id(this); //7 for this emulator, 7 isnt seen
        consumerID="22"; //Hard coded for the emulator

        JSONArray providerArray = Reader.getResults("http://fendatr.com/api/v1/consumer/" + consumerID + "/received"); //change this to access the preferences and not received Ad

        JSONObject jsonTemp;

        try {
            for (int i = 0; i < providerArray.length(); i++) {
                jsonTemp = providerArray.getJSONObject(i);
                String businessID = jsonTemp.getString("BusinessID");

                JSONArray allArrays_JSONARRAY = Reader.getResults("http://fendatr.com/api/v1/business/" + businessID + "/name");

                JSONObject jsonTemp2 = allArrays_JSONARRAY.getJSONObject(0);
                /*
                ReceivedAd favoriteProvider = new ReceivedAd(jsonTemp.getString("ReceivedAdID"),
                        jsonTemp.getString("AdID"),
                        businessID,
                        jsonTemp2.getString("Name"),
                        consumerID);
                 */

                ProviderList.add(providerArray.getString(i));



               // seeReceivedAd(receivedAd.getReceivedAdID());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
