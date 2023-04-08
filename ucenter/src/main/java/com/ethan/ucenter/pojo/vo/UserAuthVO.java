package com.ethan.ucenter.pojo.vo;

/**
 * @author Ethan 2023/3/11
 */
public class UserAuthVO {

    private String token;

    public UserAuthVO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
