package com.Tejaysdr.msrc.activitys.emp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.Tejaysdr.msrc.R;

import java.util.ArrayList;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    Context mcontext;
    ArrayList<BannerModel>bannerModelslist=new ArrayList<>();

    public BannerAdapter(Context mcontext, ArrayList<BannerModel> bannerModelslist) {
        this.mcontext = mcontext;
        this.bannerModelslist = bannerModelslist;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_banner,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.iv_bannerview.setImageResource(bannerModelslist.get(position).getBannerimage());

    }

    @Override
    public int getItemCount() {
        return bannerModelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bannerview;
        public ViewHolder( View itemView) {
            super(itemView);
            iv_bannerview=itemView.findViewById(R.id.iv_bannerview);
        }
    }
}
