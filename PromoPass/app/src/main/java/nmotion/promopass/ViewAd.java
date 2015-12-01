package nmotion.promopass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewAd extends AppCompatActivity {
    private int MENU_SAVE = Menu.FIRST;
    private int MENU_FAVORITE = Menu.FIRST + 1;
    private int MENU_BLOCK = Menu.FIRST + 2;
    private int MENU_CLEAR = Menu.FIRST + 3;

    private String AdID;
    private String ReceivedAdID;
    private String BusinessID;
    private String BusinessName;
    private String ConsumerID;
    private String Description;
    private String Title;
    private Bitmap Pic;
    private Notification notification;
    private boolean isSavedAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdID = getIntent().getStringExtra("AdID");
        ReceivedAdID = getIntent().getStringExtra("ReceivedAdID");  // used for save and clear ads
        BusinessID = getIntent().getStringExtra("BusinessID");      // used for favorite and block providers
        BusinessName = getIntent().getStringExtra("BusinessName");
        ConsumerID = getIntent().getStringExtra("ConsumerID");      // used for favorite and block providers
        if (getIntent().hasExtra("IsSavedAd"))
            isSavedAd = true;



        //AdID = "23";
        JSONArray jsonArray = Reader.getResults("http://fendatr.com/api/v1/ad/" + AdID);

        JSONObject jsonTemp;
        try {
            jsonTemp = jsonArray.getJSONObject(0);
            Description = jsonTemp.getString("Writing");
            String temp_title = jsonTemp.getString("Title");
            TextView businessTitle_txt = (TextView) findViewById(R.id.title);

            String temp_picURL = "http://fendatr.com/ULf1A14.jpg";
            ImageView picURL = (ImageView) findViewById(R.id.pic);
            ImageStreamer st = new ImageStreamer(picURL);
            st.execute(temp_picURL);


           if(temp_title =="") {
                businessTitle_txt.setVisibility(View.INVISIBLE);
            }
            else {
                Title = jsonTemp.getString("Title");
                businessTitle_txt.setText(Title);
            }
        } catch (JSONException e) {

        }




        TextView businessName_txt = (TextView) findViewById(R.id.businessName);
        businessName_txt.setText(BusinessName);

        TextView description_txt = (TextView) findViewById(R.id.description);
        description_txt.setText(Description);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isSavedAd)
        {
            menu.add(0, R.id.action_clear, Menu.FIRST, R.string.action_clear)
                    .setIcon(R.drawable.clear)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        else
        {
            menu.add(0, R.id.action_favorite, MENU_FAVORITE, R.string.action_favorite)
                    .setIcon(R.drawable.favorite)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, R.id.action_block, MENU_BLOCK, R.string.action_block)
                    .setIcon(R.drawable.block)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, R.id.action_save, MENU_SAVE, R.string.action_save)
                    .setIcon(R.drawable.save)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, R.id.action_clear, MENU_CLEAR, R.string.action_clear)
                    .setIcon(R.drawable.clear)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_favorite:
                Reader.update("http://fendatr.com/api/v1/preferences/consumer/" + ConsumerID + "/business/" + BusinessID +  "/favorite");
                Toast.makeText(this, BusinessName + getString(R.string.favorite_string), Toast.LENGTH_LONG).show();
                break;
            case R.id.action_block:
                Reader.update("http://fendatr.com/api/v1/received/ad/" + ReceivedAdID + "/clear");
                Reader.update("http://fendatr.com/api/v1/preferences/consumer/" + ConsumerID + "/business/" + BusinessID + "/block");
                Toast.makeText(this, BusinessName + getString(R.string.block_string), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ListNearbyProviders.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.action_save:
                Reader.update("http://fendatr.com/api/v1/received/ad/" + ReceivedAdID + "/save");
                Toast.makeText(this, Title + getString(R.string.saved_string), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ListNearbyProviders.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.action_clear:
                Reader.update("http://fendatr.com/api/v1/received/ad/" + ReceivedAdID + "/clear");
                Toast.makeText(this, Title + getString(R.string.clear_string),
                        Toast.LENGTH_LONG).show();
                if(isSavedAd)
                {
                    startActivity(new Intent(this, ListSavedAds.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                else
                {
                    startActivity(new Intent(this, ListNearbyProviders.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                break;
        }

        return false;
    }

}
