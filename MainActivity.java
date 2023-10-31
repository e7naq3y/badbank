package com.faceless.fxxkapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static String APP_TAG = "fxxkapp";
    public String MAC_str;
    private BottomNavigationView bottomNavigationView;
    private Fragment[] fragmentList;
    public ProgressBar mMProgress;
    public int mMprogress;
    public String originalFirmwarePath;
    public String originalRootPath;
    public String pkgname;
    public PackageManager pm;
    public String versioncode;
    private int lastFragmentIndex = 0;
    public List<String> firmwareFilesPath = new ArrayList();
    public List<String> firmwareFilesName = new ArrayList();
    public MyHandler myHandler = new MyHandler();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyHandler extends Handler {
        MyHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Toast.makeText(MainActivity.this, "初始化固件成功", 0).show();
            } else if (i == 2) {
                Toast.makeText(MainActivity.this, (String) message.obj, 0).show();
            } else if (i == 3) {
                MainActivity mainActivity = MainActivity.this;
                Toast.makeText(mainActivity, ((String) message.obj) + "生效成功", 0).show();
            } else if (i == 4) {
                MainActivity mainActivity2 = MainActivity.this;
                Toast.makeText(mainActivity2, ((String) message.obj) + "还原成功", 0).show();
            } else if (i == 10) {
                Toast.makeText(MainActivity.this, "当前无固件，请初始化固件", 0).show();
            } else if (i == 30) {
                MainActivity mainActivity3 = MainActivity.this;
                Toast.makeText(mainActivity3, ((String) message.obj) + "生效失败，固件或脚本不存在", 0).show();
            } else {
                switch (i) {
                    case 2455:
                        Toast.makeText(MainActivity.this, "当前版本为最新版本", 0).show();
                        return;
                    case 2456:
                        Toast.makeText(MainActivity.this, "更新软件失败，可能网络原因", 0).show();
                        return;
                    case 2457:
                        MainActivity.this.mMProgress.setProgress(MainActivity.this.mMprogress);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        File[] listFiles;
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        Log.d(APP_TAG, "Product Model: " + Build.MODEL + "," + Build.VERSION.SDK_INT + "," + Build.VERSION.RELEASE);
        Integer.parseInt(Build.VERSION.RELEASE);
        this.pkgname = getPackageName();
        this.pm = getPackageManager();
        try {
            this.versioncode = String.valueOf(this.pm.getPackageInfo(this.pkgname, 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CMDUtil.execSuperSuCmd("setenforce 0");
        this.MAC_str = CMDUtil.execSuCmd("settings get secure bluetooth_address").replaceAll("\n", "");
        Log.d(APP_TAG, "MAC：" + this.MAC_str);
        initView();
        this.originalRootPath = getDataDir().getAbsolutePath();
        this.originalFirmwarePath = this.originalRootPath + "/firmware";
        File file = new File(this.originalFirmwarePath);
        if (!file.exists()) {
            Message message = new Message();
            message.what = 10;
            this.myHandler.sendMessage(message);
            return;
        }
        for (File file2 : file.listFiles()) {
            if (file2.isFile()) {
                if (!this.firmwareFilesPath.contains(this.originalFirmwarePath + InternalZipConstants.ZIP_FILE_SEPARATOR + file2.getName())) {
                    this.firmwareFilesPath.add(this.originalFirmwarePath + InternalZipConstants.ZIP_FILE_SEPARATOR + file2.getName());
                    this.firmwareFilesName.add(file2.getName());
                }
            }
        }
    }

    private void initView() {
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bnv);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.fragmentList = new Fragment[]{new ApplistFragment(), new SettingFragment()};
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, this.fragmentList[0]).commit();
    }

    @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.menu_applist /* 2131230955 */:
                Log.d(APP_TAG, "点击了应用列表Fragment");
                switchFragment(0);
                break;
            case R.id.menu_setting /* 2131230956 */:
                Log.d(APP_TAG, "点击了设置Fragment");
                switchFragment(1);
                break;
        }
        return false;
    }

    private void switchFragment(int i) {
        if (this.lastFragmentIndex == i) {
            return;
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (!this.fragmentList[i].isAdded()) {
            beginTransaction.add(R.id.main_frame, this.fragmentList[i]).commit();
        } else {
            beginTransaction.show(this.fragmentList[i]).commit();
        }
        beginTransaction.hide(this.fragmentList[this.lastFragmentIndex]);
        this.lastFragmentIndex = i;
    }
}
