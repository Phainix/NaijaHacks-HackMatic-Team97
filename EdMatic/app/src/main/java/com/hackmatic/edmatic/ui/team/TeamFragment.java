package com.hackmatic.edmatic.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Team;

import java.util.List;

public class TeamFragment extends Fragment {

    private TeamViewModel teamViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_team, container, false);

        List<Team> teams = teamViewModel.getTeams();
        LinearLayout teamLayout = (LinearLayout) root.findViewById(R.id.teams);
        for (int i = 0; i < teams.size(); i++) {
            View team = inflater.inflate(R.layout.team_list_item, null);
            TextView name = (TextView) team.findViewById(R.id.name);
            name.setText(teams.get(i).getmName());
            TextView detail = (TextView) team.findViewById(R.id.detail);
            detail.setText(teams.get(i).getmDetail());
            TextView members = (TextView) team.findViewById(R.id.team_member_count);
            members.setText(String.valueOf(teams.get(i).getmTeamCount()));
            teamLayout.addView(team);
        }

        return root;
    }
}