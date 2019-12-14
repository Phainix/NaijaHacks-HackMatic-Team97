package com.hackmatic.edmatic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Team;

import java.util.List;

public class TeamItemAdapter extends BaseAdapter {
    Context context;
    List<Team> teams;
    LayoutInflater inflter;

    public TeamItemAdapter(Context applicationContext, List<Team> competitions) {
        this.context = context;
        this.teams = competitions;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return teams.size();
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
    public View getView(int i, View list_item, ViewGroup viewGroup) {
        list_item = inflter.inflate(R.layout.team_list_item, null);
        TextView name = (TextView) list_item.findViewById(R.id.name);
        TextView detail = (TextView) list_item.findViewById(R.id.detail);
        TextView members = (TextView) list_item.findViewById(R.id.team_member_count);
        name.setText(teams.get(i).getmName());
        detail.setText(teams.get(i).getmDetail());
        members.setText(String.valueOf(teams.get(i).getmTeamCount()));

        return list_item;
    }

}