package com.google.sample.cloudvision;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by delaroy on 2/13/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private String[] mDataset;
   // Context context;
    public MyAdapter(Context context, String[] mDataset) {
     //   this.context = context;
        this.mDataset = mDataset;
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public CardView mCardView;
            public TextView mTextView;
            public ImageView mImageView;
            View thisView;

            public MyViewHolder(View v) {
                super(v);
                thisView=v;
                mCardView = (CardView) v.findViewById(R.id.card_view);
                mTextView = (TextView) v.findViewById(R.id.tv_text);
                mImageView = (ImageView) v.findViewById(R.id.iv_image);
            }

        }



    public MyAdapter(String[] myDataset){

        mDataset = myDataset;

    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){

        holder.mTextView.setText(mDataset[position]);
        holder.thisView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
