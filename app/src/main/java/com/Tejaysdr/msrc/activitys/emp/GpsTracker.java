package com.Tejaysdr.msrc.activitys.emp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GpsTracker  extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "";
    private Context mContext;

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    private String value_lat;
    private String value_lng;

    SQLiteDatabase database;
    public static int count = 0;
    private Cursor cursor;
    private String random;
    long timeInMilliseconds;


    public GpsTracker() {
    }

    public GpsTracker(Context context) {
        //, Activity activity
        this.mContext = context;

        // this.activity = activity;
        //  getLocation();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            startMyOwnForeground();
            // Do something for lollipop and above versions
        } else {
            startForeground(1, new Notification());
            // do something for phones running an SDK before lollipop
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        buildGoogleApiClient();
        count = 0;
    }


    /*@Override
    public ComponentName startForegroundService(Intent service) {
        buildGoogleApiClient();
        return super.startForegroundService(service);

    }*/

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
            } /*else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    int requestPermissionsCode = 50;


                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }*/
            // If GPS enabled, get latitude/longitude using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 50);

                    } else {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {

                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
    public void stopUsingGPS() {

    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }
    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     * */


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(count==0) {
            count++;
            if (location != null) {

                value_lat = String.valueOf(location.getLatitude());
                value_lng = String.valueOf(location.getLongitude());
                //   Toast.makeText(GPSTracker.this ,"Latitude : "+value_lat +"  & Longitude : "+value_lng,Toast.LENGTH_LONG).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Latitude", value_lat);// Storing string
                editor.putString("Longitude", value_lat);// Storing string
                editor.commit();

                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String Totaladdress = "";
                try {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    //Totaladdress = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName;
                    Totaladdress = address;
                } catch (Exception e) {
                    // SaveLocationInDB(value_lat, value_lng, Totaladdress);
                    if(SaveAppData.getOperatorLoginData()!=null) {
                        LOcationUpdateForJSonObject(value_lat, value_lng, Totaladdress);
                    }
                    e.printStackTrace();
                }
                // SaveLocationInDB(value_lat, value_lng, Totaladdress);
                if(SaveAppData.getOperatorLoginData()!=null) {
                    LOcationUpdateForJSonObject(value_lat, value_lng, Totaladdress);
                }

            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }
    private void SaveLocationInDB(final String lat, final String longit, final String totaladdress) {

        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String formattedDate = df.format(c);
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            final String formattedDate1 = df1.format(c);
            Log.d("time_millisec",formattedDate1);
            final String url = BaseUrl.getBaseurl()+"employee/emp_gps_loc_new";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {
                                System.out.println(response);
                                //   Toast.makeText(GPSTracker.this ,"Latitude : "+value_lat +"  & Longitude : "+value_lng,Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                SaveIntoLocalStorageDB(lat,longit,totaladdress);
                                //progressdialog.dismiss();
                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            SaveIntoLocalStorageDB(lat,longit,totaladdress);
                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    try{
                        params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    params.put("gps_lat", lat);
                    params.put("gps_lang", longit);
                    params.put("dateCreated", formattedDate);
                    params.put("Address", formattedDate1);
                    params.put("description", "online");
                    return params;

                }
            };
            new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void LOcationUpdateForJSonObject(final String lat, final String longit, final String totaladdress)  {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        //battery percentage
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level * 100) / (float)scale;
        //    Toast.makeText(context, "Battery Percentage "+batteryPct, Toast.LENGTH_SHORT).show();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final String formattedDate1 = df1.format(c);
        Log.d("time_millisec",formattedDate1);
        String url = BaseUrl.getBaseurl() +"employee/emp_gps_loc_new";
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.accumulate("gps_lat", lat);
            jsonObject.accumulate("gps_lang", longit);
            jsonObject.accumulate("dateCreated", formattedDate);
            jsonObject.accumulate("Address", formattedDate1);
            jsonObject.accumulate("description", "online");
            jsonObject.accumulate("battery_package",batteryPct);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject map = new JSONObject();
        try {
            map.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
            map.put("Locations",jsonArray);
            map.put("token",SaveAppData.getOperatorLoginData().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: " + jsonObject.toString());
                        try {
                            System.out.println(jsonObject);
                            String message=jsonObject.getString("message");
                            String text=jsonObject.getString("text");
                            String token_expiry=jsonObject.getString("token_expiry");

                            if(token_expiry.equals("No")){

                            }else {
                                Logout(GpsTracker.this);
                            }
                            //   Toast.makeText(GPSTracker.this ,"Latitude : "+value_lat +"  & Longitude : "+value_lng,Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            SaveIntoLocalStorageDB(lat,longit,totaladdress);
                            //progressdialog.dismiss();
                            // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        SaveIntoLocalStorageDB(lat,longit,totaladdress);

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jobReq);
    }


    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
//                .setSmallIcon(R.drawable.appicon)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    private void SaveIntoLocalStorageDB(String latitude, String longitude, String totaladdress) {
        Date c1 = Calendar.getInstance().getTime();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate1 = df1.format(c1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(formattedDate1);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final int min = 20;
        final int max = 80;
        // final int random = new Random().nextInt((max - min) + 1) + min;

        Date cDate = new Date();
        String currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c);
        database = openOrCreateDatabase("digitalrupay", Context.MODE_PRIVATE, null);
        database.execSQL("create table if not exists gps_loc(Id varchar(100),gps_lat varchar(100),gps_long varchar(10),datecaptured varchar(100),address varchar(500),status varchar(10))");

        cursor = database.rawQuery("Select Id from gps_loc where gps_lat='"+latitude+"'and datecaptured='"+formattedDate+"'", null);
        count = cursor.getCount();
        if(count==0){
            database.execSQL("insert into gps_loc values('"+ String.valueOf(timeInMilliseconds)+"','"+latitude+"','"+longitude+"','"+formattedDate+"','"+totaladdress+"','"+"New"+"')");
        }

    }


    private void Logout(Context context) {
//        try {
//            Date c = Calendar.getInstance().getTime();
//            System.out.println("Current time => " + c);
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            final String formattedDate = df.format(c);
//            final String url = BaseUrlClass.getBaseUrl() + "emp_logout.php";
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            System.out.println(response);
//                            JSONObject jsonObject = null;
//                            try {
//                                SaveAppData.saveOperatorLoginData(null);
//                                SaveAppData.setusename("");
////                               EMPHomeActivity.getInstance().removeLocationUpdates();
//                                Intent i = new Intent(context, EMPLoginActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                context.startActivity(i);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                //progressdialog.dismiss();
//                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                            VolleyLog.e("Error: ", error.getMessage());
//                        }
//                    }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
//                    /*if(longit!=null&latitude!=null){
//                        params.put("gps_lat", latitude);
//                        params.put("gps_lang", longit);
//                    }else{
//                        params.put("gps_lat", "0.0");
//                        params.put("gps_lang", "0.0");
//                    }*/
//                    params.put("gps_lat", "0.0");
//                    params.put("gps_lang", "0.0");
//                    params.put("dateCreated", formattedDate);
//                    params.put("Address", "");
//                    params.put("token", SaveAppData.getusename());
//                    return params;
//
//                }
//            };
//            new DefaultRetryPolicy(
//                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            );
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//            requestQueue.add(stringRequest);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
