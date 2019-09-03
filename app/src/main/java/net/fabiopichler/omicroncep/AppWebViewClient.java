package net.fabiopichler.omicroncep;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class AppWebViewClient extends WebViewClient {

    private WebView mWebView;
    private MainActivity mMainActivity;
    private AnimationSet mAnimation;
    private RelativeLayout mRelativeLayout;

    AppWebViewClient(MainActivity mainActivity, WebView webView) {
        mMainActivity = mainActivity;
        mWebView = webView;

        mRelativeLayout = mMainActivity.findViewById(R.id.splashScreen);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1500);
        fadeOut.setDuration(600);

        mAnimation = new AnimationSet(false);
        mAnimation.addAnimation(fadeOut);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!URLUtil.isValidUrl(url))
            return false;

        Uri uri = Uri.parse(url);
        String host = uri.getHost();

        if (host == null || host.matches("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$"))
            return false;

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        view.getContext().startActivity(intent);

        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mRelativeLayout.setAnimation(mAnimation);
                mRelativeLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
}
