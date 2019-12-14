package com.hackmatic.edmatic.ui.competitions;

import android.os.Bundle;
import android.util.Log;
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
import com.hackmatic.edmatic.data.Competition;
import com.hackmatic.edmatic.ui.competition_detail.CompetitionDetailFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompetitionsFragment extends Fragment {

    private static final String TAG = CompetitionsFragment.class.getSimpleName();

    private CompetitionsViewModel competitionsViewModel;
    private View mRoot;
    private View spinner;
    private List<Competition> mCompetitions;
    private ItemAdapter mItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        competitionsViewModel =
                ViewModelProviders.of(this).get(CompetitionsViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_competitions, container, false);

        final ListView competitionsList = (ListView) mRoot.findViewById(R.id.competition_list);
        competitionsList.setOnItemClickListener(messageClickedHandler);

        spinner = getActivity().findViewById(R.id.progress_bar_holder);
        spinner.setVisibility(View.VISIBLE);

        mCompetitions = new ArrayList<>();

        mItemAdapter = new ItemAdapter(mRoot.getContext(), mCompetitions);
        competitionsList.setAdapter(mItemAdapter);

        getCompetitions();
        return mRoot;
    }

    private void getCompetitions() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(DataStore.COMPETITIONS)
                .orderBy("created_on", Query.Direction.DESCENDING)
                .limit(25);

        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Competition> competitions =  new ArrayList<Competition>();

                        SimpleDateFormat formatter = new SimpleDateFormat("d, MMM yyyy HH:mm");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            String name = document.contains("name") ? document.getString("name") : "";
                            String time = document.contains("date") ? formatter.format(document.getTimestamp("created_on").toDate()) : "";
                            String id = document.getId();

                            competitions.add( new Competition(name, time, id));
                        }
                        mItemAdapter.add(competitions);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        getFragmentManager().popBackStackImmediate();
                    }
                    spinner.setVisibility(View.GONE);
                }
            });
    }


    private AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
            Competition competition = (Competition) mItemAdapter.getItem(position);
            loadCompetitionDetailFragment(competition.getmId());

        }
    };

    private void loadCompetitionDetailFragment(String competition) {
        Bundle bundle = new Bundle();
        bundle.putString(CompetitionDetailFragment.COMPETITION_KEY, competition);

        Fragment fragment = new CompetitionDetailFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}