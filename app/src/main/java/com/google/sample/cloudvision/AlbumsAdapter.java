package com.google.sample.cloudvision;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.google.sample.cloudvision.EmailActivity.TAG;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> UserList;
    public static final String TAG ="ll";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        public TextView title, count;
//        public ImageView thumbnail, overflow;
//
//        public MyViewHolder(View view) {
//            super(view);
//            title = (TextView) view.findViewById(R.id.title);
//            //count = (TextView) view.findViewById(R.id.count);
//            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//            //overflow = (ImageView) view.findViewById(R.id.overflow);
//        }
        public TextView date, location, prob, status;
        public ImageView iv;
        public CheckBox cb;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            location = (TextView) itemView.findViewById(R.id.location);
            prob = (TextView) itemView.findViewById(R.id.problem);
            status = (TextView) itemView.findViewById(R.id.status);
            //iv = (ImageView) itemView.findViewById(R.id.iv);
            iv=(ImageView) itemView.findViewById(R.id.iv);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
        }
    }


    public AlbumsAdapter(Context mContext, List<User> UserList) {
        this.mContext = mContext;
        this.UserList = UserList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User UserInfo = UserList.get(position);
        Log.d(TAG, "onBindViewHolder: "+UserInfo.getDate());
        if(UserInfo.getImages()!=null)
            Glide.with(mContext).load(Uri.parse(UserInfo.getImages())).into(holder.iv);
        holder.date.setText(UserInfo.getDate());
        holder.location.setText(UserInfo.getLocation());
        holder.prob.setText(UserInfo.getProb());
        holder.status.setText(UserInfo.getStatus());
        //holder.status.setText("heyy");
//        if(UserInfo.getImages()!=null)
//            Glide.with(mContext).load(Uri.parse(UserInfo.getImages())).into(holder.iv);
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
        //Album album = albumList.get(position);
        //holder.title.setText(album.getName());
        //holder.count.setText(album.getNumOfSongs() + " songs");
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }

    /**
     * Click listener for popup menu items
     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }
}
