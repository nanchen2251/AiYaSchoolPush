package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.helper.DemoHelper;
import com.example.nanchen.aiyaschoolpush.utils.DialogUtil;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.hyphenate.chat.EMClient;

public class AddContactActivity extends ActivityBase {

    private TitleView mTitleBar;
    private EditText mEditText;
    private RelativeLayout mSeachedUserLayout;
    private TextView mTextName;
    private Button mBtnSearch;
    private String toAddUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mTitleBar = (TitleView) findViewById(R.id.contact_add_titleBar);
        mTitleBar.setLeftButtonAsFinish(this);
        mTitleBar.setTitle("添加好友");

        mEditText = (EditText) findViewById(R.id.edit_note);
        mSeachedUserLayout = (RelativeLayout) findViewById(R.id.ll_user);

        mTextName = (TextView) findViewById(R.id.name);
        mBtnSearch = (Button) findViewById(R.id.search);
    }

    public void searchContact(View view) {
        String name = mEditText.getText().toString();
        toAddUsername = name;
        if (TextUtils.isEmpty(name)){
            DialogUtil.showDialog(AddContactActivity.this,"用户名不能为空").show();
            return;
        }
        mSeachedUserLayout.setVisibility(View.VISIBLE);
        mTextName.setText(toAddUsername);
    }

    public void addContact(View view) {
        if(EMClient.getInstance().getCurrentUser().equals(mTextName.getText().toString())){
            DialogUtil.showDialog(AddContactActivity.this,"你不能添加你自己").show();
            return;
        }
        if(DemoHelper.getInstance().getContactList().containsKey(mTextName.getText().toString())){
            //let the user know the contact already in your contact list
            if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(mTextName.getText().toString())){
                DialogUtil.showDialog(AddContactActivity.this,"此用户已是你好友(被拉黑状态)，从黑名单列表中移出即可").show();
                return;
            }
            DialogUtil.showDialog(AddContactActivity.this,"此用户已是你好友!").show();
            return;
        }
        showLoading(AddContactActivity.this);
        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(toAddUsername, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            stopLoading();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            stopLoading();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
