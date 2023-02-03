package com.Tejaysdr.msrc.activitys.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.Tejaysdr.msrc.activitys.BaseActivity;
import com.Tejaysdr.msrc.activitys.SearchOperatorCode;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
import com.Tejaysdr.msrc.activitys.emp.UpdateComplaintActivity;
import com.Tejaysdr.msrc.activitys.stockist.StockistHomeActivity;
import com.Tejaysdr.msrc.adapter.AdminListAdapter;
import com.Tejaysdr.msrc.backendServices.DataLoader;
import com.Tejaysdr.msrc.backendServices.GPSTracker;
import com.Tejaysdr.msrc.backendServices.SendGPSServicesLocation;
import com.Tejaysdr.msrc.dataModel.AdmIninventoryData;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.Tejaysdr.msrc.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class AdminHomeActivity extends BaseActivity {
    TextView empname;
    ListView admin_inventory_list;
    ArrayList<AdmIninventoryData>  admininventoryDataList;
    AdminListAdapter adapter;
    private GPSTracker gps;
    public static Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();
    String getempID,mainURL,uri;
    private OperatorCode operatorCode;
    double latitude = 0.0, longitude = 0.0;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_home);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //setTitle(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("AppTitle", "MSRC"));
        //SetTitle("APSFL");
        OperatorLoginData operatorCode=null;
            operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
            getempID = operatorCode.getRole_id();
            gps = new GPSTracker(AdminHomeActivity.this);

            if(!gps.canGetLocation()){
                gps.showSettingsAlert();
            }
            startTimer();
            empname=(TextView)findViewById(R.id.empname);
            admin_inventory_list=(ListView)findViewById(R.id.admin_inventory_list);
            String getempID=operatorCode.getRole_id();
            //String getempname=operatorCode.getemp_first_name()+" "+operatorCode.getemp_last_name();
            //empname.setText("Hello "+getempname);
        empname.setText("Hello "+"APSFL");
            Adminlist();
          /*  Intent dataIntent = new Intent(AdminHomeActivity.this, DataLoader.class);
            Messenger dataMessenger = new Messenger(EHandler);
            dataIntent.putExtra("MESSENGER", dataMessenger);
            dataIntent.putExtra("type", DataLoader.DataType.admin_inventory.ordinal());
            dataIntent.putExtra("jsonObject", getempID);
            startService(dataIntent);*/

            admin_inventory_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AdmIninventoryData admIninventoryData=admininventoryDataList.get(position);
                    Intent updateintent=new Intent(AdminHomeActivity.this,EditAdminInventoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AdmIninventoryData", admIninventoryData);
                    updateintent.putExtras(bundle);
                    startActivityForResult(updateintent,200);
                }
            });
    }

    private void Adminlist() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"admin_inventory.php?emp_id="+objectempcode;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        admininventoryDataList=new ArrayList<>();
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                                AdmIninventoryData admIninventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(),new TypeToken<AdmIninventoryData>() {}.getType());
                                admininventoryDataList.add(admIninventoryData);
                            }
                            adapter=new AdminListAdapter(AdminHomeActivity.this,admininventoryDataList);
                            admin_inventory_list.setAdapter(adapter);
                        }
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(gps, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
    });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void startTimer() {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run() {
                        //TODO

                        if (gps.canGetLocation()) {
                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            AdminHomeLocationTracking();
                            Log.e("GetLocation", "latitude :- " + latitude + "\n longitude :- " + longitude);
                            Intent dataIntent = new Intent(AdminHomeActivity.this, DataLoader.class);
                            Messenger dataMessenger = new Messenger(hEMPGPSLOC);
                            dataIntent.putExtra("MESSENGER", dataMessenger);
                            dataIntent.putExtra("type", DataLoader.DataType.LocationTracking.ordinal());
                            dataIntent.putExtra("gps_lang", "" + longitude);
                            dataIntent.putExtra("gps_lat", "" + latitude);
                            dataIntent.putExtra("emp_id", getempID);
                            startService(dataIntent);
                        } else {
                            gps.showSettingsAlert();
                        }
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 1, 30000);
    }

    private void AdminHomeLocationTracking() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Please Wait..");
        operatorCode = SaveAppData.getSessionDataInstance().getOperatorData();
        String objectempcode=SaveAppData.getOperatorLoginData().getRole_id();
        mainURL = operatorCode.getop_url();
        uri=mainURL+"emp_gps_track.php?emp_id="+getempID+"&gps_lat="+latitude+"&gps_lang="+longitude+"";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


        }
    });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

   private Handler hEMPGPSLOC=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AdminHomeLocationTracking();
           Bundle   bundle = msg.getData();
            String response = bundle.getString("data");
        }
    };
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
                    admininventoryDataList=new ArrayList<>();
                    Iterator<String> keys = responseObj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equalsIgnoreCase("message") && !key.equalsIgnoreCase("text")) {
                            AdmIninventoryData admIninventoryData = new Gson().fromJson(responseObj.getJSONObject(key).toString(),new TypeToken<AdmIninventoryData>() {}.getType());
                            admininventoryDataList.add(admIninventoryData);
                        }
                        adapter=new AdminListAdapter(AdminHomeActivity.this,admininventoryDataList);
                        admin_inventory_list.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void redirectToLoginActivity(View view) {
        AlertDialog logoutDialog = new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }
    private void logout() {
        if(AdminHomeActivity.mTimer1!=null) {
            AdminHomeActivity.mTimer1.cancel();
        }
        if(StockistHomeActivity.mTimer1!=null)
        {
            StockistHomeActivity.mTimer1.cancel();
        }
        if(SendGPSServicesLocation.mTimer1!=null)
        {
            SendGPSServicesLocation.mTimer1.cancel();
        }
        if(EMPHomeActivity.mTimer1!=null)
        {
            EMPHomeActivity.mTimer1.cancel();
        }
        if(UpdateComplaintActivity.mTimer1!=null)
        {
            UpdateComplaintActivity.mTimer1.cancel();
        }
        SaveAppData.saveOperatorData(null);
        SaveAppData.saveOperatorLoginData(null);
        Intent login = new Intent(this, SearchOperatorCode.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        finish();
    }
    public void redirectToHomeActivity(View view) {
        AlertDialog homeDialog = new AlertDialog.Builder(this).setTitle("Home")
                .setMessage("Are you sure want to navigate to home screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        home();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).show();
    }
    private void home() {
        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
        String type = operatorCode.getRole_id();
        if (type.equalsIgnoreCase("28")) {
            Intent intent = new Intent(this, EMPHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("1")) {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (type.equalsIgnoreCase("2")) {
            Intent intent = new Intent(this, StockistHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }
}
