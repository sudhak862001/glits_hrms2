package com.Tejaysdr.msrc.activitys.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Tejaysdr.msrc.activitys.emp.UpdateComplaintActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.BaseUrl;
import com.Tejaysdr.msrc.activitys.emp.InventoryListActivity;
import com.Tejaysdr.msrc.dataModel.InventoryData;
import com.Tejaysdr.msrc.saveAppData.SaveAppData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewIndentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewIndentsFragment extends Fragment {
    Context context;

    TextView Invname, AvailableQuality;
        EditText ReturnQuality, Remarks,return_inv;
    InventoryData inventoryData;
    Spinner serial_spin;
    private String remarks1, retrunqty;
    String a1,a3;
    Button btn;
    Toast toast;

    ArrayList<String> inv_list = new ArrayList<>();
    ArrayList<InventoryData> inventoryDataList1 = new ArrayList<>();
    ArrayList<InventoryData> inventoryDataList2 = new ArrayList<>();
    String selected_inv = "",spin_selected;

    public static NewIndentsFragment newInstance(String param1, String param2) {
        NewIndentsFragment fragment = new NewIndentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_indents, container, false);
        context=getActivity();
        btn=view.findViewById(R.id.submit1);
        serial_spin=view.findViewById(R.id.serial_spin);
        a1=getActivity().getIntent().getStringExtra("inv_id");
        Invname = view.findViewById(R.id.Quality1);
        String a2=getActivity().getIntent().getStringExtra("avlqty");
        AvailableQuality = view.findViewById(R.id.Quality2);
        AvailableQuality.setText(a2);
        a3=getActivity().getIntent().getStringExtra("emp_inward_id");
       String a4=getActivity().getIntent().getStringExtra("inv_name");
        Invname.setText(a4);

        Remarks = view.findViewById(R.id.remarks5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remarks1 = Remarks.getText().toString();
                spin_selected = serial_spin.getSelectedItem().toString();
                inv_list.add(selected_inv);

                if(spin_selected.equalsIgnoreCase("select")){

                    Toast.makeText(context, "select inventory", Toast.LENGTH_SHORT).show();

                } else if(remarks1.isEmpty()){
                  Remarks.setError("Please Enter remarks");
                 } else {

                    newreturn();
                }
            }
        });

        serialnumapiintegration();

        return view;

    }

    private void newreturn() {
         ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait..");
        dialog.show();
        String uri = BaseUrl.getBaseurl()+"employee/update_inv_return_stock";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("msg");
                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(context, InventoryListActivity.class);
                 startActivity(intent);
                 getActivity().finish();
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
//                error.printStackTrace();
                toast.makeText(context, "error" + error, Toast.LENGTH_SHORT).show();
//                Toast toast=   Toast.makeText(getActivity() ,Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor(Color.GREEN); //any color your want
                toast.show();


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("inv_id",a1);
                params.put("emp_inward_id",a3);
                params.put("inv_qty","1");
                params.put("remarks",remarks1);
                params.put("assign_inv_sl_no_ids",inv_list.toString());
                Log.d("PARAMS",params.toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void serialnumapiintegration() {


        StringBuilder stringBuilder = new StringBuilder();


       String uri = BaseUrl.getBaseurl() + "employee/inventory_wise_serial_numbers";
        //   uri = "https://tejays.digitalrupay.com/webservices/employee/inventory_wise_serial_numbers";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String response12 = jsonObject.getString("response");

                    if (response12.equalsIgnoreCase("Success")) {
                        inventoryDataList1.clear();

                        InventoryData inventoryData1=new InventoryData();
                        inventoryData1.setAssign_inv_sl_no("Select");
                        inventoryDataList1.add(inventoryData1);

                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (!key.equalsIgnoreCase("msg") && !key.equalsIgnoreCase("response")) {
                                InventoryData inventoryData = new Gson().fromJson(jsonObject.getJSONObject(key).toString(), new TypeToken<InventoryData>() {
                                }.getType());
                                inventoryDataList1.add(inventoryData);
                                //      invgroup = inventoryData.getInvGroup();
                            }

                            String[] inventorydata= new String[inventoryDataList1.size()];
                            for(int i=0;i<inventorydata.length;i++){
                                inventorydata[i]=inventoryDataList1.get(i).getAssign_inv_sl_no();
                            }

                            ArrayAdapter arrayAdapter=new ArrayAdapter(context,R.layout.spinner_text,inventorydata);
                            arrayAdapter.setDropDownViewResource(R.layout.spinner_complaint);
                            serial_spin.setAdapter(arrayAdapter);


                            serial_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    /*InventoryData inv_data = inventoryDataList1.get(position);
                                    stringBuilder.append(inv_data.getAssign_inv_sl_no());
                                    Toast.makeText(getActivity(), "INV NAME "+inventoryDataList1.get(position).getAssign_inv_sl_no(), Toast.LENGTH_SHORT).show();*/
                                    // use for loop
                                    try {

                                        selected_inv=inventoryDataList1.get(position).getAssign_inv_sl_no_id();

                                      //  inv_list.add(selected_inv);

                                       /* if(inv_list.contains(selected_inv)){
                                            Toast.makeText(context,"Inv Already Selected", Toast.LENGTH_SHORT).show();
                                            inv_list.remove(position);

                                        }else {


                                            inv_list.add(inventoryDataList1.get(position).getAssign_inv_sl_no());

                                            for(int i=0;i<inventoryDataList1.size();i++){
                                                if(i==0){
                                                    selected_inv=inventoryDataList1.get(position).getAssign_inv_sl_no();

                                                }else {
                                                    selected_inv=selected_inv+" , "+inventoryDataList1.get(position).getAssign_inv_sl_no();

                                                }
                                            }
                                            Log.d("xxxx",inv_list.toString());

                                            HashSet<String> hset = new HashSet<String>(inv_list);
                                        //    Log.d("xxxxHSET"+id+inv_list.get(position),hset.toString());
                                          //  Log.d("xxxxINV",selected_inv);
                                            return_inv.setText(inv_list.toString());


                                        }*/
                                    } catch (NumberFormatException e) {
                                        Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });



                        }
                    } else {
//                        dialog.dismiss();
                        // Toast.makeText(UpdateComplaintActivity.this, ""+response12, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();

                params.put("emp_id",SaveAppData.getOperatorLoginData().getEmp_id());
                params.put("inv_id",a1);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    
}






