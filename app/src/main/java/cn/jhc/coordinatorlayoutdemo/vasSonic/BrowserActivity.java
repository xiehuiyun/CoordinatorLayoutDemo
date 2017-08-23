package cn.jhc.coordinatorlayoutdemo.vasSonic;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jhc.coordinatorlayoutdemo.R;

public class BrowserActivity extends AppCompatActivity {

    public final static String PARAM_URL = "param_url";

    public final static String PARAM_MODE = "param_mode";
    private SonicSession sonicSession;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String url = intent.getStringExtra(PARAM_URL);
        int mode = intent.getIntExtra(PARAM_MODE, -1);

        if (TextUtils.isEmpty(url) || mode == -1) {
            finish();
            return;
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new HostSonicRuntime(getApplication()), new SonicConfig.Builder().build());
        }

        SonicSessionClientImp sessionClient = null;

        // if it's sonic mode , startup sonic session at first time
        if (VasActivity.MODE_DEFAULT != mode) {
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();

            // if it's offline pkg mode, we need to intercept the session connection
            if (VasActivity.MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
                    @Override
                    public String getCacheData(SonicSession session) {
                        return null;// offline pkg does not need cache
                    }
                });

                sessionConfigBuilder.setConnectionIntercepter(new SonicSessionConnectionInterceptor() {
                    @Override
                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                        return new OfflinePkgSessionConnection(session, intent, BrowserActivity.this);
                    }
                });
            }

            // create sonic session and run sonic flow
            sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
            if (null != sonicSession) {
                sonicSession.bindClient(sessionClient = new SonicSessionClientImp());
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                throw new UnknownError("create sessin fail!");
            }
        }

        // start init flow ...
        // in the real world, the init flow may cost a long time as startup
        // runtime、init configs....
        setContentView(R.layout.activity_browser);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }
        });

        WebSettings settings = webView.getSettings();
        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        settings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sessionClient, intent), "sonic");
        // init webview settings
        settings.setAllowContentAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // webview is ready now, just tell session client to bind
        if (sessionClient != null) {
            sessionClient.bindWebView(webView);
            sessionClient.clientReady();
        } else {
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        if (sonicSession != null) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }

    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> contexts;

        public OfflinePkgSessionConnection(SonicSession session, Intent intent, Context context) {
            super(session, intent);
            this.contexts = new WeakReference<Context>(context);
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }

        @Override
        protected int internalConnect() {
            Context ctx = contexts.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.xml");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }
    }
}
