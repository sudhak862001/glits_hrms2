package com.Tejaysdr.msrc.activitys.emp;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApplyleaveActivity extends AppCompatActivity {
    private static final String TAG = "";
    TextInputEditText et_fromdate,et_todate,et_Subject,et_description,et_hSubject,et_hdescription,et_fromtime,et_totime,et_date;
    Spinner sp_type;
    Button btn_submit;
    String gettypeId="";
    TextInputLayout tl_fromdate,tl_todate,tl_fromtime,tl_totime,tl_date;
    Calendar myCalendar;
    String serviceFormat;
    private String dateEdittext;
    String Fromdate="",Todate="",Descrption="Fullday";
    Toolbar toolbar;
    LinearLayout ll_fulldays,ll_halfday;
    int mHour,mMinute,mSec,totime,fromtime;
    TextView btn_byhalfday,btn_byfullday;
    private  String FCMToken;
    Bundle val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyleave);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        et_fromdate = findViewById(R.id.et_fromdate);
        et_fromtime =  findViewById(R.id.et_fromtime);
        et_totime =  findViewById(R.id.et_totime);
        et_date =  findViewById(R.id.et_date);
        btn_byhalfday = (TextView) findViewById(R.id.btn_byhalfday);
        btn_byfullday = (TextView) findViewById(R.id.btn_byfullday);
        ll_fulldays = (LinearLayout) findViewById(R.id.ll_fulldays);
        ll_halfday = (LinearLayout) findViewById(R.id.ll_halfday);
        val = getIntent().getExtras();
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        FCMToken = pref.getString("FCMToken", null);
        //  Log.e("",FCMToken);
        if (FCMToken == null) {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            FCMToken = task.getResult();

                            // Log and toast
                        }
                    });
        }        SharedPreferences pref1 = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref1.edit();
        pref1.getString("FCMToken", FCMToken);
        editor.putString("FCM", FCMToken);
        editor.commit();


        btn_byfullday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_byfullday.setBackgroundColor(getResources().getColor(R.color.fullday));
                btn_byhalfday.setBackgroundColor(getResources().getColor(R.color.halfday));
                ll_halfday.setVisibility(View.GONE);
                ll_fulldays.setVisibility(View.VISIBLE);
                Descrption="Fullday";
            }
        });

        btn_byhalfday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_byhalfday.setBackgroundColor(getResources().getColor(R.color.fullday));
                btn_byfullday.setBackgroundColor(getResources().getColor(R.color.halfday));
                ll_halfday.setVisibility(View.VISIBLE);
                ll_fulldays.setVisibility(View.GONE);
                Descrption="Halfday";
            }
        });

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateEdittext = "et_fromdate";
                ShowDatePickerDialog1();
            }
        });

        et_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEdittext = "et_fromdate";
                ShowDatePickerDialog();
            }
        });
        et_todate =  findViewById(R.id.et_todate);
        et_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEdittext = "et_todate";
                ShowDatePickerDialog();
            }
        });
        et_Subject =  findViewById(R.id.et_Subject);
        et_hSubject =  findViewById(R.id.et_hSubject);
        tl_fromdate = (TextInputLayout) findViewById(R.id.tl_fromdate);
        tl_todate = (TextInputLayout) findViewById(R.id.tl_todate);
        et_description =  findViewById(R.id.et_description);
        et_hdescription =  findViewById(R.id.et_hdescription);
        sp_type = (Spinner) findViewById(R.id.sp_type);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from_date=et_fromdate.getText().toString();
                String to_date=et_todate.getText().toString();
                String sctdate=et_date.getText().toString();
                String tottime=et_totime.getText().toString();
                String fromttime=et_fromtime.getText().toString();
                String hremarks=et_hdescription.getText().toString();
                String leaveremarks=et_description.getText().toString();
                if(Descrption.equals("Fullday")) {
                    if (from_date.isEmpty()) {
                        et_fromdate.setError("Please Select From Date");
                        return;
                    }else if (to_date.isEmpty()) {
                        et_fromdate.setError("Please Select To Date");
                        return;
                    }else if (leaveremarks.isEmpty()) {
                        et_description.setError("Please Enter Leave Remarks");
                        return;
                    } else {
                        LeaveRequest();
                    }
                }

                if(Descrption.equals("Halfday")) {
                    if (sctdate.isEmpty()) {
                        et_date.setError("Please Select  Date");
                        return;
                    }else if (tottime.isEmpty()) {
                        et_date.setError("Please Select To Time");
                        return;
                    }else if (fromttime.isEmpty()) {
                        et_date.setError("Please Select From Time");
                        return;
                    }else if (hremarks.isEmpty()) {
                        et_hdescription.setError("Please Enter Leave Remarks");
                        return;
                    } else {
                        LeaveRequest();
                    }
                }
            }
        });


        et_fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSec = c.get(Calendar.SECOND);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ApplyleaveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String hour = hourOfDay + "";
                                if (hourOfDay < 10) {
                                    hour = "0" + hourOfDay;
                                }
                                String min = minute + "";
                                if (minute < 10)
                                    min = "0" + minute;
                                et_fromtime.setText(hour + ":" + min+":00");
                                Fromdate=et_date.getText().toString()+" "+et_fromtime.getText().toString();
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        et_totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ApplyleaveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String hour = hourOfDay + "";
                                if (hourOfDay < 10) {
                                    hour = "0" + hourOfDay;
                                }
                                String min = minute + "";
                                if (minute < 10)
                                    min = "0" + minute;

                                et_totime.setText(hour + ":" + min+":00");
                                Todate=et_date.getText().toString()+" "+et_totime.getText().toString();
                                checkTimes(et_fromtime.getText().toString(),et_totime.getText().toString(),et_totime);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        setSupportActionBar(toolbar);
        setTitle("Leave Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    private void ShowDatePickerDialog() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyleaveActivity.this, Nextfollowupdate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener Nextfollowupdate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelfrom();
        }
    };
    private void ShowDatePickerDialog1() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyleaveActivity.this, Nextfollowupdate1, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener Nextfollowupdate1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelfrom1();
        }
    };
    private void updateLabelfrom() {
        serviceFormat = "yyyy-MM-dd";
        SimpleDateFormat serviceSdf = new SimpleDateFormat(serviceFormat);
        serviceFormat = serviceSdf.format(myCalendar.getTime());
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        switch (dateEdittext) {
            case "et_fromdate":
                Fromdate=sdf.format(myCalendar.getTime())+" "+"00:00:00";
                et_fromdate.setText(sdf.format(myCalendar.getTime()));
                break;
            case "et_todate":
                Todate=sdf.format(myCalendar.getTime())+" "+"23:59:00";
                et_todate.setText(sdf.format(myCalendar.getTime()));
                checkDates(et_fromdate.getText().toString(),et_todate.getText().toString(),et_todate);
                break;
        }
        // et_fromdate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabelfrom1() {
        serviceFormat = "yyyy-MM-dd";
        SimpleDateFormat serviceSdf = new SimpleDateFormat(serviceFormat);
        serviceFormat = serviceSdf.format(myCalendar.getTime());
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        et_date.setText(sdf.format(myCalendar.getTime()));
        // et_fromdate.setText(sdf.format(myCalendar.getTime()));
    }


    public boolean checkDates(String d1, String d2, TextView todate) {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        boolean b = false;
        try {
            //  b = dfDate.parse(d1).before(dfDate.parse(d2)) || dfDate.parse(d1).equals(dfDate.parse(d2));
            if(dfDate.parse(d1).before(dfDate.parse(d2)))
            {
                b = true;//If start date is before end date
            }
            else if(dfDate.parse(d1).equals(dfDate.parse(d2)))
            {
                b = true;//If two dates are equal
            }
            else
            {
                b = false;//If start date is after the end date
                todate.setText("");
                Toast.makeText(ApplyleaveActivity.this, "Pleaase Enter Valid Date", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }
    public boolean checkTimes(String inTime, String outTime, TextView toTime) {
        int dateDelta = inTime.compareTo(outTime);
        Boolean b=false;
        switch (dateDelta) {
            case 0:
                toTime.setText("");
                //Toast.makeText(ApplyLeaveActivity.this,"startTime and endTime not **Equal**",Toast.LENGTH_SHORT).show();
                //startTime and endTime not **Equal**
                break;
            case 1:
                toTime.setText("");
                //Toast.makeText(ApplyLeaveActivity.this,"endTime is **Greater** then startTime",Toast.LENGTH_SHORT).show();
                //endTime is **Greater** then startTime
                break;
            case -1:

                // Toast.makeText(ApplyLeaveActivity.this,"startTime is **Greater** then endTime",Toast.LENGTH_SHORT).show();
                //startTime is **Greater** then endTime
                break;
        }
        return b;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




    /*private void LeaveRequest() {
        final ProgressDialog progressdialog = BaseUrlClass.createProgressDialog(ApplyLeaveActivity.this);
        progressdialog.show();
        progressdialog.setCancelable(false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emp_id ", SaveAppData.getLoginData().getemp_id());
        map.put("leave_from_date", Fromdate);
        map.put("leave_to_date", Todate);
        if(Descrption.equals("Halfday")) {
            map.put("leave_reason", et_hdescription.getText().toString());
        }else{
            map.put("leave_reason", et_description.getText().toString());
        }
        JsonObjectRequest request_json = new JsonObjectRequest(BaseUrlClass.getBaseUrl()+"leave_request.php", new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        JSONObject jsonObject = null;
                            try {
                                String message = response.getString("message");
                                String text = response.getString("text");
                                if (message.equalsIgnoreCase("success")) {
                                    Toast.makeText(ApplyLeaveActivity.this, text, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ApplyLeaveActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ApplyLeaveActivity.this, text, Toast.LENGTH_LONG).show();
                                    progressdialog.dismiss();
                                }
                                //Process os success response
                            } catch (org.json.JSONException e) {
                                progressdialog.dismiss();
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
            }
        });
        request_json.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        RequestQueue requestQueue = Volley.newRequestQueue(ApplyLeaveActivity.this);
        requestQueue.add(request_json);
    }*/


    private void LeaveRequest() {
//        final android.app.ProgressDialog progressdialog= BaseUrl.createProgressDialog(ApplyleaveActivity.this);
//        progressdialog.show();
//        progressdialog.setCancelable(false);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
        final String URL =BaseUrl.getBaseurl()+"employee/leave_request";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println(URL);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            try {
                                String message = jsonObject.getString("message");
                                String token_expiry = jsonObject.getString("token_expiry");
                                if(token_expiry.equals("Yes")){
                                    Logout();
                                }
                                String text = jsonObject.getString("text");
                                if (message.equalsIgnoreCase("success")) {
                                    Toast.makeText(ApplyleaveActivity.this, text, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ApplyleaveActivity.this,EMPHomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ApplyleaveActivity.this, text, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                                //Process os success response
                            } catch (org.json.JSONException e) {
                                dialog.dismiss();
                                e.printStackTrace();
                            }

                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                           dialog.dismiss();
                            // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       dialog.dismiss();
//                            createDialoges(error.toString());
                    }
                })
        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new java.util.HashMap<String, String>();
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("token",SaveAppData.getOperatorLoginData().getToken());
//                params.put("tr_id","1");
                params.put("leave_from_date",Fromdate);
                params.put("leave_to_date", Todate);
                if(Descrption.equals("Halfday")) {
                    params.put("leave_reason", et_hdescription.getText().toString());
                }else{
                    params.put("leave_reason", et_description.getText().toString());
                }
                JSONObject jsonObject=new JSONObject(params);
                return params;
            }
        };
        new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        RequestQueue requestQueue = newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                                Intent i = new Intent(ApplyleaveActivity.this,EMPHomeActivity.class);
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
                    params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
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
