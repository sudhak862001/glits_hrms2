package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.dataModel.InventoryData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IndentlistAdapter extends BaseAdapter {
    static Context context;
    ArrayList<InventoryData> list;
    private Date dt;
    String date4;
    public IndentlistAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
        list = vList;
        context = playlistFragment;
    }

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) ;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_return_list1, parent, false);
        TextView empName=(TextView)view.findViewById(R.id.tv_mname4);
        String getName=list.get(position).getInvName();
        empName.setText(getName);
        TextView tv_mitennumber=view.findViewById(R.id.tv_returnquality4);
        String returnqty=list.get(position).getReturnqty();
       tv_mitennumber.setText(returnqty);
        TextView tv_remarks=view.findViewById(R.id.tv_remarks4);

        TextView datecreated=view.findViewById(R.id.tv_datecreated4);
        String datecreated1=list.get(position).getInvDateCreated();
        String tv_remarks1=list.get(position).getRemarks();
         tv_remarks.setText(tv_remarks1);
        String date1[]=list.get(position).getInvDateCreated().split(" ");
        String time1[]=list.get(position).getInvDateCreated().split(" ");
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
       datecreated.setText(date4+ " " +ctime1);
        return view;
    }
}
