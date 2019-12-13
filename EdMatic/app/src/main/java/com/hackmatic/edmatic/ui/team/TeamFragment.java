package com.hackmatic.edmatic.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.adapter.ItemAdapter;
import com.hackmatic.edmatic.adapter.TeamItemAdapter;
import com.hackmatic.edmatic.data.Team;

import java.util.List;

public class TeamFragment extends Fragment {

    private TeamViewModel teamViewModel;
    private View mRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_team, container, false);

        List<Team> teams = teamViewModel.getTeams();

        ListView teamList = (ListView) mRoot.findViewById(R.id.teams);
        teamList.setOnItemClickListener(messageClickedHandler);

        TeamItemAdapter itemAdapter = new TeamItemAdapter(mRoot.getContext(), teams);
        teamList.setAdapter(itemAdapter);

        return mRoot;
    }

    private AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
            // Toast.makeText(mRoot.getContext(),
                    // "Item selected " + position,
                    // Toast.LENGTH_SHORT).show();
        }
    };
}