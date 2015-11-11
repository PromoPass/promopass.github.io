package nmotion.promopass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class ListNearbyProviders extends AppCompatActivity {

    private Menu optionsMenu;
    private Toolbar toolbar;
    private int MENU_CLEAR = Menu.FIRST;
    private int MENU_CLOSE = Menu.FIRST + 1;
    private boolean isSelected;

    private ListView nearbyProvidersView;
    private ArrayAdapter<String> nearbyProviders;
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
        nearbyProviders = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        nearbyProvidersView.setAdapter(nearbyProviders);

        DeviceIdentifier di = new DeviceIdentifier();
        String consumerID = di.id(this);

        DatabaseReader dr1 = new DatabaseReader();
        dr1.execute("http://fendatr.com/api/v1/ad/" + consumerID + "/received");

        JSONArray receivedAds;
        try {

            receivedAds = dr1.get();

            JSONObject jsonTemp;
            for( int i=0; i<receivedAds.length();i++) {
                jsonTemp = receivedAds.getJSONObject(i);
                String businessID = jsonTemp.getString("BusinessID");

                DatabaseReader dr2 = new DatabaseReader();
                dr2.execute("http://fendatr.com/api/v1/business/" + businessID + "/name");

                JSONArray allArrays_JSONARRAY = dr2.get();

                JSONObject jsonTemp2 = allArrays_JSONARRAY.getJSONObject(i);
                String name = jsonTemp2.getString("Name");
                nearbyProviders.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        nearbyProvidersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeSelection();
                Intent intent = new Intent(view.getContext(), ViewAd.class);
                intent.putExtra("AdID", nearbyProviders.getItem(position));
                startActivity(intent);
            }
        });

        nearbyProvidersView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedView != null)
                    selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                selectedPosition = position;
                selectedView = view;
                view.setBackgroundColor(Color.GRAY);

                if(!isSelected) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimarySelected));
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_clear:
                Toast.makeText(this, "Clear "+nearbyProviders.getItem(selectedPosition), Toast.LENGTH_SHORT).show();
                nearbyProviders.remove(nearbyProviders.getItem(selectedPosition));
                break;
        }

        removeSelection();

        return false;
    }

    private void removeSelection(){

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        optionsMenu.removeItem(R.id.action_close);
        optionsMenu.removeItem(R.id.action_clear);

        if(selectedView != null)
            selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        selectedPosition = -1;
        isSelected=false;
    }


}
