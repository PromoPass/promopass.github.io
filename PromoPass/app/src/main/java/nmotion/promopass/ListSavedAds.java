package nmotion.promopass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListSavedAds extends AppCompatActivity {

    private ListView savedAdsView;
    private ArrayAdapter<ReceivedAd> savedAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_saved_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        savedAdsView = (ListView) findViewById(R.id.savedAds_list);
        savedAds = new ArrayAdapter<ReceivedAd>(this, android.R.layout.simple_list_item_1);
        savedAdsView.setAdapter(savedAds);

        // get all saved ads for consumer listed by received date

    }

}
