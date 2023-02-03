package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.dataModel.InventoryData;

import java.util.ArrayList;
import java.util.Date;

public class InventoryAssignedAdapter extends BaseAdapter {
    static Context context;
    ArrayList<InventoryData> list;
    private Date dt;
    String date4;
    public  InventoryAssignedAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_list_view2, parent, false);
        LinearLayout l1=view.findViewById(R.id.aasignedlinear);
        LinearLayout l2=view.findViewById(R.id.assignedlinear1);
//        TextView startpoint=(TextView)view.findViewById(R.id.tv_startpoint);
//        TextView endpoint=(TextView)view.findViewById(R.id.tv_endpoint);
        TextView empName=(TextView)view.findViewById(R.id.tv_mname1);
        TextView tv_mitennumber=view.findViewById(R.id.tv_mitennumber1);
//        TextView tv_mdealerminquantity=(TextView)view.findViewById(R.id.tv_mdealerminquantity);
        TextView tv_assinedqty=(TextView)view.findViewById(R.id.tv_assinedqty);
        TextView tv_assignedon=(TextView)view.findViewById(R.id.tv_assignedon);
        TextView tv_remarks=(TextView)view.findViewById(R.id.tv_remarksi);
//        TextView tv_mdateAssined=view.findViewById(R.id.tv_mdateassined);
        String getName=list.get(position).getInvName();
        String tv_mitennumber12=list.get(position).getInvCode();
        String getassignedqty=list.get(position).getAssignedqty();
//        String starto=list.get(position).getStartpoint();
//        startpoint.setText(starto);
//        String endpo=list.get(position).getEndpoint();
//        endpoint.setText(endpo);
        String getremarks=list.get(position).getRemarks();
        tv_remarks.setText(getremarks);
        tv_assinedqty.setText(getassignedqty);
        String datacreated=list.get(position).getAssignedon();
//        if(list.equals(isEmpty()))
//        {
//            l1.setVisibility(View.GONE);
//        }
//        else {
//            l1.setVisibility(View.VISIBLE);
//        }
//        if(endpo.equals("0")){
//            l2.setVisibility(View.GONE);
//        }
//else {
//    l2.setVisibility(View.VISIBLE);
//        }
//        String date1[]=list.get(position).getInvDateCreated().split(" ");
//        String time1[]=list.get(position).getInvDateCreated().split(" ");
//        String cdate1=date1[0];
//        String ctime1=time1[1];
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            dt = format.parse(cdate1);
//            // dt2 = format.parse(cdate);
//            SimpleDateFormat your_format1 = new SimpleDateFormat("dd-MM-yyyy ");
//            date4  = your_format1.format(dt);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    tv_assignedon.setText(date4+ " " +ctime1);
        empName.setText(getName);
        tv_mitennumber.setText(tv_mitennumber12);
        tv_assignedon.setText(datacreated);
        return view;
    }
}
