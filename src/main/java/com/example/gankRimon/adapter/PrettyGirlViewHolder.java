package com.example.gankRimon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gankRimon.R;

/**
 * Created by Rimon on 2016/5/27.
 */
public class PrettyGirlViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;
    public int position;

    public int getPositions() {
        return position;
    }

    public void setPositions(int position) {
        this.position = position;
    }

    public PrettyGirlViewHolder(View itemView, final PrettyGirlAdapter.OnItemClickListener onClickListener,
                                final PrettyGirlAdapter.OnItemLongClickListener onLongClickListener) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.girlView);
        textView = (TextView) itemView.findViewById(R.id.girlViewTitle);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v,position);
                }
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(v);
                }
                return true;
            }
        });
    }

}
