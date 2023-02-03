package com.Tejaysdr.msrc.activitys.emp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.Tejaysdr.msrc.activitys.Fragments.QualityFragment;
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
import com.Tejaysdr.msrc.activitys.Fragments.AcceptedFragment;
import com.Tejaysdr.msrc.activitys.Fragments.AssingedFragment;
import com.Tejaysdr.msrc.activitys.Fragments.CompletedFragment;
import com.Tejaysdr.msrc.activitys.Fragments.InTransitFragment;
import com.Tejaysdr.msrc.activitys.Fragments.OnsiteFragment;
import com.Tejaysdr.msrc.activitys.Fragments.PendingforcustomerdependencyFragment;
import com.Tejaysdr.msrc.activitys.Fragments.PendingforothersFragment;
import com.Tejaysdr.msrc.activitys.Fragments.PendingforsparesFragment;
import com.Tejaysdr.msrc.activitys.Fragments.ResolvedFragment;
import com.Tejaysdr.msrc.activitys.Fragments.WorkInProgressFragment;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ComplaintListCatActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    Toolbar toolbar_complaint_cat;
    private String objectempcode;
    private String TotalComplaints,OpenComplaints,AssignedComplaints,Acceptedcompplaints,InTransitcomplaints,QualityCheck,
            OnsiteComplaints,WorkInProgressComplaints,ResolvedComplaints,ClosedComplaints,PendingforsparesComplaints,PendingforcustomerdependencyComplaints,PendingforothersComplaints;
    TabItem tv_tabitem1;
    private String inprogress;

    Fragment f1=new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list_cat);

        toolbar_complaint_cat=findViewById(R.id.toolbar_complaint_cat);
        setSupportActionBar(toolbar_complaint_cat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_complaint_cat.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Complaints");
        getComplaintCount();
       /* setTitle("Complaints");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            //startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:12345678901")));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*String i = getIntent().getExtras().getString("FRAGMENT");

        switch (i){
            case completed:
             f1=  new  CompletedFragment();
               break;
        }*/
    }

    private void getComplaintCount() {
        //operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        objectempcode = SaveAppData.getOperatorLoginData().getEmp_id();
        //mainURL = operatorCode.getop_url();
        String url = BaseUrl.getBaseurl() + "Employee/complaint_dashboard";
        final ProgressDialog dialog = new ProgressDialog(ComplaintListCatActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialog.dismiss();
//                    Toast.makeText(ComplaintListCatActivity.this, "hi"+jsonObject, Toast.LENGTH_SHORT).show();
                    String message = jsonObject.getString("msg");
                    if (message.equalsIgnoreCase("Successfull")) {
                         TotalComplaints=jsonObject.getString("TotalComplaints");
////                        OpenComplaints=jsonObject.getString("OpenComplaints");
//                        Toast.makeText(ComplaintListCatActivity.this, "hi"+jsonObject, Toast.LENGTH_SHORT).show();

                        AssignedComplaints=jsonObject.getString("AssignedComplaints");
                        Acceptedcompplaints=jsonObject.getString("AcceptedComplaints");
                        InTransitcomplaints=jsonObject.getString("InTransitComplaints");
//                        Toast.makeText(ComplaintListCatActivity.this, "AssignedComplaints"+AssignedComplaints, Toast.LENGTH_SHORT).show();
                      OnsiteComplaints=jsonObject.getString("ONsiteComplaints");
                      WorkInProgressComplaints=jsonObject.getString("WorkInProgressComplaints");
                        ResolvedComplaints=jsonObject.getString("ResolvedComplaints");
                         ClosedComplaints=jsonObject.getString("ClosedComplaints");
                        PendingforsparesComplaints=jsonObject.getString("PendingForSparesComplaints");
                       PendingforcustomerdependencyComplaints=jsonObject.getString("PendingForCustomerDependency");
                       PendingforothersComplaints=jsonObject.getString("PendingForOthers");
                        QualityCheck=jsonObject.getString("Qualitycheck");
//                        Toast.makeText(ComplaintListCatActivity.this, "heloooo"+TotalComplaints+AssignedComplaints, Toast.LENGTH_SHORT).show();
//                         tabLayout.getTabAt(0).setText("Open"+"("+OpenComplaints+")");
//                        tabLayout.getTabAt(1).setText("InProgress"+"("+inprogress+")");
//                        tabLayout.getTabAt(2).setText("Resolved"+"("+ResolvedComplaints+")");
//                        tabLayout.getTabAt(3).setText("Completed"+"("+ClosedComplaints+")");
                        String bluetype = "<font color='#FF3700B3'>";
                        String y = "</font>";
                        String red = "<font color='#FF4081'>";
                       /* t1.setText(Html.fromHtml("<font color='#FF3700B3'>"+"Aadhar No/Register No"+"</font>"+"   "+"<font color='#F44336'>"+"*"+"</font>"));
                        t1.setSelected(true);*/

                        tabLayout.getTabAt(0).setText(Html.fromHtml("Assigned"+""+red+"("+AssignedComplaints+")"+y));
                        tabLayout.getTabAt(1).setText(Html.fromHtml("Accepted"+""+red+"("+Acceptedcompplaints+")"+y));
                        //                      tabLayout.getTabAt(3).setText("Completed"+"("+ClosedComplaints+")");
                        tabLayout.getTabAt(2).setText(Html.fromHtml("InTransit"+""+red+"("+InTransitcomplaints+")"+y));
                        tabLayout.getTabAt(3).setText(Html.fromHtml("Onsite"+""+red+"("+OnsiteComplaints+")"+y));
                        tabLayout.getTabAt(4).setText(Html.fromHtml("Workinprogress"+""+red+"("+WorkInProgressComplaints+")"+y));
//                        tabLayout.getTabAt(6).setText("Resolved"+"("+ResolvedComplaints+")");
//                        tabLayout.getTabAt(7).setText("Closed"+"("+ClosedComplaints+")");
                        tabLayout.getTabAt(5).setText(Html.fromHtml("Pending for spares"+""+red+"("+PendingforsparesComplaints+")"+y));
                        tabLayout.getTabAt(6).setText(Html.fromHtml("Pending for customer dependency"+""+red+"("+PendingforcustomerdependencyComplaints+")"+y));
                        tabLayout.getTabAt(7).setText(Html.fromHtml("Pending for others"+""+red+"("+PendingforothersComplaints+")"+y));
                        tabLayout.getTabAt(8).setText(Html.fromHtml("Resolved"+""+red+"("+ResolvedComplaints+")"+y));
                        tabLayout.getTabAt(9).setText(Html.fromHtml("Quality Check"+""+red+"("+QualityCheck+")"+y));
                        tabLayout.getTabAt(10).setText(Html.fromHtml("Closed"+""+red+"("+ClosedComplaints+")"+y));



                    } else {
                        dialog.dismiss();
                        Toast.makeText(ComplaintListCatActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ComplaintListCatActivity.this, "ERRORRR", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id",objectempcode);
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
        RequestQueue requestQueue = Volley.newRequestQueue(ComplaintListCatActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
    @SuppressLint("ResourceType")
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new OpenFragment(), "Open Complaint");
//        adapter.addFragment(new InProgressFragment(), "In Progress");
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Completed");
       adapter.addFragment(new AssingedFragment(), "Assigned"+"("+AssignedComplaints+")");
        adapter.addFragment(new AcceptedFragment(), "Accepted"+"("+Acceptedcompplaints+")");
        adapter.addFragment(new InTransitFragment(), "In Transit"+"("+InTransitcomplaints+")");
        adapter.addFragment(new OnsiteFragment(), "OnSite"+"("+OnsiteComplaints+")");
        adapter.addFragment(new WorkInProgressFragment(), "Work in progress"+"("+WorkInProgressComplaints+")");
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Closed");
        adapter.addFragment(new PendingforsparesFragment(), "Pending For Spares"+"("+PendingforsparesComplaints+")");
        adapter.addFragment(new PendingforcustomerdependencyFragment(), "Pending for Customer Dependency"+"("+PendingforcustomerdependencyComplaints+")");
        adapter.addFragment(new PendingforothersFragment(), "Pending For Others"+"("+PendingforothersComplaints+")");
        adapter.addFragment(new ResolvedFragment(), "Resolved"+"("+ResolvedComplaints+")");
        adapter.addFragment(new QualityFragment(),"Quality Check"+"("+QualityCheck+")");
        adapter.addFragment(new CompletedFragment(), "Closed"+"("+ClosedComplaints+")");
        viewPager.setAdapter(adapter);


    }
   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new OpenFragment(), "Open Complaint");
//        adapter.addFragment(new InProgressFragment(), "In Progress");
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Completed");
        adapter.addFragment(new AssingedFragment());
        adapter.addFragment(new AcceptedFragment());
        adapter.addFragment(new InTransitFragment());
        adapter.addFragment(new OnsiteFragment());
        adapter.addFragment(new WorkInProgressFragment());
//        adapter.addFragment(new ResolvedFragment(), "Resolved");
//        adapter.addFragment(new CompletedFragment(), "Closed");
        adapter.addFragment(new PendingforsparesFragment());
        adapter.addFragment(new PendingforcustomerdependencyFragment());
        adapter.addFragment(new PendingforothersFragment());
        adapter.addFragment(new ResolvedFragment());
        adapter.addFragment(new QualityFragment());
        adapter.addFragment(new CompletedFragment());
        viewPager.setAdapter(adapter);


    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title ) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
       /* public void addFragment(Fragment fragment ) {
            mFragmentList.add(fragment);

        }*/
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);


        }

    }

}