package com.Tejaysdr.msrc.activitys.emp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.adapter.SpinnerComplaintAdapter;
import com.Tejaysdr.msrc.adapter.SpinnerInventoryAdapter;
import com.Tejaysdr.msrc.backendServices.GPSTracker;
import com.Tejaysdr.msrc.backendServices.SendGPSServicesLocation;
import com.Tejaysdr.msrc.dataModel.ComplaintData;
import com.Tejaysdr.msrc.dataModel.InventoryItemsData;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class IndentRaisingActivity extends BaseActivity {

    TextView empname, selectdate;
    Spinner inventory_complaint, inventory_item;
    LinearLayout lay_remarks, li_inventory_item;
    boolean openremark;
    ImageView ic_calender;
    private double latitude, longitude;
    private GPSTracker gps;
    EditText Quality, remarks;
    public static Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    ArrayList<ComplaintData> complaintsDataList = new ArrayList<>();
    ArrayList<InventoryItemsData> inventoryList;
    String getempID, inv_id, required_qty, indent_reason, required_date;
    private OperatorCode operatorCode;
    String mainURL, uri;
    private String remarks1;
    Toolbar toolbar_requstmaterial;
    String rremarks, selectddate,Quality1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_raising);
        openremark = false;

        //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        OperatorLoginData operatorCode = null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname = (TextView) findViewById(R.id.in_empname);
        inventory_item = (Spinner) findViewById(R.id.inventory_item);
        inventory_complaint = (Spinner) findViewById(R.id.inventory_complaint);
        lay_remarks = (LinearLayout) findViewById(R.id.lay_remarks);
        selectdate = (TextView) findViewById(R.id.selectdate);
        ic_calender = (ImageView) findViewById(R.id.ic_calender);
        Quality = (EditText) findViewById(R.id.Quality);
        remarks = (EditText) findViewById(R.id.remarks);
        remarks1 = remarks.getText().toString();
        String getempname = operatorCode.getFullname();
        empname.setText("Hello " + getempname);
        selectdate.setText("");
        getempID = operatorCode.getEmp_id();
        //getComplaintsList(getempID);
        toolbar_requstmaterial = findViewById(R.id.toolbar_requstmaterial);
        setSupportActionBar(toolbar_requstmaterial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_requstmaterial.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Request Material");
        InventoryItems();
        /*Intent dataIntent1 = new Intent(IndentRaisingActivity.this, DataLoader.class);
        Messenger dataMessenger1 = new Messenger(iHandler);
        dataIntent1.putExtra("MESSENGER", dataMessenger1);
        dataIntent1.putExtra("type", DataLoader.DataType.Inventory_Items.ordinal());
        startService(dataIntent1);*/

        inventory_complaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //indent_reason=complaintsDataList.get(position).getComplaintId();
                // indent_reason=complaintsDataList.get(position).getSub_cat_name();
                indent_reason = inventory_complaint.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        inventory_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inv_id = inventoryList.get(position).getInvId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gps = new GPSTracker(IndentRaisingActivity.this);
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        }
        startTimer();
        Intent intent = new Intent(this, SendGPSServicesLocation.class);
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

                           /* Intent dataIntent = new Intent(EMPHomeActivity.this, DataLoader.class);
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


    private void getLocationTracking() {
    }

    private Handler hEMPGPSLOC = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String response = bundle.getString("data");
        }
    };

    private void InventoryItems() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
        /*operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"inventory_items.php";*/
        uri = BaseUrl.getBaseurl() + "Employee/inv_list_items";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog.dismiss();
                    //Toast.makeText(InventoryListActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String response12 = jsonObject.getString("response");
                    if (response12.equalsIgnoreCase("Success")) {
                        inventoryList = new ArrayList<>();
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                InventoryItemsData inventoryItemsData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<InventoryItemsData>() {
                                }.getType());
                                inventoryList.add(inventoryItemsData);
                            }
                            SpinnerInventoryAdapter adapter = new SpinnerInventoryAdapter(IndentRaisingActivity.this, inventoryList);
                            inventory_item.setAdapter(adapter);
                        }
                        Toast.makeText(IndentRaisingActivity.this, "" + response12, Toast.LENGTH_SHORT).show();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(IndentRaisingActivity.this, "" + response12, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id", getempID);
                return params;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


       /* StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        inventoryList=new ArrayList<>();
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                InventoryItemsData inventoryItemsData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<InventoryItemsData>() {}.getType());
                                inventoryList.add(inventoryItemsData);
                            }
                        }
                        SpinnerInventoryAdapter adapter = new SpinnerInventoryAdapter(IndentRaisingActivity.this, inventoryList);
                        inventory_item.setAdapter(adapter);
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(IndentRaisingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
    }

    private void getComplaintsList(String getempID) {
        // getComplaintlistempid();
       /* Intent dataIntent = new Intent(IndentRaisingActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(complaintHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.COMPLAINTS_LIST.ordinal());
        dataIntent.putExtra("jsonObject", getempID);
        startService(dataIntent);*/
    }

    private void getComplaintlistempid() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
      /*  operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getEmp_id();
        mainURL = operatorCode.getop_url();*/
        //uri=mainURL+"complaints_details.php?emp_id="+objectempcode;
        String url = BaseUrl.getBaseurl() + "Employee/complaints";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog.dismiss();
                    //Toast.makeText(ComplaintsActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String message = jsonObject.getString("response");
                    if (message.equalsIgnoreCase("Success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                ComplaintData complaintData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<ComplaintData>() {
                                }.getType());
                                complaintsDataList.add(complaintData);
                            }
                            SpinnerComplaintAdapter adapter = new SpinnerComplaintAdapter(IndentRaisingActivity.this, complaintsDataList);
                            inventory_complaint.setAdapter(adapter);
                            // Toast.makeText(IndentRaisingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(IndentRaisingActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id", getempID);
                return params;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

     /*   StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                ComplaintData complaintData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<ComplaintData>() {}.getType());
                                complaintsDataList.add(complaintData);
                            }

                            SpinnerComplaintAdapter adapter = new SpinnerComplaintAdapter(IndentRaisingActivity.this, complaintsDataList);
                            inventory_complaint.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(IndentRaisingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
    }

    public void openDateCalendar(View view) {
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        ic_calender.setVisibility(View.GONE);
                       selectdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                        selectdate.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
/*    private Handler complaintHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            ComplaintData complaintData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<ComplaintData>() {}.getType());
                            complaintsDataList.add(complaintData);
                        }

                        SpinnerComplaintAdapter adapter = new SpinnerComplaintAdapter(IndentRaisingActivity.this, complaintsDataList);
                        inventory_complaint.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };*/

    /*private Handler iHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    inventoryList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            InventoryItemsData inventoryItemsData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<InventoryItemsData>() {}.getType());
                            inventoryList.add(inventoryItemsData);
                        }
                    }
                    SpinnerInventoryAdapter adapter = new SpinnerInventoryAdapter(IndentRaisingActivity.this, inventoryList);
                    inventory_item.setAdapter(adapter);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };*/

    public void submit(View view) {

        required_qty = Quality.getText().toString().trim();
        Quality1=Quality.getText().toString();
        required_date = selectdate.getText().toString();
        selectddate = selectdate.getText().toString();
        rremarks = remarks.getText().toString();

        Log.d("remaks", rremarks);
           if (Quality1.isEmpty()) {
            Quality.setError("please select quality");
        }
        else if (selectddate.isEmpty()) {
            selectdate.setError("please select date");
        }

        else if(rremarks.isEmpty()) {
            remarks.setError(" please enter remarks");
        }

        else {
            remarksvalidation1();

        }
    }

    private void remarksvalidation1() {
        IndentRaisingonbuttonClick();

    }
       /* Intent dataIntent = new Intent(IndentRaisingActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(INDENTHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.INDENT_RASING.ordinal());
        dataIntent.putExtra("getempID", getempID);
        dataIntent.putExtra("inv_id", inv_id);
        dataIntent.putExtra("required_qty", required_qty);
        dataIntent.putExtra("indent_reason", indent_reason);
        dataIntent.putExtra("required_date", required_date);
        startService(dataIntent);*/


        private void IndentRaisingonbuttonClick () {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Please Wait..");
            dialog.show();
            String uri = BaseUrl.getBaseurl() + "Employee/indent_raising";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("msg");
                        Toast.makeText(IndentRaisingActivity.this, "" + message, Toast.LENGTH_SHORT).show();
//                    JSONObject jsonObject=new JSONObject(response);
//                    dialog.dismiss();
//                    String message = jsonObject.getString( "msg");
                        String invoice_id = jsonObject.getString("indent_inv_id");
//                   // Toast.makeText(IndentRaisingActivity.this, ""+invoice_id, Toast.LENGTH_SHORT).show();
//                    if (message.equalsIgnoreCase("Indent Raised Successfully.")) {
//                        Iterator<String> keys = jsonObject.keys();
//                        while (keys.hasNext()) {
//                            String key = keys.next();
//                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
//                               Intent intent=new Intent(IndentRaisingActivity.this,IndentSuccessActivity.class);
//                               intent.putExtra("invoice_id",invoice_id);
//                              startActivity(intent);
//                              finish();
                        Intent intent = new Intent(IndentRaisingActivity.this, EMPHomeActivity.class);
                        intent.putExtra("invoice_id", invoice_id);
                        startActivity(intent);
                        finish();
//                                Toast.makeText(IndentRaisingActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                    else {
//                        dialog.dismiss();
//                        Toast.makeText(IndentRaisingActivity.this, "Error Message", Toast.LENGTH_SHORT).show();
//                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
//                error.printStackTrace();
                    Toast.makeText(IndentRaisingActivity.this, "" + error, Toast.LENGTH_SHORT).show();

                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type","application/json");
                    headers.put("x-access-token", SaveAppData.getOperatorLoginData().getAccessToken());
                    return headers;

//            @Override
//            public Map<String, String> getHeaders () throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
////                headers.put("Content-Type","application/json");
//                headers.put("x-access-token", SaveAppData.getOperatorLoginData().getAccessToken());
//                return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("emp_id", getempID);
                    params.put("inv_id", inv_id);
//                parms.put("gps_lat",String.valueOf(latitude));
//                parms.put("gps_lang",String.valueOf(longitude));
                    params.put("required_qty", required_qty);
                    params.put("indent_reason", indent_reason);
                    params.put("required_date", required_date);
                    params.put("indent_remarks", remarks.getText().toString());
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
       /* uri=mainURL+"indent_raising.php?emp_id="+getempID+"&inv_id="+inv_id+"&required_qty="+required_qty+"&indent_reason="+indent_reason+"&required_date="+required_date+"";
        Log.e("IdentRaising",uri);
        Log.d("IdentRaising",uri);
        System.out.println(uri);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    String invoice_id=jsonObject.getString("invoice_id");
                    if (message.equalsIgnoreCase("success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                Intent intent=new Intent(IndentRaisingActivity.this,IndentSuccessActivity.class);
                                intent.putExtra("invoice_id",invoice_id);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(IndentRaisingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/

        }

    /*private Handler INDENTHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                String invoice_id=responseObj.getString("invoice_id");
                if (message.equalsIgnoreCase("success")) {
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            Intent intent=new Intent(IndentRaisingActivity.this,IndentSuccessActivity.class);
                            intent.putExtra("invoice_id",invoice_id);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };*/

