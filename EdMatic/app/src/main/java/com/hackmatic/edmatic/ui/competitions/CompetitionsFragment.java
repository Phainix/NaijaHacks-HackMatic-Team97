package com.hackmatic.edmatic.ui.competitions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Competition;

import java.util.List;

public class CompetitionsFragment extends Fragment {

    private CompetitionsViewModel competitionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        competitionsViewModel =
                ViewModelProviders.of(this).get(CompetitionsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_competitions, container, false);

        List<Competition> competitions = competitionsViewModel.getCompetitions();
        LinearLayout competitionsLayout = (LinearLayout) root.findViewById(R.id.competition_list);
        for (int i = 0; i < competitions.size(); i++) {
            View competition = inflater.inflate(R.layout.activity_list_item, null);
            TextView name = (TextView) competition.findViewById(R.id.name);
            name.setText(competitions.get(i).getmName());
            TextView time = (TextView) competition.findViewById(R.id.time);
            time.setText(competitions.get(i).getmTime());
            competitionsLayout.addView(competition);
        }

        View list_item = (View) root.findViewById(R.id.list_item);
        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(root.getContext(),
                 "Item selected",
                 Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}