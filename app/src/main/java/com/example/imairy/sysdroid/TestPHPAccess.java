package com.example.imairy.sysdroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
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

/**
 * Created by imairy on 2017/06/13.
 */



public class TestPHPAccess extends AsyncTask<String, Integer, Integer> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }


    @Override
    protected Integer doInBackground(String... contents) {

        Log.d("connect!!", "TestPHP!!");
        final String json =
                "{\"user\":{" +
                        "\"name\":\"name1\","+
                        "\"password\":\"password\","+
                        "\"password_confirmation\":\"password\""+
                        "}}";
        String buffer = "";
        HttpURLConnection con = null;

        try {

            URL url = new URL("http://10.0.14.224:80/dbconnect.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setInstanceFollowRedirects(false);
            con.setRequestProperty("Accept-Language", "jp");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            OutputStream os = con.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.print(json);
            ps.close();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer);
            }

            Log.d("return_Json",sb.toString());
//            JSONArray jsonArray = new JSONArray(buffer);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("HTTP REQ", jsonObject.getString("name"));
//            }
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
        return 1;
    }

//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        // doInBackground後処理
//    }

}

