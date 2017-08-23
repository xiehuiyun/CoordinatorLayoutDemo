package cn.jhc.coordinatorlayoutdemo.vasSonic;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import com.tencent.sonic.sdk.SonicDiffDataCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/22.
 */

public class SonicJavaScriptInterface {

    public static final String PARAM_CLICK_TIME = "clickTime";

    public static final String PARAM_LOAD_URL_TIME = "loadUrlTime";

    private final SonicSessionClientImp sessionClient;

    private final Intent intent;

    public SonicJavaScriptInterface(SonicSessionClientImp sessionClient, Intent intent) {
        this.sessionClient = sessionClient;
        this.intent = intent;
    }

    @JavascriptInterface
    public void getDiffData() {
        getDiffData2("getDiffDataCallBack");
    }

    @JavascriptInterface
    public void getDiffData2(final String jsCallbackFunc) {
        if (sessionClient != null) {
            sessionClient.getDiffData(new SonicDiffDataCallback() {
                @Override
                public void callback(final String resultData) {
                    Runnable callback = new Runnable() {
                        @Override
                        public void run() {
                            String jsCode = "javascript:" + jsCallbackFunc + "('" + toJsString(resultData) + "')";
                            sessionClient.getWebView().loadUrl(jsCode);
                        }
                    };

                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        callback.run();
                    } else {
                        new Handler(Looper.getMainLooper()).post(callback);
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public String getPerformance() {
        long clickTime = intent.getLongExtra(PARAM_CLICK_TIME, -1);
        long loadUriTime = intent.getLongExtra(PARAM_LOAD_URL_TIME, -1);

        try {
            JSONObject result = new JSONObject();
            result.put(PARAM_CLICK_TIME, clickTime);
            result.put(PARAM_LOAD_URL_TIME, loadUriTime);
            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String toJsString(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder(1024);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"':
                case '\\':
                case '/':
                    out.append('\\').append(c);
                    break;
                case '\t':
                    out.append("\\t");
                    break;
                case '\b':
                    out.append("\\n");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                case '\f':
                    out.append("\\f");
                    break;
                default:
                    if (c <= 0x1F) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }
        }
        return out.toString();
    }
}
