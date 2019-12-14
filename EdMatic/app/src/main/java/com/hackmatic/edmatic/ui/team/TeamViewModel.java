package com.hackmatic.edmatic.ui.team;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackmatic.edmatic.DataStore;
import com.hackmatic.edmatic.data.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TeamViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is team fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<Team> getTeams() {
        Team team1 = new Team("Math Rockers", "8Lagos, Bentley High", 5);
        Team team2 = new Team("Physics Marshals", "Benin", 3);
        Team team3 = new Team("Physicians Extaordinairen", "Kano", 4);
        Team team4 = new Team("C++ Champions", "Abeokuta", 2);
        Team team5 = new Team("C++ Champions", "Abeokuta", 2);
        Team team6 = new Team("C++ Champions", "Abeokuta", 2);

        List<Team> team =  new ArrayList<Team>();
        team.add(team1);
        team.add(team2);
        team.add(team3);
        team.add(team4);
        team.add(team5);
        team.add(team6);

        return team;
    }
}