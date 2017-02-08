package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanchen.aiyaschoolpush.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.Collections;
import java.util.List;

public class GroupBlacklistActivity extends ActivityBase {

    private ListView listView;
    private ProgressBar progressBar;
    private BlacklistAdapter adapter;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_blacklist);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.list);

        groupId = getIntent().getStringExtra("groupId");
        // register context menu
        registerForContextMenu(listView);
        final String st1 = getResources().getString(R.string.get_failed_please_check);
        new Thread(new Runnable() {

            public void run() {
                try {
                    List<String> blockedList = EMClient.getInstance().groupManager().getBlockedUsers(groupId);
                    if(blockedList != null){
                        Collections.sort(blockedList);
                        adapter = new BlacklistAdapter(GroupBlacklistActivity.this, 1, blockedList);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), st1, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.em_remove_from_blacklist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove) {
            final String tobeRemoveUser = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
            // move out of blacklist
            removeOutBlacklist(tobeRemoveUser);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * move out of blacklist
     *
     * @param tobeRemoveUser
     */
    void removeOutBlacklist(final String tobeRemoveUser) {
        final String st2 = getResources().getString(R.string.Removed_from_the_failure);
        try {
            EMClient.getInstance().groupManager().unblockUser(groupId, tobeRemoveUser);
            adapter.remove(tobeRemoveUser);
        } catch (HyphenateException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), st2, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void back(View view) {
        finish();
    }

    /**
     * adapter
     *
     */
    private class BlacklistAdapter extends ArrayAdapter<String> {

        public BlacklistAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.ease_row_contact, null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(getItem(position));

            return convertView;
        }

    }
}
