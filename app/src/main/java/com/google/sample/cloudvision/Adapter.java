package com.google.sample.cloudvision;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<User> UserList;

    public Adapter(Context context, List<User> TempList) {

        this.UserList = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User UserInfo = UserList.get(position);
        holder.date.setText(UserInfo.getDate());
        holder.location.setText(UserInfo.getLocation());
        holder.prob.setText(UserInfo.getProb());
        holder.status.setText(UserInfo.getStatus());
        //holder.status.setText("heyy");
        if(UserInfo.getImages()!=null)
            Glide.with(context).load(Uri.parse(UserInfo.getImages())).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date,location,prob,status;
        public ImageView iv;
        public CheckBox cb;
        public ViewHolder(View itemView) {
            super(itemView);
            date=(TextView) itemView.findViewById(R.id.date);
            location=(TextView) itemView.findViewById(R.id.location);
            prob=(TextView) itemView.findViewById(R.id.problem);
            status=(TextView) itemView.findViewById(R.id.status);
            iv=(ImageView) itemView.findViewById(R.id.iv);
            cb=(CheckBox) itemView.findViewById(R.id.cb);
        }
    }
}
