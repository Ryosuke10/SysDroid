package com.example.imairy.sysdroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fab;
    CardView cvAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_register);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        cvAdd = (CardView)findViewById(R.id.cv_add);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onClick(View v){
        if(v == findViewById(R.id.bt_go)){
            final String username = ((EditText)findViewById(R.id.et_username)).getText().toString();
            String password = ((EditText)findViewById(R.id.et_password)).getText().toString();
            String repeatPassword = ((EditText)findViewById(R.id.et_repeatpassword)).getText().toString();
            /* バリデーションチェック
             * 値が正常な場合Regist
              * */
            Log.d("clickmis",username);
            Log.d("clickmis",password + repeatPassword);
            if(username.trim().length() != 0 && password.trim().length() != 0 && password.equals(repeatPassword)){
                Log.d("ins","OK");
                Regist regist = new Regist();
                //非同期通信の結果を受け取ったら開始
                regist.setOnCallBack(new Regist.CallBackTask(){
                    @Override
                    public void CallBack(UserBean userBean) {
                        super.CallBack(userBean);
                        // resultにはdoInBackgroundの返り値が入ります。
                        // ここからAsyncTask処理後の処理を記述します。
                        Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
                        if(userBean.getId() != "u0000") {
                            Log.i("AsyncTaskCallback", "create" + userBean.getId());
                            returnMainActivity(userBean);
                        }else{
                            Log.i("AsyncTaskCallback", "miss");
                            popToast();
                        }
                    }

                });
                //非同期通信開始
                regist.execute(username,password);
            }
        }else{
            Log.d("clickmis","miss");
        }
    }

    public void returnMainActivity(UserBean userBean){
        //画面遷移&アニメーション
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("UserBean",userBean);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }
    }

    public void popToast(){
        Toast.makeText(this,"ユーザーの作成に失敗しました。", Toast.LENGTH_SHORT).show();
    }
}
