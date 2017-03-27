package com.example.dattien.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityPermissionsDispatcher.successPermissionWithCheck(this);
    }

    @NeedsPermission({
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA
    })
    void successPermission() {
        Toast.makeText(this, "Show Permission", Toast.LENGTH_SHORT).show();
    }


    @OnShowRationale({
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA
    })
    void showRationaleForLogin(final PermissionRequest request) {
        new AlertDialog.Builder(this)
            .setMessage("Phone state and wifi state are need to login function")
            .setPositiveButton("Allow", (dialog, button) -> request.proceed())
            .setNegativeButton("Deny", (dialog, button) -> request.cancel())
            .setCancelable(false)
            .show();
    }

    @SuppressLint("HardwareIds")
    @NeedsPermission({
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA
    })

    @OnPermissionDenied({
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA
    })
    void showDeniedForLogin() {
        Toast.makeText(this, "Phone state or wifi state was denied." +
                " Please consider granting it in order to access the login function",
            Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA
    })
    void showNeverAskForLogin() {
        Toast.makeText(this, "Phone state or wifi state was denied with never ask again.",
            Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,
            requestCode, grantResults);

    }
}
