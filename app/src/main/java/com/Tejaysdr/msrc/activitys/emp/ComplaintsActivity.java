package com.Tejaysdr.msrc.activitys.emp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.adapter.ComplaintAdapter;
import com.Tejaysdr.msrc.dataModel.ComplaintData;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.Tejaysdr.msrc.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ComplaintsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    TextView empname;
    ListView complaintslist;
    ArrayList<ComplaintData> complaintsDataList = new ArrayList<>();
    private OperatorCode operatorCode;
    String mainURL;
    private String objectempcode;
    Toolbar toolbar_complaintlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        OperatorLoginData operatorCode = null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        toolbar_complaintlist=findViewById(R.id.toolbar_complaintlist);
        setSupportActionBar(toolbar_complaintlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_complaintlist.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Complaints");

        empname = (TextView) findViewById(R.id.in_empname);
        complaintslist = (ListView) findViewById(R.id.complaintlist);

        String getempID = operatorCode.getRole_id();
        getComplaintsList(getempID);

        String getempname = operatorCode.getFullname();
        //empname.setText("Hello " + getempname);

        complaintslist.setOnItemClickListener(this);
    }

    private void getComplaintsList(String getempID) {
        GetComplaintInfo();
       /* Intent dataIntent = new Intent(ComplaintsActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(COMPLAINTS_LISTHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.COMPLAINTS_LIST.ordinal());
        dataIntent.putExtra("jsonObject", getempID);
        startService(dataIntent);
        startService(dataIntent);*/
    }

    private void GetComplaintInfo() {
        //operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        objectempcode = SaveAppData.getOperatorLoginData().getEmp_id();
        //mainURL = operatorCode.getop_url();
        String url = BaseUrl.getBaseurl() + "Employee/complaints";
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
   /*     StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    //Toast.makeText(ComplaintsActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                ComplaintData complaintData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<ComplaintData>() {}.getType());
                                complaintsDataList.add(complaintData);
                            }
                            ComplaintAdapter adapter=new ComplaintAdapter(ComplaintsActivity.this,complaintsDataList);
                            complaintslist.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(ComplaintsActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }*/
StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            dialog.dismiss();
            //Toast.makeText(ComplaintsActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
            String message = jsonObject.getString("response");
            if (message.equalsIgnoreCase("Success")) {
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                        ComplaintData complaintData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<ComplaintData>() {}.getType());

                        complaintsDataList.add(complaintData);
                    }
                    ComplaintAdapter adapter=new ComplaintAdapter(ComplaintsActivity.this,complaintsDataList);
                    complaintslist.setAdapter(adapter);
                }
            }
            else {
                dialog.dismiss();
                Toast.makeText(ComplaintsActivity.this, ""+message, Toast.LENGTH_SHORT).show();
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

    }){
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String,String>params=new HashMap<>();
        params.put("emp_id",objectempcode);
        params.put("complaint_type","open");
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
RequestQueue requestQueue=Volley.newRequestQueue(this);
requestQueue.add(stringRequest);
    }




/*    private Handler COMPLAINTS_LISTHandler=new Handler(){
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
                        ComplaintAdapter adapter=new ComplaintAdapter(ComplaintsActivity.this,complaintsDataList);
                        complaintslist.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ComplaintData complaintData=complaintsDataList.get(position);
        Intent updateintent=new Intent(ComplaintsActivity.this,UpdateComplaintActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("complaintData", complaintData);
        updateintent.putExtras(bundle);
        startActivity(updateintent);
    }
}
