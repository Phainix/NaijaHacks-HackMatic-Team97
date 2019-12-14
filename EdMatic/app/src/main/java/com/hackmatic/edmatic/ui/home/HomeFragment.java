package com.hackmatic.edmatic.ui.home;

import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackmatic.edmatic.DataStore;
import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Activity;
import com.hackmatic.edmatic.data.Challenge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private FirebaseAuth mAuth;
    private HomeViewModel homeViewModel;

    TextView mName;
    private View mRoot;
    private LayoutInflater mInflater;
    private LinearLayout mActivitiesLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();

        setUpLayout();

        List<Challenge> challenges = homeViewModel.getUpcomingChallenges();
        LinearLayout upcomingLayout = (LinearLayout) mRoot.findViewById(R.id.upcoming_list);
        for (int i = 0; i < challenges.size(); i++) {
            mInflater = inflater;
            View upcomingItem = mInflater.inflate(R.layout.upcoming_list_item, null);
            TextView name = (TextView) upcomingItem.findViewById(R.id.name);
            name.setText(challenges.get(i).getmName());
            TextView time = (TextView) upcomingItem.findViewById(R.id.time);
            time.setText(challenges.get(i).getmTime());
            TextView team = (TextView) upcomingItem.findViewById(R.id.team_name);
            team.setText(challenges.get(i).getmTeam());
            upcomingLayout.addView(upcomingItem);
        }

//        List<Activity> activities = homeViewModel.getActivities();
        mActivitiesLayout = (LinearLayout) mRoot.findViewById(R.id.activities);
//        for (int i = 0; i < activities.size(); i++) {
//            View activity = inflater.inflate(R.layout.activity_list_item, null);
//            TextView name = (TextView) activity.findViewById(R.id.name);
//            name.setText(activities.get(i).getmName());
//            TextView time = (TextView) activity.findViewById(R.id.time);
//            time.setText(activities.get(i).getmTime());
//            activitiesLayout.addView(activity);
//        }

        getActivities();

        return mRoot;
    }

    private void getActivities() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(DataStore.ACTIVITIES)
                .orderBy("created_on", Query.Direction.DESCENDING)
                .limit(25);

        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Activity> activities =  new ArrayList<Activity>();
                        SimpleDateFormat formatter = new SimpleDateFormat("d, MMM yyyy HH:mm");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String d = "";
                            if(document.contains("created_on")) {
                                Date date = document.getTimestamp("created_on").toDate();
                                d = formatter.format(date);
                            }

                            View activity = mInflater.inflate(R.layout.activity_list_item, null);
                            TextView name = (TextView) activity.findViewById(R.id.name);
                            name.setText(document.contains("activity") ? document.getString("activity") : "");
                            TextView time = (TextView) activity.findViewById(R.id.time);
                            time.setText(d);
                            mActivitiesLayout.addView(activity);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
    }

    private void setUpLayout() {
        mName = (TextView) mRoot.findViewById(R.id.username);
        mName.setText(mAuth.getCurrentUser().getDisplayName());
    }
}