package com.Tejaysdr.msrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.backendServices.GPSTracker;
import com.Tejaysdr.msrc.backendServices.SendGPSServicesLocation;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DriverActivity extends AppCompatActivity {
    TextView tv_dname;
    double latitude = 0.0, longitude = 0.0;
    private GPSTracker gps;
    public static Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        tv_dname=findViewById(R.id.tv_dname);
        String dname= SaveAppData.getOperatorLoginData().getFullname();
        tv_dname.setText("Welcome to "+dname);
        gps = new GPSTracker(DriverActivity.this);
        if(!gps.canGetLocation()){
            gps.showSettingsAlert();
        }
        startTimer();
        Intent intent=new Intent(this, SendGPSServicesLocation.class);
        startService(intent);


    }
    private void startTimer() {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //TODO

                        if (gps.canGetLocation()) {
                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            getLocationTracking();
                            Log.e("GetLocation", "latitude :- " + latitude + "\n longitude :- " + longitude);
//                            getLatlong();
                     /*       Intent dataIntent = new Intent(EMPHomeActivity.this, DataLoader.class);
                            Messenger dataMessenger = new Messenger(hEMPGPSLOC);
                            dataIntent.putExtra("MESSENGER", dataMessenger);
                            dataIntent.putExtra("type", DataLoader.DataType.LocationTracking.ordinal());
                            dataIntent.putExtra("gps_lang", "" + longitude);
                            dataIntent.putExtra("gps_lat", "" + latitude);
                            dataIntent.putExtra("emp_id", getempID);
                            startService(dataIntent);*/
                        } else {
                            gps.showSettingsAlert();
                        }
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 30000);
    }

    private void getLatlong() {
        String uri= BaseUrl.getBaseurl()+"Employee/emp_gps_loc";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(UpdateComplaintActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("gps_lat", String.valueOf(latitude));
                params.put("gps_lang", String.valueOf(longitude));
                //params.put("emp_statuscode",MSRCApplication.EMPStatusCode);

                return params;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getLocationTracking() {
    }
}