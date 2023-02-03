package com.Tejaysdr.msrc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.Tejaysdr.msrc.activitys.EMPLoginActivity;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
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
import com.google.android.gms.maps.MapView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    public static Activity activity;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private static final String TAG = "Dahboard";
    public static final int REQUEST_CHECK_SETTINGS = 20001;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                } catch (Exception ignored) {


                }
            }
        }).start();
        activity = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ImageView zoom = (ImageView) findViewById(R.id.logo);
//        final TextView ZoomOut = (TextView) findViewById(R.id.tv_welcome);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);
        Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
//        ZoomOut.startAnimation(animZoomOut);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        checkAndroidVersion();


    }


    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //requestLocationPermission();
            displayLocationSettingsRequest(MainActivity.this);


        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SaveAppData.getOperatorLoginData() != null) {
//                        if (SaveAppData.getOperatorLoginData().getImgstatus() =="1"){
                        Intent i = new Intent(MainActivity.this, EMPHomeActivity.class);
                        startActivity(i);
                        finish();
//                       }else {
//                            Intent i = new Intent(MainActivity.this, ImagepickerActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
                    } else {
                        Intent i = new Intent(MainActivity.this, EMPLoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }

    }


    private void requestLocationPermission() {

        boolean foreground = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            boolean background = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                        continue;
                    } else {
                        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        checkPermissions();

                        Toast.makeText(getApplicationContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        handleForegroundLocationUpdates();
                        checkPermissions();
                        Toast.makeText(getApplicationContext(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }
        }
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                false;
        shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("You need to accept all Permissions?")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION,},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }
                    })
                    .show();

            Snackbar.make(findViewById(android.R.id.content), "Permission Rationalie",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                ActivityCompat.requestPermissions(  MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION,},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION,},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void handleLocationUpdates() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SaveAppData.getOperatorLoginData() != null) {
//                    if (SaveAppData.getOperatorLoginData().getImgstatus() =="1"){
                        Intent i = new Intent(MainActivity.this, EMPHomeActivity.class);
                        startActivity(i);
                        finish();
//                    }else {
//                        Intent i = new Intent(MainActivity.this, ImagepickerActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
                } else {
                    Intent i = new Intent(MainActivity.this, EMPLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
        //foreground and background
        Toast.makeText(getApplicationContext(), "Start Foreground and Background Location Updates", Toast.LENGTH_SHORT).show();
    }

    private void handleForegroundLocationUpdates() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SaveAppData.getOperatorLoginData()!= null) {
//                    if (SaveAppData.getOperatorLoginData().getImgstatus() =="1"){
                        Intent i = new Intent(MainActivity.this, EMPHomeActivity.class);
                        startActivity(i);
                        finish();
//                    }else {
//                        Intent i = new Intent(MainActivity.this, ImagepickerActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
                } else {
                    Intent i = new Intent(MainActivity.this, EMPLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
        //handleForeground Location Updates
        Toast.makeText(getApplicationContext(), "Start foreground location updates", Toast.LENGTH_SHORT).show();
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
                            if(!checkPermissions()) {
                                requestPermissions();
                            }else {
                                requestLocationPermission();
                            }
                        }else {
                            requestLocationPermission();
                        }
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                if(!checkPermissions()) {
                                    requestPermissions();
                                }
                            }else {
                                requestLocationPermission();
                            }
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
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

    public boolean checkPermissions() {
        int fineLocationPermissionState = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        int backgroundLocationPermissionState = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        return (fineLocationPermissionState == PackageManager.PERMISSION_GRANTED) &&
                (backgroundLocationPermissionState == PackageManager.PERMISSION_GRANTED);
    }

//        final ImageView zoom = (ImageView) findViewById(R.id.logo);
////        final TextView ZoomOut=(TextView)findViewById(R.id.ZoomOut);
//        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
//        zoom.startAnimation(zoomAnimation);
//        Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
////        ZoomOut.startAnimation(animZoomOut);
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                if (SaveAppData.getOperatorLoginData() != null) {
//                    if(SaveAppData.getSessionDataInstance().getOperatorLoginData() != null) {
//                        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
//                       String type = operatorCode.getRole_id();
////                        if (type.equalsIgnoreCase("28")) {
////                            Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
////                            startActivity(intent);
////                            MainActivity.this.finish();
////                        }
////                        else if (type.equalsIgnoreCase("36")) {
////                            Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
////                            startActivity(intent);
////                            MainActivity.this.finish();
////                        }
//                        /*else if (type.equalsIgnoreCase("1")) {
//                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
//                            startActivity(intent);
//                            MainActivity.this.finish();
//                        } else if (type.equalsIgnoreCase("2")) {
//                            Intent intent = new Intent(MainActivity.this, StockistHomeActivity.class);
//                            startActivity(intent);
//                            MainActivity.this.finish();
//                        }*/
//                        Intent intent = new Intent(MainActivity.this,EMPLoginActivity.class);
//                        startActivity(intent);
//                    }else{
//                        Intent intent=new Intent(MainActivity.this,PermissionActivity.class);
//                        startActivity(intent);
//                        MainActivity.this.finish();
//                    }
//                } else {
//                    redirectToLoginActivity();
//                }
//            }
//            }, 1500);
//        }
//    private void redirectToLoginActivity() {
//        Intent intent=new Intent(MainActivity.this,PermissionActivity.class);
//        startActivity(intent);
//        MainActivity.this.finish();


}

