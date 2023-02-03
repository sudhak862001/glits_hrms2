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

public class ReturnlistAdapter extends BaseAdapter {
    static Context context;
    ArrayList<InventoryData> list;
    public ReturnlistAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
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
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent ){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_return_list,parent, false);
        TextView invname = (TextView) view.findViewById(R.id.invname);
        TextView tv_mitennumber=(TextView) view.findViewById(R.id.tv_mitennumber2);
        TextView requiredquantity = (TextView) view.findViewById(R.id.requiredquantity);
        TextView indentno1= (TextView) view.findViewById(R.id.indentno);
        TextView reasoni=(TextView) view.findViewById(R.id.reasoni);
        TextView  requiredon=(TextView)view.findViewById(R.id.requiredon);
        TextView status=(TextView)view.findViewById(R.id.status);
        TextView statustext=(TextView)view.findViewById(R.id.statustext);
        TextView remarksindent = view.findViewById(R.id.remarksindent);
        TextView aprvedqty=view.findViewById(R.id.approvedqty1);
        String aprvedqty1=list.get(position).getApprovedqty();
        aprvedqty.setText(aprvedqty1);
        LinearLayout l=view.findViewById(R.id.linearindent);
        LinearLayout l1=view.findViewById(R.id.approvelinear);
        LinearLayout ls=view.findViewById(R.id.statuslinear);
        String getName=list.get(position).getInvName();
        invname.setText(getName);
        String tv_mitennumber12=list.get(position).getInvCode();
        tv_mitennumber.setText(tv_mitennumber12);
        String requiredqty1=list.get(position).getRequiredqty();
        requiredquantity.setText(requiredqty1);
        String indentno=list.get(position).getIndentno();
        indentno1.setText(indentno);
        String indentreason=list.get(position).getIndentreason();
        reasoni.setText(indentreason);
        String requiredqtyon1=list.get(position).getRequiredon();
        requiredon.setText(requiredqtyon1);
        String status1=list.get(position).getStatus();
        status.setText(status1);
        String status2=list.get(position).getStatustxt();
        statustext.setText(status2);
        String inwardremarks=list.get(position).getInwardremarks();
        remarksindent.setText(inwardremarks);
//        if(status1.equals("0")){
//            l.setVisibility(View.GONE);
//        }
//        else {
//           l.setVisibility(View.VISIBLE);
//        }
        if(status1.equals("1")){
            l1.setVisibility(View.VISIBLE);
        }
        else {
            l1.setVisibility(View.GONE);
        }

//        if(aprvedqty1.equals("")){
//            l1.setVisibility(View.GONE);
//}
//     else {
//            l1.setVisibility(View.VISIBLE);
//        }


        return view;
    }
}
