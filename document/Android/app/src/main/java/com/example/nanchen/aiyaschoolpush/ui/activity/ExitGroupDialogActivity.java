package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;

public class ExitGroupDialogActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_group_dialog);


        TextView text = (TextView) findViewById(R.id.tv_text);
        Button exitBtn = (Button) findViewById(R.id.btn_exit);

        text.setText(R.string.exit_group_hint);
        String toast = getIntent().getStringExtra("deleteToast");
        if(toast != null)
            text.setText(toast);
        exitBtn.setText(R.string.exit_group);
    }

    public void logout(View view){
        setResult(RESULT_OK);
        finish();

    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
