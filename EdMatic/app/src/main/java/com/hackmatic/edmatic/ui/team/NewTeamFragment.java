package com.hackmatic.edmatic.ui.team;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackmatic.edmatic.DataStore;
import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.utilities.AppActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTeamFragment extends Fragment {

    private static final String TAG = NewTeamFragment.class.getSimpleName();
    private View mRoot;
    private FirebaseAuth mAuth;

    private Button mSubmit;
    private View spinner;
    private int LAYOUT;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_team_new, container, false);

        LAYOUT = R.id.new_team_layout;

        mSubmit = mRoot.findViewById(R.id.submit);
        spinner = getActivity().findViewById(R.id.progress_bar_holder);

        mAuth = FirebaseAuth.getInstance();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                createNewTeam();
            }
        });
        return mRoot;
    }

    private void createNewTeam() {
        EditText nameInput = (EditText) mRoot.findViewById(R.id.name);
        final String name = nameInput.getText().toString();

        EditText descInput = (EditText) mRoot.findViewById(R.id.desc);
        final String desc = descInput.getText().toString();

        EditText fieldInput = (EditText) mRoot.findViewById(R.id.fields);
        String fields = fieldInput.getText().toString();

        final String[] f = fields.split(",");

        // Snackbar.make(mRoot.findViewById(LAYOUT), "Submission " + name + " " + desc + " "
                // + fields, Snackbar.LENGTH_SHORT).show();


        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                saveNewTeam(name, desc, Arrays.asList(f));
                return null;
            }
        };
        task.execute();
    }

    private void saveNewTeam(final String name, String desc, List<String> fields) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userID = mAuth.getCurrentUser().getUid();

        Map<String, Object> team = new HashMap<>();
        team.put("name", name);
        team.put("desc", desc);
        team.put("fields", fields);
        team.put("status", true);
        team.put("created_by", userID);
        team.put("created_on", FieldValue.serverTimestamp());
        team.put("members", new ArrayList<String>(
                Arrays.asList(userID)));

        // Add a new document with a generated ID
        db.collection(DataStore.TEAMS)
            .add(team)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "Team record added");
                    spinner.setVisibility(View.GONE);
                    Snackbar.make(mRoot.findViewById(LAYOUT), "Team creation successful", Snackbar.LENGTH_SHORT).show();
                    AppActivity.save("Created " + name + " team");

                    Fragment fragment = new TeamFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                    spinner.setVisibility(View.GONE);
                    Snackbar.make(mRoot.findViewById(LAYOUT), "Team creation failed, please try again", Snackbar.LENGTH_SHORT).show();
                }
            });
    }
}
