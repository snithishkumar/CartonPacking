package com.ordered.report.services;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.ordered.report.R;
import com.ordered.report.SyncAdapter.ServiceAPI;
import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.LoginDao;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.models.UsersEntity;
import com.ordered.report.utils.Constants;
import com.ordered.report.utils.Utils;


public class LoginService {
    private Context context = null;
    private ConnectivityManager cm = null;
    private SyncServiceApi syncServiceApi = null;
    public final String LOG_TAG = LoginService.class.getSimpleName();
    private LoginDao loginDao = null;
    private LoginEvent loginEvent = null;


    public LoginService(Context context) {
        try {
            this.context = context;
            initServiceApi(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initServiceApi(Context context) throws Exception {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        syncServiceApi = ServiceAPI.INSTANCE.getSyncServiceApi();
        loginDao = new LoginDao(context);
    }

    public void checkAuthentication(String userName, String password) {
        try {
            if (Utils.isNetworkAvailable(cm)) {
                /*Response serverResponse = null;
                try {
                    // Get data from the server
                    serverResponse = syncServiceApi.checkAuthentication(userName, password);
                    System.out.println("download error catched 173 = " + serverResponse.getStatus());
                } catch (Exception e) {
                    System.out.println("download error catched 174");
                    e.printStackTrace();
                }
                if (serverResponse != null) {
                    switch (serverResponse.getStatus()) {
                        case HttpURLConnection.HTTP_OK: // Success
                            try {
                                String loginResponseStr = FileUtils.toString(serverResponse.getBody().in());
                                startSyncAdapterJob();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case HttpURLConnection.HTTP_INTERNAL_ERROR: // Internal Server Error
                            Log.e(LOG_TAG, "Internal Server Error from server");
                            postAuthResult(Constants.INTERNAL_SERVER_ERROR);
                            break;
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            postAuthResult(Constants.INVALID_USERNAME);
                            break;
                    }
                }*/

            } else {
                UsersEntity usersEntity = loginDao.getUserEntity(userName);
                if (usersEntity != null) {
                    if (usersEntity.getPassword().equals(password)) {
                        postAuthResult(Constants.SUCCESS);
                        ServiceUtl.startSyncAdapterJob(context);
                        ServiceUtl.requestSync(context);
                    } else {
                        postAuthResult(Constants.INVALID_USERNAME);
                    }
                } else {
                    postAuthResult(Constants.NO_INTERNET);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postAuthResult(Integer result) {
        if (loginEvent == null) {
            loginEvent = new LoginEvent();
        }
        loginEvent.setStatusCode(result);
        AppBus.getInstance().post(loginEvent);
    }




}
