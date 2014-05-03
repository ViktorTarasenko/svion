package com.svion.client;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MyActivity extends Activity {
    private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;
    private static final String SCOPE =
            "oauth2:https://www.googleapis.com/auth/userinfo.email";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public void onDestroy() {
        super.onStop();
        Websockethandler.closeWebsocket();
        Log.d("myActivity","closing websocket");
    }
    public void askUserAccountAndAuthorize() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //System.out.println("code is");
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                String mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                if (mEmail == null) {
                    askUserAccountAndAuthorize();
                }
                else {
                    if (isDeviceOnline()) {
                        new FetchTokenAndAuthorizeTask(this,mEmail,SCOPE).execute();
                    } else {
                        Toast.makeText(this, R.string.offline, Toast.LENGTH_LONG).show();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.pick_account, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean isDeviceOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    public void handleException(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    int statusCode = ((GooglePlayServicesAvailabilityException)e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,MyActivity.this
                            ,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    Intent intent = ((UserRecoverableAuthException)e).getIntent();
                    startActivityForResult(intent,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }

                else {
                    final String text = e.getMessage();
                    MyActivity.this.runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyActivity.this, text, Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            }
        });
    }

    public void menu_search_game_click(View v){
            //переходим с первой на вторую активность
           // Intent intent = new Intent(this, SearchGame.class);
           // startActivity(intent);
            //finish();
        new GameInitializer(MyActivity.this).execute();
    }

    public void menu_settings_click(View v){
        //переходим с первой на вторую активность
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        //finish();
    }
    public void menu_help_click(View v){
        //переходим с первой на вторую активность
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
        //finish();
    }

    public void menu_exit_click(View v){
        moveTaskToBack(true);
        super.onBackPressed();
    }

}
