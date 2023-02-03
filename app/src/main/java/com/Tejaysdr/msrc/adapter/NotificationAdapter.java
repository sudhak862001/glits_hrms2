package com.Tejaysdr.msrc.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.emp.AppliedleaveActivity;
import com.Tejaysdr.msrc.activitys.emp.AssignedMaterialsActivity;
import com.Tejaysdr.msrc.activitys.emp.ComplaintListCatActivity;
import com.Tejaysdr.msrc.activitys.emp.CompletedExpensesActivity;
import com.Tejaysdr.msrc.activitys.emp.ExpensesActivity;
import com.Tejaysdr.msrc.activitys.emp.ExpenseslListActivity;
import com.Tejaysdr.msrc.activitys.emp.IndentlistActivity;
import com.Tejaysdr.msrc.dataModel.InventoryData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends BaseAdapter {
    static Context context;
    ArrayList<InventoryData> list;
    private Date dt;
    String date4;

    public NotificationAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
        list = vList;
        context = playlistFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final InventoryData tag = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_list_notifi, parent, false);
        LinearLayout linearLayout=view.findViewById(R.id.linear_custom);
        CardView cardView=view.findViewById(R.id.noti_curd);
        TextView t1 = view.findViewById(R.id.tv_cat);
        TextView t2 = view.findViewById(R.id.tv_msg);
        TextView t3 = view.findViewById(R.id.tv_dt);
        String t11 = list.get(position).getCategory();
        t1.setText(t11);
        String t12 = list.get(position).getMessage1();
        t2.setText(t12);
        String t13 = list.get(position).getDateCretaed();

      //  noti_id=tag.getId();
        String noti_status=tag.getStatus();
        if (noti_status.equals("0")){
            cardView.setBackgroundResource(R.color.Phoneadders_color);
        }else if (noti_status.equals("1")){
            cardView.setBackgroundResource(R.color.white);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noti_id=tag.getId();
               if (t11.equalsIgnoreCase("Inventory Qty Assigned")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, AssignedMaterialsActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Inventory Assigned from WH")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, AssignedMaterialsActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("New Complaint Assigned")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, ComplaintListCatActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Indent Raising Inventory Pendi")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, IndentlistActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Indent Raising Inventory Rejected")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, IndentlistActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Indent Raising Inventory Approved")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, IndentlistActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Complaint Closed")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, ComplaintListCatActivity.class);
                    intent.putExtra("FRAGMENT","completed");
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Manager Expense Bill Status")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, ExpenseslListActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Finance Expense Bill Status")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, CompletedExpensesActivity.class);
                    context.startActivity(intent);
                }else if (t11.equalsIgnoreCase("Your Leave request Status")) {
                    apihitmethod(noti_id);
                    Intent intent = new Intent(context, AppliedleaveActivity.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, ExpensesActivity.class);
                    context.startActivity(intent);
                }

            }
        });

        String date1[]=list.get(position).getDateCretaed().split(" ");
        String time1[]=list.get(position).getDateCretaed().split(" ");
        String cdate1=date1[0];
        String ctime1=time1[1];

        SimpleDateFormat format = new SimpleDateFormat("MM-DD-yyyy");
        try {
            dt = format.parse(cdate1);
            // dt2 = format.parse(cdate);
            SimpleDateFormat your_format1 = new SimpleDateFormat("DD-MM-yyyy");
            date4  = your_format1.format(dt);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        t3.setText(t13);
    
        return view;

    }

    private void apihitmethod(String noti_id) {
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.show();
        //String url="https://crm.tejays.in/webservices/employee/emp_notification_upd";
        String url= BaseUrl.getBaseurl()+"employee/emp_notification_upd";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
//                    Toast.makeText(context, ""+jsonObject, Toast.LENGTH_SHORT).show();
                  //  String count=jsonObject.getString("noti_status");
                    //  Toast.makeText(EMPHomeActivity.this, "hello"+count, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                //params.put("base64",ba1);
                params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("notif_id",noti_id);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}
