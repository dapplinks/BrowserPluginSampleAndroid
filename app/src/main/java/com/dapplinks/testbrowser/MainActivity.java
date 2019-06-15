package com.dapplinks.testbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.linkto.dappxsdk.DappxBridge;
import com.linkto.dappxsdk.params.SelectParams;

public class MainActivity extends Activity {
    private EditText mEtUrl;
    private Button mBtnEnter;
    private WebView mWvBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mEtUrl = findViewById(R.id.et_url);

        mBtnEnter = findViewById(R.id.btn_enter);
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEtUrl.getText().toString();

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }

                mWvBrowser.loadUrl(url);
            }
        });

        mWvBrowser = findViewById(R.id.wv_browser);

        WebSettings ws = mWvBrowser.getSettings();
        ws.setAllowContentAccess(true);
        ws.setAllowFileAccess(true);
        ws.setAppCacheEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setJavaScriptEnabled(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setSupportZoom(true);

        DappxBridge dappxBridge = new DappxBridge(this, mWvBrowser);
        mWvBrowser.addJavascriptInterface(dappxBridge, "dappxBridge");

        SelectParams params = new SelectParams();
        params.appName = "Sample Test";
        params.appIcon = "http://download.dappx.com/static/assets/images/appicon.png";

        // 如果只想显示部分钱包，就添加类似下面的代码：
//        params.enabledWallets.add(TokenPocket.getsInstance().id);
//        params.enabledWallets.add(MeetOne.getsInstance().id);

        final String script = dappxBridge.getScript(params);

        mWvBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);

                mWvBrowser.loadUrl("javascript: " + script);
            }
        });

        mWvBrowser.loadUrl("https://betdice.one/game/dice/dice");
    }
}
