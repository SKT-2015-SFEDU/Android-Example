package ru.keyran.netwatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.manager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        watchService = new WatchService();
        startService(new Intent(this, watchService.getClass()));
        receiver = new FromNetworkStatusReceiver();
        filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        changeConnectionStatus();

    }

    @Override
    protected void onResume() {
        super.onResume();
        changeConnectionStatus();
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);

    }

    public void openBrowser(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://google.com"));
        startActivity(intent);
    }

    private void changeConnectionStatus() {
        NetworkInfo net = this.manager.getActiveNetworkInfo();
        TextView connstat = (TextView) findViewById(R.id.connectionStatusText);
        Button browser = (Button) findViewById(R.id.browser_button);
        if (net == null){
            connstat.setText(R.string.disconnected);
            browser.setVisibility(View.INVISIBLE);
        }
        else{
            connstat.setText(R.string.connected);
            browser.setVisibility(View.VISIBLE);
        }
    }

    private ConnectivityManager manager;
    private WatchService watchService;
    private FromNetworkStatusReceiver receiver;
    private IntentFilter filter;


    public class FromNetworkStatusReceiver extends BroadcastReceiver {
        public FromNetworkStatusReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.this.changeConnectionStatus();
        }
    }

}

