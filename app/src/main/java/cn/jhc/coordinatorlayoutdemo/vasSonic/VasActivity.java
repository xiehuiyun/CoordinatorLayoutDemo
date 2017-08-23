package cn.jhc.coordinatorlayoutdemo.vasSonic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionClient;
import com.tencent.sonic.sdk.SonicSessionConfig;

import cn.jhc.coordinatorlayoutdemo.R;

public class VasActivity extends AppCompatActivity implements View.OnClickListener {

    private Button resetBtn, defaultBtn, sonicPreloadBtn, sonicBtn, sonicWithOfflineCacheBtn;
    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;

    private static final String DEMO_URL = "http://mc.vip.qq.com/demo/indexv3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vas);
        initView();
        if (hasPermission()) {
            init();
        } else {
            requestPermission();
        }

    }

    private void initView() {
        resetBtn = (Button) findViewById(R.id.btn_reset);
        defaultBtn = (Button) findViewById(R.id.btn_default_mode);
        sonicBtn = (Button) findViewById(R.id.btn_sonic);
        sonicPreloadBtn = (Button) findViewById(R.id.btn_sonic_preload);
        sonicWithOfflineCacheBtn = (Button) findViewById(R.id.btn_sonic_with_offline);

        resetBtn.setOnClickListener(this);
        defaultBtn.setOnClickListener(this);
        sonicBtn.setOnClickListener(this);
        sonicPreloadBtn.setOnClickListener(this);
        sonicWithOfflineCacheBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                SonicEngine.getInstance().cleanCache();
                break;
            case R.id.btn_default_mode:
                startBrowserActivity(MODE_DEFAULT);
                break;
            case R.id.btn_sonic:
                startBrowserActivity(MODE_SONIC);
                break;
            case R.id.btn_sonic_preload:
                SonicSessionConfig config = new SonicSessionConfig.Builder().build();
                boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(DEMO_URL, config);
                Toast.makeText(getApplicationContext(), preloadSuccess ? "Preload start up success!" : "Preload start up fail!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_sonic_with_offline:
                startBrowserActivity(MODE_SONIC_WITH_OFFLINE_CACHE);
                break;
        }
    }

    private void init() {
        // init sonic engine
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new HostSonicRuntime(getApplication()), new SonicConfig.Builder().build());
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                init();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startBrowserActivity(int mode) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, DEMO_URL);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        startActivityForResult(intent, -1);
    }

}
