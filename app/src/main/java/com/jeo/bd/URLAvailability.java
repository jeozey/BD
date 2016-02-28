package com.jeo.bd;

import java.net.HttpURLConnection;
import java.net.URL;

public class URLAvailability {
    private static HttpURLConnection con;
    private static int state = -1;
    private static URL url;

    public Boolean isConnect(int paramInt, String paramString) {
        if (paramString != null) {
            try {
                url = new URL(paramString);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(paramInt);
                state = con.getResponseCode();
                System.out.println(state);
                if (state == 200) {
                    System.out.println("URL可用！");
                    return true;
                }
            } catch (Exception localException) {
                System.out.println("URL不可用");
            }
            return false;
        } else {
            return false;
        }
    }
}