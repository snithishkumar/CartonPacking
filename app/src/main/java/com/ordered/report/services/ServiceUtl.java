package com.ordered.report.services;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.ordered.report.R;

public class ServiceUtl {

    public enum AccountManagerUtil {
        INSTANCE;
        private Account account;

        AccountManagerUtil() {

        }

        public Account getAccount(Context context) {
            if(account == null){
                AccountManager accountManager = AccountManager.get(context);
                account = new Account(context.getString(R.string.app_name), AUTHORITY);
                accountManager.addAccountExplicitly(account, context.getString(R.string.carton_sync_password), null);
            }

            return account;
        }

    }

    public static final String AUTHORITY = "com.ordered.report.SyncAdapter";




    public static void startSyncAdapterJob(Context context) {
        Account account = AccountManagerUtil.INSTANCE.getAccount(context);
        ContentResolver.setIsSyncable(account, AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
        ContentResolver.setMasterSyncAutomatically(true);
        ContentResolver.addPeriodicSync(account, AUTHORITY, Bundle.EMPTY, 30);
        /*Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(account, AUTHORITY, settingsBundle);*/
    }


    public static void requestSync(Context context){
        Account account = AccountManagerUtil.INSTANCE.getAccount(context);
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(account, AUTHORITY, settingsBundle);
    }
}
