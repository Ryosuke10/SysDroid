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
        String test = "{\"user\":{" +
                "\"id\":\"" + contents[0] + "\","+
                "\"pass\":\"" + contents[1] + "\"}}";
        String buffer = "";
        HttpURLConnection con = null;

        try {

            URL url = new URL("http://10.0.14.151:80/login.php");

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setInstanceFollowRedirects(false);
            con.setRequestProperty("Accept-Language", "jp");
            con.setDoOutput(true);
            con.setDoInput(true);
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");//get,postで送りたい場合
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");//jsonで送りたい場合
            con.setChunkedStreamingMode(0);
            // タイムアウト
            con.setReadTimeout(100000);
            con.setConnectTimeout(100000);

            OutputStream os = con.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.write(test.getBytes("UTF-8"));
            con.connect();
            ps.close();
            os.close();

            Log.d("HTTPLOG",Integer.toString(con.getResponseCode()));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer);
            }

            Log.d("return_Json",sb.toString());

            //サーバーから受信した文字列をJSONObjectに変換
            JSONObject jsonObject = new JSONObject(sb.toString());

            try {
                //Beanにユーザー情報格納
                userBean = new UserBean(jsonObject.getString("id"), jsonObject.getString("pass"), jsonObject.getString("name"));
                Log.d("jsonget", jsonObject.getString("name"));
//            JSONArray jsonArray = new JSONArray(buffer);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("HTTP REQ", jsonObject.getString("name"));
//            }
                isLogin = true;
            }catch (JSONException e) {
                e.printStackTrace();
                Log.d("LOGINERROR", "loginエラーです");
                //TODO:loginエラー時の処理追記
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            con.disconnect();
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

