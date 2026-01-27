package com.kwirll.txtshare;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class config {

    public static String host;

    static {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            host = "http://" + ip + ":8080";
        } catch (UnknownHostException e) {
            host = "http://localhost:8080";
        }
    }
}