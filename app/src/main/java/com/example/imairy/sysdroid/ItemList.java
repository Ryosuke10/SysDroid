package com.example.imairy.sysdroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by imairy on 2017/06/26.
 */

public class ItemList extends AsyncTask<String, Integer, ArrayList<ItemBean>> {

    private CallBackTask callbacktask;

    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(ArrayList<ItemBean> itemBeanArrayList) {
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
    protected ArrayList<ItemBean> doInBackground(String... contents) {

        ArrayList<ItemBean> itemBeanArrayList = new ArrayList<ItemBean>();
        ItemBean itemBean;

        Log.d("cont0",contents[0]);
        String callMethod = "{" +
                "\"method\":\"" + contents[0] + "\"}";
        ConnectionServer connectionServer = new ConnectionServer();

        JSONObject jsonObject = connectionServer.connection("Main.php",callMethod);

        try {
            if(jsonObject != null) {
                //Beanにユーザー情報格納
                for(int i = 0 ; i < jsonObject.length() ; i++){
                    itemBean = new ItemBean(jsonObject.getJSONObject(String.valueOf("a"+i)));
                    itemBeanArrayList.add(itemBean);
                }
            }else{
                Log.e("商品一覧","取得失敗");
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.e("Json取得エラー", "取得失敗");
        }catch(NumberFormatException e){
            e.printStackTrace();
            Log.e("NumberFormatError:","キャスト失敗");
        }

        return itemBeanArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemBean> itemBeanArrayList) {
        super.onPostExecute(itemBeanArrayList);
        callbacktask.CallBack(itemBeanArrayList);
    }

}


