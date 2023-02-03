package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.Tejaysdr.msrc.activitys.EMPLoginActivity;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ChangePasswordActivity extends AppCompatActivity {
    Button bt_passowrdsubmit;
    EditText tv_oldpassword,tv_newpassword,tv_cpassword;
    Toolbar toolbar_chnagepasssword;
     String oldpassword,newpassword,conformpassword;
    private ImageView iv_visible,iv_invisible,iv_visible1,iv_invisible1,iv_visible2,iv_invisible2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        tv_oldpassword=findViewById(R.id.tv_oldpassword);
        tv_newpassword=findViewById(R.id.tv_newpassword);
        tv_cpassword=findViewById(R.id.tv_cpassword);
        bt_passowrdsubmit=findViewById(R.id.bt_passowrdsubmit);
        toolbar_chnagepasssword=findViewById(R.id.toolbar_chnagepasssword);
        toolbar_chnagepasssword=findViewById(R.id.toolbar_chnagepasssword);
        iv_invisible=findViewById(R.id.iv_invisible);
        iv_invisible1=findViewById(R.id.iv_invisible1);
        iv_invisible2=findViewById(R.id.iv_invisible2);
        iv_visible=findViewById(R.id.iv_visible);
        iv_visible1=findViewById(R.id.iv_visible1);
        iv_visible2=findViewById(R.id.iv_visible2);
        setSupportActionBar(toolbar_chnagepasssword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_chnagepasssword.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Change Password");
        oldpassword=tv_oldpassword.getText().toString();
         newpassword=tv_newpassword.getText().toString();
      conformpassword=tv_cpassword.getText().toString();
        bt_passowrdsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tv_oldpassword.getText().toString().isEmpty())
                {
                    tv_oldpassword.setError("Enter Old Password");
                    Toast.makeText(ChangePasswordActivity.this, "Enter Old Password", Toast.LENGTH_SHORT).show();
                }
                else if (tv_newpassword.getText().toString().isEmpty())
                {
                    Toast.makeText(ChangePasswordActivity.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                }
                else if (!tv_cpassword.getText().toString().equals(tv_newpassword.getText().toString()))
                {
                    Toast.makeText(ChangePasswordActivity.this, "old password and new password should not be same", Toast.LENGTH_SHORT).show();
                }
                else if (tv_oldpassword.getText().toString().equals(tv_newpassword.getText().toString()))
                {
                    Toast.makeText(ChangePasswordActivity.this, "old password and new password should not be same", Toast.LENGTH_SHORT).show();
                }

                else {
                    Changepassword();

                }
            }
        });

        iv_invisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible.setVisibility(View.VISIBLE);
                iv_invisible.setVisibility(View.GONE);
                String pass = tv_oldpassword.getText().toString();
                tv_oldpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                tv_oldpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_oldpassword.setText(pass);
                tv_oldpassword.setSelection(pass.length());
            }
        });

        iv_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible.setVisibility(View.GONE);
                iv_invisible.setVisibility(View.VISIBLE);
                String pass = tv_oldpassword.getText().toString();
                tv_oldpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                tv_oldpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tv_oldpassword.setText(pass);
                tv_oldpassword.setSelection(pass.length());
            }
        });
        iv_invisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible1.setVisibility(View.VISIBLE);
                iv_invisible1.setVisibility(View.GONE);
                String pass = tv_newpassword.getText().toString();
                tv_newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                tv_newpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_newpassword.setText(pass);
                tv_newpassword.setSelection(pass.length());
            }
        });

        iv_visible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible1.setVisibility(View.GONE);
                iv_invisible1.setVisibility(View.VISIBLE);
                String pass = tv_newpassword.getText().toString();
                tv_newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                tv_newpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tv_newpassword.setText(pass);
                tv_newpassword.setSelection(pass.length());
            }
        });
        iv_invisible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible2.setVisibility(View.VISIBLE);
                iv_invisible2.setVisibility(View.GONE);
                String pass = tv_cpassword.getText().toString();
                tv_cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                tv_cpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_cpassword.setText(pass);
                tv_cpassword.setSelection(pass.length());
            }
        });

        iv_visible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_visible2.setVisibility(View.GONE);
                iv_invisible2.setVisibility(View.VISIBLE);
                String pass = tv_cpassword.getText().toString();
                tv_cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                tv_cpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tv_cpassword.setText(pass);
                tv_cpassword.setSelection(pass.length());
            }
        });

    }

    private void Changepassword() {
        String url= BaseUrl.getBaseurl()+"Employee/change_pwd";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String message=jsonObject.getString("response");
                    if (message.equalsIgnoreCase("Success"))
                    {
                        Toast.makeText(ChangePasswordActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(ChangePasswordActivity.this, EMPLoginActivity.class);
                        SaveAppData.saveOperatorLoginData(null);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                    }
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
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("username",SaveAppData.getOperatorLoginData().getLogin_username());
                params.put("password",tv_oldpassword.getText().toString());
                params.put("new_password",tv_newpassword.getText().toString());
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}