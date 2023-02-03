package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.dataModel.ComplaintData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sridhar on 2/10/2017.
 */

public class ComplaintAdapter extends BaseAdapter {
    static Context context;
    ArrayList<ComplaintData> list;
    private Date dt;
    private String date4;

    public ComplaintAdapter(Context playlistFragment, ArrayList<ComplaintData> vList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        view=LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_complaint_list,parent,false);
  /*      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_complaint_list, parent, false);*/
        TextView empName=(TextView)view.findViewById(R.id.custName);
        TextView custPhoneadderss=(TextView)view.findViewById(R.id.custPhoneadderss);
        TextView custstate=view.findViewById(R.id.custstate);
        TextView custcity=view.findViewById(R.id.custcity);
        TextView comlntstatus=view.findViewById(R.id.comlntstatus);
        TextView datecreated=view.findViewById(R.id.datecreated);
        ImageView iv_rightimage=view.findViewById(R.id.iv_rightimage);
        String getName=list.get(position).getFirstName();
       // String lastname=list.get(position).getlast_name();
        String custom_customer_no=list.get(position).getCompNo();
        String mobileno=list.get(position).getMobileNo();

        //String city=list.get(position).getcity();

        empName.setText(getName+" "+"("+custom_customer_no+")");

        custPhoneadderss.setText(mobileno);
        comlntstatus.setText(list.get(position).getCompCategory());
        custstate.setText(list.get(position).getCompOwner());
        custcity.setText(list.get(position).getCompStatus());
        if (list.get(position).getCompStatus().equals("Closed") || list.get(position).getCompStatus().equals("Resolved"))
        {
            iv_rightimage.setVisibility(View.GONE);
        }
      else {
            iv_rightimage.setVisibility(View.VISIBLE);
        }

        //datecreated.setText(list.get(position).getcreated_date());
        String date1[]=list.get(position).getCompDateCreated().split(",");
        String time1[]=list.get(position).getCompDateCreated().split(",");
        String cdate1=date1[0];
        String ctime1=time1[1];

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt = format.parse(cdate1);
            // dt2 = format.parse(cdate);
            SimpleDateFormat your_format1 = new SimpleDateFormat("dd-MM-yyyy ");
            date4  = your_format1.format(dt);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        datecreated.setText(cdate1+""+ctime1);
       // datecreated.setText(cdate1+""+date4+""+ctime1);


      /*  String complntstatus=list.get(position).getcomp_status();
        if (complntstatus.equals("1"))
        {
            comlntstatus.setText("Processing");
        }
        else if (complntstatus.equals("0")){
            comlntstatus.setText("Pending");
        }
        else if (complntstatus.equals("2")){
            comlntstatus.setText("Closed");
        }*/

        return view;
    }
}