package com.hackmatic.edmatic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    Spinner mGrade;
    Spinner mLevels;
    Button mSkip;
    Button mSubmit;
    private ArrayAdapter<String> mLevelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSkip = (Button) findViewById(R.id.details_skip);
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSubmit = (Button) findViewById(R.id.details_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addListenerOnGradeSelection();
        loadLevels(0);
    }

    private void addListenerOnGradeSelection() {
        mGrade = (Spinner) findViewById(R.id.grade);
        mGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(parent.getContext(),
                // "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                // Toast.LENGTH_SHORT).show();

                mLevelAdapter.clear();
                mLevelAdapter.addAll(getLevelByGrade(position));
                mLevelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> getLevelByGrade(int position) {
        List<String> list = new ArrayList<String>();
        if(position == 0 || position == 1 || position == 2) {
            list.add("1");
            list.add("2");
            list.add("3");
        } else if(position == 3) {
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("6");
            list.add("7");
        }
        return list;
    }

    private void loadLevels(int grade) {
        mLevels = (Spinner) findViewById(R.id.level);
        mLevelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getLevelByGrade(grade));
        mLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLevels.setAdapter(mLevelAdapter);
    }


}
