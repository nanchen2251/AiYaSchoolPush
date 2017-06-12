package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.im.Constant;
import com.hyphenate.chat.EMMessage;

public class ContextMenuActivity extends ActivityBase {


    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;
    public static final int RESULT_CODE_FORWARD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EMMessage message = getIntent().getParcelableExtra("message");
        boolean isChatroom = getIntent().getBooleanExtra("ischatroom", false);

        int type = message.getType().ordinal();
        if (type == EMMessage.Type.TXT.ordinal()) {
            if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)
                    || message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)
                //red packet code : 屏蔽红包消息、转账消息的转发功能
//                    || message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)
//                    || message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)
                    ) {
                //end of red packet code
                setContentView(R.layout.em_context_menu_for_location);
            } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                setContentView(R.layout.em_context_menu_for_image);
            } else {
                setContentView(R.layout.em_context_menu_for_text);
            }
        } else if (type == EMMessage.Type.LOCATION.ordinal()) {
            setContentView(R.layout.em_context_menu_for_location);
        } else if (type == EMMessage.Type.IMAGE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_image);
        } else if (type == EMMessage.Type.VOICE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_voice);
        } else if (type == EMMessage.Type.VIDEO.ordinal()) {
            setContentView(R.layout.em_context_menu_for_video);
        } else if (type == EMMessage.Type.FILE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_location);
        }
        if (isChatroom
                //red packet code : 屏蔽红包消息、转账消息的撤回功能
//                || message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)
//                || message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)
                ) {
            //end of red packet code
            View v = (View) findViewById(R.id.forward);
            if (v != null) {
                v.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void copy(View view) {
        setResult(RESULT_CODE_COPY);
        finish();
    }

    public void delete(View view) {
        setResult(RESULT_CODE_DELETE);
        finish();
    }

    public void forward(View view) {
        setResult(RESULT_CODE_FORWARD);
        finish();
    }
}
