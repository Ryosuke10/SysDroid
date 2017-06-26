package com.example.imairy.sysdroid;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;
    Intent i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btGo = (Button)findViewById(R.id.bt_go);
        cv = (CardView) findViewById(R.id.cv);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        if(getIntent().hasExtra("UserBean")) {
            UserBean userBean = (UserBean) getIntent().getSerializableExtra("UserBean");
            etUsername.setText(userBean.getId());
            etPassword.setText(userBean.getPass());
        }

    }

    public void onClick(View view) throws InterruptedException {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                Login login = new Login();
                String id = ((EditText)findViewById(R.id.et_username)).getText().toString();
                String pass = ((EditText)findViewById(R.id.et_password)).getText().toString();
                i2 = new Intent(this,ItemListActivity.class);

                login.setOnCallBack(new Login.CallBackTask(){

                    @Override
                    public void CallBack(UserBean userBean) {
                        super.CallBack(userBean);
                        // resultにはdoInBackgroundの返り値が入ります。
                        // ここからAsyncTask処理後の処理を記述します。
                        Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
                        if(UserLoginData.isLogin) {
                            Log.i("AsyncTaskCallback", "ItemLIstへ");
                            getItemList(userBean);
                        }else{
                            Log.i("AsyncTaskCallback", "POPTOAST");
                            popToast("ログインに失敗しました。\nIDかパスワードが間違っています。");
                        }
                    }

                });
                login.execute(id,pass);
                //処理が終わるまでストップ
        }
    }

    public void getItemList(UserBean userBean){
        i2.putExtra("UserBean",userBean);

        //商品一覧取得
        ItemList itemList = new ItemList();
        itemList.setOnCallBack(new ItemList.CallBackTask(){

            @Override
            public void CallBack(ArrayList<ItemBean> itemBeanArrayList) {
                super.CallBack(itemBeanArrayList);
                // resultにはdoInBackgroundの返り値が入ります。
                // ここからAsyncTask処理後の処理を記述します。
                Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
                if(!itemBeanArrayList.isEmpty()) {
                    Log.i("AsyncTaskCallback", "ItemLIst取得完了");
                    itemListTransition(itemBeanArrayList);//ここから飛ばす。今現在の処理削除。
                }else{
                    Log.i("AsyncTaskCallback", "POPTOAST");
                    popToast("商品一覧の取得に失敗しました。");
                }
            }

        });
        itemList.execute("getItemList");
    }

    public void popToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void itemListTransition(ArrayList<ItemBean> itemBeanArrayList){
        i2.putExtra("ItemList",itemBeanArrayList);

        //画面遷移&アニメーション
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(i2, oc2.toBundle());
    }
}
