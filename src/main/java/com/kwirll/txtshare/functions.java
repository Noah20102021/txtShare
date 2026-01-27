package com.kwirll.txtshare;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class functions {

    static HashMap<String, txtClass> txtMap = new HashMap<String, txtClass>();

    public static Boolean push(String key, String value) {
        if (txtMap.containsKey(key)){
            txtClass temp = txtMap.get(key);
            if (temp.getExpires().after(new Date())) {
                return false;
            } else{
                txtMap.remove(key);
            }
        }
        txtMap.put(key, new txtClass(key, value, new Date(System.currentTimeMillis() + 5000 * 60), ""));
        return true;
    }

    public static String get(String key) {
        txtClass temp = txtMap.get(key);
        if (temp == null) return null;
        if (!Objects.equals(temp.getPass(), "")) return null;
        return temp.getValue();
    }

}
