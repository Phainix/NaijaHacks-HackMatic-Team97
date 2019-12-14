package com.hackmatic.edmatic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Competition;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    Context context;
    List<Competition> competitions;
    LayoutInflater inflter;

    public ItemAdapter(Context applicationContext, List<Competition> competitions) {
        this.context = context;
        this.competitions = competitions;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void add(List<Competition> competitions) {
        this.competitions.addAll(competitions);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return competitions.size();
    }

    @Override
    public Object getItem(int i) {
        return competitions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View list_item, ViewGroup viewGroup) {
        list_item = inflter.inflate(R.layout.activity_list_item, null);
        TextView name = (TextView) list_item.findViewById(R.id.name);
        TextView time = (TextView) list_item.findViewById(R.id.time);
        name.setText(competitions.get(i).getmName());
        time.setText(competitions.get(i).getmTime());

        return list_item;
    }

}