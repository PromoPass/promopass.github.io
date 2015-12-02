package nmotion.promopass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    private ArrayAdapter<String> mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.Main_Menu));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(InternetCheck.isNetworkConnected(this)){

            DeviceIdentifier.id(this);

            mainListView = (ListView) findViewById(R.id.main_list);
            mainList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            mainListView.setAdapter(mainList);

            mainList.add(getString(R.string.Nearby_Businesses));
            mainList.add(getString(R.string.Saved_Ads));
            mainList.add(getString(R.string.Favorite_Businesses));
            mainList.add(getString(R.string.Blocked_Businesses));


            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = null;
                    switch (position){
                        case 0:
                            intent = new Intent(view.getContext(), ListNearbyProviders.class);
                            break;
                        case 1:
                            intent = new Intent(view.getContext(), ListSavedAds.class);
                            break;
                        case 2:
                            intent = new Intent(view.getContext(), ListFavoriteProviders.class);
                            break;
                        case 3:
                            intent = new Intent(view.getContext(), ListBlockedProviders.class);
                            break;
                    }
                    startActivity(intent);
                }
            });
        }

    }

}
