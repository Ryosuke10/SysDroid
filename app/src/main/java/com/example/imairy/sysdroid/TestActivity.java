package com.example.imairy.sysdroid;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> list;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        UserBean userBean = (UserBean)getIntent().getSerializableExtra("UserBean");
        list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        gridView = (GridView) findViewById(R.id.test);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        //actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView userId_tv = (TextView)header.findViewById(R.id.userId);
        TextView userName_tv = (TextView)header.findViewById(R.id.userName);
        userName_tv.setText(userBean.getName());
        userId_tv.setText(userBean.getId());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tester2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_item_list) {
            // Handle the camera action
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("gridd",String.valueOf(id));
        Log.d("grid2",String.valueOf(list.get(position)));list.set(position,"aaa");
        /////test
        // 更新対象のViewを取得
        View targetView = gridView.getChildAt(position);
        // getViewで対象のViewを更新
        gridView.getAdapter().getView(position, targetView, gridView);
        View iv =  gridView.getChildAt(position) ;
        /////

        String sharedElementName = "image";
        iv.setTransitionName(sharedElementName);

        Intent intent = new Intent(this, ItemDetailsActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this, iv, sharedElementName)
                        .toBundle());

    }

}
