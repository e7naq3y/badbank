package com.faceless.fxxkapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.faceless.fxxkapp.ApplistFragment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ApplistFragment extends Fragment {
    public static String APP_TAG = "fxxkapp";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String choosenAppName;
    public static String choosenAppPackageName;
    private ListView appListView;
    Context applistFragmentcontext;
    View contentView;
    private String mParam1;
    private String mParam2;
    MainActivity mymainActivity;
    private PackageManager packageManager;

    public static ApplistFragment newInstance(String str, String str2) {
        ApplistFragment applistFragment = new ApplistFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        applistFragment.setArguments(bundle);
        return applistFragment;
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
        this.contentView = layoutInflater.inflate(R.layout.fragment_applist, viewGroup, false);
        this.applistFragmentcontext = getActivity();
        this.mymainActivity = (MainActivity) getActivity();
        initView();
        return this.contentView;
    }

    private void initView() {
        this.packageManager = this.mymainActivity.getPackageManager();
        this.appListView = (ListView) this.contentView.findViewById(R.id.app_list_view);
        List<ResolveInfo> appInfos = getAppInfos();
        int i = 0;
        while (i < appInfos.size()) {
            String str = appInfos.get(i).activityInfo.packageName;
            String str2 = (String) appInfos.get(i).activityInfo.loadLabel(this.packageManager);
            ArrayList arrayList = new ArrayList();
            arrayList.add("wx");
            arrayList.add("一加社区");
            arrayList.add("游戏中心");
            arrayList.add("智能家居");
            arrayList.add("健康");
            arrayList.add("天气");
            arrayList.add("录音");
            arrayList.add("小布指令");
            arrayList.add("便签");
            arrayList.add("指南针");
            arrayList.add("计算器");
            arrayList.add("手机搬家");
            arrayList.add("OPPO 商城");
            arrayList.add("即录剪辑");
            arrayList.add("计算器");
            arrayList.add("小游戏");
            arrayList.add("阅读");
            arrayList.add("邮件");
            arrayList.add("家庭空间");
            arrayList.add("龙珠");
            arrayList.add("Clash");
            try {
                if (isSystemApp(this.packageManager, str)) {
                    appInfos.remove(i);
                    i--;
                }
                if (arrayList.contains(str2)) {
                    appInfos.remove(i);
                    i--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        this.appListView.setAdapter((ListAdapter) new AppListAdapter(appInfos));
    }

    private List<ResolveInfo> getAppInfos() {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        return this.packageManager.queryIntentActivities(intent, 0);
    }

    private static boolean isSystemApp(PackageManager packageManager, String str) throws Exception {
        return (packageManager.getPackageInfo(str, 0).applicationInfo.flags & 1) > 0;
    }

    /* loaded from: classes.dex */
    public class AppListAdapter extends BaseAdapter {
        List<ResolveInfo> mAppInfos;
        private int selectedPosition = -1;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public AppListAdapter(List<ResolveInfo> list) {
            this.mAppInfos = list;
        }

        @Override // android.widget.BaseAdapter
        public void notifyDataSetInvalidated() {
            super.notifyDataSetInvalidated();
        }

        public void setSelectPosition(int i) {
            this.selectedPosition = i;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mAppInfos.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.mAppInfos.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = new ViewHolder();
            if (view == null) {
                view = ((LayoutInflater) ApplistFragment.this.mymainActivity.getSystemService("layout_inflater")).inflate(R.layout.item_app_list_view, (ViewGroup) null);
                viewHolder.mAppIconImageView = (ImageView) view.findViewById(R.id.app_icon_image_view);
                viewHolder.mAppNameTextView = (TextView) view.findViewById(R.id.app_name_text_view);
                viewHolder.mAppNameButton_update = (Button) view.findViewById(R.id.applist_btn_update);
                viewHolder.mAppNameButton_inject = (Button) view.findViewById(R.id.applist_btn_inject);
                viewHolder.mAppNameButton_clear = (Button) view.findViewById(R.id.applist_btn_clear);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.mAppNameTextView.setText(this.mAppInfos.get(i).activityInfo.loadLabel(ApplistFragment.this.packageManager));
            viewHolder.mAppIconImageView.setImageDrawable(this.mAppInfos.get(i).activityInfo.loadIcon(ApplistFragment.this.packageManager));
            viewHolder.mAppNameButton_update.setOnClickListener(new AnonymousClass1(i));
            viewHolder.mAppNameButton_inject.setOnClickListener(new View.OnClickListener() { // from class: com.faceless.fxxkapp.-$$Lambda$ApplistFragment$AppListAdapter$yGyD4Hax_ASvq8LPvBijOtT3EIA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ApplistFragment.AppListAdapter.this.lambda$getView$0$ApplistFragment$AppListAdapter(i, view2);
                }
            });
            viewHolder.mAppNameButton_clear.setOnClickListener(new View.OnClickListener() { // from class: com.faceless.fxxkapp.ApplistFragment.AppListAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    File[] listFiles;
                    ApplistFragment.choosenAppPackageName = AppListAdapter.this.mAppInfos.get(i).activityInfo.packageName;
                    ApplistFragment.choosenAppName = (String) AppListAdapter.this.mAppInfos.get(i).activityInfo.loadLabel(ApplistFragment.this.packageManager);
                    Log.d(ApplistFragment.APP_TAG, ApplistFragment.choosenAppPackageName + "清除按钮被点击了");
                    String str = ApplistFragment.this.mymainActivity.originalRootPath + "/fridajs/" + ApplistFragment.choosenAppPackageName;
                    CMDUtil.execSuperSuCmd("am force-stop " + ApplistFragment.choosenAppPackageName);
                    try {
                        for (File file : new File(str).listFiles()) {
                            if (!file.isDirectory()) {
                                if (!file.getName().equals(ApplistFragment.choosenAppPackageName + ".zip")) {
                                    CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + file.getName());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest.config.so");
                    CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest64.config.so");
                    for (int i2 = 0; i2 < ApplistFragment.this.mymainActivity.firmwareFilesName.size(); i2++) {
                        CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + ApplistFragment.this.mymainActivity.firmwareFilesName.get(i2));
                    }
                    Message message = new Message();
                    message.what = 4;
                    message.obj = ApplistFragment.choosenAppName;
                    ApplistFragment.this.mymainActivity.myHandler.sendMessage(message);
                }
            });
            return view;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: com.faceless.fxxkapp.ApplistFragment$AppListAdapter$1  reason: invalid class name */
        /* loaded from: classes.dex */
        public class AnonymousClass1 implements View.OnClickListener {
            final /* synthetic */ int val$position;

            AnonymousClass1(int i) {
                this.val$position = i;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ApplistFragment.choosenAppPackageName = AppListAdapter.this.mAppInfos.get(this.val$position).activityInfo.packageName;
                ApplistFragment.choosenAppName = (String) AppListAdapter.this.mAppInfos.get(this.val$position).activityInfo.loadLabel(ApplistFragment.this.packageManager);
                String str = ApplistFragment.APP_TAG;
                Log.d(str, ApplistFragment.choosenAppPackageName + "更新按钮被点击了");
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("MAC", ApplistFragment.this.mymainActivity.MAC_str);
                    jSONObject.put("appPackageName", ApplistFragment.choosenAppPackageName);
                    jSONObject.put("appName", ApplistFragment.choosenAppName);
                    jSONObject.put("fxxkappversion", ApplistFragment.this.mymainActivity.versioncode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String jSONObject2 = jSONObject.toString();
                Thread thread = new Thread(new Runnable() { // from class: com.faceless.fxxkapp.-$$Lambda$ApplistFragment$AppListAdapter$1$mk02lf66K8ZnnyS0ISdZ5BwxprA
                    @Override // java.lang.Runnable
                    public final void run() {
                        ApplistFragment.AppListAdapter.AnonymousClass1.this.lambda$onClick$0$ApplistFragment$AppListAdapter$1(r2, jSONObject2);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }

            public /* synthetic */ void lambda$onClick$0$ApplistFragment$AppListAdapter$1(String str, String str2) {
                String str3 = ApplistFragment.this.applistFragmentcontext.getDataDir().getAbsolutePath() + "/fridajs/" + ApplistFragment.choosenAppPackageName;
                String str4 = ApplistFragment.choosenAppPackageName + ".zip";
                String postDownloadFileAndSave = OkHttpUtil.postDownloadFileAndSave(str, str2, str3, str4);
                Log.d(ApplistFragment.APP_TAG, postDownloadFileAndSave);
                Message message = new Message();
                message.what = 2;
                message.obj = ApplistFragment.choosenAppName + postDownloadFileAndSave;
                ApplistFragment.this.mymainActivity.myHandler.sendMessage(message);
                FileUtil.UnzipWithPassword(str3 + InternalZipConstants.ZIP_FILE_SEPARATOR + str4, str3, "catchmeifyoucan");
            }
        }

        public /* synthetic */ void lambda$getView$0$ApplistFragment$AppListAdapter(int i, View view) {
            File[] listFiles;
            ApplistFragment.choosenAppPackageName = this.mAppInfos.get(i).activityInfo.packageName;
            ApplistFragment.choosenAppName = (String) this.mAppInfos.get(i).activityInfo.loadLabel(ApplistFragment.this.packageManager);
            Log.d(ApplistFragment.APP_TAG, ApplistFragment.choosenAppPackageName + "注入按钮被点击了");
            String str = ApplistFragment.this.mymainActivity.originalRootPath + "/fridajs/" + ApplistFragment.choosenAppPackageName;
            File file = new File(str + "/frida.js");
            File file2 = new File(ApplistFragment.this.mymainActivity.originalRootPath + "/firmware/libtest.so");
            File file3 = new File(ApplistFragment.this.mymainActivity.originalRootPath + "/firmware/libtest64.so");
            boolean exists = file.exists();
            if (!Boolean.valueOf(exists && file2.exists() && file3.exists()).booleanValue()) {
                Message message = new Message();
                message.what = 30;
                message.obj = ApplistFragment.choosenAppName;
                ApplistFragment.this.mymainActivity.myHandler.sendMessage(message);
                return;
            }
            CMDUtil.execSuperSuCmd("am force-stop " + ApplistFragment.choosenAppPackageName);
            for (File file4 : new File(str).listFiles()) {
                if (!file4.isDirectory()) {
                    if (!file4.getName().equals(ApplistFragment.choosenAppPackageName + ".zip")) {
                        CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + file4.getName());
                        CMDUtil.execSuperSuCmd("cp " + file4.getAbsolutePath() + " /data/user/0/" + ApplistFragment.choosenAppPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + file4.getName());
                        StringBuilder sb = new StringBuilder();
                        sb.append("chmod 777 /data/user/0/");
                        sb.append(ApplistFragment.choosenAppPackageName);
                        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                        sb.append(file4.getName());
                        CMDUtil.execSuperSuCmd(sb.toString());
                    }
                }
            }
            String str2 = ApplistFragment.this.mymainActivity.originalRootPath + "/tmp.config";
            CMDUtil.execSuperSuCmd("rm -rf " + str2);
            FileUtil.writeconfigToFile("{\n  \"interaction\": {\n    \"type\": \"script\",\n    \"path\": \"frida.js\"\n  }\n}", ApplistFragment.this.mymainActivity.originalRootPath + InternalZipConstants.ZIP_FILE_SEPARATOR, "tmp.config");
            CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest.config.so");
            CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest64.config.so");
            CMDUtil.execSuperSuCmd("cp " + str2 + " /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest.config.so");
            CMDUtil.execSuperSuCmd("cp " + str2 + " /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest64.config.so");
            CMDUtil.execSuperSuCmd("chmod 777 /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest.config.so");
            CMDUtil.execSuperSuCmd("chmod 777 /data/user/0/" + ApplistFragment.choosenAppPackageName + "/libtest64.config.so");
            CMDUtil.execSuperSuCmd("echo " + ApplistFragment.this.mymainActivity.MAC_str + " > /data/user/0/" + ApplistFragment.choosenAppPackageName + "/MAC.txt");
            for (int i2 = 0; i2 < ApplistFragment.this.mymainActivity.firmwareFilesPath.size(); i2++) {
                CMDUtil.execSuperSuCmd("rm -rf /data/user/0/" + ApplistFragment.choosenAppPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + ApplistFragment.this.mymainActivity.firmwareFilesName.get(i2));
                CMDUtil.execSuperSuCmd("cp " + ApplistFragment.this.mymainActivity.firmwareFilesPath.get(i2) + " /data/user/0/" + ApplistFragment.choosenAppPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + ApplistFragment.this.mymainActivity.firmwareFilesName.get(i2));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("chmod 777 /data/user/0/");
                sb2.append(ApplistFragment.choosenAppPackageName);
                sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                sb2.append(ApplistFragment.this.mymainActivity.firmwareFilesName.get(i2));
                CMDUtil.execSuperSuCmd(sb2.toString());
            }
            Message message2 = new Message();
            message2.what = 3;
            message2.obj = ApplistFragment.choosenAppName;
            ApplistFragment.this.mymainActivity.myHandler.sendMessage(message2);
        }

        /* loaded from: classes.dex */
        public class ViewHolder {
            public ImageView mAppIconImageView;
            public Button mAppNameButton_clear;
            public Button mAppNameButton_inject;
            public Button mAppNameButton_update;
            public TextView mAppNameTextView;

            public ViewHolder() {
            }
        }
    }
}
