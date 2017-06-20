package com.example.imairy.sysdroid;

import java.io.Serializable;

/**
 * Created by imairy on 2017/06/20.
 */

public class UserBean implements Serializable {
    private String id;
    private String name;
    private String pass;


    public UserBean() {
        id = "u0000";
        name = "七誌";
        pass = "PASSW";
    }

    public UserBean(String id, String pass, String name) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
