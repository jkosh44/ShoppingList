package hu.ait.android.shoppinglist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by joe on 11/12/15.
 */
public class LowBatteryReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean batteryState = intent.getBooleanExtra("state", false);
        Toast.makeText(context, context.getString(R.string.low_battery_message), Toast.LENGTH_LONG).show();
    }
}
