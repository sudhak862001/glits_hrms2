package com.Tejaysdr.msrc.activitys.emp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

public class InventoryActivity extends BaseActivity {
    TextView empname;
    Toolbar toolbar_inventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.in_empname);
        toolbar_inventory=findViewById(R.id.toolbar_inventory);
        setSupportActionBar(toolbar_inventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_inventory.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setTitle("Materials and Indent");
        String getempname=operatorCode.getFullname();
        //empname.setText("Hello "+getempname);
    }
    public void goInventorylist(View view){
        Intent intent=new Intent(this,InventoryListActivity.class);
        startActivity(intent);
    }
//    public void goIndent(View view){
//        Intent intent=new Intent(this,IndentsActivity.class);
//       startActivity(intent);
//    }
    public void goIndent(View view){
        Intent intent=new Intent(this,IndentRaisingActivity.class);
        startActivity(intent);
    }
    public void goAssignedmaterials(View view){
        Intent intent=new Intent(this,AssignedMaterialsActivity.class);
        startActivity(intent);
    }
    public void goIndentlist(View view){
        Intent intent=new Intent(this,IndentlistActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InventoryActivity.this,EMPHomeActivity.class));
        finish();
    }
}
