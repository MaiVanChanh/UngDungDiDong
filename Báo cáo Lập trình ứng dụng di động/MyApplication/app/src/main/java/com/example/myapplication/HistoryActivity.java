package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.data.AdapterTransaction;
import com.example.myapplication.data.Transaction;
import com.example.myapplication.data.database;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryActivity: ";
    ListView listViewHistory;
    AdapterTransaction adapterTransaction;
    List<Transaction> transactions;
    database db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listViewHistory = (ListView) findViewById(R.id.listView_history);
        db = new database(getApplicationContext(), "database.db", null, 1);
        transactions = db.getHistory();
        adapterTransaction = new AdapterTransaction(getApplicationContext(), R.layout.layout_listview_danhsachgiaodich, transactions);
        listViewHistory.setAdapter(adapterTransaction);

        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(com.example.myapplication.HistoryActivity.this, com.example.myapplication.DetailTransactionActivity.class);
                intent.putExtra("id", transactions.get(i).getId());
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        super.onRestart();
        db = new database(getApplicationContext(), "database.db", null, 1);
        transactions = db.getHistory();
        adapterTransaction = new AdapterTransaction(getApplicationContext(), R.layout.layout_listview_danhsachgiaodich, transactions);
        listViewHistory.setAdapter(adapterTransaction);
        Log.d(TAG, "onRestart: end");
    }
    /* tạo Option quay lại*/
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), com.example.myapplication.MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
