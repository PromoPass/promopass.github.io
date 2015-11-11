package nmotion.promopass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAd extends AppCompatActivity {

    private int MENU_CLEAR = Menu.FIRST;
    private String AdID;
    private String ReceivedAdID;
    private String BusinessID;
    private String BusinessName;

    private Notification notification;

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

        TextView businessName_txt = (TextView) findViewById(R.id.businessName);
        businessName_txt.setText(BusinessName);

        // get Ad Information by AdID
        // set Title and Writing

        Intent intent = new Intent(this, ReceiveSignal.class);
        startService(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, R.id.action_clear, MENU_CLEAR, R.string.action_clear)
                .setIcon(R.drawable.clear)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_clear:
                Reader.update("http://fendatr.com/api/v1/ad/" + ReceivedAdID + "/clear");
                Toast.makeText(this, BusinessName + ": This ad has been deleted.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ListNearbyProviders.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }

        return false;
    }

}
