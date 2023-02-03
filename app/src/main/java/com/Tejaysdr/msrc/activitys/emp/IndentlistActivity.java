package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.adapter.Complaints1Adapter;
import com.Tejaysdr.msrc.dataModel.InventoryData;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class IndentlistActivity extends AppCompatActivity {
    TextView empname;
    ListView inventorylist;
    ArrayList<InventoryData> inventoryDataList = new ArrayList<>();
    private OperatorCode operatorCode;
    private String mainURL,uri;
    Toolbar toolbar_mlist3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indentlist);
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
//        empname=(TextView)findViewById(R.id.in_empname);
        inventorylist=(ListView)findViewById(R.id.indentlist2);
       toolbar_mlist3=findViewById(R.id.toolbar_mlist3);
        setSupportActionBar(toolbar_mlist3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_mlist3.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Indent list");
//        String getempname=operatorCode.getFullname();
//        empname.setText("Hello "+getempname);
//        String getempID=operatorCode.getEmp_id();
        getIndentlist();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    private void getIndentlist() {
        getInventoryListtovolley();
    }

    private void getInventoryListtovolley() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
      /*  operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"emp_inventory.php?emp_id="+objectempcode;*/
        uri = BaseUrl.getBaseurl() +"Employee/emp_indent_list";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    //Toast.makeText(InventoryListActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String response12 = jsonObject.getString("response");
                    if (response12.equalsIgnoreCase("Success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                InventoryData inventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<InventoryData>() {}.getType());
                                inventoryDataList.add(inventoryData);
                            }
                           Complaints1Adapter adapter=new Complaints1Adapter(IndentlistActivity.this,inventoryDataList);
                            inventorylist.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(IndentlistActivity.this, ""+response12, Toast.LENGTH_SHORT).show();
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
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
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
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(IndentlistActivity.this,InventoryActivity.class));
//        super.onBackPressed();
//        finish();
//    }
}
