package com.Tejaysdr.msrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.dataModel.UsedInventoryModel;
import com.Tejaysdr.msrc.utils.ComplaintInterface2;

import java.util.ArrayList;
import java.util.List;

public class UsedInventoryAdapter extends RecyclerView.Adapter<UsedInventoryAdapter.InventoryHolder> {
    Context context;
    List<UsedInventoryModel> list=new ArrayList<>();
    ComplaintInterface2 complaintInterface;
    int pos1;

    public int getPos1() {
        return pos1;
    }

    public void setPos1(int pos1) {
        this.pos1 = pos1;
    }

    public UsedInventoryAdapter(Context context, List<UsedInventoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsedInventoryAdapter.InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_usedinventory,parent,false);
        return new InventoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsedInventoryAdapter.InventoryHolder holder, int position) {

        UsedInventoryModel tag = list.get(position);
        int pos = position;
         pos1=position;
        holder.invName.setText("   :  " + list.get(position).getInvName());
        holder.usdQty.setText("   :  " + list.get(position).getUsdQty());
        holder.srtrdng.setText("   :  " + list.get(position).getSrtrdng());
        holder.endrdng.setText("   :  " +  list.get(position).getEndrdng());
        holder.srlnum.setText("   :  " +list.get(position).getSrlnum());
      if(tag.getSrtrdng().equals("")){
        holder.linearLayout1.setVisibility(View.GONE);
     }
    else {
       holder.linearLayout1.setVisibility(View.VISIBLE);
    }
        if(tag.getEndrdng().equals("")){
            holder.linearLayout2.setVisibility(View.GONE);
        }
        else {
            holder.linearLayout2.setVisibility(View.VISIBLE);
        }
        if(tag.getSrlnum().equalsIgnoreCase("select")){
            holder.linearLayout3.setVisibility(View.GONE);
        }
        else {
            holder.linearLayout3.setVisibility(View.VISIBLE);
        }
        holder.iv_delete_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (pos != -1 && list.size() !=0){
                        list.remove(pos);
                        notifyItemRemoved(pos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InventoryHolder extends RecyclerView.ViewHolder {
        TextView invName,usdQty,srtrdng,endrdng,srlnum;
        ImageView iv_delete_card;
        LinearLayout linearLayout1,linearLayout2,linearLayout3;
        public InventoryHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout1=(itemView).findViewById(R.id.linearstartread);
            linearLayout2=(itemView).findViewById(R.id.linearendread);
            linearLayout3=(itemView).findViewById(R.id.linearseiallast);
            invName=itemView.findViewById(R.id.inv_name);
            usdQty=itemView.findViewById(R.id.inv_used);
            srtrdng=itemView.findViewById(R.id.strtrdng);
            endrdng=itemView.findViewById(R.id.endrdng);
            srlnum=itemView.findViewById(R.id.srlnum);
            iv_delete_card=itemView.findViewById(R.id.iv_delete);
        }
    }
}
