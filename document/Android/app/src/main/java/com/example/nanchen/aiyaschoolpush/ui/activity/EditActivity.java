package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;

public class EditActivity extends ActivityBase {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        editText = (EditText) findViewById(R.id.edittext);
        String title = getIntent().getStringExtra("title");
        String data = getIntent().getStringExtra("data");
        if(title != null)
            ((TextView)findViewById(R.id.tv_title)).setText(title);
        if(data != null)
            editText.setText(data);
        editText.setSelection(editText.length());
    }

    public void save(View view){
        setResult(RESULT_OK,new Intent().putExtra("data", editText.getText().toString()));
        finish();
    }

    public void back(View view) {
        finish();
    }
}
