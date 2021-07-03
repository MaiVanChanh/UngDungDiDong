package com.example.myapplication.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class AdapterTransaction extends BaseAdapter {


    Context context;
    int layout;
    List<Transaction> transactionList;

    public AdapterTransaction(Context context, int layout, List<Transaction> transactionList) {
        this.context = context;
        this.layout = layout;
        this.transactionList = transactionList;
    }


    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_listview_danhsachgiaodich,null);

        TextView date = (TextView) view.findViewById(R.id.textView_ngayGiaoDich);
        date.setText(transactionList.get(i).date);
        TextView name = (TextView) view.findViewById(R.id.textView_tenGiaoDich);
        name.setText(transactionList.get(i).name);
        TextView money = (TextView) view.findViewById(R.id.textView_soTien);
        money.setText(transactionList.get(i).money+"");

        return view;
    }
}
