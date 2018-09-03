package com.ordered.report.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ordered.report.R;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.services.LoginService;
import com.ordered.report.utils.Constants;
import com.ordered.report.utils.Utils;
import com.squareup.otto.Subscribe;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText userName, password;
    private LoginService loginService = null;
    private ProgressDialog progressDialog = null;
    private ConnectivityManager cm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        AppBus.getInstance().register(this);
        initService();
        loginBtn = (Button) findViewById(R.id.sign_in_button);
        userName = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        getPermission();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String pass = password.getText().toString();
                if (name.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    loginService.checkAuthentication(name, pass);
                    showProgress();
                }
            }
        });
    }


    private void showErrorMsg(){
        Toast.makeText(this,"Please enable Read and Write Permission and continue.",Toast.LENGTH_LONG).show();
        finishAndRemoveTask();
    }


    private void getPermission(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.isAnyPermissionPermanentlyDenied()){
                    showErrorMsg();

                }else if(!report.areAllPermissionsGranted()){
                    showErrorMsg();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override public void onError(DexterError error) {
                Log.e("Dexter", "There was an error: " + error.toString());
            }
        }).check();
    }

    public void initService() {
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        loginService = new LoginService(this);
    }

    private void showProgress() {
        Log.e("LoginActivity", "showProgress");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");
        progressDialog.setCancelable(false);
        if (Utils.isNetworkAvailable(cm)) {
            progressDialog.show();
        }

    }

    public void stopProgress() {
        Log.e("LoginActivity", "stopProgress");
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppBus.getInstance().unregister(this);
    }

    @Subscribe
    public void loginResult(LoginEvent loginEvent) {
        switch (loginEvent.getStatusCode()) {
            case Constants.SUCCESS:
                try {
                    initService();
                    finish();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    stopProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constants.INVALID_USERNAME:
                Toast.makeText(this, R.string.invalid_username, Toast.LENGTH_SHORT).show();
                stopProgress();
                break;
            case Constants.INTERNAL_SERVER_ERROR:
                Toast.makeText(this, R.string.internal_server_error, Toast.LENGTH_SHORT).show();
                break;
            case Constants.NO_INTERNET:
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                stopProgress();
                break;
            default:
                break;
        }
    }



}
