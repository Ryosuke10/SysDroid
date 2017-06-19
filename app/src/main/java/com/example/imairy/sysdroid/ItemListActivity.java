package com.example.imairy.sysdroid;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ArrayList<String> test = new ArrayList<String>();

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        test.add("a");
        test.add("b");
        test.add("c");
        test.add("d");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.grids_cell,test);
        GridView gv = (GridView)findViewById(R.id.testgrid);
        gv.setAdapter(adapter);
    }
}
