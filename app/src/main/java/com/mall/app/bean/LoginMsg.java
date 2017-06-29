package com.mall.app.bean;

import java.io.Serializable;

/**
 */
public class LoginMsg implements Serializable {


    private String tel;
    private String token;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
