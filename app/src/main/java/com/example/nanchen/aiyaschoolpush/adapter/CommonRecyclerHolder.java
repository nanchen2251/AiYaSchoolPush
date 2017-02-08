package com.example.nanchen.aiyaschoolpush.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.squareup.picasso.Picasso;

/**
 * @author nanchen
 * @fileName ischool
 * @packageName com.idtechinfo.shouxiner.adapter.common
 * @date 2016/09/02  15:41
 */
public class CommonRecyclerHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private Context context;

    private CommonRecyclerHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        //指定一个初始为8
        views = new SparseArray<>(8);
    }

    /**
     * 取得一个RecyclerHolder对象
     * @param context 上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static CommonRecyclerHolder getRecyclerHolder(Context context, View itemView){
        return new CommonRecyclerHolder(context,itemView);
    }

    public SparseArray<View> getViews(){
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null ){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public CommonRecyclerHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRecyclerHolder setTextColor(int viewId,int color){
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecyclerHolder setImageResource(int viewId, int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecyclerHolder setImageByUrl(int viewId, String url){
        Picasso.with(context).load(url)
                .placeholder(context.getResources().getDrawable(R.drawable.ic_default_image))
                .error(context.getResources().getDrawable(R.drawable.ic_default_image))
                .into((ImageView) getView(viewId));
        return this;
    }



    public CommonRecyclerHolder setOnRecyclerItemClickListener(int viewId,OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置九宫格图片
     * @param viewId        id
     * @param clickAdapter  Adapter
     */
    public CommonRecyclerHolder setNineGridAdapter(int viewId,NineGridViewClickAdapter clickAdapter){
        NineGridView nineGridView = getView(viewId);
        nineGridView.setAdapter(clickAdapter);
        return this;
    }

    /**
     * 设置一个控件是否可见
     * @param viewId        id
     * @param visibility    可见性
     * @return
     */
    public CommonRecyclerHolder setVisibility(int viewId,int visibility){
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public CommonRecyclerHolder setOnClckListener(int viewId,OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
