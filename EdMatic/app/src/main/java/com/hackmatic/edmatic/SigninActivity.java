package com.hackmatic.edmatic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SigninActivity extends AppCompatActivity {

    TextView mSignup;
    Button mGoogleAuth;
    Button mTwitterAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mSignup = (TextView) findViewById(R.id.signup_prompt);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mGoogleAuth = (Button) findViewById(R.id.googleAuth);
        mGoogleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mTwitterAuth = (Button) findViewById(R.id.twitterAuth);
        mTwitterAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
