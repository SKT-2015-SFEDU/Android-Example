package ru.keyran.netwatcher;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class WatchService extends Service {
    public WatchService() {
     }

    public void writeToLog(String s){
        bdlog.append(s);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new NetworkStatusReceiver();
        receiverFilter = new IntentFilter();
        receiverFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        bdlog = new BDLog(this);
        registerReceiver(receiver, receiverFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private NetworkStatusReceiver receiver;
    private IntentFilter receiverFilter;
    BDLog bdlog;

    public class NetworkStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(context.getString(R.string.logTag), context.getString(R.string.logchanged));
            Bundle bundle = intent.getExtras();
            String KeyNI=getString(R.string.network_info);
            NetworkInfo bundleNetworkInfo=(android.net.NetworkInfo)bundle.get(KeyNI);
            if(bundleNetworkInfo!=null)
                WatchService.this.writeToLog(bundleNetworkInfo.getState().toString());
            else WatchService.this.writeToLog(getString(R.string.no_connection));
        }

    }


}
