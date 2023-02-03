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
import com.Tejaysdr.msrc.dataModel.ComplaintData;
import com.Tejaysdr.msrc.utils.ComplaintInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComplaintAdapter2 extends RecyclerView.Adapter<ComplaintAdapter2.ComplaintHolder> {

    Context context;
    ArrayList<ComplaintData> list=new ArrayList<>();
    ComplaintInterface complaintInterface;

    private Date dt;
    private String date4;

    public ComplaintAdapter2(Context context, ArrayList<ComplaintData> list, ComplaintInterface complaintInterface) {
        this.context = context;
        this.list = list;
        this.complaintInterface = complaintInterface;
    }

    @NonNull
    @Override
    public ComplaintHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_complaint_list,parent,false);
        return new ComplaintHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintHolder holder, int position) {

        String getName=list.get(position).getFirstName();
        String username=list.get(position).getUserName();
        String getCompanyname=list.get(position).getCompany_name();
        String getCompanyname1=list.get(position).getComnmaeteleco();
        String custom_customer_no=list.get(position).getCompNo();
        String mobileno=list.get(position).getMobileNo();

//        holder.empName.setText(getCompanyname+" "+"("+custom_customer_no+")");
        holder.custPhoneadderss.setText(mobileno);
        holder.circuit_id.setText(username);
        holder.comlntstatus.setText(list.get(position).getCompCategory());
        holder.custstate.setText(list.get(position).getCompOwner());
        holder.custcity.setText(list.get(position).getCompStatus());
        if (list.get(position).getCompStatus().equals("Closed") ||
                list.get(position).getCompStatus().equals("Resolved"))
        {
            holder.iv_rightimage.setVisibility(View.GONE);
        }
        else {
            holder.iv_rightimage.setVisibility(View.VISIBLE);
        }
     if(list.get(position).getComusertype().equals("1")){
         holder.empName.setText(getCompanyname1 +""+"("+custom_customer_no+")");
//         holder.linearLayout1.setVisibility(View.VISIBLE);

     }
     else{
         holder.empName.setText(getCompanyname+" "+"("+custom_customer_no+")");
//         holder.linearLayout1.setVisibility(View.VISIBLE);
     }
        String date1[]=list.get(position).getCompDateCreated().split(",");
        String time1[]=list.get(position).getCompDateCreated().split(",");
        String cdate1=date1[0];
        String ctime1=time1[1];

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt = format.parse(cdate1);

            SimpleDateFormat your_format1 = new SimpleDateFormat("dd-MM-yyyy ");
            date4  = your_format1.format(dt);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.datecreated.setText(cdate1+""+ctime1);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ComplaintHolder extends RecyclerView.ViewHolder {

        TextView empName;
        TextView circuit_id;
        TextView custPhoneadderss;
        TextView custstate;
        TextView custcity;
        TextView comlntstatus;
        TextView datecreated;
        ImageView iv_rightimage;
        LinearLayout linearLayout1;
        public ComplaintHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout1=itemView.findViewById(R.id.circuitlinear);
             empName=(TextView)itemView.findViewById(R.id.custName);
             custPhoneadderss=(TextView)itemView.findViewById(R.id.custPhoneadderss);
             custstate=itemView.findViewById(R.id.custstate);
             custcity=itemView.findViewById(R.id.custcity);
             comlntstatus=itemView.findViewById(R.id.comlntstatus);
             datecreated=itemView.findViewById(R.id.datecreated);
             circuit_id=itemView.findViewById(R.id.circuit_id);
             iv_rightimage=itemView.findViewById(R.id.iv_rightimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    complaintInterface.OnItemClick(getAdapterPosition());

                }
            });
        }
    }
}
