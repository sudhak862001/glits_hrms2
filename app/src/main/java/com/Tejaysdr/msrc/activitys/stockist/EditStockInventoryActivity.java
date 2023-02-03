package com.Tejaysdr.msrc.activitys.stockist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.dataModel.StockistInventoryData;
import com.Tejaysdr.msrc.dataModel.UpdateAdmininventoryData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

public class EditStockInventoryActivity extends BaseActivity {
    StockistInventoryData stockistInventoryData;
    TextView Inventory_Name,Already_Available_Qty,Required_Quantity,Required_On,empname,tv_displaydate,Approved_Quantity;
    EditText Shipment_Charges,Remarks;
    Spinner Select_Status;
    String[] selectstatus;
    String status,emp_id,indent_id,inv_id,shp_date,shp_remarks,AvailableQty;
    RelativeLayout selectdate;
    ImageView dateimage;
    private OperatorCode operatorCode;
    String mainURL,uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_inventory);
        //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        stockistInventoryData = (StockistInventoryData) getIntent().getExtras().getSerializable("stockinventoryData");
        OperatorLoginData operatorCode=null;
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        empname=(TextView)findViewById(R.id.empname);
        String getempname=stockistInventoryData.getemp_first_name()+" "+stockistInventoryData.getemp_last_name();
        empname.setText(getempname+" ( "+stockistInventoryData.getind_invoice_id()+" )");
        emp_id=operatorCode.getRole_id();
        indent_id=stockistInventoryData.getindent_id();
        inv_id=stockistInventoryData.getinv_id();
        selectdate=(RelativeLayout)findViewById(R.id.selectdate);
        Inventory_Name=(TextView)findViewById(R.id.Inventory_Name);
        Approved_Quantity=(TextView)findViewById(R.id.Approved_Quantity);
        Already_Available_Qty=(TextView)findViewById(R.id.Already_Available_Qty);
        Required_Quantity=(TextView)findViewById(R.id.Required_Quantity);
        Required_On=(TextView)findViewById(R.id.Required_On);
        dateimage=(ImageView)findViewById(R.id.dateimage);
        tv_displaydate=(TextView) findViewById(R.id.tv_displaydate);
        Shipment_Charges=(EditText)findViewById(R.id.Shipment_Charges);
        Remarks=(EditText)findViewById(R.id.Remarks);

        Select_Status=(Spinner)findViewById(R.id.Select_Status);
        selectstatus=getResources().getStringArray(R.array.Stockarray);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_complaint,selectstatus);
        Select_Status.setAdapter(adapter);
        Select_Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String getpositionname=selectstatus[position];
                if(getpositionname.equalsIgnoreCase("Shipped")){
                    status="3";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stockistInventoryedit();
     /*   Intent dataIntent = new Intent(EditStockInventoryActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(EHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.stockist_inventory_edit.ordinal());
        dataIntent.putExtra("empID", emp_id);
        dataIntent.putExtra("indentid", indent_id);
        startService(dataIntent);*/
    }

    private void stockistInventoryedit() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Please Wait..");
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"stockist_inventory_edit.php?emp_id="+objectempcode+"&indent_id="+indent_id;
        ;
        Log.e("Updatestock",uri);
        Log.d("updatestock1",uri);
        System.out.println(uri);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        if(!jsonObject.isNull("AvailableQty")) {
                            AvailableQty = jsonObject.getString("AvailableQty");
                        }else {
                            AvailableQty="0";
                        }
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                UpdateAdmininventoryData admIninventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<UpdateAdmininventoryData>() {}.getType());
                                setData(admIninventoryData);
                            }
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(EditStockInventoryActivity.this, ""+message, Toast.LENGTH_SHORT).show();
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
    });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openDateCalendar(View view) {
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        dateimage.setVisibility(View.GONE);
                        tv_displaydate.setVisibility(View.VISIBLE);
                        tv_displaydate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
   /* private Handler EHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    if(!responseObj.isNull("AvailableQty")) {
                        AvailableQty = responseObj.getString("AvailableQty");
                    }else {
                        AvailableQty="0";
                    }
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            UpdateAdmininventoryData admIninventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<UpdateAdmininventoryData>() {}.getType());
                            setData(admIninventoryData);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };*/

    private void setData(UpdateAdmininventoryData admIninventoryData) {
        Inventory_Name.setText(admIninventoryData.getname());
        Already_Available_Qty.setText(AvailableQty);
        Required_Quantity.setText(admIninventoryData.getrequired_qty());
        Required_On.setText(admIninventoryData.getrequired_date());
        Approved_Quantity.setText(admIninventoryData.getapproved_qty());

    }
    public void update(View view){
        shp_remarks=Remarks.getText().toString().replace(" ","%20");
        shp_date=tv_displaydate.getText().toString();
        UpdateStockINventorylist();
       /* Intent dataIntent = new Intent(EditStockInventoryActivity.this, DataLoader.class);
        Messenger dataMessenger = new Messenger(rHandler);
        dataIntent.putExtra("MESSENGER", dataMessenger);
        dataIntent.putExtra("type", DataLoader.DataType.stockist_inv_update.ordinal());
        dataIntent.putExtra("empID", emp_id);
        dataIntent.putExtra("indentid", indent_id);
        dataIntent.putExtra("inv_id", inv_id);
        dataIntent.putExtra("status", status);
        dataIntent.putExtra("inward_remarks", shp_remarks);
        dataIntent.putExtra("shp_date",shp_date);
        startService(dataIntent);*/
    }

    private void UpdateStockINventorylist() {
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Please Wait..");
        uri=mainURL+"stockist_inv_update.php?emp_id="+emp_id+"&indent_id="+indent_id+"&inv_id="+inv_id+"&status="+status+"&shp_remarks="+shp_remarks+"&shp_date="+shp_date;
    Log.d("Updatestock",uri);
    Log.e("Updatestock",uri);
    System.out.println(uri);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            dialog.dismiss();
            String message = jsonObject.getString("message");
            if (message.equalsIgnoreCase("success")) {
                Dialog dialog=new Dialog(EditStockInventoryActivity.this);
                dialog.setContentView(R.layout.dialogcomplaint);
                dialog.setCancelable(false);
                TextView textView=(TextView)dialog.findViewById(R.id.successfully);
                textView.setText("Successfully Update");
                Button button=(Button) dialog.findViewById(R.id.Redirecttodashboard);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(EditStockInventoryActivity.this,StockistHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
            else {
                dialog.dismiss();
                Toast.makeText(EditStockInventoryActivity.this, ""+message, Toast.LENGTH_SHORT).show();
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
    });
RequestQueue requestQueue=Volley.newRequestQueue(this);
requestQueue.add(stringRequest);
    }

   /* private Handler rHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
            try{
                JSONObject responseObj = new JSONObject(response);
                String message = responseObj.getString("message");
                if (message.equalsIgnoreCase("success")) {
                    Dialog dialog=new Dialog(EditStockInventoryActivity.this);
                    dialog.setContentView(R.layout.dialogcomplaint);
                    dialog.setCancelable(false);
                    TextView textView=(TextView)dialog.findViewById(R.id.successfully);
                    textView.setText("Successfully Update");
                    Button button=(Button) dialog.findViewById(R.id.Redirecttodashboard);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(EditStockInventoryActivity.this,StockistHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialog.show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };*/


}
