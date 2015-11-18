package ru.keyran.netwatcher;

import android.content.Context;
import android.content.Intent;
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
        watchService = new WatchService();
        startService(new Intent(this, watchService.getClass()));
        changeConnectionStatus();

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
    private WatchService watchService;
}
