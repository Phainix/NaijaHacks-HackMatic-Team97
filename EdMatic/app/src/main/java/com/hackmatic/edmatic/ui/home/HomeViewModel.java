package com.hackmatic.edmatic.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hackmatic.edmatic.data.Activity;
import com.hackmatic.edmatic.data.Challenge;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private List<Challenge> mNotes = new ArrayList<>();

    public HomeViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<Challenge> getUpcomingChallenges() {
        Challenge challenge1 = new Challenge("Math Masters (Phase 4)", "8:30 AM - 02:00 PM", "Math Rockers");
        Challenge challenge2 = new Challenge("Chemistry National Championship", "8:30 AM - 02:00 PM", "Chemistry Rockers");
        Challenge challenge3 = new Challenge("Physics 2019 Juniors Qualification", "8:30 AM - 02:00 PM", "Physicians");

        List<Challenge> challenges =  new ArrayList<Challenge>();
        challenges.add(challenge1);
        challenges.add(challenge2);
        challenges.add(challenge3);
        return challenges;
    }

    public List<Activity> getActivities() {
        Activity activity1 = new Activity("Issued Challenge", " 8:40 AMM");
        Activity activity2 = new Activity("Won Math Masters Phase (2)", "9:00 AM - 9:50 AM");
        Activity activity3 = new Activity("Accepted Physics Challenge", "10:00 AM ");
        Activity activity4 = new Activity("Joined Lagos Math Gurus", "11:00 AM");
        Activity activity5 = new Activity("Created Physics Challenge", "02:00 AM ");

        List<Activity> activities =  new ArrayList<Activity>();
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        activities.add(activity4);
        activities.add(activity5);
        return activities;
    }
}