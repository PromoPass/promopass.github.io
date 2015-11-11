package nmotion.promopass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Anna on 11/11/2015.
 */
public class Receiver extends BroadcastReceiver {

    public Receiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent receiveSignal = new Intent(context, ReceiveSignal.class);
        context.startService(receiveSignal);
    }
}