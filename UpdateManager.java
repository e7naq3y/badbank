package com.faceless.fxxkapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import com.topjohnwu.superuser.nio.FileSystemManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class UpdateManager {
    private static String saveFileName;
    private static String savePath;
    private Thread downLoadThread;
    private MainActivity mMainActivity;
    TextView text;
    private boolean isNew = false;
    private boolean intercept = false;
    private String apkUrl = "https://catchmehacker.xyz/api/fxxk/web/fxxkappapk";
    private String apkversionUrl = "https://catchmehacker.xyz/api/fxxk/web/fxxkappversion";
    private int updateversionCode = 0;
    private Runnable mdownApkRunnable = new Runnable() { // from class: com.faceless.fxxkapp.UpdateManager.4
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(UpdateManager.this.apkUrl).openConnection();
                    httpURLConnection.connect();
                    int contentLength = httpURLConnection.getContentLength();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    File file = new File(UpdateManager.savePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    String unused = UpdateManager.saveFileName = UpdateManager.savePath + currentTimeMillis + "_ARKUpdate.apk";
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(UpdateManager.saveFileName));
                    byte[] bArr = new byte[1024];
                    int i = 0;
                    while (true) {
                        if (UpdateManager.this.intercept) {
                            break;
                        }
                        int read = inputStream.read(bArr);
                        i += read;
                        UpdateManager.this.mMainActivity.mMprogress = (int) ((i / contentLength) * 100.0f);
                        UpdateManager.this.mMainActivity.myHandler.sendEmptyMessage(2457);
                        if (read <= 0) {
                            UpdateManager.this.installAPK();
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public UpdateManager(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
        savePath = this.mMainActivity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + InternalZipConstants.ZIP_FILE_SEPARATOR;
        Log.d("fxxkapp", "apk存储路径：" + savePath);
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void checkVersion() throws InterruptedException {
        Thread thread = new Thread(new Runnable() { // from class: com.faceless.fxxkapp.-$$Lambda$UpdateManager$pynCEMFdbUgOLmeDp1bF48nqiyg
            @Override // java.lang.Runnable
            public final void run() {
                UpdateManager.this.lambda$checkVersion$0$UpdateManager();
            }
        });
        thread.start();
        thread.join();
        if (getVersionCode(this.mMainActivity) < this.updateversionCode) {
            this.isNew = false;
        } else {
            this.isNew = true;
        }
        Log.d("fxxkapp", String.valueOf(this.isNew));
        checkUpdateInfo();
    }

    public /* synthetic */ void lambda$checkVersion$0$UpdateManager() {
        try {
            this.updateversionCode = new JSONObject(OkHttpUtil.getStringSync(this.apkversionUrl)).getJSONObject("data").getInt("versioncode");
            Log.d("fxxkapp", "本软件版本号：" + getVersionCode(this.mMainActivity));
            Log.d("fxxkapp", "服务器软件版本号：" + this.updateversionCode);
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            this.mMainActivity.myHandler.sendEmptyMessage(2456);
        }
    }

    public void checkUpdateInfo() {
        if (this.isNew) {
            this.mMainActivity.myHandler.sendEmptyMessage(2455);
        } else {
            showUpdateDialog();
        }
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mMainActivity);
        builder.setTitle("软件版本更新");
        builder.setMessage("有最新的软件包，是否下载!");
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() { // from class: com.faceless.fxxkapp.UpdateManager.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UpdateManager.this.showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() { // from class: com.faceless.fxxkapp.UpdateManager.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mMainActivity);
        builder.setTitle("软件版本更新");
        View inflate = LayoutInflater.from(this.mMainActivity).inflate(R.layout.progress, (ViewGroup) null);
        this.mMainActivity.mMProgress = (ProgressBar) inflate.findViewById(R.id.progress);
        builder.setView(inflate);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.faceless.fxxkapp.UpdateManager.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                UpdateManager.this.intercept = true;
            }
        });
        builder.show();
        downloadApk();
    }

    private void downloadApk() {
        this.downLoadThread = new Thread(this.mdownApkRunnable);
        this.downLoadThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void installAPK() {
        Uri parse;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(FileSystemManager.MODE_READ_ONLY);
        File file = new File(saveFileName);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            MainActivity mainActivity = this.mMainActivity;
            parse = FileProvider.getUriForFile(mainActivity, this.mMainActivity.getPackageName() + ".fileprovider", file);
            intent.setDataAndType(parse, "application/vnd.android.package-archive");
        } else {
            parse = Uri.parse("file://" + file.toString());
        }
        intent.setDataAndType(parse, "application/vnd.android.package-archive");
        this.mMainActivity.startActivity(intent);
    }
}
