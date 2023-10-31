package com.faceless.fxxkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.topjohnwu.superuser.Shell;

/* loaded from: classes.dex */
public class SplashActivity extends Activity {
    static {
        Shell.enableVerboseLogging = false;
        Shell.setDefaultBuilder(Shell.Builder.create().setFlags(2).setTimeout(10L));
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Shell.getShell(new Shell.GetShellCallback() { // from class: com.faceless.fxxkapp.-$$Lambda$SplashActivity$V04Odc3n3hu6ZQQLTy8i40heClg
            @Override // com.topjohnwu.superuser.Shell.GetShellCallback
            public final void onShell(Shell shell) {
                SplashActivity.this.lambda$onCreate$0$SplashActivity(shell);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$SplashActivity(Shell shell) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
