package com.example.imairy.sysdroid;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    ArrayList<String> list;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        gridView = (GridView) findViewById(R.id.test);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("gridd",String.valueOf(id));
        Log.d("grid2",String.valueOf(list.get(position)));
        View iv =  gridView.getChildAt(position) ;
        String sharedElementName = "image";
        iv.setTransitionName(sharedElementName);

        Intent intent = new Intent(this, ItemDetailsActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this, iv, sharedElementName)
                        .toBundle());

    }
}
