package net.fabiopichler.omicroncep;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MobileAppInterface {

    private MainActivity mMainActivity;
    private WebView m_WebView;

    MobileAppInterface(MainActivity mainActivity, WebView webView) {
        mMainActivity = mainActivity;
        m_WebView = webView;
    }

    @JavascriptInterface
    public void closeApp() {
        m_WebView.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.finishAffinity();
            }
        });
    }

    @JavascriptInterface
    public void openDrawer() {
        m_WebView.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.openDrawer();
            }
        });
    }

    @JavascriptInterface
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mMainActivity, toast, Toast.LENGTH_SHORT).show();
    }
}
