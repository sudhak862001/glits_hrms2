package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by sridhar on 2/10/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        // TODO Auto-generated constructor stub

    }

    @Override
    public int getCount() {

        // TODO Auto-generated method stub
        int count = super.getCount();

        return count>0 ? count-1 : count ;


    }


}