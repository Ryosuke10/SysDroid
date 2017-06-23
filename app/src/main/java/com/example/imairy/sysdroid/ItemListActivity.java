package com.example.imairy.sysdroid;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ItemListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final String genre[] = {"フード","ファッション","ホビー"};

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return ItemFragment.newInstance(position);
                }
                return ItemFragment.newInstance(position);
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
        public ItemFragment() {
        }

        public static ItemFragment newInstance(int position) {
            ItemFragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putInt("ARG_POSITION",position);
            fragment.setArguments(args);
            return fragment;
        }

        private ArrayAdapter<String> adapter;
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            adapter = new ArrayAdapter<String>(getActivity(),R.layout.item_list,R.id.text_view);
            adapter.addAll(createDataList(100));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View parent = inflater.inflate(R.layout.item_grid,container,false);

            AbsListView list = (AbsListView) parent.findViewById(R.id.gridView);
            list.setAdapter(adapter);
            return parent;
        }
        private static List<String> createDataList(int counts){
            List<String> list = new ArrayList<String>();
            for(int i= 0; i<counts;i++){
                list.add("i="+i);
            }
            return list;
        }
    }
}