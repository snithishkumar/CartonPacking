package com.ordered.report.SyncAdapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Selva on 6/21/2016.
 */
public class AuthenticatorService extends Service {
    private Authenticator authenticator = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
