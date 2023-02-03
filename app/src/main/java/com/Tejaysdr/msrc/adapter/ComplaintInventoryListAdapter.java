package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.dataModel.InventoryData;

import java.util.ArrayList;

/**
 * Created by sridhar on 2/13/2017.
 */

public class ComplaintInventoryListAdapter extends BaseAdapter {

    static Context context;
    ArrayList<InventoryData> list;

    public ComplaintInventoryListAdapter(Context playlistFragment, ArrayList<InventoryData> vList) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_complaint_inventorylist, parent, false);
        TextView empName = (TextView) view.findViewById(R.id.empName);
        String getName = list.get(position).getInvName();
        String getitem_number = list.get(position).getInvCode();
        String gettot_emp_inv = list.get(position).getInvUnit();
        empName.setText(getName + " (" + getitem_number + ")"+"-"+gettot_emp_inv);
        return view;
    }

}