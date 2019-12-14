package com.hackmatic.edmatic.data;

public class Competition {
    private final String mName;
    private final String mTime;
    private final String mId;

    public Competition(String name, String time, String id) {
        mName = name;
        mTime = time;
        mId = id;
    }

    public String getmName() {
        return mName;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmId() {
        return mId;
    }

    @Override
    public String toString() {
        return mName;
    }
}
