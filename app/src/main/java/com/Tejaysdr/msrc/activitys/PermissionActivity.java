package com.Tejaysdr.msrc.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class PermissionActivity extends AppCompatActivity {
    private int REQUEST_PERMISSION=1001;
    private boolean locationpermission,camera,read,write;
    public static String TAG="Location";
    private int REQUEST_CHECK_SETTINGS=2001;
    private int REQUEST_LOCATION=2002;
    boolean settings_permission=false;
    private int VIDEO_CAPTURE=101;
    public static PermissionActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        activity=this;

        if(!checkandroidversion()){
            requestpermission();
            //displayLocationSettingsRequest(this);
        }else {
            //displayLocationSettingsRequest(this);
            if (SaveAppData.getOperatorLoginData() != null) {
                if(SaveAppData.getSessionDataInstance().getOperatorLoginData() != null) {
                    OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                    String type = operatorCode.getRole_id();
                    if (type.equalsIgnoreCase("28")) {
                        Intent intent = new Intent(PermissionActivity.this, EMPHomeActivity.class);
                        startActivity(intent);
                        PermissionActivity.this.finish();
                    }
                    else if (type.equalsIgnoreCase("36")) {
                        Intent intent = new Intent(PermissionActivity.this, EMPHomeActivity.class);
                        startActivity(intent);
                        PermissionActivity.this.finish();
                    }
                        /*else if (type.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        } else if (type.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(MainActivity.this, StockistHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }*/
                }else{
                    Intent intent=new Intent(PermissionActivity.this,EMPLoginActivity.class);
                    startActivity(intent);
                    PermissionActivity.this.finish();
                }
            } else {
                Intent intent=new Intent(PermissionActivity.this,EMPLoginActivity.class);
                startActivity(intent);
                PermissionActivity.this.finish();
            }
        }

//        bt_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                startActivityForResult(intent, VIDEO_CAPTURE);
//            }
//        });


    }

    private boolean checkandroidversion() {
        int permission= ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)+
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)+
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)+
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission== PackageManager.PERMISSION_GRANTED) {
            locationpermission = true;
            camera = true;
            write = true;
            read = true;
        }
        return permission== PackageManager.PERMISSION_GRANTED;


    }

    private void requestpermission(){

        boolean shouldproviderationale= ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)|| ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)|| ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)|| ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(shouldproviderationale){
            new AlertDialog.Builder(this)
                    .setTitle("You need Accept all the permission")
                    .setPositiveButton("Accept", (dialogInterface, i) -> ActivityCompat.requestPermissions(PermissionActivity.this,
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }, REQUEST_PERMISSION)).show();

        }else {
            ActivityCompat.requestPermissions(PermissionActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {

            if (grantResults.length>0) {
                locationpermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                camera = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                write = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    read = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                }else {
                    read=true;
                    grantResults[3]=0;
                }
            }
            if (locationpermission && camera && write && read) {
                if (SaveAppData.getOperatorLoginData() != null) {
                    if(SaveAppData.getSessionDataInstance().getOperatorLoginData() != null) {
                        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                        String type = operatorCode.getRole_id();
                        if (type.equalsIgnoreCase("28")) {
                            Intent intent = new Intent(PermissionActivity.this, EMPHomeActivity.class);
                            startActivity(intent);
                            PermissionActivity.this.finish();
                        }
                        else if (type.equalsIgnoreCase("36")) {
                            Intent intent = new Intent(PermissionActivity.this, EMPHomeActivity.class);
                            startActivity(intent);
                            PermissionActivity.this.finish();
                        }
                        /*else if (type.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        } else if (type.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(MainActivity.this, StockistHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }*/
                    }else{
                        Intent intent=new Intent(PermissionActivity.this,EMPLoginActivity.class);
                        startActivity(intent);
                        PermissionActivity.this.finish();
                    }
                } else {
                    Intent intent=new Intent(PermissionActivity.this,EMPLoginActivity.class);
                    startActivity(intent);
                    PermissionActivity.this.finish();
                }
//                Toast.makeText(DashboardActivity.this, "All Locations Are satisfied", Toast.LENGTH_SHORT).show();

            }
            for (int grantResult : grantResults) {
                if (grantResult == -1) {

                    new AlertDialog.Builder(this)
                            .setTitle("Permissions Required")
                            .setMessage("Please Accept all the permission")
                            .setPositiveButton("settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                                    startActivityForResult(intent,2);
                                }
                            }).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==2){
            if (SaveAppData.getOperatorLoginData() != null) {
                if(SaveAppData.getSessionDataInstance().getOperatorLoginData() != null) {
                    OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
                    String type = operatorCode.getRole_id();
                    if (type.equalsIgnoreCase("28")) {
                        Intent intent = new Intent(PermissionActivity.this, EMPHomeActivity.class);
                        startActivity(intent);
                        PermissionActivity.this.finish();
                    }
                    else if (type.equalsIgnoreCase("36")) {
                        Intent intent = new Intent(PermissionActivity.this, EMPHomeActivity.class);
                        startActivity(intent);
                        PermissionActivity.this.finish();
                    }
                        /*else if (type.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        } else if (type.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(MainActivity.this, StockistHomeActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }*/
                }else{
                    Intent intent=new Intent(PermissionActivity.this,EMPLoginActivity.class);
                    startActivity(intent);
                    PermissionActivity.this.finish();
                }
            } else {
                Intent intent=new Intent(PermissionActivity.this,EMPLoginActivity.class);
                startActivity(intent);
                PermissionActivity.this.finish();
            }
        }
    }

    public void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                            if(!checkandroidversion()) {
                                requestpermission();
                            }else {
                                requestpermission();
                            }
                        }else {
                            requestpermission();
                        }
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                if(!checkandroidversion()) {
                                    requestpermission();
                                }
                            }else {
                                requestpermission();
                            }
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(PermissionActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}