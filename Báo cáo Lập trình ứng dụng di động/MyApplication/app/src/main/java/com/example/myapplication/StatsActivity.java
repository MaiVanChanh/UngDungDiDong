package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.data.database;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatsActivity extends AppCompatActivity {
    EditText editNgayBatDau, editNgayKetThuc;
    TextView textTongChi;
    Button btnFilter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        init();

        final database db = new database(getApplicationContext(), "database.db", null, 1);

        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        editNgayBatDau.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
        editNgayKetThuc.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

        NumberFormat format = NumberFormat.getCurrencyInstance();
        float sum = db.getStats(editNgayBatDau.getText().toString(), editNgayKetThuc.getText().toString());
        textTongChi.setText(format.format(sum) + "");

        editNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.myapplication.StatsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        editNgayBatDau.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month,day);
                datePickerDialog.show();
            }
        });
        editNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.myapplication.StatsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        editNgayKetThuc.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                NumberFormat format = NumberFormat.getCurrencyInstance();
                float sum = db.getStats(editNgayBatDau.getText().toString(), editNgayKetThuc.getText().toString());
                textTongChi.setText(format.format(sum) + "");

            }
        });

    }


    private void init() {
        editNgayBatDau = (EditText) findViewById(R.id.editText_ngayBatDau);
        editNgayKetThuc = (EditText) findViewById(R.id.editText_ngayKetThuc);
        textTongChi = (TextView) findViewById(R.id.textView_tongChi);
        btnFilter = (Button) findViewById(R.id.button_Filter);
    }
    /* tạo Option quay lại*/
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), com.example.myapplication.MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}