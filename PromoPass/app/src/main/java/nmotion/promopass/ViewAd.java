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
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        test = (TextView) findViewById(R.id.textView);
        test.setText(getIntent().getStringExtra("AdID"));

        // get Ad from AdID

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
                // set Received Ad as cleared
                Toast.makeText(this, test.getText() + ": This ad has been cleared.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ListNearbyProviders.class));
                break;
        }

        return false;
    }

}
