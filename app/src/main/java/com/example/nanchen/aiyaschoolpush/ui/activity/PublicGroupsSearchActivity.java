package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanchen.aiyaschoolpush.R;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

public class PublicGroupsSearchActivity extends ActivityBase {

    private RelativeLayout containerLayout;
    private EditText idET;
    private TextView nameText;
    public static EMGroup searchedGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_groups_seach);


        containerLayout = (RelativeLayout) findViewById(R.id.rl_searched_group);
        idET = (EditText) findViewById(R.id.et_search_id);
        nameText = (TextView) findViewById(R.id.name);

        searchedGroup = null;
    }

    /**
     * search group with group id
     * @param v
     */
    public void searchGroup(View v){
        if(TextUtils.isEmpty(idET.getText())){
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.searching));
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {

            public void run() {
                try {
                    searchedGroup = EMClient.getInstance().groupManager().getGroupFromServer(idET.getText().toString());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            containerLayout.setVisibility(View.VISIBLE);
                            nameText.setText(searchedGroup.getGroupName());
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            searchedGroup = null;
                            containerLayout.setVisibility(View.GONE);
                            if(e.getErrorCode() == EMError.GROUP_INVALID_ID){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.group_not_existed), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.group_search_failed) + " : " + getString(R.string.connect_failuer_toast), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();

    }


    /**
     * enter the detail screen of group
     * @param view
     */
    public void enterToDetails(View view){
        startActivity(new Intent(this, GroupSimpleDetailActivity.class));
    }
}
