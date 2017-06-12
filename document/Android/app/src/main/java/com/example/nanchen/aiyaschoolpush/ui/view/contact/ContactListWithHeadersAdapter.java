package com.example.nanchen.aiyaschoolpush.ui.view.contact;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.security.SecureRandom;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.contact
 * @date 2016/10/14  09:02
 */

public class ContactListWithHeadersAdapter extends ContactListAdapter<ViewHolder>
        implements StickyRecyclerHeadersAdapter<ViewHolder> {

    private RecyclerView recyclerView;
    //在RecyclerView提供数据的时候调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.contact_name);
        textView.setText(getItem(position).getPeopleName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    mOnItemClickListener.onItemClick(recyclerView, view, position);
                }
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getFirstLetter().charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(getItem(position).getFirstLetter()));
        holder.itemView.setBackgroundColor(getRandomColor());
    }

    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        public void onItemClick(RecyclerView parent, View view, int position);
    }


}
