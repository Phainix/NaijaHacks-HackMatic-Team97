package com.hackmatic.edmatic.ui.home;

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
import com.hackmatic.edmatic.data.Activity;
import com.hackmatic.edmatic.data.Challenge;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        List<Challenge> challenges = homeViewModel.getUpcomingChallenges();
        LinearLayout upcomingLayout = (LinearLayout) root.findViewById(R.id.upcoming_list);
        for (int i = 0; i < challenges.size(); i++) {
            View upcomingItem = inflater.inflate(R.layout.upcoming_list_item, null);
            TextView name = (TextView) upcomingItem.findViewById(R.id.name);
            name.setText(challenges.get(i).getmName());
            TextView time = (TextView) upcomingItem.findViewById(R.id.time);
            time.setText(challenges.get(i).getmTime());
            TextView team = (TextView) upcomingItem.findViewById(R.id.team_name);
            team.setText(challenges.get(i).getmTeam());
            upcomingLayout.addView(upcomingItem);
        }

        List<Activity> activities = homeViewModel.getActivities();
        LinearLayout activitiesLayout = (LinearLayout) root.findViewById(R.id.activities);
        for (int i = 0; i < activities.size(); i++) {
            View activity = inflater.inflate(R.layout.activity_list_item, null);
            TextView name = (TextView) activity.findViewById(R.id.name);
            name.setText(activities.get(i).getmName());
            TextView time = (TextView) activity.findViewById(R.id.time);
            time.setText(activities.get(i).getmTime());
            activitiesLayout.addView(activity);
        }

        return root;
    }
}