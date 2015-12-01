package nmotion.promopass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Anna on 11/30/2015.
 */
public class PromoPassAdapter extends ArrayAdapter<ReceivedAd> {

    private Context context;

    public PromoPassAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = convertView;

            TextView text1 = (TextView) twoLineListItem.findViewById(android.R.id.text1);
            TextView text2 = (TextView) twoLineListItem.findViewById(android.R.id.text2);

            text2.setText(this.getItem(position).toString());
            text1.setText(this.getItem(position).getTitle());
        }

        return twoLineListItem;
    }
}
