package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanchen.aiyaschoolpush.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager.EMGroupOptions;
import com.hyphenate.chat.EMGroupManager.EMGroupStyle;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.exceptions.HyphenateException;

public class NewGroupActivity extends ActivityBase {

    private EditText groupNameEditText;
    private ProgressDialog progressDialog;
    private EditText introductionEditText;
    private CheckBox publibCheckBox;
    private CheckBox memberCheckbox;
    private TextView secondTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        groupNameEditText = (EditText) findViewById(R.id.edit_group_name);
        introductionEditText = (EditText) findViewById(R.id.edit_group_introduction);
        publibCheckBox = (CheckBox) findViewById(R.id.cb_public);
        memberCheckbox = (CheckBox) findViewById(R.id.cb_member_inviter);
        secondTextView = (TextView) findViewById(R.id.second_desc);

        publibCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    secondTextView.setText("加入公开群需要群主同意");
                }else{
                    secondTextView.setText(R.string.Open_group_members_invited);
                }
            }
        });
    }

    /**
     * @param v
     */
    public void save(View v) {
        String name = groupNameEditText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            new EaseAlertDialog(this, R.string.Group_name_cannot_be_empty).show();
        } else {
            // select from contact list
            startActivityForResult(new Intent(this, GroupPickContactsActivity.class).putExtra("groupName", name), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
        final String st2 = getResources().getString(R.string.Failed_to_create_groups);
        if (resultCode == RESULT_OK) {
            //new group
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(st1);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String groupName = groupNameEditText.getText().toString().trim();
                    String desc = introductionEditText.getText().toString();
                    String[] members = data.getStringArrayExtra("newmembers");
                    try {
                        EMGroupOptions option = new EMGroupOptions();
                        option.maxUsers = 200;

                        String reason = NewGroupActivity.this.getString(R.string.invite_join_group);
                        reason  = EMClient.getInstance().getCurrentUser() + reason + groupName;

                        if(publibCheckBox.isChecked()){
                            option.style = memberCheckbox.isChecked() ? EMGroupStyle.EMGroupStylePublicJoinNeedApproval : EMGroupStyle.EMGroupStylePublicOpenJoin;
                        }else{
                            option.style = memberCheckbox.isChecked()?EMGroupStyle.EMGroupStylePrivateMemberCanInvite:EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                        }
                        EMClient.getInstance().groupManager().createGroup(groupName, desc, members, reason, option);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    } catch (final HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(NewGroupActivity.this, st2 + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            }).start();
        }
    }

    public void back(View view) {
        finish();
    }
}
