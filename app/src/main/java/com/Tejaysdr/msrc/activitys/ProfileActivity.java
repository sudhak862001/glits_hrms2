package com.Tejaysdr.msrc.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.emp.ChangePasswordActivity;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

public class ProfileActivity extends AppCompatActivity{
    Toolbar toolbar_profile;
    TextView u_name,tv_mobile,tv_email,pempusername,pempdesignation;
    Button change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar_profile=findViewById(R.id.toolbar_profile);
        change_password=findViewById(R.id.btn_changepassword);
        setSupportActionBar(toolbar_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_profile.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Profile");
        tv_email=findViewById(R.id.tv_email);
        tv_mobile=findViewById(R.id.tv_mobile);
        pempusername=findViewById(R.id.pempusername);
        u_name=findViewById(R.id.u_name);
        pempdesignation=findViewById(R.id.pempdesignation);
        u_name.setText(SaveAppData.getOperatorLoginData().getFullname());
        tv_mobile.setText(SaveAppData.getOperatorLoginData().getMobile());
        tv_email.setText(SaveAppData.getOperatorLoginData().getEmail());
        pempusername.setText(SaveAppData.getOperatorLoginData().getLogin_username());
        pempdesignation.setText(SaveAppData.getOperatorLoginData().getDesignation());

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(ProfileActivity.this,EMPHomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /*@Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if (key_code== KeyEvent.KEYCODE_BACK)
            super.onKeyDown(key_code, key_event);
            return false;
        }*/
       // return false;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this,EMPHomeActivity.class));
        super.onBackPressed();
        finish();
    }
}