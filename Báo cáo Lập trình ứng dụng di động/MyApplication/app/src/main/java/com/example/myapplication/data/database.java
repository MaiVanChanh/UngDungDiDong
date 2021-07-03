package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    String TAG = "SQLiteOpenHelper: ";
    String DATABASE_NAME = "database.db";
    String TABLE_NAME = "giaodich";
    int DATABASE_VERSION = 1;

    public database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG, "database: end");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Transaction getTransactionById(int id) {
        Transaction a = new Transaction();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SQLiteDatabase db = getReadableDatabase();
        Cursor p = db.rawQuery("SELECT * FROM giaodich WHERE id =?", new String[]{String.valueOf(id)});
        p.moveToFirst();
        a.setId(p.getInt(0));
        a.setName(p.getString(1));
        a.setMoney(p.getFloat(2));
        a.setNote(p.getString(3));
        a.setDate(LocalDate.parse(p.getString(4), formatter2).format(formatter));
        return a;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public float getTransactionMonthPre() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor p = db.rawQuery("SELECT sum(money) FROM giaodich WHERE strftime('%m',date) = strftime('%m','now','-1 month')", null);
        p.moveToFirst();
        return p.getFloat(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public float getTransactionMonth() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor p = db.rawQuery("SELECT sum(money) FROM giaodich WHERE strftime('%m',date) = strftime('%m','now')", null);
        p.moveToFirst();
        return p.getFloat(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public float getTransactionYear() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor p = db.rawQuery("SELECT sum(money) FROM giaodich WHERE strftime('%Y',date) = strftime('%Y','now')", null);
        p.moveToFirst();
        return p.getFloat(0);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public float getStats(String begin, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String d1 = LocalDate.parse(begin, formatter).format(formatter2);
        String d2 = LocalDate.parse(end, formatter).format(formatter2);
        SQLiteDatabase db = getReadableDatabase();
        Cursor p = db.rawQuery("SELECT sum(money) FROM giaodich WHERE date  BETWEEN ? AND ?", new String[]{d1, d2});
        p.moveToFirst();
        return p.getFloat(0);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Transaction> getHistory() {
        SQLiteDatabase db = getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Cursor p = db.rawQuery("SELECT * FROM giaodich ORDER BY date DESC", null);
        if (p != null) {
            p.moveToFirst();
            while (!p.isAfterLast()) {
                Transaction a = new Transaction();
                a.setId(p.getInt(0));
                a.setName(p.getString(1));
                a.setMoney(p.getFloat(2));
                a.setNote(p.getString(3));
                a.setDate(LocalDate.parse(p.getString(4), formatter2).format(formatter));
                transactions.add(a);
                p.moveToNext();
            }
        }
        return transactions;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertTransaction(Transaction transaction) {
        Log.d(TAG, "insertTransaction: begin");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.parse(transaction.date, formatter).format(formatter2);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", transaction.name);
        values.put("money", transaction.money);
        values.put("note", transaction.note);
        values.put("date", date);
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "insertTransaction: end");
    }

    public void deleteTransactionById(int id) {
        Log.d(TAG, "deleteTransactionById: begin");
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        Log.d(TAG, "deleteTransactionById: end");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void editTransaction(Transaction transaction) {
        Log.d(TAG, "editTransaction: begin");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.parse(transaction.date, formatter).format(formatter2);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", transaction.name);
        values.put("money", transaction.money);
        values.put("note", transaction.note);
        values.put("date", date);
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(transaction.id)});
        Log.d(TAG, "editTransaction: end");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate: begin");
        String CREATE_TABLE = "CREATE TABLE [" + TABLE_NAME + "] ( id INTEGER  PRIMARY KEY AUTOINCREMENT,  name  STRING, money REAL, note  TEXT ,  date  NUMERIC);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d(TAG, "onCreate: sql: " + CREATE_TABLE);
        Log.d(TAG, "onCreate: end");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: begin");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.d(TAG, "onUpgrade: end");
    }
}
