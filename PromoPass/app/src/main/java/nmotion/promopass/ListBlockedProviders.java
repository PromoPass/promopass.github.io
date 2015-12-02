package nmotion.promopass;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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

public class ListBlockedProviders extends AppCompatActivity {

    private Menu optionsMenu;
    private Toolbar toolbar;
    private int MENU_CLEAR = Menu.FIRST;
    private int MENU_CLOSE = Menu.FIRST + 1;
    private boolean isSelected;
    private ListView blockedProvidersView;
    private ArrayAdapter<Preference> blockedProviders;
    private int selectedPosition;
    private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_blocked_providers);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blockedProvidersView = (ListView) findViewById(R.id.blocked_list);
        blockedProviders = new ArrayAdapter<Preference>(this, android.R.layout.simple_list_item_1);
        blockedProvidersView.setAdapter(blockedProviders);

        if(InternetCheck.isNetworkConnected(this)){

            String consumerID = DeviceIdentifier.id(this);

            // get all favorite providers that are not blocked
            JSONArray blockedPreferences = Reader.getResults("http://fendatr.com/api/v1/preferences/consumer/"
                    + consumerID + "/blocked/businesses");

            try {
                JSONObject jsonTemp;

                for (int i = 0; i < blockedPreferences.length(); i++) {
                    jsonTemp = blockedPreferences.getJSONObject(i);

                    // get BusinessName & PreferenceID
                    Preference preference = new Preference(jsonTemp.getString("Name"), jsonTemp.getString("PreferenceID"));
                    blockedProviders.add(preference);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        blockedProvidersView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                            .setIcon(R.drawable.unblock)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    isSelected = true;
                }
                return true;
            }
        });

        blockedProvidersView.setEmptyView(findViewById(R.id.emptyBlocked));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(isSelected) {
                removeSelection();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(InternetCheck.isNetworkConnected(this)){

            Preference selectedPreference = blockedProviders.getItem(selectedPosition);
            switch (item.getItemId()) {
                case R.id.action_clear:
                    // set block = 0
                    Reader.update("http://fendatr.com/api/v1/preferences/" + selectedPreference.getPreferenceID() + "/unblock");
                    Toast.makeText(this, selectedPreference.toString() + getString(R.string.unblock_string),
                            Toast.LENGTH_LONG).show();
                    blockedProviders.remove(selectedPreference);
                    break;
            }

            removeSelection();

        }

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
