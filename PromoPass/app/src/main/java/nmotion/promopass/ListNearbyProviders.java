package nmotion.promopass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

        nearbyProviders.add("Hello");
        nearbyProviders.add("Goodbye");
        nearbyProviders.add("Hello");

        String result = "";
        try {
            //The JSON Object holds a JSON Array that holds many JSon objects.
            // So we need to first get the Json Array and then get each json object individually.
            JSONObject json = new JSONObject("{\"Provider\": [ { \"ProviderID\": \"324392432nkfde\", \"FirstName\": \"Jeffrey\", \"LastName\": \"Olsen\", \"Email\": \"jolsen342@gmail.com\" }, { \"ProviderID\": \"o23jkhr23234\", \"FirstName\": \"Jessica\", \"LastName\": \"Covington\", \"Email\": \"sanspei@gmail.com\" }, { \"ProviderID\": \"YIk_6QHvRUKGxXTbSOu7pA\", \"FirstName\": \"Fenda\", \"LastName\": \"Troung\", \"Email\": \"fenda.tr@gmail.com\" } ]}");
            String allArrays = json.getString("Provider");

            JSONArray allArrays_JSONARRAY = new JSONArray(allArrays);
             
            JSONObject jsonTemp = null;
            String name = "";
            //loop through the Json array
            for( int i=0; i<allArrays_JSONARRAY.length();i++) {
                jsonTemp = allArrays_JSONARRAY.getJSONObject(i);
                name = jsonTemp.getString("FirstName");
                nearbyProviders.add(name);

            }


        } catch (JSONException e) {
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
