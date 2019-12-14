package com.hackmatic.edmatic.ui.competitions;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.adapter.ItemAdapter;
import com.hackmatic.edmatic.data.Competition;
import com.hackmatic.edmatic.ui.competition_detail.CompetitionDetailFragment;

import java.util.List;

public class CompetitionsFragment extends Fragment {

    private CompetitionsViewModel competitionsViewModel;
    private View mRoot;
    private View spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        competitionsViewModel =
                ViewModelProviders.of(this).get(CompetitionsViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_competitions, container, false);

        final ListView competitionsList = (ListView) mRoot.findViewById(R.id.competition_list);
        competitionsList.setOnItemClickListener(messageClickedHandler);

        spinner = getActivity().findViewById(R.id.progress_bar_holder);
        spinner.setVisibility(View.VISIBLE);

        List<Competition> competitions = competitionsViewModel.getCompetitions();

        ItemAdapter itemAdapter = new ItemAdapter(mRoot.getContext(), competitions);
        competitionsList.setAdapter(itemAdapter);
        return mRoot;
    }



    private AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
            loadCompetitionDetailFragment(position);

        }
    };

    private void loadCompetitionDetailFragment(int competition) {
        Bundle bundle = new Bundle();
        bundle.putInt(CompetitionDetailFragment.COMPETITION_KEY, competition);

        Fragment fragment = new CompetitionDetailFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}