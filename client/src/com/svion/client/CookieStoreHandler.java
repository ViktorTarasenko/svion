package com.svion.client;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by victor on 14.03.14.
 */
public class CookieStoreHandler {
    private static CookieStoreHandler cookieStoreHandler;
    private CookieStoreHandler() {
        //httpClient.setCookieStore(cookieStore);
    }
    public static CookieStoreHandler getInstance() {
        if (cookieStoreHandler == null) {
            cookieStoreHandler = new CookieStoreHandler();
        }
        return cookieStoreHandler;
    }
    private CookieStore cookieStore = new BasicCookieStore();
    private DefaultHttpClient httpClient = new DefaultHttpClient();
    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public DefaultHttpClient getHttpClient() {
        return httpClient;
    }
}
