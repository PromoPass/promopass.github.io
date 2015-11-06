package nmotion.promopass;

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

public class ListNearbyProviders extends AppCompatActivity {

    private Menu optionsMenu;
    private Toolbar toolbar;
    private int MENU_CLEAR = Menu.FIRST;
    private int MENU_CLOSE = Menu.FIRST + 1;
    private boolean isSelected;

    private ListView nearbyProvidersView;
    private ArrayAdapter<String> nearbyProviders;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nearby_providers);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nearbyProvidersView = (ListView) findViewById(R.id.nearbyProviders_list);
        nearbyProviders = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        nearbyProvidersView.setAdapter(nearbyProviders);

        nearbyProviders.add("Hello");
        nearbyProviders.add("Goodbye");

        nearbyProvidersView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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
                return false;
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
                Toast.makeText(this, "Clear", Toast.LENGTH_LONG).show();
                break;
        }

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        optionsMenu.removeItem(R.id.action_close);
        optionsMenu.removeItem(R.id.action_clear);
        isSelected=false;

        return false;
    }


}
