package com.example.imairy.sysdroid;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        UserBean userBean = (UserBean)getIntent().getSerializableExtra("UserBean");
        ArrayList<ItemBean> itemBeanArrayList = (ArrayList<ItemBean>)getIntent().getSerializableExtra("ItemList");

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final String genre[] = {"フード","ファッション","ホビー"};

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
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

        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

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
    public void onPageScrolled(int position, float positionOfafset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("ItemListActivity", "onPageSelected() position="+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class ItemFragment extends Fragment implements AdapterView.OnItemClickListener{
        ArrayList<ItemBean> itemBeanArrayList;

        public ItemFragment() {
        }

        public static ItemFragment newInstance(int position) {
            switch(position){
                case 0:
                    ItemFragment fragment0 = new ItemFragment();
                    Bundle args0 = new Bundle();
                    args0.putInt("ARG_POSITION",position);
                    fragment0.setArguments(args0);
                    return fragment0;
                case 1:
                    ItemFragment fragment1 = new ItemFragment();
                    Bundle args1 = new Bundle();
                    args1.putInt("ARG_POSITION",position);
                    fragment1.setArguments(args1);
                    return fragment1;
                case 2:
                    ItemFragment fragment2 = new ItemFragment();
                    Bundle args2 = new Bundle();
                    args2.putInt("ARG_POSITION",position);
                    fragment2.setArguments(args2);
                    return fragment2;
            }/*
            ItemFragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putInt("ARG_POSITION",position);
            fragment.setArguments(args);
            return fragment;*/
            return null;
        }

        private ArrayAdapter<String> adapter;
        //private ImageAdapter[] adapter = new ImageAdapter[3];
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            itemBeanArrayList = (ArrayList<ItemBean>) getActivity().getIntent().getSerializableExtra("ItemList");
            Log.d("aa",itemBeanArrayList.get(0).getItem_name());
            int page = getArguments().getInt("ARG_POSITION",0);

            adapter = new ArrayAdapter<String>(getActivity(),R.layout.item_list,R.id.item_name);
            //createDataList(表示するデータの個数,ページ番号)
            adapter.addAll(createDataList(100,page,itemBeanArrayList));

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View parent = inflater.inflate(R.layout.item_list_main,container,false);

            AbsListView list = (AbsListView) parent.findViewById(R.id.gridView);
            list.setAdapter(adapter);
            list.setOnItemClickListener(this);
            return parent;
        }
        private static List<String> createDataList(int counts,int page,ArrayList<ItemBean> itemBeanArrayList){
            List[] list = new ArrayList[3];
            for(int i = 0;i<list.length;i++){
                list[i] = new ArrayList();
            }
            switch (page) {
                case 0:
                    //左タブ（
                    //dbhyouji
                    for(ItemBean itemBean : itemBeanArrayList) {
                        list[0].add(itemBean.getItem_name());
                    }
                    //
                    break;
                case 1:
                    for(ItemBean itemBean : itemBeanArrayList) {
                        if(itemBean.getCategory_id().equals("c0001")) {
                            list[1].add(itemBean.getItem_name());
                        }
                    }
                    break;
                case 2:
                    for(ItemBean itemBean : itemBeanArrayList) {
                        if(itemBean.getCategory_id().equals("c0002")) {
                            list[2].add(itemBean.getItem_name());
                        }
                    }
                default:
                    break;
            }
            return list[page];
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // 更新対象のViewを取得
            View targetView = parent.getChildAt(position);
            // getViewで対象のViewを更新
            parent.getAdapter().getView(position, targetView, parent);
            View iv =  parent.getChildAt(position) ;

            /////parent.getAdapter().getItem(position)
            String sharedElementName = "image";
            iv.setTransitionName(sharedElementName);
            String img = parent.getAdapter().getItem(position).toString();

            Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
            intent.putExtra("ItemBean",itemBeanArrayList.get(position));
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(getActivity(), iv, sharedElementName)
                            .toBundle());

        }
    }
}

class ImageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private String[] names;
    private Bitmap[] bmapIcons;

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }

    public ImageAdapter(Context context, int itemLayoutId, String[] iconNames, int[] iconItems ){
        inflater = LayoutInflater.from(context);
        layoutId = itemLayoutId;
        names = iconNames;
        bmapIcons = new Bitmap[iconItems.length];

        for(int i=0;i<iconItems.length;i++) {
            bmapIcons[i] = BitmapFactory.decodeResource(context.getResources(), iconItems[i]);
        }
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(layoutId,null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon_item);
            holder.text = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(String.valueOf(position+1)+":"+names[position]);
        holder.icon.setImageBitmap(bmapIcons[position]);

        return convertView;
    }

    @Override
    public int getCount(){
        return names.length;
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }
}