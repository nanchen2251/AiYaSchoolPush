package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.NewFriendsMsgAdapter;
import com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao;
import com.example.nanchen.aiyaschoolpush.im.InviteMessage;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;

import java.util.List;

public class NewFriendsMsgActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        TitleView titleView = (TitleView) findViewById(R.id.new_friends_titleBar);
        titleView.setLeftButtonAsFinish(this);
        titleView.setTitle("申请与通知");

        ListView listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }
}
