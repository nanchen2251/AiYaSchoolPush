package com.example.nanchen.aiyaschoolpush.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.CropOption;
import com.example.nanchen.aiyaschoolpush.R;

import java.util.ArrayList;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.adapter
 * @date 2016/09/28  15:26
 */

public class CropOptionAdapter extends ArrayAdapter<CropOption> {
    private ArrayList<CropOption> mOptions;
    private LayoutInflater mInflater;

    public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
        super(context, R.layout.layout_crop_selector, options);
        mOptions = options;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.layout_crop_selector, null);

        CropOption item = mOptions.get(position);

        if (item != null) {
            ((ImageView) convertView.findViewById(R.id.iv_icon))
                    .setImageDrawable(item.icon);
            ((TextView) convertView.findViewById(R.id.tv_name))
                    .setText(item.title);

            return convertView;
        }

        return null;
    }
}
