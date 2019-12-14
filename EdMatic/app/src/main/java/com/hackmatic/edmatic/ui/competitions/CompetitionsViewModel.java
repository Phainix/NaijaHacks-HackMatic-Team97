package com.hackmatic.edmatic.ui.competitions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hackmatic.edmatic.data.Competition;

import java.util.ArrayList;
import java.util.List;

public class CompetitionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CompetitionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is competitions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<Competition> getCompetitions() {
        Competition competition1 = new Competition("Chemistry Lions", " 8:40 AM");
        Competition competition2 = new Competition("Math Masters Phase (2)", "9:00 AM");
        Competition competition3 = new Competition("Physics Challenge", "10:00 AM ");
        Competition competition4 = new Competition("Lagos Math Gurus", "11:00 AM");
        Competition competition5 = new Competition("Astronauts Junior Phase", "02:00 AM ");

        List<Competition> competitions =  new ArrayList<Competition>();
        competitions.add(competition1);
        competitions.add(competition2);
        competitions.add(competition3);
        competitions.add(competition4);
        competitions.add(competition5);
        competitions.add(competition1);
        competitions.add(competition2);
        competitions.add(competition3);
        competitions.add(competition4);
        competitions.add(competition5);
        return competitions;
    }
}