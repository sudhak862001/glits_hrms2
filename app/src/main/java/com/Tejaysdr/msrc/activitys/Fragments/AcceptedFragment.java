package com.Tejaysdr.msrc.activitys.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Tejaysdr.msrc.adapter.ComplaintAdapter2;
import com.Tejaysdr.msrc.utils.ComplaintInterface;
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
import com.Tejaysdr.msrc.activitys.emp.UpdateComplaintActivity;
import com.Tejaysdr.msrc.adapter.ComplaintAdapter;
import com.Tejaysdr.msrc.dataModel.ComplaintData;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
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
 * Use the {@link AcceptedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptedFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    TextView empname,tv_norecords;
    RecyclerView complaintslist;
    Context mcontext;
    ArrayList<ComplaintData> complaintsDataList = new ArrayList<>();
    private OperatorCode operatorCode;
    private String objectempcode;


    public AcceptedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AcceptedFragment newInstance(String param1, String param2) {
        AcceptedFragment fragment = new AcceptedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accepted, container, false);
        swipeRefreshLayout=view.findViewById(R.id.swipelayout2);
        complaintslist = view.findViewById(R.id.complaintlist1);
        complaintslist.setHasFixedSize(true);
        complaintslist.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
        tv_norecords=view.findViewById(R.id.tv_norecords);
        // complaintslist.setOnItemClickListener(getContext());
        /*complaintslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ComplaintData complaintData=complaintsDataList.get(position);
                Intent updateintent=new Intent(getActivity(), UpdateComplaintActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("complaintData", complaintData);
                updateintent.putExtra("spinnerstatus","Accepted");
                updateintent.putExtra("complaint",complaintData.getCompStatus());
                updateintent.putExtra("complaintdesc",complaintData.getCompCategory());
                updateintent.putExtra("complaintdate",complaintData.getCompDateCreated());
                updateintent.putExtras(bundle);
                startActivity(updateintent);

            }
        });*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetComplaintInfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        GetComplaintInfo();
        return view;
    }

    private void GetComplaintInfo() {
        //operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        objectempcode = SaveAppData.getOperatorLoginData().getEmp_id();
        //mainURL = operatorCode.getop_url();
        String url = BaseUrl.getBaseurl() + "Employee/my_complaints";
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog.dismiss();
                    //Toast.makeText(ComplaintsActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                    String message = jsonObject.getString("response");
                    complaintsDataList=new ArrayList<>();
                    if (message.equalsIgnoreCase("Success")) {
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                ComplaintData complaintData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<ComplaintData>() {
                                }.getType());

                                complaintsDataList.add(complaintData);

                            }
                            ComplaintAdapter2 adapter = new ComplaintAdapter2(mcontext, complaintsDataList, new ComplaintInterface() {
                                @Override
                                public void OnItemClick(int position) {
                                    ComplaintData complaintData=complaintsDataList.get(position);
                                    Intent updateintent=new Intent(getActivity(), UpdateComplaintActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("complaintData", complaintData);
                                    updateintent.putExtra("spinnerstatus","Accepted");
                                    updateintent.putExtra("complaint",complaintData.getCompStatus());
                                    updateintent.putExtra("complaintdesc",complaintData.getCompCategory());
                                    updateintent.putExtra("complaintdate",complaintData.getCompDateCreated());
                                    updateintent.putExtras(bundle);
                                    startActivity(updateintent);
                                }
                            });
                            complaintslist.setAdapter(adapter);
                            complaintslist.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);

                        }
                    } else {
                        dialog.dismiss();
                        tv_norecords.setVisibility(View.VISIBLE);
                        complaintslist.setVisibility(View.GONE);

                        // Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
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
                params.put("emp_id", objectempcode);
                params.put("comp_status","Accepted");
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}

