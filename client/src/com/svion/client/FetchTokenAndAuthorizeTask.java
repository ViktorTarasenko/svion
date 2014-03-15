package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.Toast;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.UserRecoverableException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

public class FetchTokenAndAuthorizeTask extends AsyncTask
{
        private MyActivity myActivity;
        String mScope;
        String mEmail;
        public FetchTokenAndAuthorizeTask(MyActivity activity, String name, String scope) {
            this.myActivity = activity;
            this.mScope = scope;
            this.mEmail = name;
        }
    @Override
    protected Object doInBackground(Object[] params) {
         String token = null;
        try {
            myActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myActivity, "beginning authorization....", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            token = fetchToken();
            /*final String tk = token;
            myActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myActivity,tk, Toast.LENGTH_SHORT).show();
                        }
                    }
            );*/
            DefaultHttpClient httpclient = CookieStoreHandler.getInstance().getHttpClient();

            HttpParams httpParams = httpclient.getParams();

            httpParams.setParameter("http.protocol.handle-redirects",false);
            HttpGet authorizeRequest = new HttpGet(myActivity.getResources().getString(R.string.server)+"/authorize/google?token="+ URLEncoder.encode(token, "UTF-8"));
            authorizeRequest.setParams(httpParams);
            final HttpResponse response = httpclient.execute(authorizeRequest);
            response.getEntity().consumeContent();
           /* final Integer code = response.getStatusLine().getStatusCode();
            myActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myActivity,code.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );*/
            myActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myActivity,"server responded with code "+response.getStatusLine().getStatusCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                myActivity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(myActivity, "successfully logged in", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent startGameActivityIntent = new Intent(myActivity, GameActivity.class);
                myActivity.startActivity(startGameActivityIntent);

            }
            return response.getStatusLine().getStatusCode();
        }
        catch (UserRecoverableAuthException e) {
            myActivity.handleException(e);
        }
         catch (GoogleAuthException e) {
             myActivity.handleException(e);
         }
        catch (IOException e) {
            myActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(myActivity, "io exception occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
         }
        finally {
          return  null;
    }

    }
    protected String fetchToken() throws UserRecoverableAuthException, GoogleAuthException,IOException {
        return GoogleAuthUtil.getToken(myActivity, mEmail, mScope);
    }

}
