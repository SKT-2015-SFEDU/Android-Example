package ru.keyran.netwatcher;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

    private void sendNotification(String s){
        Intent intent =new Intent (this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(s)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .build();
        nm.notify(0, notification);
        }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new NetworkStatusReceiver();
        receiverFilter = new IntentFilter();
        receiverFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        bdlog = new BDLog(this);
        registerReceiver(receiver, receiverFilter);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
    NotificationManager nm;

    public class NetworkStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(context.getString(R.string.logTag), context.getString(R.string.logchanged));
            Bundle bundle = intent.getExtras();
            String KeyNI=getString(R.string.network_info);
            NetworkInfo bundleNetworkInfo=(android.net.NetworkInfo)bundle.get(KeyNI);
            if(bundleNetworkInfo!=null) {
                WatchService.this.writeToLog(bundleNetworkInfo.getState().toString());
                sendNotification(bundleNetworkInfo.getState().toString());
            }
            else {
                    WatchService.this.writeToLog(getString(R.string.no_connection));
                    sendNotification(getString(R.string.no_connection));
                }
        }

    }


}
