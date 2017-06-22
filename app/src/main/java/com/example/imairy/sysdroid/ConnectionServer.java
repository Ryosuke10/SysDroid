package com.example.imairy.sysdroid;

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

/**
 * Created by imairy on 2017/06/21.
 */

public class ConnectionServer {

    public ConnectionServer() {
    }

    /**
     * Webサーバーと通信するメソッド
     * @param destination
     * @param postData
     * @return jsonObject 通信に成功時受信した値,失敗時null
     */
    public JSONObject connection(String destination,String postData){
        HttpURLConnection con = null;
        JSONObject jsonObject = null;
        try {
            String buffer = "";
            URL url = new URL("http://" + IPAddress.IPADDRESS + ":80/" + destination);

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
            con.setReadTimeout(10000);
            con.setConnectTimeout(10000);

            OutputStream os = con.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.write(postData.getBytes("UTF-8"));
            con.connect();
            ps.close();
            os.close();

            Log.d("HTTPLOG", Integer.toString(con.getResponseCode()));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer);
            }

            Log.d("return_Json", sb.toString());

            if(sb.toString() != null) {
                //サーバーから受信した文字列をJSONObjectに変換
                jsonObject = new JSONObject(sb.toString());
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return jsonObject;
    }
}
