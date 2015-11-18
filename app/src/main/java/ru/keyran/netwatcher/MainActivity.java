package ru.keyran.netwatcher;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.manager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        changeConnectionStatus();
        receiver = new NetworkStatusReceiver();
        receiverFilter = new IntentFilter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, receiverFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void changeConnectionStatus() {
        NetworkInfo net = this.manager.getActiveNetworkInfo();
        TextView connstat = (TextView) findViewById(R.id.connectionStatusText);
        if (net == null){
            connstat.setText(R.string.disconnected);
        }
        else connstat.setText(R.string.connected);
    }

    private ConnectivityManager manager;
    private NetworkStatusReceiver receiver;
    private IntentFilter receiverFilter;
}
