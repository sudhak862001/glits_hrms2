package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.dataModel.Expensesbdemodel;

import java.util.ArrayList;

public class ExpenseBdeAdapter extends RecyclerView.Adapter<ExpenseBdeAdapter.MyViewHolder> {
    Context context;
    ArrayList<Expensesbdemodel> followuplist;
    ArrayList<Expensesbdemodel> followuplistfilter;
    String page;
    private AlertDialog alertDialogAndroid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exp_date,tv_appr_mangr,tv_appr_finan,tv_mngr_amount,tv_fin_amount,tv_bill_amt,tv_exp_type,tv_appr_amount,tv_status,tv_edit,tv_m_remarks,tv_f_remarks;
        LinearLayout ll_m_remarks,ll_f_remarks,ll_m_amount,ll_f_amount;
        TextView tv_hoteladdress,tv_hotelname,tv_checkoutdate,tv_checkindate,tv_remarks,tv_price,tv_approx_kms,tv_to_loc,tv_from_loc,tv_exp_mode,tv_exp_type1,tv_exp_date1;
        LinearLayout ll_travel,ll_food,ll_accomidation,ll_exp_mode;
        public MyViewHolder(View view) {
            super(view);
            tv_exp_date = (TextView) view.findViewById(R.id.tv_exp_date);
            //tv_appr_mangr = (TextView) view.findViewById(R.id.tv_appr_mangr);
            //tv_appr_finan = (TextView) view.findViewById(R.id.tv_appr_finan);
            tv_bill_amt = (TextView) view.findViewById(R.id.tv_bill_amt);
            tv_appr_amount = (TextView) view.findViewById(R.id.tv_appr_amount);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_exp_type = (TextView) view.findViewById(R.id.tv_exp_type);
            // tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            // tv_m_remarks = (TextView) view.findViewById(R.id.tv_m_remarks);
            // tv_f_remarks = (TextView) view.findViewById(R.id.tv_f_remarks);
            //  ll_f_remarks=(LinearLayout)view.findViewById(R.id.ll_f_remarks);
            //  ll_m_remarks=(LinearLayout)view.findViewById(R.id.ll_m_remarks);

        }
    }
    public ExpenseBdeAdapter(Context playlistFragment, ArrayList<Expensesbdemodel> vList, String pages) {
        followuplist = vList;
        followuplistfilter= new ArrayList<>();
        this.followuplistfilter.addAll(followuplist);
        context = playlistFragment;
        page=pages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_bde_new_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Expensesbdemodel tag = followuplist.get(position);
        if(page.equals("completed")){
            holder.tv_appr_amount.setText(tag.getExpApprovedAmt());
            // holder.ll_f_remarks.setVisibility(View.VISIBLE);
            //  holder.ll_m_remarks.setVisibility(View.VISIBLE);
        }else{
            holder.tv_appr_amount.setText(tag.getExpMApprovedAmt());
            //  holder.ll_f_remarks.setVisibility(View.GONE);
            // holder.ll_m_remarks.setVisibility(View.GONE);
        }
        String[] items1 = tag.getExpItemDate().split("-");
        String year=items1[0];
        String month=items1[1];
        String date=items1[2];
        String styledText = "<u><font color='red'>"+date+"-"+month+"</font></u>";
        holder.tv_exp_date.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        // holder.tv_exp_date.setText(tag.getExpItemDate());
        holder.tv_bill_amt.setText(tag.getExpPrice());
        if(tag.getExpType().equals("Accommodation")) {
            holder.tv_exp_type.setText("Accmd");
        }else{
            holder.tv_exp_type.setText(tag.getExpType());
        }
        // holder.tv_appr_finan.setText(tag.getApproved_by_fin());
        //  holder.tv_appr_mangr.setText(tag.getApproved_by_m());

        holder.tv_status.setText(tag.getExpStatus());
        // holder.tv_f_remarks.setText(tag.getFin_remarks());
        // holder.tv_m_remarks.setText(tag.getManager_remarks());
       /* holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ExpensesBDEViewActivity.class);
                intent.putExtra("exp_id",tag.getExp_id());
                context.startActivity(intent);
            }
        });*/


        holder.tv_exp_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid;
                layoutInflaterAndroid = LayoutInflater.from(context);

                View mView = layoutInflaterAndroid.inflate(R.layout.expenses_popuplist, null);


                holder.tv_exp_date1 = (TextView) mView.findViewById(R.id.tv_exp_date);
                holder.tv_hoteladdress = (TextView) mView.findViewById(R.id.tv_hoteladdress);
                holder.tv_hotelname = (TextView) mView.findViewById(R.id.tv_hotelname);
                holder.tv_checkoutdate = (TextView) mView.findViewById(R.id.tv_checkoutdate);
                holder.tv_checkindate = (TextView) mView.findViewById(R.id.tv_checkindate);
                holder.tv_remarks = (TextView) mView.findViewById(R.id.tv_remarks);
                holder.tv_price = (TextView) mView.findViewById(R.id.tv_price);
                holder.tv_approx_kms = (TextView) mView.findViewById(R.id.tv_approx_kms);
                holder.tv_to_loc = (TextView) mView.findViewById(R.id.tv_to_loc);
                holder.tv_from_loc = (TextView) mView.findViewById(R.id.tv_from_loc);
                holder.tv_exp_mode = (TextView) mView.findViewById(R.id.tv_exp_mode);
                holder.tv_exp_type1 = (TextView) mView.findViewById(R.id.tv_exp_type);
                holder.ll_travel=(LinearLayout)mView.findViewById(R.id.ll_travel);
                holder.ll_accomidation=(LinearLayout)mView.findViewById(R.id.ll_accomidation);
                holder.ll_food=(LinearLayout)mView.findViewById(R.id.ll_food);
                holder.ll_exp_mode=(LinearLayout)mView.findViewById(R.id.ll_exp_mode);
                holder.tv_m_remarks = (TextView) mView.findViewById(R.id.tv_m_remarks);
                holder.tv_f_remarks = (TextView) mView.findViewById(R.id.tv_f_remarks);
                holder.ll_f_remarks=(LinearLayout)mView.findViewById(R.id.ll_f_remarks);
                holder.ll_m_remarks=(LinearLayout)mView.findViewById(R.id.ll_m_remarks);
                holder.tv_appr_mangr = (TextView) mView.findViewById(R.id.tv_appr_mangr);
                holder.tv_appr_finan = (TextView) mView.findViewById(R.id.tv_appr_finan);
                holder.tv_mngr_amount = (TextView) mView.findViewById(R.id.tv_mngr_amount);
                holder.tv_fin_amount = (TextView) mView.findViewById(R.id.tv_fin_amount);
                holder.ll_f_amount = (LinearLayout) mView.findViewById(R.id.ll_f_amount);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);

                if(page.equals("completed")){
                    holder.ll_f_remarks.setVisibility(View.VISIBLE);
                    holder.ll_f_amount.setVisibility(View.VISIBLE);
                }else{
                    holder.ll_f_remarks.setVisibility(View.GONE);
                    holder.ll_f_amount.setVisibility(View.GONE);
                }
                if(tag.getExpType().equals("Accommodation")){
                    holder.ll_accomidation.setVisibility(View.VISIBLE);
                    holder.ll_food.setVisibility(View.VISIBLE);
                    holder.ll_travel.setVisibility(View.GONE);
                    holder.tv_checkoutdate.setText(tag.getCheckOutDate());
                    holder.tv_checkindate.setText(tag.getCheckInDate());
                    holder.tv_hoteladdress.setText(tag.getHotelAddress());
                    holder.tv_hotelname.setText(tag.getHotelName());
                    holder.ll_exp_mode.setVisibility(View.GONE);
                }else if(tag.getExpType().equals("Food")){
                    holder.ll_food.setVisibility(View.VISIBLE);
                    holder.ll_accomidation.setVisibility(View.GONE);
                    holder.ll_travel.setVisibility(View.GONE);
                    holder.tv_hoteladdress.setText(tag.getHotelAddress());
                    holder.tv_hotelname.setText(tag.getHotelName());
                    holder.ll_exp_mode.setVisibility(View.GONE);
                }else if(tag.getExpType().equals("Travel")){
                    holder.ll_food.setVisibility(View.GONE);
                    holder.ll_accomidation.setVisibility(View.GONE);
                    holder.ll_travel.setVisibility(View.VISIBLE);
                    holder.tv_approx_kms.setText(tag.getApproxKm());
                    holder.tv_to_loc.setText(tag.getToLoc());
                    holder.tv_from_loc.setText(tag.getFromLoc());
                    holder.tv_exp_mode.setText(tag.getExpMode());
                    holder.ll_exp_mode.setVisibility(View.VISIBLE);
                }else if(tag.getExpType().equals("Others")){
                    holder.ll_food.setVisibility(View.GONE);
                    holder.ll_accomidation.setVisibility(View.GONE);
                    holder.ll_travel.setVisibility(View.GONE);
                    holder.tv_approx_kms.setText(tag.getApproxKm());
                    holder.tv_to_loc.setText(tag.getToLoc());
                    holder.tv_from_loc.setText(tag.getFromLoc());
                    holder.tv_exp_mode.setText(tag.getExpMode());
                    holder.ll_exp_mode.setVisibility(View.GONE);
                }
                holder.tv_exp_date1.setText(tag.getExpItemDate());
                holder.tv_remarks.setText(tag.getExpRemarks());
                holder.tv_price.setText(tag.getExpPrice());
                holder.tv_exp_type1.setText(tag.getExpType());
                holder.tv_f_remarks.setText(tag.getFinRemarks());
                holder.tv_m_remarks.setText(tag.getManagerRemarks());
                holder.tv_appr_finan.setText(tag.getApprovedByFin());
                holder.tv_appr_mangr.setText(tag.getApprovedByM());
                holder.tv_mngr_amount.setText(tag.getExpMApprovedAmt());
                holder.tv_fin_amount.setText(tag.getExpApprovedAmt());
                TextView btn_cancel = (TextView) mView.findViewById(R.id.buttonOk);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogAndroid.cancel();
                    }
                });
                alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return followuplist.size();
    }

}
