package nmotion.promopass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoriteProvider extends AppCompatActivity {
    private ListView nearbyProvidersView;
    private ArrayAdapter<ReceivedAd> nearbyProviders;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_provider);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nearbyProvidersView = (ListView) findViewById(R.id.nearbyProviders_list);
        nearbyProviders = new ArrayAdapter<ReceivedAd>(this, android.R.layout.simple_list_item_1);
        nearbyProvidersView.setAdapter(nearbyProviders);



    }


}
