package com.example.imairy.sysdroid;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

public class ItemListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final String genre[] = {"フード","ファッション","ホビー"};
        final ArrayList<String> list = new ArrayList<String>();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(position + 1, list);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return genre[position];
            }

            @Override
            public int getCount() {
                return genre.length;
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOfafset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("ItemListActivity", "onPageSelected() position="+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class ItemFragment extends Fragment {
        private GridView mGridView;
        private ArrayList<String> mArrayList = new ArrayList<String>();
        public ItemFragment() {
        }

        public static ItemFragment newInstance(int page,ArrayList<String> list) {
            ItemFragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putStringArrayList("array",list);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            if(args != null){
                mArrayList = args.getStringArrayList("array");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);
            return inflater.inflate(R.layout.item_grid,container,false);
            /*View view = inflater.inflate(R.layout.item_grid, container, false);
            ArrayList<String> list = new ArrayList<String>();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
            return view;*/
        }

        @Override
        public void onViewCreated(View view,Bundle savedInstanceState){
            super.onViewCreated(view,savedInstanceState);
            mGridView = (GridView)view.findViewById(R.id.gridView);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mArrayList);
            mGridView.setAdapter(adapter);
        }
    }
}