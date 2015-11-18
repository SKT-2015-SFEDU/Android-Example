package ru.keyran.netwatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkStatusReceiver extends BroadcastReceiver {
    public NetworkStatusReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(context.getString(R.string.logTag),context.getString(R.string.logchanged));
    }
}
