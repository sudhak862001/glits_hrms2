package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.emp.Returnlist;
import com.Tejaysdr.msrc.dataModel.InventoryData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sridhar on 2/9/2017.
 */

public class InventoryListAdapter extends BaseAdapter {

    static Context context;
    ArrayList<InventoryData> list;
    private Date dt;
    String date4;

    public InventoryListAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
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
        view = inflater.inflate(R.layout.adapter_inventory_list, parent, false);
        TextView tv_invcode=(TextView) view.findViewById(R.id.tv_invcode);
        String tv_invcode1=list.get(position).getInvCode();
        tv_invcode.setText(tv_invcode1);
        TextView empName=(TextView)view.findViewById(R.id.tv_mname3);
        TextView tv_invward=(TextView)view.findViewById(R.id.tv_inwardid);
        String tv_inward1=list.get(position).getEmpinwardid();
        tv_invward.setText(tv_inward1);
        TextView tv_mitennumber=view.findViewById(R.id.tv_mitennumber3);
        TextView tv_invid=(TextView) view.findViewById(R.id.tv_invid1);
       String tv_invid1=list.get(position).getInvId();
         tv_invid.setText(tv_invid1);
//        LinearLayout l3=view.findViewById(R.id.inventorylinear);
        Button btn=view.findViewById(R.id.btnreturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Returnlist.class);
                intent.putExtra("inv_id",list.get(position).getInvId());
                intent.putExtra("emp_inward_id",list.get(position).getEmpinwardid());
                intent.putExtra("inv_name",list.get(position).getInvName());
                intent.putExtra("avlqty",list.get(position).avlqty);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        TextView tv_mdealerminquantity=(TextView)view.findViewById(R.id.tv_mdealerminquantity);
//        TextView tv_usedqty=(TextView)view.findViewById(R.id.tv_usedqty);
        TextView tv_mdatecreated=view.findViewById(R.id.tv_mdatecreated3);
        TextView tv_avaqty=view.findViewById(R.id.tv_avlqty3);

        String getinvlist=list.get(position).getAvlqty();
        tv_avaqty.setText(getinvlist);
        String getName=list.get(position).getInvName();
        String tv_mitennumber12=list.get(position).getInvgroupname();

//        String tv_usedqty1=list.get(position).getInvqty();
        String getitemdelarminq=list.get(position).getInvUnit();
//        String datacreated=list.get(position).getDateCretaed();
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

//        tv_mdatecreated.setText(date4+ " " +ctime1);
        empName.setText(getName);
        tv_mitennumber.setText(tv_mitennumber12);
        tv_mdealerminquantity.setText(getitemdelarminq);
//        tv_usedqty.setText(tv_usedqty1);
        //tv_mdatecreated.setText(datacreated);
//        if(tv_avaqty.equals("0"))
//        {
//            l3.setVisibility(View.GONE);
//        }
//        else {
//            l3.setVisibility(View.VISIBLE);
//        }

        return view;
    }


}