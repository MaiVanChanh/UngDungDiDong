package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Transaction;
import com.example.myapplication.data.database;


public class DetailTransactionActivity extends AppCompatActivity {
    TextView textDate, textName, textMoney, textNote;
    database db;
    int id;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        textDate = (TextView)findViewById(R.id.textView_ngayGiaoDich);
        textName = (TextView)findViewById(R.id.textView_tenGiaoDich);
        textMoney = (TextView)findViewById(R.id.textView_soTien);
        textNote = (TextView)findViewById(R.id.textView_ghiChu);
        findViewById(R.id.button_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db = new database(getApplicationContext(),"database.db",null,1);
        id = getIntent().getIntExtra("id",0);
        Transaction a = db.getTransactionById(id);
        textDate.setText(a.getDate());
        textMoney.setText(a.getMoney()+"");
        textNote.setText(a.getNote());
        textName.setText(a.getName());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        super.onRestart();
        db = new database(getApplicationContext(),"database.db",null,1);
        id = getIntent().getIntExtra("id",0);
        Transaction a = db.getTransactionById(id);
        textDate.setText(a.getDate());
        textMoney.setText(a.getMoney()+"");
        textNote.setText(a.getNote());
        textName.setText(a.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), com.example.myapplication.HistoryActivity.class);
        startActivityForResult(myIntent, 0);

        switch (item.getItemId()){
            case R.id.edit_optionMenu_Detail:
                Intent intent = new Intent(this, AddTransactionActivity.class);
                intent.putExtra("edit",true);
                intent.putExtra("id",id);
                startActivity(intent);
                return true;

            case R.id.delete_optionMenu_Detail:
                db.deleteTransactionById(id);
                finish();
                return true;
            default:break;
        }
        return true;
    }
    /* tạo Option quay lại*//*
    public boolean onOptionsItemSelected1(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), com.example.myapplication.MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }*/
}