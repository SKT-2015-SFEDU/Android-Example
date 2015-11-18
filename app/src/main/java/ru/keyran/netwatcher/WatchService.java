package ru.keyran.netwatcher;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class WatchService extends Service {
    public WatchService() {
        receiver = new NetworkStatusReceiver();
        receiverFilter = new IntentFilter();

    }

    @Override
    public void onCreate() {
        super.onCreate();
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
}
