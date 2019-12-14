package com.hackmatic.edmatic.data;

public class Challenge {
    private final String mName;
    private final String mTime;
    private final String mTeam;

    public Challenge(String name, String time, String team) {
        mName = name;
        mTime = time;
        mTeam = team;
    }

    public String getmName() {
        return mName;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmTeam() {
        return mTeam;
    }

    @Override
    public String toString() {
        return mName;
    }

}
