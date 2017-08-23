package cn.jhc.coordinatorlayoutdemo.vasSonic;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceResponse;

import com.tencent.sonic.sdk.BuildConfig;
import com.tencent.sonic.sdk.SonicRuntime;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */

public class HostSonicRuntime extends SonicRuntime {

    public HostSonicRuntime(Context context) {
        super(context);
    }

    @Override
    public void log(String tag, int level, String message) {
        switch (level) {
            case Log.ERROR:
                Log.e(tag, message);
                break;
            case Log.INFO:
                Log.i(tag, message);
                break;
            default:
                Log.d(tag, message);
        }
    }

    @Override
    public String getCookie(String url) {
        CookieManager manager = CookieManager.getInstance();
        return manager.getCookie(url);
    }

    @Override
    public boolean setCookie(String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies!=null && cookies.size()>0){
            CookieManager manager = CookieManager.getInstance();
            for (String cookie : cookies){
                manager.setCookie(url,cookie);
            }
            return true;
        }
        return false;
    }

    /**
     * 获取用户UA信息
     * @return
     */
    @Override
    public String getUserAgent() {
        return "";
    }

    /**
     * 获取用户ID信息
     * @return
     */
    @Override
    public String getCurrentUserAccount() {
        return "sonic-demo-master";
    }

    @Override
    public boolean isSonicUrl(String url) {
        return true;
    }

    @Override
    public Object createWebResourceResponse(String mimeType, String encoding, InputStream data, Map<String, String> headers) {
        WebResourceResponse resourceResponse = new WebResourceResponse(mimeType, encoding, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.setResponseHeaders(headers);
        }
        return resourceResponse;
    }

    @Override
    public boolean isNetworkValid() {
        return true;
    }

    @Override
    public void showToast(CharSequence text, int duration) {

    }

    @Override
    public void postTaskToThread(Runnable task, long delayMillis) {
        Thread thread = new Thread(task, "SonicThread");
        thread.start();
    }

    @Override
    public void notifyError(SonicSessionClient client, String url, int errorCode) {

    }

    @Override
    public File getSonicCacheDir() {
        if (BuildConfig.DEBUG){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sonic";
            File file = new File(path.intern());
            if (file.exists()){
                file.mkdir();
            }
            return file;
        }
        return super.getSonicCacheDir();
    }


    @Override
    public String getHostDirectAddress(String url) {
        return null;
    }
}
