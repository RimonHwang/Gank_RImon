package com.example.gankRimon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gankRimon.R;
import com.example.gankRimon.model.Gank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rimon on 2016/5/27.
 */
public class PrettyGirlAdapter extends RecyclerView.Adapter<PrettyGirlViewHolder> {

    private Context context;
    private final List<Gank.Result> resultList=new ArrayList<>();
    private OnItemClickListener onClickListener;
    private OnItemLongClickListener onLongClickListener;

    public PrettyGirlAdapter(Context context) {
        this.context = context;
    }

    public PrettyGirlAdapter(Context context, OnItemClickListener onClickListener) {
        this.context=context;
        this.onClickListener=onClickListener;
    }

    public PrettyGirlAdapter(Context context, OnItemClickListener onClickListener, OnItemLongClickListener
            onLongClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    public void addData(Gank.Result result) {
        resultList.add(result);
        notifyDataSetChanged();
    }

    public void clear() {
        resultList.clear();
        notifyDataSetChanged();
    }


    @Override
    public PrettyGirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.prettygirl_item,parent,false);
        PrettyGirlViewHolder holder=new PrettyGirlViewHolder(itemView,onClickListener,onLongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(PrettyGirlViewHolder holder, int position) {
        holder.setPositions(position);
        final Gank.Result result=resultList.get(position);
        ImageView imageView =holder.imageView;
        holder.textView.setText(result.getDesc());
        Glide.with(context)
                .load(result.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        }


    @Override
    public int getItemCount() {

        String size=resultList.size()+"";
        Log.e("妹子数量",size);
        return resultList.size();
    }

    public interface OnItemClickListener {
        void onClick(View v,int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(View v);
    }


}
