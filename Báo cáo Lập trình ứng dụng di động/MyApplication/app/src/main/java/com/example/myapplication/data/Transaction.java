package com.example.myapplication.data;

public class Transaction {
    public Transaction(int id, String name, float money, String note, String date) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.note = note;
        this.date = date;
    }
    public Transaction(){}

    int id;
    String name;
    float money;
    String note;
    String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
