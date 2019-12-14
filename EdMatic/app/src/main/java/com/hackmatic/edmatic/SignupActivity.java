package com.hackmatic.edmatic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final int GOOGLE_SIGN_UP = 0;
    private static final String TAG = SignupActivity.class.getName();

    TextView mSignIn;
    Button mGoogleAuth;
    Button mTwitterAuth;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        setUpClickHandlers();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestId()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setUpClickHandlers() {
        mSignIn = (TextView) findViewById(R.id.signin_prompt);
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mGoogleAuth = (Button) findViewById(R.id.googleAuth);
        mGoogleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_UP);
            }
        });

        mTwitterAuth = (Button) findViewById(R.id.twitterAuth);
        mTwitterAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, DetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            goToDashboard();
        }
    }

    private void goToDashboard() {
        Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToDetails() {
        Intent intent = new Intent(SignupActivity.this, DetailActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_UP) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign up failed", e);
                Snackbar.make(findViewById(R.id.signup_layout), "Google sign up failed please try again", Snackbar.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        acct.getDisplayName();
        acct.getEmail();
        acct.getFamilyName();
        acct.getPhotoUrl();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    final FirebaseUser user = mAuth.getCurrentUser();
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            saveUserDetailsToCollection(acct, user);
                            return null;
                        }
                    };
                    asyncTask.execute();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Snackbar.make(findViewById(R.id.signup_layout), "Google sign up failed please try again", Snackbar.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }

    private void saveUserDetailsToCollection(final GoogleSignInAccount acct, final FirebaseUser firebaseUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", acct.getDisplayName());
        user.put("email", acct.getEmail());
        user.put("fname", acct.getGivenName());
        user.put("lname", acct.getFamilyName());
        user.put("token", acct.getIdToken());
        user.put("id", acct.getId());
        user.put("auth_code", acct.getServerAuthCode());
        user.put("uid", firebaseUser.getUid());
        user.put("created_on", FieldValue.serverTimestamp());

        // Add a new document with a generated ID
        db.collection("users")
                .document(firebaseUser.getUid())
            .set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "User record added with ID: " + firebaseUser.getUid());
                    goToDetails();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                    Snackbar.make(findViewById(R.id.signup_layout), "Google sign up failed please try again", Snackbar.LENGTH_SHORT).show();
                }
            });
    }
}
