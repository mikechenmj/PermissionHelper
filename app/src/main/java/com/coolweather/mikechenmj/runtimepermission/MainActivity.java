package com.coolweather.mikechenmj.runtimepermission;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;
    private static final String[] PERMISSION_NEEDED = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void call() {
        String number = "1234";
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                null,null,null);
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        startActivity(intent);
    }

    public void onClick(View v) {
        PermissionHelper.requestPermissions(this, PERMISSION_NEEDED, REQUEST_CODE,
                new PermissionHelper.PermissionCallback() {
                    @Override
                    public boolean onPermissionGrantedStates(String permission, boolean isGranted, boolean shouldShowRationale) {
//                        if (permission.equals(Manifest.permission.CALL_PHONE)) {
//                            if (isGranted) {
//                                call();
//                            } else if (shouldShowRationale) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                builder.setTitle("获取权限原因")
//                                        .setMessage("需要此权限才能实现功能")
//                                        .setCancelable(false)
//                                        .setNegativeButton(android.R.string.cancel, null)
//                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                ActivityCompat.requestPermissions(MainActivity.this, PERMISSION_NEEDED, REQUEST_CODE);
//                                            }
//                                        }).create().show();
//                                return true;
//                            }
//                        }
                        return false;
                    }

                    @Override
                    public void onAllGranted(boolean isAllGranted) {
                        if (isAllGranted) {
                            call();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                PermissionHelper.onRequestPermissionsResult(this, permissions, grantResults,
                        new PermissionHelper.PermissionCallback() {
                            @Override
                            public boolean onPermissionGrantedStates(String permission, boolean isGranted, boolean shouldShowRationale) {
//                                if (permission.equals(Manifest.permission.CALL_PHONE)) {
//                                    if (isGranted) {
//                                        call();
//                                    }
//                                }
                                return false;
                            }

                            @Override
                            public void onAllGranted(boolean isAllGranted) {
                                if (isAllGranted) {
                                    call();
                                }
                            }
                        });
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
