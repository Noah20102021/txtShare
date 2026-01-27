package com.kwirll.txtshare;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class functions {

    static HashMap<String, txtClass> txtMap = new HashMap<String, txtClass>();

    public static Boolean push(String key, String value, String pass) {
        if (txtMap.containsKey(key)){
            txtClass temp = txtMap.get(key);
            if (temp.getExpires().after(new Date())) {
                return false;
            } else{
                txtMap.remove(key);
            }
        }
        txtMap.put(key, new txtClass(key, value, new Date(System.currentTimeMillis() + 5000 * 60), pass));
        return true;
    }

    public static String get(String key, String pass) {
        txtClass temp = txtMap.get(key);
        if (temp == null) return null;
        if (!Objects.equals(temp.getPass(), "")) {
            if (Objects.equals(temp.getPass(), pass)){
                return temp.getValue();
            }else {
                return null;
            }
        }else {
            return temp.getValue();
        }
    }

}
