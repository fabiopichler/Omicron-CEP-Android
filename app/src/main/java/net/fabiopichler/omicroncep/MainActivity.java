package net.fabiopichler.omicroncep;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String Url = "file:///android_asset/www/index.html";

    private DrawerLayout mDrawer;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mWebView = findViewById(R.id.webview);

        mWebView.clearCache(true);
        mWebView.clearHistory();

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new AppWebViewClient(this, mWebView));
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

        mWebView.addJavascriptInterface(new MobileAppInterface(this, mWebView), "Android_MobileApp");

        mWebView.loadUrl(Url);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);

        } else if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDrawer() {
        mDrawer.openDrawer(GravityCompat.END);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.END);

        switch (item.getItemId()) {
            case R.id.menuCepView:
                loadUrl("cep");
                break;

            case R.id.menuAddressView:
                loadUrl("address");
                break;

            case R.id.menuItemWebsite:
                openURL("https://fabiopichler.net/");
                break;

            case R.id.menuItemFacebook:
                openURL("https://www.facebook.com/fabiopichler.net");
                break;

            case R.id.menuItemTwitter:
                openURL("https://twitter.com/FabioPichler");
                break;

            case R.id.menuItemAbout:
                loadUrl("about");
                break;

            case R.id.menuItemExit:
                finishAffinity();
                break;
        }

        return true;
    }

    private void loadUrl(String path) {
        mWebView.loadUrl(URLUtil.stripAnchor(mWebView.getUrl()) + "#" + path);
    }

    private void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
