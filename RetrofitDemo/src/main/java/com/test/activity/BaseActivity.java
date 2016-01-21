package com.test.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-21 Time: 14:47
 * ToDo:
 */
public class BaseActivity extends FragmentActivity {
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public void insertDummyContactWrapper(String... permissions) {
        //需要的权限集合
        List<String> permissionsNeeded = new ArrayList<String>();
        //权限集合
        final List<String> permissionsList = new ArrayList<String>();
        //
        for (String p : permissions) {
            //
            if (!addPermission(permissionsList, p))
                permissionsNeeded.add(p);
        }
        //
        if (permissionsList.size() > 0) {
            //
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "你需要添加权限：" + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(BaseActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

    }

    public boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(BaseActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, permission))
                return false;
        }
        return true;
    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
}  
