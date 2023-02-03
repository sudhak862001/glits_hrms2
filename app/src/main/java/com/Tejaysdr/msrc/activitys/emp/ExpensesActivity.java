package com.Tejaysdr.msrc.activitys.emp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.Tejaysdr.msrc.R;

public class ExpensesActivity extends AppCompatActivity {
    private static final String TAG = "";
    Toolbar toolbar;
    CardView cv_raiseexp,cv_manageapproval,cv_financeapprove,cd_bdelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        cv_raiseexp=(CardView) findViewById(R.id.cd_raiseexpenses);
        cd_bdelist=(CardView) findViewById(R.id.cd_bdelist);
        cv_manageapproval=(CardView) findViewById(R.id.cv_manageapproval);
        cv_financeapprove=(CardView) findViewById(R.id.cv_financeapprove);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Expenses");

        cv_raiseexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ExpensesActivity.this, NewraiseExpensesActivity.class);
                i.putExtra("Page","New");
                startActivity(i);
            }
       });
        cd_bdelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ExpensesActivity.this, ExpenseslListActivity.class);
                i.putExtra("Page","New");
                startActivity(i);
            }
        });

        cv_manageapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ExpensesActivity.this, ExpenseslListActivity.class);
                startActivity(i);
            }
        });
        cv_financeapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ExpensesActivity.this,CompletedExpensesActivity.class);
                startActivity(i);

            }
        });
  }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i=new Intent(this,EMPHomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    }
