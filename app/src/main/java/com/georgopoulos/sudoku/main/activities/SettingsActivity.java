package com.georgopoulos.sudoku.main.activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.georgopoulos.sudoku.R;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup difficultyGroup;
    RadioButton easyRB, mediumRB, hardRB, impossibleRB;
    String difficultyString = "easy";
    Intent intent = new Intent();
    Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("");

        setTitle("Settings");

        difficultyGroup = (RadioGroup) findViewById(R.id.difficultyGroup);
        easyRB = (RadioButton) findViewById(R.id.easy);
        mediumRB = (RadioButton) findViewById(R.id.medium);
        hardRB = (RadioButton) findViewById(R.id.hard);
        impossibleRB = (RadioButton) findViewById(R.id.impossible);
        acceptButton = (Button) findViewById(R.id.acceptButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("difficulty", difficultyString );
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        difficultyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.easy:
                        difficultyString = "easy";
                        break;
                    case R.id.medium:
                        difficultyString = "medium";
                        break;
                    case R.id.hard:
                        difficultyString = "hard";
                        break;
                    case R.id.impossible:
                        difficultyString = "impossible";
                        break;

                }

            }


        });


    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
