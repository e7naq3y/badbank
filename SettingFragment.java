package com.faceless.fxxkapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class SettingFragment extends Fragment {
    public static String APP_TAG = "fxxkapp";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;
    Context settingFragmentcontext;
    String unzipfirmwarepath = null;

    public static SettingFragment newInstance(String str, String str2) {
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
        this.settingFragmentcontext = getActivity();
        this.mainActivity = (MainActivity) getActivity();
        ((Button) inflate.findViewById(R.id.button_initfirmware)).setOnClickListener(new View.OnClickListener() { // from class: com.faceless.fxxkapp.SettingFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Log.d("fxxkapp", "初始化固件按钮被点击");
                try {
                    SettingFragment.this.initfirmware();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) inflate.findViewById(R.id.button_updatesoftware)).setOnClickListener(new View.OnClickListener() { // from class: com.faceless.fxxkapp.SettingFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Log.d("fxxkapp", "更新软件按钮被点击");
                try {
                    new UpdateManager(SettingFragment.this.mainActivity).checkVersion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return inflate;
    }

    public boolean initfirmware() throws IOException {
        File[] listFiles;
        Boolean.valueOf(FileUtil.copyAssetAndWrite(this.settingFragmentcontext, "firmware.zip"));
        File file = new File(this.settingFragmentcontext.getCacheDir(), "firmware.zip");
        Log.d(APP_TAG, "固件的原路径：" + file.getAbsolutePath() + "，存在情况：" + String.valueOf(file.exists()));
        String str = APP_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("固件的解压到的路径为：");
        sb.append(this.mainActivity.originalFirmwarePath);
        Log.d(str, sb.toString());
        FileUtil.unzipFile(file.getAbsolutePath(), this.mainActivity.originalFirmwarePath);
        Log.d(APP_TAG, "固件的解压成功");
        File file2 = new File(this.mainActivity.originalFirmwarePath);
        if (!file2.exists()) {
            Message message = new Message();
            message.what = 10;
            this.mainActivity.myHandler.sendMessage(message);
        } else {
            for (File file3 : file2.listFiles()) {
                if (file3.isFile()) {
                    if (!this.mainActivity.firmwareFilesPath.contains(this.mainActivity.originalFirmwarePath + InternalZipConstants.ZIP_FILE_SEPARATOR + file3.getName())) {
                        this.mainActivity.firmwareFilesPath.add(this.mainActivity.originalFirmwarePath + InternalZipConstants.ZIP_FILE_SEPARATOR + file3.getName());
                        this.mainActivity.firmwareFilesName.add(file3.getName());
                    }
                }
            }
        }
        Message message2 = new Message();
        message2.what = 1;
        this.mainActivity.myHandler.sendMessage(message2);
        return true;
    }
}
