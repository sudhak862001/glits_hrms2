package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.Tejaysdr.msrc.R;

public class LeaveActivity extends AppCompatActivity {
    private static final String TAG = "";
    Toolbar toolbar;
    CardView cd_entreturns,cd_viewreturns,cd_pending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        cd_viewreturns=(CardView) findViewById(R.id.cd_viewreturns);
        cd_pending=(CardView) findViewById(R.id.cd_pending);
        cd_entreturns=(CardView) findViewById(R.id.cd_entreturns);
        cd_entreturns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LeaveActivity.this,ApplyleaveActivity.class);
                startActivity(i);
            }
        });
        cd_viewreturns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LeaveActivity.this,AppliedleaveActivity.class);
                startActivity(i);
            }
        });
        cd_pending.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
//                if(SaveAppData.getOperatorLoginData().getLogin_emp_id().equals("1")||SaveAppData.getOperatorLoginData().equals("2")) {
                    Intent i = new Intent(LeaveActivity.this,PendingleaveActivity.class);
                                          startActivity(i);
              }
//              else {
//                  Toast.makeText(LeaveActivity.this,"You Don't have access", Toast.LENGTH_SHORT).show();
//            }
//            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Leave Management");
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    }
