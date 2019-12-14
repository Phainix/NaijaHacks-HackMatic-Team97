package com.hackmatic.edmatic.data;

public class Team {
    private final String mName;
    private final String mDetail;
    private final int mTeamCount;

    public Team(String name, String detail, int teamCount) {
        mName = name;
        mDetail = detail;
        mTeamCount = teamCount;
    }

    public String getmName() {
        return mName;
    }

    public String getmDetail() {
        return mDetail;
    }

    public int getmTeamCount() {
        return mTeamCount;
    }

    @Override
    public String toString() {
        return mName;
    }
}
