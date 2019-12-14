package com.hackmatic.edmatic.ui.team;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackmatic.edmatic.DataStore;
import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.adapter.ItemAdapter;
import com.hackmatic.edmatic.adapter.TeamItemAdapter;
import com.hackmatic.edmatic.data.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {

    private static final String TAG = TeamFragment.class.getSimpleName();

    private TeamViewModel teamViewModel;
    private View mRoot;
    private List<Team> mTeams;
    private View spinner;
    private Button mNewTeam;
    private TeamItemAdapter mItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_team, container, false);

        final ListView teamList = (ListView) mRoot.findViewById(R.id.teams);
        teamList.setOnItemClickListener(messageClickedHandler);

        spinner = getActivity().findViewById(R.id.progress_bar_holder);
        spinner.setVisibility(View.VISIBLE);

        mTeams = new ArrayList<>();
        mItemAdapter = new TeamItemAdapter(mRoot.getContext(), mTeams);
        teamList.setAdapter(mItemAdapter);

        getTeams();

        mNewTeam = mRoot.findViewById(R.id.new_team);
        mNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewTeamFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return mRoot;
    }

    private void getTeams() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(DataStore.TEAMS)
                .orderBy("created_on", Query.Direction.DESCENDING)
                .limit(25);

        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Team> team =  new ArrayList<Team>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            String name = document.contains("name") ? document.getString("name") : "";
                            String desc = document.contains("desc") ? document.getString("desc") : "";
                            ArrayList<String> members = (ArrayList) document.get("members");

                            team.add( new Team(name, desc, members.size()));
                        }
                        mItemAdapter.add(team);
                        spinner.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
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