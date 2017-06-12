package com.example.nanchen.aiyaschoolpush.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 *  万能适配器的ViewHolder
 *
 * @author nanchen
 * @date   2016-07-29  15:04:10
 */
public class ViewHolder {

    //现在对于int作为键的官方推荐用SparseArray替代HashMap
    private final SparseArray<View> views;
    private View convertView;
    private Context context;

    private ViewHolder(Context context,ViewGroup parent,int itemLayoutId,int position) {
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(itemLayoutId,parent,false);
        convertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static ViewHolder get(Context context,View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context,parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置字符串
     */
    public ViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setText(int viewId,CharSequence text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }


    /**
     * 设置图片
     */
    public ViewHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageDrawable(int viewId, Drawable drawable){
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageByUrl(int viewId,String url){
        Picasso.with(context).load(url).into((ImageView) getView(viewId));
        //        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        //        ImageLoader.getInstance().displayImage(url, (ImageView) getView(viewId));
        return this;
    }

    /**
     * 设置文本颜色
     */
    public ViewHolder setTextColor(int viewId,int color){
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }
}
