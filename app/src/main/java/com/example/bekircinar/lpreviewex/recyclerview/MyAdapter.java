package com.example.bekircinar.lpreviewex.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bekircinar.lpreviewex.R;

import java.util.ArrayList;

/**
 * Created by bekir.cinar on 06/07/14.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<Item> mItems;
    private OnRecyclerViewItemClickListener itemClickListener;

    public interface OnRecyclerViewItemClickListener {
        public void onItemClicked(View view);
    }

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyler, null);
        // set the view's size, margins, paddings and layout parameters
        v.setOnClickListener(this);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {
        Log.d("numara: ", "" + i);
        viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.selector_item));
        viewHolder.mTextView.setText(mItems.get(i).getText());
        viewHolder.mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClicked(view);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void removeItem(int position) {
        Log.d("position: ", "" + position);
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Item item, int position) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.my_text);
            mImageView = (ImageView) v.findViewById(R.id.my_image);
        }

    }
}
