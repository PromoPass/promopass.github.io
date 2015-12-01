package nmotion.promopass;

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

public class ListFavoriteProviders extends AppCompatActivity {

    private Menu optionsMenu;
    private Toolbar toolbar;
    private int MENU_CLEAR = Menu.FIRST;
    private int MENU_CLOSE = Menu.FIRST + 1;
    private boolean isSelected;
    private ListView favoriteProvidersView;
    private ArrayAdapter<Preference> favoriteProviders;
    private int selectedPosition;
    private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favorite_providers);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        favoriteProvidersView = (ListView) findViewById(R.id.favorites_list);
        favoriteProviders = new ArrayAdapter<Preference>(this, android.R.layout.simple_list_item_1);
        favoriteProvidersView.setAdapter(favoriteProviders);

        // get all saved ads for consumer listed by received date

        String consumerID = DeviceIdentifier.id(this);
        // get all favorite providers that are not blocked
        //JSONArray savedReceivedAds = Reader.getResults("http://fendatr.com/api/v1/received/ad/" + consumerID + "/saved");
/*
        try {
            JSONObject jsonTemp;

            for (int i = 0; i < savedReceivedAds.length(); i++) {
                jsonTemp = savedReceivedAds.getJSONObject(i);

                // get BusinessName & PreferenceID
                Preference preference = new Preference("", "");
                favoriteProviders.add(preference);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        favoriteProvidersView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                            .setIcon(R.drawable.unfavorite)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    isSelected = true;
                }
                return true;
            }
        });

        favoriteProvidersView.setEmptyView(findViewById(R.id.emptyFavorites));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Preference selectedAd = favoriteProviders.getItem(selectedPosition);
        switch (item.getItemId()) {
            case R.id.action_clear:
                // set favorite = 0
                //Reader.update("http://fendatr.com/api/v1/received/ad/" + selectedAd.getReceivedAdID() + "/clear");
                Toast.makeText(this, selectedAd.toString() + getString(R.string.unfavorite_string),
                        Toast.LENGTH_LONG).show();
                favoriteProviders.remove(selectedAd);
                break;
        }

        removeSelection();

        return false;
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
