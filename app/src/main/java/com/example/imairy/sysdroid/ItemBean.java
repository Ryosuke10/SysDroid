package com.example.imairy.sysdroid;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by imairy on 2017/06/26.
 */

public class ItemBean implements Serializable {
    private String item_id;
    private String item_name;
    private int price;
    private String category_id;
    private String image;

    public ItemBean() {
    }

    public ItemBean(JSONObject array) throws JSONException ,NumberFormatException{
        this.item_id = array.getString("item_id");
        this.item_name = array.getString("item_name");
        this.price = Integer.parseInt(array.getString("price"));
        this.category_id = array.getString("category_id");
        this.image = array.getString("image");
    }

    public ItemBean(String item_id, String item_name, int price, String category_id, String image) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.price = price;
        this.category_id = category_id;
        this.image = image;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
