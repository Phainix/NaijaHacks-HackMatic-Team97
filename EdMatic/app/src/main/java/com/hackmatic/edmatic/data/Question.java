package com.hackmatic.edmatic.data;

public class Question {

    private final String mQuestion;
    private final String mAnswer;
    private final String mDuration;
    private final String mId;

    public Question(String question, String answer, String duration, String id) {
        mQuestion = question;
        mDuration = duration;
        mAnswer = answer;
        mId = id;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public String getmAnswer() {
        return mAnswer;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getmId() {
        return mId;
    }
}
