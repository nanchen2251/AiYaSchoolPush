package com.example.nanchen.aiyaschoolpush;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.ninegrid.NineGridView;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush
 * @date 2016/11/29  09:43
 */

public class PicassoImageLoader implements NineGridView.ImageLoader {

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        com.squareup.picasso.Picasso.with(context).load(url)//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
