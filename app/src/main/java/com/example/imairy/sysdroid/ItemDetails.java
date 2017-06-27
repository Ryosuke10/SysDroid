package com.example.imairy.sysdroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by imairy on 2017/06/27.
 */

public class ItemDetails extends AsyncTask<String,Void,Bitmap> {

    private ItemDetails.CallBackTask callbacktask;

    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(Bitmap bitmap) {
        }
    }

    public void setOnCallBack(com.example.imairy.sysdroid.ItemDetails.CallBackTask _cbj) {
        callbacktask = _cbj;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }


    @Override
    protected Bitmap doInBackground(String... contents) {
        URL url;
        Bitmap oBmp;
        InputStream istream;
        try {
            //画像のURLを直うち
            url = new URL("http://" + IPAddress.IPADDRESS + ":80" + contents[0]);
            //インプットストリームで画像を読み込む
            istream = url.openStream();
            //読み込んだファイルをビットマップに変換
            oBmp = BitmapFactory.decodeStream(istream);
            //インプットストリームを閉じる
            istream.close();
            return oBmp;
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return Bitmap.createBitmap(0,0, Bitmap.Config.ALPHA_8);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        callbacktask.CallBack(bitmap);
    }
}
