package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.fragment.ChatFragment;
import com.example.nanchen.aiyaschoolpush.im.PermissionsManager;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

public class ChatActivity extends ActivityBase {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
//        chatFragment = new EaseChatFragment();
//        chatFragment = new NewChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String getToChatUsername(){
        return toChatUsername;
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
