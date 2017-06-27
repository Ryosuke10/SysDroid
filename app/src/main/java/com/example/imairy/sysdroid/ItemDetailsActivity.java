package com.example.imairy.sysdroid;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ItemBean itemBean = (ItemBean)getIntent().getSerializableExtra("ItemBean");

        String sharedElementName = "image";
        findViewById(R.id.itemView).setTransitionName(sharedElementName);
        TextView itemName = (TextView)findViewById(R.id.itemName);
        TextView price = (TextView)findViewById(R.id.itemPrice);
        TextView itemDescription = (TextView)findViewById(R.id.itemDescription);
        itemName.setText(itemBean.getItem_name());
        price.setText(String.valueOf("￥" + itemBean.getPrice()));
        itemDescription.setText("テスト用商品です。");
 //        ImageView im = (ImageView)findViewById(R.id.itemView);
//        Bitmap image = BitmapFactory.decodeByteArray(getIntent().getStringExtra("ItemBean").getBytes(),0,getIntent().getStringExtra("ItemBean").getBytes().length);

        ItemDetails itemDetails = new ItemDetails();
        //非同期通信の結果を受け取ったら開始
        itemDetails.setOnCallBack(new ItemDetails.CallBackTask(){
            @Override
            public void CallBack(Bitmap bitmap) {
                super.CallBack(bitmap);
                // resultにはdoInBackgroundの返り値が入ります。
                // ここからAsyncTask処理後の処理を記述します。
                Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
                if(bitmap != null) {
                    Log.i("AsyncTaskCallback", "img" + bitmap.toString());
                    setImageBitmap(bitmap);
                }else{
                    Log.i("AsyncTaskCallback", "miss");
                }
            }

        });
        //非同期通信開始
        itemDetails.execute(itemBean.getImage());

//        try {
//            img.setImageBitmap(image);
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }

    public void setImageBitmap(Bitmap bitmap){
        ImageView imageView = (ImageView)findViewById(R.id.itemView);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.button3)){
            popToast("購入しました。商品一覧画面へ戻ります。");
            this.finish();
        }
    }

    public void popToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
}
