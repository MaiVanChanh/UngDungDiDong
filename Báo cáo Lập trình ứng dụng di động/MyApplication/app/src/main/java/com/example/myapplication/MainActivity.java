package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.data.database;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    TextView textYear, textMonthPre, textMonth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textYear = (TextView) findViewById(R.id.textView_namNay);
        textMonthPre = (TextView) findViewById(R.id.textView_thangTruoc);
        textMonth = (TextView) findViewById(R.id.textView_thangNay);

        database db = new database(getApplicationContext(), "database.db", null, 1);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        float sumYear = db.getTransactionYear();
        textYear.setText(format.format(sumYear) + "");
        float sumMonthPre = db.getTransactionMonthPre();
        textMonthPre.setText(format.format(sumMonthPre) + "");
        float sumMonth = db.getTransactionMonth();
        textMonth.setText(format.format(sumMonth) + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        database db = new database(getApplicationContext(), "database.db", null, 1);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        float sumYear = db.getTransactionYear();
        textYear.setText(format.format(sumYear) + "");
        float sumMonthPre = db.getTransactionMonthPre();
        textMonthPre.setText(format.format(sumMonthPre) + "");
        float sumMonth = db.getTransactionMonth();
        textMonth.setText(format.format(sumMonth) + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_main:
                Intent intent = new Intent(this, AddTransactionActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit_main:
                finish();
                return true;
            case R.id.stats_main:
                intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                return true;
            case R.id.history_main:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return true;
        }
        return true;
    }
}