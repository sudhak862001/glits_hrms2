package com.Tejaysdr.msrc.activitys.emp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.BatteryManager;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.Utils_location;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.gms.location.LocationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationUpdatesIntentService extends IntentService {
    private static final String ACTION_PROCESS_UPDATES =
            "com.google.android.gms.location.sample.locationupdatespendingintent.action" +
                    ".PROCESS_UPDATES";
    private static final String TAG = LocationUpdatesIntentService.class.getSimpleName();
    private int count;
    private String mlat, mlong;
    private SQLiteDatabase database;
    Cursor cursor;
    long timeInMilliseconds;


    public LocationUpdatesIntentService() {
        // Name the worker thread.
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    Utils_location.setLocationUpdatesResult(this, locations);
                    Utils_location.sendNotification(this, Utils_location.getLocationResultTitle(this, locations));
                    Log.i(TAG, Utils_location.getLocationUpdatesResult(this));


                    if (locations.isEmpty()) {
                        //return context.getString(R.string.unknown_location);
                    } else {
                        for (Location location : locations) {

                            if (count == 0) {
                                count++;
                                if (location != null) {

                                    mlat = String.valueOf(location.getLatitude());
                                    mlong = String.valueOf(location.getLongitude());
                                    //   Toast.makeText(GPSTracker.this ,"Latitude : "+value_lat +"  & Longitude : "+value_lng,Toast.LENGTH_LONG).show();
                                    SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("Latitude", mlat);// Storing string
                                    editor.putString("Longitude", mlong);// Storing string
                                    editor.commit();

                                    Geocoder geocoder;
                                    List<Address> addresses = null;
                                    geocoder = new Geocoder(this, Locale.getDefault());
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
                                        // Totaladdress = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName;
                                        Totaladdress = address;
                                    } catch (Exception e) {
                                        // SaveLocationInDB(value_lat, value_lng, Totaladdress);
                                        LOcationUpdateForJSonObject(mlat, mlong, Totaladdress, this);
                                        e.printStackTrace();
                                    }
                                    // SaveLocationInDB(value_lat, value_lng, Totaladdress);
                                    LOcationUpdateForJSonObject(mlat, mlong, Totaladdress, this);

                                }
                            }

                        }
                    }
                }
            }
        }
    }


    private void LOcationUpdateForJSonObject(final String lat, final String longit, final String totaladdress, Context context) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        //battery percentage
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level * 100) / (float)scale;
        //    Toast.makeText(context, "Battery Percentage "+batteryPct, Toast.LENGTH_SHORT).show();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final String formattedDate1 = df1.format(c);
        Log.d("time_millisec", formattedDate1);
        String url = BaseUrl.getBaseurl() +"employee/emp_gps_loc_new";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("gps_lat", lat);
            jsonObject.accumulate("gps_lang", longit);
            jsonObject.accumulate("dateCreated", formattedDate);
            jsonObject.accumulate("Address", totaladdress);
            jsonObject.accumulate("description", "online");
            jsonObject.accumulate("battery_package",batteryPct);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject map = new JSONObject();
        try {
            map.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
            map.put("Locations", jsonArray);
            map.put("token", SaveAppData.getusename());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG", "onResponse: " + jsonObject.toString());
                        try {
                            String message = jsonObject.getString("message");
                            String text = jsonObject.getString("text");
                            String token_expiry = jsonObject.getString("token_expiry");

                            if (token_expiry.equals("No")) {

                            } else {
//                                Logout(context);
                            }
                            //   Toast.makeText(GPSTracker.this ,"Latitude : "+value_lat +"  & Longitude : "+value_lng,Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            SaveIntoLocalStorageDB(lat, longit, totaladdress, context);
                            //progressdialog.dismiss();
                            // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        SaveIntoLocalStorageDB(lat, longit, totaladdress, context);

                    }
                });
        jobReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jobReq);
    }


//    private void Logout(Context context) {
//        try {
//            Date c = Calendar.getInstance().getTime();
//            System.out.println("Current time => " + c);
//
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
//                                EMPHomeActivity.getInstance().removeLocationUpdates();
//                                Intent i = new Intent(context, EMPHomeActivity.class);
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
//                    params.put("emp_id", SaveAppData.getSessionDataInstance().getLoginData().getEmpId());
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
//    }

    public void SaveIntoLocalStorageDB(String latitude, String longitude, String totaladdress, Context context) {
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
        database = context.openOrCreateDatabase("digitalrupay", Context.MODE_PRIVATE, null);
        database.execSQL("create table if not exists gps_loc(Id varchar(100),gps_lat varchar(100),gps_long varchar(10),datecaptured varchar(100),address varchar(500),status varchar(10))");

        cursor = database.rawQuery("Select Id from gps_loc where gps_lat='" + latitude + "'and datecaptured='" + formattedDate + "'", null);
        count = cursor.getCount();
        if (count == 0) {
            database.execSQL("insert into gps_loc values('" + String.valueOf(timeInMilliseconds) + "','" + latitude + "','" + longitude + "','" + formattedDate + "','" + totaladdress + "','" + "New" + "')");
        }

    }
}
