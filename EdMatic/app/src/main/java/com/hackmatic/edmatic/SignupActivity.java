package com.hackmatic.edmatic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    TextView mSignIn;
    Button mGoogleAuth;
    Button mTwitterAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                Intent intent = new Intent(SignupActivity.this, DetailActivity.class);
                startActivity(intent);
                finish();
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
}
