package com.hackmatic.edmatic.ui.competition_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Competition;
import com.hackmatic.edmatic.ui.competitions.CompetitionsViewModel;

import java.util.List;

public class CompetitionDetailFragment  extends Fragment {

    public final static String COMPETITION_KEY = "competition";

    private CompetitionDetailViewModel competitionDetailViewModel;
    private View mRoot;
    private int mCompetitionId;
    private TextView mTeamName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        competitionDetailViewModel =
                ViewModelProviders.of(this).get(CompetitionDetailViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_competition_detail, container, false);

        mCompetitionId = 99;
        Bundle b = this.getArguments();
        if(b != null) {
            mCompetitionId = b.getInt(COMPETITION_KEY);
            setUpView();
        }
        Toast.makeText(mRoot.getContext(),
                "competition selected " + mCompetitionId,
                Toast.LENGTH_SHORT).show();

        return mRoot;
    }

    private void setUpView() {
        CompetitionsViewModel competitionsViewModel = new CompetitionsViewModel();
        Competition competition = competitionsViewModel.getCompetitions().get(mCompetitionId);

        mTeamName = (TextView) mRoot.findViewById(R.id.team_name);
        mTeamName.setText(competition.getmName());
    }
}
