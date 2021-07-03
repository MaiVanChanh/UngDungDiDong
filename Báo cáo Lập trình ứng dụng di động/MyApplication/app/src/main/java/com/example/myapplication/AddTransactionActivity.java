package com.example.myapplication;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.data.Transaction;
import com.example.myapplication.data.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity {
    EditText editTen, editSoTien, editNgay, editGhiChu;
    Button btnLuu;


/* tạo Option quay lại*/
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), com.example.myapplication.MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        init();
        final database db = new database(getApplicationContext(),"database.db",null,1);

        final boolean IsEdit = getIntent().getBooleanExtra("edit",false);
        final int id = getIntent().getIntExtra("id",-1);
        if(IsEdit == false){
            editNgay.setText(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        }
        else {
            Transaction a = db.getTransactionById(id);
            editNgay.setText(a.getDate());
            editSoTien.setText(a.getMoney()+"");
            editGhiChu.setText(a.getNote());
            editTen.setText(a.getName());
        }

        editNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDatetime();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(editTen.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên giao dịch", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editSoTien.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập số tiền chi tiêu", Toast.LENGTH_SHORT).show();
                    return;
                }

                Transaction a = new Transaction();
                a.setId(id);
                a.setName(editTen.getText().toString());
                a.setMoney(Float.parseFloat(editSoTien.getText().toString()));
                a.setNote(editGhiChu.getText().toString());
                a.setDate(editNgay.getText().toString());
                if(IsEdit == false){
                    db.insertTransaction(a);
                    Toast.makeText(getApplicationContext(),"Đã thêm thành công!", Toast.LENGTH_SHORT).show();
                    editNgay.setText(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                    editTen.setText("");
                    editSoTien.setText("");
                    editGhiChu.setText("");
                }else {
                    db.editTransaction(a);
                    Toast.makeText(getApplicationContext(),"Đã sửa thành công!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    void pickDatetime() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    void init() {
        editTen = (EditText) findViewById(R.id.editText_tenGiaoDich);
        editSoTien = (EditText) findViewById(R.id.editText_soTien);
        editNgay = (EditText) findViewById(R.id.editText_ngayGiaoDich);
        editGhiChu = (EditText) findViewById(R.id.editText_ghiChu);
        btnLuu = (Button) findViewById(R.id.button_Luu);
    }


}