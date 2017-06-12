package com.example.nanchen.aiyaschoolpush.utils;

import android.content.Context;
import android.view.View;

import com.example.nanchen.aiyaschoolpush.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.lang.ref.WeakReference;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush_test
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/10/27  17:15
 */

public class DialogUtil {

    public static NiftyDialogBuilder showDialog(Context context,String desc){
        WeakReference<Context> contextWeakReference = new WeakReference<Context>(context);
        Context c = contextWeakReference.get();
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(c);
        dialogBuilder.withTitle("提示")                                  //.withTitle(null)  no title
                .withMessage(desc)
                .withDialogColor(c.getResources().getColor(R.color.main_bg_color1))                               //def  | withDialogColor(int resid)                               //def
                .withIcon(c.getResources().getDrawable(R.mipmap.icon))
                .withButton1Text("我知道了")                                      //def gone
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });
        return dialogBuilder;
    }
}
