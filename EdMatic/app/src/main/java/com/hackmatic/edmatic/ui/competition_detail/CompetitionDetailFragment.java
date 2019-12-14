package com.hackmatic.edmatic.ui.competition_detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackmatic.edmatic.DataStore;
import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.data.Competition;
import com.hackmatic.edmatic.data.Question;
import com.hackmatic.edmatic.ui.competitions.CompetitionsViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDetailFragment  extends Fragment {

    private static final String TAG = CompetitionDetailFragment.class.getSimpleName();

    public final static String COMPETITION_KEY = "competition";
    private static final int LAYOUT = R.id.competition_detail_layout;

    private CompetitionDetailViewModel competitionDetailViewModel;
    private View mRoot;
    private String mCompetitionId;
    private TextView mTeamName;
    private TextView mTeamDetail;
    private TextView mTick;
    private TextView mDesc;
    private TextView mQuestion;
    private View spinner;
    private View questionLeft;
    private View questionRight;
    private int currentQuestion = 0;
    private Button mAnswer;
    private String mAnswerText = "";

    private boolean isCompetitionLoaded = false;
    private boolean isQuestionLoaded = false;

    private String timer = "1:45";
    private List<Question> mQuestions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        competitionDetailViewModel =
                ViewModelProviders.of(this).get(CompetitionDetailViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_competition_detail, container, false);
        mTeamName = (TextView) mRoot.findViewById(R.id.team_name);
        mTeamDetail = (TextView) mRoot.findViewById(R.id.team_member_count);
        mTick  = (TextView) mRoot.findViewById(R.id.ticking_time);
        mDesc  = (TextView) mRoot.findViewById(R.id.competition_activity_desc);
        questionLeft  = (View) mRoot.findViewById(R.id.question_left);
        questionRight  = (View) mRoot.findViewById(R.id.question_right);
        mQuestion = (TextView) mRoot.findViewById(R.id.question);

        spinner = getActivity().findViewById(R.id.progress_bar_holder);
        spinner.setVisibility(View.VISIBLE);

        mCompetitionId = "99";
        Bundle b = this.getArguments();
        if(b != null) {
            mCompetitionId = b.getString(COMPETITION_KEY);
            setUpView();
        }

        mAnswer = (Button) mRoot.findViewById(R.id.answer);
        mAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAnswerDialog();
            }
        });
        return mRoot;
    }

    private void setUpAnswerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mRoot.getContext());
        builder.setTitle("Answer");

        View viewInflated = LayoutInflater.from(mRoot.getContext()).inflate(R.layout.answer, (ViewGroup) getView(), false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);

        // final EditText input = new EditText(mRoot.getContext());
        // builder.setView(input);
        // input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(viewInflated);


        builder.setPositiveButton("Answer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mAnswerText = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setUpView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(DataStore.COMPETITIONS).document(mCompetitionId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        ArrayList<String> participants = (ArrayList) document.get("participants");

                        mTeamName.setText(document.contains("name") ? document.getString("name") : null);
                        mTeamDetail.setText(participants.size() > 0 ? participants.size() + " team(s) competing" : "No team competing");
                        isCompetitionLoaded = true;
                        setUpViewInformation();
                    } else {
                        Log.d(TAG, "No such document");
                        Snackbar.make(mRoot.findViewById(LAYOUT), "Competition could not be retrieved", Snackbar.LENGTH_SHORT).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                } else {
                    Log.d(TAG, "Fetch failed with ", task.getException());
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });

        Query query = db.collection(DataStore.QUESTIONS)
                .orderBy("created_on", Query.Direction.DESCENDING)
                .whereEqualTo("competition", mCompetitionId);

        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    mQuestions = new ArrayList<Question>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String question = document.contains("question") ? document.getString("question") : "";
                        String answer = document.contains("answer") ? document.getString("answer") : "";
                        String duration = document.contains("duration") ? document.getString("duration") : "";
                        String id = document.getId();

                        mQuestions.add( new Question(question, answer, duration, id));
                        isQuestionLoaded = true;
                    }
                    setUpViewInformation();

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    getFragmentManager().popBackStackImmediate();
                }
                spinner.setVisibility(View.GONE);
                }
            });

    }

    private void loadQuestions() {
        Question question = mQuestions.get(currentQuestion);
        mQuestion.setText(question.getmQuestion());
    }

    public void setUpViewInformation() {
        if(isCompetitionLoaded && isQuestionLoaded) {
            spinner.setVisibility(View.GONE);
            loadQuestions();
            setCountDown();
        }
    }

    private void setCountDown() {
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTick.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTick.setText("0");
                mDesc.setText("Round 1 - Team A");
                questionLeft.animate().translationX(-1000).setDuration(1200);
                questionRight.animate().translationX(1000).setDuration(1200);
            }
        }.start();
    }
}
