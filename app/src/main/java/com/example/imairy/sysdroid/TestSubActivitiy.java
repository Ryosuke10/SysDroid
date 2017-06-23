package com.example.imairy.sysdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestSubActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub_activitiy);


        String sharedElementName = "image";
        findViewById(R.id.textView7).setTransitionName(sharedElementName);
    }
}
