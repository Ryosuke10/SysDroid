package com.example.imairy.sysdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


    }

    @Override
    public void onClick(View v){
        Button b = (Button)findViewById(R.id.testbutton);
        b.setText("click");
        TestPHPAccess php = new TestPHPAccess();

        Log.d("php1","aaa");
        Log.d("php2",php.execute("a").toString());
    }

}
