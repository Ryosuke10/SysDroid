package com.example.imairy.sysdroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by imairy on 2017/06/20.
 */

public class Regist extends AsyncTask<String,Void,UserBean> {

    private Regist.CallBackTask callbacktask;

    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(UserBean userBean) {
        }
    }

    public void setOnCallBack(com.example.imairy.sysdroid.Regist.CallBackTask _cbj) {
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

        String registData = "{\"user\":{" +
                "\"name\":\"" + contents[0] + "\"," +
                "\"pass\":\"" + contents[1] + "\"}}";
        ConnectionServer connectionServer = new ConnectionServer();
        JSONObject jsonObject = connectionServer.connection("regist.php",registData);
        try {
            //Beanにユーザー情報格納
            userBean = new UserBean(jsonObject.getString("id"), jsonObject.getString("pass"), jsonObject.getString("name"));
            Log.d("jsonget", jsonObject.getString("name"));
//            JSONArray jsonArray = new JSONArray(buffer);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("HTTP REQ", jsonObject.getString("name"));
//            }
        } catch (JSONException e) {
        e.printStackTrace();
            Log.d("LOGINERROR", "loginエラーです");
            //TODO:loginエラー時の処理追記
        }
        return userBean;
    }

    @Override
    protected void onPostExecute(UserBean userBean) {
        super.onPostExecute(userBean);
        callbacktask.CallBack(userBean);
    }
}