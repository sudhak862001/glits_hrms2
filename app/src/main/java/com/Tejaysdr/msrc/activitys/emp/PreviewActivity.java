package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Tejaysdr.msrc.ImagepickerActivity;
import com.Tejaysdr.msrc.MSRCApplication;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.EMPLoginActivity;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class PreviewActivity extends AppCompatActivity {
    LinearLayout linearLayout;
   EditText reject;
   TextView emp_name;
    Spinner spinner;
    Button submit;
    String[] spinners = {"Select","Accept","Reject"};
    PDFView pdfView;
    String emp_accept = "0",emp_rejectrmrks = "";
    String pdfurl = null ;//"https://tejays.digitalrupay.com/emp_appointment_letters/5126_Emp_APP.pdf";

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        linearLayout=findViewById(R.id.resonlinear);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        reject=findViewById(R.id.reason);
        submit = findViewById(R.id.submitpreview);
        spinner = findViewById(R.id.spinner);
        emp_name = findViewById(R.id.emp_name);

        OperatorLoginData operatorCode = null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();

        emp_name.setText("Hi, "+operatorCode.getFullname());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinners);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getSelectedItem().equals("Reject")){
                   linearLayout.setVisibility(View.VISIBLE);
                }
                else{
                    linearLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner.getSelectedItem().equals("Select")){
                    Toast.makeText(PreviewActivity.this, "Please Select Accept or Reject", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateempappointmentstatus();

                }

            }
        });

        pdfurl = SaveAppData.getOperatorLoginData().getEmp_appointment_letter();


        if (pdfurl != null){
        new RetrivePDFfromUrl().execute(pdfurl);
        progressDialog.show();
        }
    }

    private void updateempappointmentstatus() {

        progressDialog.show();

        emp_accept = spinner.getSelectedItem().toString();
        emp_rejectrmrks = reject.getText().toString();

        if (emp_accept.equals("Accept")){
            emp_accept = "1";
        }else {
            emp_accept = "2";
        }
//        Toast.makeText(this, "emp Accept"+emp_accept+"\nremarks"+emp_rejectrmrks, Toast.LENGTH_SHORT).show();

        String baseurl = BaseUrl.getBaseurl() + "employee/update_emp_appointment_letter";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("response");
                    if (msg.equals("Success")) {
                        if (emp_accept.equals("2")) {
                            logoutmethod();
                        }  else {
                            Intent intent=new Intent(PreviewActivity.this,EMPHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                } catch (JSONException e) {
                    Toast.makeText(PreviewActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PreviewActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("emp_appointment_acceptance", emp_accept);
                params.put("emp_appointment_reject_reason", emp_rejectrmrks);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void logoutmethod() {
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        //       String baseurl ="http://crm.nettlinx.com/webservices/Employee/emp_logout";

        //battery percentage
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level * 100) / (float)scale;
//        Toast.makeText(this, "Battery Percentage "+batteryPct, Toast.LENGTH_SHORT).show();
        String baseurl =BaseUrl.getBaseurl()+"Employee/emp_logout";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, baseurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                EMPHomeActivity.getInstance().removeLocationUpdates();
                SaveAppData.setusename("");
                SaveAppData.saveOperatorLoginData(null);
                SaveAppData.saveOperatorData(null);
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                //               Toast.makeText(EMPHomeActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                MSRCApplication.EMPStatusCode="0";
                Intent login = new Intent(PreviewActivity.this, EMPLoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PreviewActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("token","token");
                params.put("gps_lat",String.valueOf("latitude"));
                params.put("gps_lang",String.valueOf("longitude"));
                params.put("battery_package",String.valueOf(batteryPct));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        // after the execution of our async
        // task we are loading our pdf in our pdf view.
        pdfView.fromStream(inputStream).load();
        progressDialog.dismiss();
    }
}
}