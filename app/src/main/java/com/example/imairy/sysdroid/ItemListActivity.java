package com.example.imairy.sysdroid;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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

public class ItemListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TestFragment.newInstance(position + 1);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "tab " + (position + 1);
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("ItemListActivity", "onPageSelected() position="+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class TestFragment extends Fragment {

        public TestFragment() {
        }

        public static TestFragment newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt("page", page);
            TestFragment fragment = new TestFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int page = getArguments().getInt("page", 0);
            View view = inflater.inflate(R.layout.item_grid, container, false);
            ArrayList<String> list = new ArrayList<String>();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
            switch (page){
                case 1:
                    list.add("A");
                    list.add("B");
                    list.add("C");
                    list.add("D");
                    list.add("E");
                    list.add("F");
                    list.add("G");
                    list.add("H");
                    list.add("I");
                    list.add("J");

                    ((GridView) view.findViewById(R.id.gridView)).setAdapter(adapter);
                    return view;
                case 2:
                    list.add("あ");
                    list.add("い");
                    list.add("う");
                    list.add("え");
                    list.add("お");
                    list.add("か");
                    list.add("き");
                    list.add("く");
                    list.add("け");
                    list.add("こ");
                    ((GridView) view.findViewById(R.id.gridView)).setAdapter(adapter);
                    return view;
                case 3:
                    list.add("1");
                    list.add("2");
                    list.add("3");
                    list.add("4");
                    list.add("5");
                    list.add("6");
                    list.add("7");
                    list.add("8");
                    list.add("9");
                    list.add("10");
                    ((GridView) view.findViewById(R.id.gridView)).setAdapter(adapter);
                    return view;
                default :
                    break;
            }
            return view;
        }
    }
}