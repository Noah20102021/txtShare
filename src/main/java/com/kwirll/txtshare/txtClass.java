package com.kwirll.txtshare;

import lombok.Getter;

import java.util.Date;

public class txtClass {
    @Getter
    private String key;
    @Getter
    private String value;
    @Getter
    private Date expires;
    @Getter
    private String pass;

    public txtClass(String key, String value, Date expires, String pass) {
        this.key = key;
        this.value = value;
        this.expires = expires;
        this.pass = pass;
    }

}
