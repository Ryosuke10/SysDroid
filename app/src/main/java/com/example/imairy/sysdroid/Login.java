package com.example.imairy.sysdroid;

/**
 * Created by imairy on 2017/06/19.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Login extends AsyncTask<String, Integer, UserBean> {

    private CallBackTask callbacktask;

    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(UserBean userBean) {
        }
    }

    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }


    @Override
    protected UserBean doInBackground(String... contents) {

        UserBean userBean = new UserBean();
        Log.d("cont0",contents[0]);
        Log.d("cont1",contents[1]);
        boolean isLogin = false;
        String inputData = "{\"user\":{" +
                "\"id\":\"" + contents[0] + "\","+
                "\"pass\":\"" + contents[1] + "\"}}";
        ConnectionServer connectionServer = new ConnectionServer();

        JSONObject jsonObject = connectionServer.connection("login.php",inputData);

        try {
            if(jsonObject != null) {
                //Beanにユーザー情報格納
                userBean = new UserBean(jsonObject.getString("id"), jsonObject.getString("pass"), jsonObject.getString("name"));
                Log.d("jsonget", jsonObject.getString("name"));
                isLogin = true;
            }else{
                Log.e("loginerror","ログイン失敗");
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.e("LOGINERROR", "loginエラーです");
        }

        UserLoginData.isLogin = isLogin;
        return userBean;
    }

    @Override
    protected void onPostExecute(UserBean userBean) {
        super.onPostExecute(userBean);
        callbacktask.CallBack(userBean);
    }

}

