package com.hackmatic.edmatic.data;

public class Competition {
    private final String mName;
    private final String mTime;

    public Competition(String name, String time) {
        mName = name;
        mTime = time;
    }

    public String getmName() {
        return mName;
    }

    public String getmTime() {
        return mTime;
    }

    @Override
    public String toString() {
        return mName;
    }
}
