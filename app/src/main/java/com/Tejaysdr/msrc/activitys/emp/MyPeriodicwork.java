package com.Tejaysdr.msrc.activitys.emp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.MyPeriodicWork;
import com.Tejaysdr.msrc.backendServices.GPSTracker;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyPeriodicwork extends Worker {
    private static final String TAB = MyPeriodicWork.class.getSimpleName();
    public MyPeriodicwork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(new Intent(getApplicationContext(), GPSTracker.class));
                // Do something for lollipop and above versions
            } else {
                getApplicationContext().startService(new Intent(getApplicationContext(), GPSTracker.class));
                // do something for phones running an SDK before lollipop
            }

           /* GPSTracker gps = new GPSTracker(getApplicationContext());
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Latitude", String.valueOf(latitude));// Storing string
            editor.putString("Longitude", String.valueOf(longitude));// Storing string
            editor.commit();

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            String Totaladdress="";
            try {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                Totaladdress = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName;
            }catch (Exception e){
                SaveLocationInDB(String.valueOf(latitude), String.valueOf(longitude),Totaladdress);
                e.printStackTrace();
            }
            SaveLocationInDB(String.valueOf(latitude), String.valueOf(longitude),Totaladdress);
            Log.w("Lat and Long", "" + latitude + longitude);*/
        } catch (Exception e) {
            // SaveLocationInDB(String.valueOf(latitude), String.valueOf(longitude),Totaladdress);
            e.printStackTrace();
        }
        return Result.success();
    }

    private void SaveLocationInDB(final String lat, final String longit, final String totaladdress) {

        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String formattedDate = df.format(c);
            final String url = BaseUrl.getBaseurl() +"employee/emp_gps_loc_new";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {


                            } catch (Exception e) {
                                e.printStackTrace();
                                //progressdialog.dismiss();
                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                    params.put("gps_lat", lat);
                    params.put("gps_lang", longit);
                    params.put("dateCreated", formattedDate);
                    params.put("Address", totaladdress);
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
}

