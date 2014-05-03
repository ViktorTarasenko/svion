package com.svion.client;


import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * Created by victor on 14.03.14.
 */
public class HttpClientHandler {
    private static HttpClientHandler httpClientHandler;
    private HttpClientHandler() {
        httpClient = new DefaultHttpClient();
        //httpClient.setCookieStore(cookieStore);

    }
    public static HttpClientHandler getInstance() {
        if (httpClientHandler == null) {
            httpClientHandler = new HttpClientHandler();
        }
        return httpClientHandler;
    }
    private CookieStore cookieStore = new BasicCookieStore();

    private DefaultHttpClient httpClient = new DefaultHttpClient();
    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public DefaultHttpClient getHttpClient() {
        return httpClient;
    }
    public String getSessionId(){
        for (Cookie cookie : httpClient.getCookieStore().getCookies()){
            if (cookie.getName().equals("JSESSIONID")) {
                return cookie.getValue();
            }
        }
        return null;
    }



}
