package com.Tejaysdr.msrc.activitys.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.Tejaysdr.msrc.adapter.IndentlistAdapter;
import com.Tejaysdr.msrc.adapter.InventoryAssignedAdapter;
import com.Tejaysdr.msrc.dataModel.InventoryData;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndentlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndentlistFragment extends Fragment {

    Context context;
    InventoryAssignedAdapter assignedAdapter;
    ArrayList<InventoryData> list=new ArrayList();
    ListView listView;
    InventoryData inventoryData;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;


    public IndentlistFragment() {
        // Required empty public constructor
    }


    public static IndentlistFragment newInstance(String param1, String param2) {
        IndentlistFragment fragment = new IndentlistFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_indentlist, container, false);
        context=getActivity();

        requestQueue= Volley.newRequestQueue(context);
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);

        listView=root.findViewById(R.id.LV_returnlist);

//        assignedAdapter=new InventoryAssignedAdapter(context,list);
//        listView.setAdapter(assignedAdapter);

        getInventoryListtovolley();

        return root;
    }

    private void getInventoryListtovolley() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait..");
        dialog.show();
      /*  operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"emp_inventory.php?emp_id="+objectempcode;*/
      String  uri = BaseUrl.getBaseurl() +"employee/emp_return_inv_list";
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
                            if (!key.equalsIgnoreCase("msg") && !key.equalsIgnoreCase("text")) {
                                InventoryData inventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<InventoryData>() {}.getType());
                                list.add(inventoryData);
                            }
                            IndentlistAdapter adapter=new IndentlistAdapter(context,list);
                            listView.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(context, ""+response12, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}