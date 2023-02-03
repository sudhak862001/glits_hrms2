package com.Tejaysdr.msrc.activitys.emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseActivity;

public class IndentSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_success);
        //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        TextView textView=(TextView)findViewById(R.id.invoice_id);
       // String id=getIntent().getExtras().getString("invoice_id");
       // textView.setText("Invoice : "+id);
        Button button=(Button) findViewById(R.id.Redirecttodashboard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IndentSuccessActivity.this,EMPHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
