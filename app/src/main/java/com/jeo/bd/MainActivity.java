package com.jeo.bd;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private static final String SHARED_PREFERENCE_ADDRESS = "address";
    private static final String SHARED_PREFERENCE_FOLDER = "folder";
    private static final String SHARED_PREFERENCE_NAME = "SELF_URL";
    private static final String SHARED_PREFERENCE_PORT = "port";
    private String address;
    private String folder;
    private String port;
    SharedPreferences sharedPrefs;
    private WebView webview;

    private Boolean checkConnect(int paramInt, String paramString) {
        return new URLAvailability().isConnect(paramInt, paramString);
    }

    private void goUrl(String paramString) {
        this.webview.loadUrl(paramString);
    }

    public static boolean isUrl(String paramString) {
        if (!TextUtils.isEmpty(paramString)) {
            Matcher localMatcher;
            localMatcher = Pattern.compile("^(http|www|ftp|)?(://)?(//w+(-//w+)*)(//.(//w+(-//w+)*))*((://d+)?)(/(//w+(-//w+)*))*(//.?(//w)*)(//?)?(((//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*(//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*)*(//w*)*)$").matcher(paramString);
            return localMatcher.find();
        }
        return false;
    }

    String str;

    private void showDialog() {
        new MyCustomDialog(this, "访问失败,请设置连接参数!", this.address, this.port, this.folder, new MyCustomDialog.OnCustomDialogListener() {
            public void back(String add, String por, String fol) {
                SharedPreferences.Editor localEditor = MainActivity.this.sharedPrefs.edit();
                localEditor.putString("address", add);
                localEditor.putString("port", por);
                localEditor.putString("folder", fol);
                localEditor.commit();
                str = "http://" + add + ":" + por + "/" + fol + "/w.asp?f=a";
                if (TextUtils.isEmpty(folder)) {
                    str = "http://" + add + ":" + por + "/w.asp?f=a";
                }

                new MyAsyncTask().execute(str);

//                if (!checkConnect(60000, str).booleanValue()) {
//                    showDialog();
//                } else {
//                    goUrl(str);
//                }

            }
        }).show();
    }

    class MyAsyncTask extends AsyncTask<String,Integer,Boolean>{
        private String url;
        @Override
        protected Boolean doInBackground(String... params) {
            if (!checkConnect(60000, params[0]).booleanValue()){
                return false;
            }
            url = params[0];
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            if(true){
                goUrl(url);
            }else{
                showDialog();
            }

            super.onPostExecute(aVoid);
        }
    }
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.main);
        this.webview = ((WebView) findViewById(R.id.webview));
        this.webview.setWebViewClient(new HelloWebViewClient());
        this.webview.setWebViewClient(new HelloWebViewClient());
        this.webview.setInitialScale(100);
        WebSettings localWebSettings = this.webview.getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setBuiltInZoomControls(false);
        this.sharedPrefs = getSharedPreferences("SELF_URL", 0);
        this.address = this.sharedPrefs.getString("address", "117.71.252.146");
        this.port = this.sharedPrefs.getString("port", "20082");
        this.folder = this.sharedPrefs.getString("folder", "a");

        str = "http://" + this.address + ":" + this.port + "/" + this.folder + "/w.asp?f=a";
        if (TextUtils.isEmpty(folder)) {
            str = "http://" + this.address + ":" + this.port + "/w.asp?f=a";
        }

        new MyAsyncTask().execute(str);

//        if (!checkConnect(60000, str).booleanValue()) {
//            showDialog();
//        } else {
//            goUrl(str);
//        }

    }

    private class HelloWebViewClient extends WebViewClient {
        private HelloWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
            paramWebView.loadUrl(paramString);
            return true;
        }

    }

    private long exitTime = 0;

    private void back(int type) {
        try {
            if (webview.canGoBack()) {
                webview.goBack();
            } else if (type == 1) {
                if ((System.currentTimeMillis() - exitTime) > 60000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    exitApp();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void exitApp() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            back(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}