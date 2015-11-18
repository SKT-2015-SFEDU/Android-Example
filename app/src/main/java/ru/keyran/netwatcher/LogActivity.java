package ru.keyran.netwatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Vector;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        receiver = new DbChangeReceiver();
        filter = new IntentFilter();
        filter.addAction(getString(R.string.DB_ITEM_ADDED));

        ListView list = (ListView) findViewById(R.id.logEntriesList);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        list.setAdapter(adapter);
        log = new BDLog(this);
        updateEntries();
    }

    private void updateEntries(){
        Vector<Pair<String, String>> v = log.get(30);
        adapter.clear();
        for (Pair<String, String> elem: v) {
            adapter.add(elem.first + " " +elem.second);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,filter);
        updateEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void itemAdded(String s){
        adapter.insert(s, 0);
    }

    ArrayAdapter<String> adapter;
    BDLog log;
    DbChangeReceiver receiver;
    IntentFilter filter;

    public class DbChangeReceiver extends BroadcastReceiver {
        public DbChangeReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra("date")+ " " + intent.getStringExtra("message");
            LogActivity.this.itemAdded(s);
        }
    }
}
