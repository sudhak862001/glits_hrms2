package com.Tejaysdr.msrc.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;
import com.Tejaysdr.msrc.dataModel.LeaveHistoryModel;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LeaveListAdapter extends RecyclerView.Adapter<LeaveListAdapter.MyViewHolder>{
    Context context;
    ArrayList<LeaveHistoryModel> followuplist = new ArrayList<>();
    ArrayList<LeaveHistoryModel> followuplistfilter;
    private AlertDialog alertDialogAndroid;
    private Dialog dialog4;
    String Page;
    String status="Cancel";
    String Fromdate="",Todate="";
    ArrayList<OperatorLoginData>operatorLoginDatalist=new ArrayList<>();
//    OperatorLoginData operatorLoginData=new OperatorLoginData();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hempname,tv_empname,tv_ndays,tv_fromdate,tv_todate,tv_pstatus,tv_approvedby,tv_aremarks,tv_lremarks,edit;
        LinearLayout ll_edit;
        public MyViewHolder(View view) {
            super(view);
            tv_hempname = (TextView) view.findViewById(R.id.tv_hempname);
            tv_empname = (TextView) view.findViewById(R.id.tv_empname);
            tv_ndays = (TextView) view.findViewById(R.id.tv_ndays);
            tv_fromdate = (TextView) view.findViewById(R.id.tv_fromdate);
            tv_aremarks = (TextView) view.findViewById(R.id.tv_remarks);
            tv_todate = (TextView) view.findViewById(R.id.tv_todate);
            tv_pstatus = (TextView) view.findViewById(R.id.tv_pstatus);
            tv_approvedby = (TextView) view.findViewById(R.id.tv_approvedby);
            tv_lremarks = (TextView) view.findViewById(R.id.tv_lremarks);
            edit = (TextView) view.findViewById(R.id.edit);
            ll_edit = (LinearLayout) view.findViewById(R.id.ll_edit);


        }
    }
    public LeaveListAdapter(Context playlistFragment, ArrayList<LeaveHistoryModel> vList, String training) {
        followuplist = vList;
        followuplistfilter= new ArrayList<>();
        this.followuplistfilter.addAll(followuplist);
        this.Page=training;
        context = playlistFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_list_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final LeaveHistoryModel tag = followuplist.get(position);
        OperatorLoginData operatorCode = SaveAppData.getSessionDataInstance().getOperatorLoginData();
     String type=operatorCode.getRole_id();
     if(type.equals("1")){
//      Toast.makeText(context,""+type,Toast.LENGTH_SHORT).show();
         holder.edit.setVisibility(View.VISIBLE);
     }
     else {
         holder.edit.setVisibility(View.GONE);
     }

//       int type=1;
//        if(type==1){
//            holder.ll_edit.setVisibility(View.VISIBLE);
//        }
//        else{
//            holder.ll_edit.setVisibility(View.GONE);
//        }
        if (Page.equals("Pending")) {
            if(tag.getLeaveStatus().equals("Pending for Approval")){
                holder.ll_edit.setVisibility(View.VISIBLE);
            }else{
                holder.ll_edit.setVisibility(View.GONE);
            }
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View alertLayout = li.inflate(R.layout.leave_status, null);
                    final LeaveHistoryModel tag = followuplist.get(position);
                    final TextView tv_approve=(TextView)alertLayout.findViewById(R.id.tv_approve);
                    final TextView tv_reject=(TextView)alertLayout.findViewById(R.id.tv_reject);
                    final EditText remarks = (EditText) alertLayout.findViewById(R.id.et_Remarks);
                    final Spinner sp_leave_status = (Spinner) alertLayout.findViewById(R.id.sp_leave_status);
                    tv_approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            status="Approve";
                            tv_approve.setText("Approved");
                            tv_reject.setVisibility(View.GONE);
                        }
                    });
                    tv_reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            status="Cancel";
                            tv_reject.setText("Rejected");
                            tv_approve.setVisibility(View.GONE);
                        }
                    });
                    /*sp_leave_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                             status=sp_leave_status.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });*/
                    ImageView iv_cancel = (ImageView) alertLayout.findViewById(R.id.iv_cancel);
                    iv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog4.cancel();
                        }
                    });
                    Button btn_update = (Button) alertLayout.findViewById(R.id.btn_update);
                    btn_update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String Remarks=remarks.getText().toString();
                            if(status.equals("")){
                                Toast.makeText(context,"Please Select Status", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(Remarks.isEmpty()) {
                                remarks.setError("Please Enter Remarks");
                                return;
                            }else {
                                Leavestatus(position,tag.getL_req_id(),remarks.getText().toString(),status);
                            }
                        }
                    });

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                    alert.setView(alertLayout);
                    dialog4 = alert.create();
                    dialog4.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Window window = dialog4.getWindow();
                    window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);

                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.dimAmount = 0.7f;
                    lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    dialog4.getWindow().setAttributes(lp);
                    dialog4.show();
                }
            });
        } else {
            holder.ll_edit.setVisibility(View.GONE);
        }
        if(tag.getType().equals("Trainer")){
            holder.tv_hempname.setText("Trainer");
            holder.tv_empname.setText(tag.getTrainer_name());
        }else{
            holder.tv_hempname.setText("Employee Name");
            holder.tv_empname.setText(tag.getEmp_first_name());
        }
        holder.tv_fromdate.setText(tag.getLeave_from_date());
        holder.tv_todate.setText(tag.getLeave_to_date());
        holder.tv_ndays.setText(tag.getNo_of_days());
        holder.tv_pstatus.setText(tag.getLeaveStatus());
        holder.tv_approvedby.setText(tag.getApproved_by());
        holder.tv_aremarks.setText(tag.getApproved_remarks());
        holder.tv_lremarks.setText("Leave Reason : "+tag.getLeave_reason());
    }

    @Override
    public int getItemCount() {
        return followuplist.size();
    }

    private void Leavestatus(final int position, final String l_req_id, final String remark, final String status) {
//        final android.app.ProgressDialog progressdialog= BaseUrlClass.createProgressDialog(context);
//        progressdialog.show();
//        progressdialog.setCancelable(false);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait..");
        dialog.show();

        final String URL =BaseUrl.getBaseurl()+"employee/update_leave_request";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String obj) {
                        System.out.println(obj);
                        JSONObject response = null;
                        try {
                            response = new JSONObject(obj);
                            try {
                                String message = response.getString("message");
                                String text = response.getString("text");
                                String token_expiry = response.getString("token_expiry");
                                if(token_expiry.equals("Yes")){
                                    Logout();
                                }
                                if (message.equalsIgnoreCase("Success")) {
                                    followuplist.get(position).setLeaveStatus(status);
                                    followuplist.get(position).setApproved_remarks(remark);
                                    notifyDataSetChanged();
                                    Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(context,"Success", Toast.LENGTH_LONG).show();
                                    dialog4.cancel();
                                }else{
//                                    Toast.makeText(context,text, Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }

                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                       dialog.dismiss();
//                            createDialoges(error.toString());
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                java.util.Map<String, String> params = new java.util.HashMap<String, String>();
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id() );
                params.put("token",SaveAppData.getOperatorLoginData().getToken());
                params.put("l_req_id",l_req_id);
                params.put("status",status);
//               params.put( "leave_from_date",Fromdate);
//               params.put("leave_to_date",Todate);
                params.put("remarks",remark);
                return params;

            }
        };
        new com.android.volley.DefaultRetryPolicy(
                com.android.volley.DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                0,
                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        com.android.volley.RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



    // Filter Class

    private void Logout() {
        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String formattedDate = df.format(c);
            final String url = BaseUrl.getBaseurl() + "emp_logout.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            JSONObject jsonObject = null;
                            try {
                                SaveAppData.saveOperatorLoginData(null);
                                SaveAppData.setusename("");
//                                DashboardActivity.getInstance().removeLocationUpdates();
                                Intent i = new Intent(context, EMPHomeActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);

                            } catch (Exception e) {
                                e.printStackTrace();
                                //progressdialog.dismiss();
                                // Constants.createDialoges(ConformBookingActivity.this, getResources().getString(R.string.get_bonus));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("emp_id", SaveAppData.getOperatorLoginData().getEmp_id());
                    /*if(longit!=null&latitude!=null){
                        params.put("gps_lat", latitude);
                        params.put("gps_lang", longit);
                    }else{
                        params.put("gps_lat", "0.0");
                        params.put("gps_lang", "0.0");
                    }*/
                    params.put("gps_lat", "0.0");
                    params.put("gps_lang", "0.0");
                    params.put("dateCreated", formattedDate);
                    params.put("Address", "");
                    params.put("token", SaveAppData.getusename());
                    return params;

                }
            };
            new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
