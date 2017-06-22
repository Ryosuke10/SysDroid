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


public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;

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
                login.setOnCallBack(new Login.CallBackTask(){

                    @Override
                    public void CallBack(UserBean userBean) {
                        super.CallBack(userBean);
                        // resultにはdoInBackgroundの返り値が入ります。
                        // ここからAsyncTask処理後の処理を記述します。
                        Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
                        if(UserLoginData.isLogin) {
                            Log.i("AsyncTaskCallback", "ItemLIstへ");
                            itemListTransition(userBean);
                        }else{
                            Log.i("AsyncTaskCallback", "POPTOAST");
                            popToast();
                        }
                    }

                });
                login.execute(id,pass);
                //処理が終わるまでストップ
        }
    }

    public void itemListTransition(UserBean userBean){
        //画面遷移&アニメーション
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent i2 = new Intent(this,ItemListActivity.class);
        i2.putExtra("UserBean",userBean);
        startActivity(i2, oc2.toBundle());
    }

    public void popToast(){
        Toast.makeText(this,"ログインに失敗しました。\nIDかパスワードが間違っています。", Toast.LENGTH_SHORT).show();
    }
}
