package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.adapter.LeaveListAdapter;
import com.Tejaysdr.msrc.dataModel.LeaveHistoryModel;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppliedleaveActivity extends AppCompatActivity {
    private static final String TAG = "";
    RecyclerView rv_leavelist;
    TextView tv_Norecords;
    Toolbar toolbar;
    LeaveListAdapter leaveListAdapter;
    ArrayList<LeaveHistoryModel> leadHistorylist = new ArrayList<>();
    Bundle values;
    private String schl_id;
    private String FCMToken;
    Bundle val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliedleave);
//        val = getIntent().getExtras();
//        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
//        FCMToken = pref.getString("FCMToken", null);
//        //  Log.e("",FCMToken);
//        if (FCMToken == null) {
//            FirebaseMessaging.getInstance().getToken()
//                    .addOnCompleteListener(new OnCompleteListener<String>() {
//                        @Override
//                        public void onComplete(@NonNull Task<String> task) {
//                            if (!task.isSuccessful()) {
//                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                                return;
//                            }
//
//                            // Get new FCM registration token
//                            FCMToken = task.getResult();
//
//                            // Log and toast
//                        }
//                    });
//        }
//        SharedPreferences pref1 = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref1.edit();
//        pref1.getString("FCMToken", FCMToken);
//        editor.putString("FCM", FCMToken);
//        editor.commit();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        rv_leavelist = (RecyclerView)findViewById(R.id.leavelist);
        tv_Norecords = (TextView)findViewById(R.id.errMsg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_leavelist.setLayoutManager(mLayoutManager);
        setTitle("Applied Leaves");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getReturnsInfo();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    private void getReturnsInfo() {
        try {
//            final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(this);
//            progressdialog.show();
//            progressdialog.setCancelable(false);
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Please Wait..");
            dialog.show();
            final String url = BaseUrl.getBaseurl() +"employee/my_leave_requests";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                try {
                                    leadHistorylist = new ArrayList<>();
                                    String message = jsonObject.getString("message");
                                    String token_expiry = jsonObject.getString("token_expiry");
                                    if(token_expiry.equals("Yes")){
                                        Logout();
                                    }
                                    if (message.equalsIgnoreCase("success")) {
                                        Iterator<String> keys = jsonObject.keys();
                                        while (keys.hasNext()) {
                                            String key = keys.next();
                                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")&& !key.equalsIgnoreCase("token_expiry")) {
                                              LeaveHistoryModel taggroupData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<LeaveHistoryModel>() {
                                                }.getType());

                                                leadHistorylist.add(taggroupData);
                                            }
                                        }

                                        leaveListAdapter = new LeaveListAdapter(AppliedleaveActivity.this, leadHistorylist,"Training");
                                        rv_leavelist.setAdapter(leaveListAdapter);
                                        leaveListAdapter.notifyDataSetChanged();
                                        tv_Norecords.setVisibility(View.GONE);
                                        rv_leavelist.setVisibility(View.VISIBLE);
                                       dialog.dismiss();
                                    } else {
                                        Toast.makeText(AppliedleaveActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                        tv_Norecords.setVisibility(View.VISIBLE);
                                        rv_leavelist.setVisibility(View.GONE);
                                      dialog.dismiss();
                                    }
                                  dialog.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    dialog.dismiss();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                dialog.dismiss();
                                //progressdialog.dismiss();
                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();

                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                    params.put("token",SaveAppData.getOperatorLoginData().getToken());
                    return params;

                }
            };
            new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private void Logout() {
        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String formattedDate = df.format(c);
            final String url = BaseUrl.getBaseurl() + "emp_logout.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {
                                SaveAppData.saveOperatorLoginData(null);
                                SaveAppData.setusename("");
//                               EMPHomeActivity.getInstance().removeLocationUpdates();
                                Intent i = new Intent(AppliedleaveActivity.this,EMPHomeActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

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
                    SharedPreferences pref = getSharedPreferences("MyPref", 0);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                    /*if(longit!=null&latitude!=null){
                        params.put("gps_lat", latitude);
                        params.put("gps_lang", longit);
                    }else{
                        params.put("gps_lat", "0.0");
                        params.put("gps_lang", "0.0");
                    }*/
                    params.put("gps_lat", "0.0");
                    params.put("gps_lang", "0.0");
                    params.put("dateCreated", formattedDate);
                    params.put("Address", "");
                    params.put("token", SaveAppData.getusename());
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
