package nmotion.promopass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListNearbyProviders extends AppCompatActivity {

    private Menu optionsMenu;
    private Toolbar toolbar;
    private int MENU_CLEAR = Menu.FIRST;
    private int MENU_CLOSE = Menu.FIRST + 1;
    private boolean isSelected;

    private ListView nearbyProvidersView;
    private ArrayAdapter<ReceivedAd> nearbyProviders;
    //private ArrayAdapter<String> nearbyProviders;
    private int selectedPosition;
    private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nearby_providers);
        setTitle("");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nearbyProvidersView = (ListView) findViewById(R.id.nearbyProviders_list);
        nearbyProviders = new ArrayAdapter<ReceivedAd>(this, android.R.layout.simple_list_item_1);
        nearbyProvidersView.setAdapter(nearbyProviders);

        String consumerID = DeviceIdentifier.id(this); //7 for this emulator, 7 isnt seen
        consumerID="22";
        JSONArray receivedAds = Reader.getResults("http://fendatr.com/api/v1/consumer/" + consumerID + "/received");

        try {
            JSONObject jsonTemp;

            for (int i = 0; i < receivedAds.length(); i++) {
                jsonTemp = receivedAds.getJSONObject(i);
                String businessID = jsonTemp.getString("BusinessID");

                JSONArray allArrays_JSONARRAY = Reader.getResults("http://fendatr.com/api/v1/business/" + businessID + "/name");

                JSONObject jsonTemp2 = allArrays_JSONARRAY.getJSONObject(0);

                ReceivedAd receivedAd = new ReceivedAd(jsonTemp.getString("ReceivedAdID"),
                        jsonTemp.getString("AdID"),
                        businessID,
                        jsonTemp2.getString("Name"));
                nearbyProviders.add(receivedAd);



                seeReceivedAd(receivedAd.getReceivedAdID());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nearbyProvidersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeSelection();
                Intent intent = new Intent(view.getContext(), ViewAd.class);

                intent.putExtra("ReceivedAdID", nearbyProviders.getItem(position).getReceivedAdID());
                intent.putExtra("AdID", nearbyProviders.getItem(position).getAdID());
                intent.putExtra("BusinessID", nearbyProviders.getItem(position).getBusinessID());
                intent.putExtra("BusinessName", nearbyProviders.getItem(position).toString());

                startActivity(intent);
            }
        });

        nearbyProvidersView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedView != null)
                    selectedView.setBackgroundColor(ContextCompat.getColor(selectedView.getContext(), android.R.color.transparent));
                selectedPosition = position;
                selectedView = view;
                view.setBackgroundColor(Color.GRAY);

                if (!isSelected) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(toolbar.getContext(), R.color.colorPrimarySelected));
                    optionsMenu.add(0, R.id.action_close, MENU_CLOSE, R.string.action_close)
                            .setIcon(R.drawable.close)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    optionsMenu.add(0, R.id.action_clear, MENU_CLEAR, R.string.action_clear)
                            .setIcon(R.drawable.clear)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    isSelected = true;
                }
                return true;
            }
        });

        nearbyProvidersView.setEmptyView(findViewById(R.id.empty));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_clear:
                ReceivedAd selectedAd = nearbyProviders.getItem(selectedPosition);

                Reader.update("http://fendatr.com/api/v1/received/ad/" + selectedAd.getReceivedAdID() + "/clear");
                nearbyProviders.remove(selectedAd);

                Toast.makeText(this, selectedAd.toString() + ": This ad has been deleted.",
                        Toast.LENGTH_LONG).show();
                break;
        }

        removeSelection();

        return false;
    }

    private void seeReceivedAd(String receivedAdID){
        Reader.update("http://fendatr.com/api/v1/received/ad/"+receivedAdID+"/see");
    }

    private void removeSelection(){


        toolbar.setBackgroundColor(ContextCompat.getColor(toolbar.getContext(), R.color.colorPrimary));
        optionsMenu.removeItem(R.id.action_close);
        optionsMenu.removeItem(R.id.action_clear);

        if(selectedView != null)
            selectedView.setBackgroundColor(ContextCompat.getColor(selectedView.getContext(), android.R.color.transparent));
        selectedPosition = -1;
        isSelected=false;
    }


}
