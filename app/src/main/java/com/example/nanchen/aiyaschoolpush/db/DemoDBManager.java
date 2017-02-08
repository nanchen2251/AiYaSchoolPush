package com.example.nanchen.aiyaschoolpush.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nanchen.aiyaschoolpush.im.Constant;
import com.example.nanchen.aiyaschoolpush.im.InviteMesageStatus;
import com.example.nanchen.aiyaschoolpush.im.InviteMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.example.nanchen.aiyaschoolpush.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DemoDBManager {
    static private DemoDBManager dbMgr = new DemoDBManager();
    private com.example.nanchen.aiyaschoolpush.db.DbOpenHelper dbHelper;
    
    private DemoDBManager(){
        dbHelper = com.example.nanchen.aiyaschoolpush.db.DbOpenHelper.getInstance(App.getInstance().getApplicationContext());
    }
    
    public static synchronized DemoDBManager getInstance(){
        if(dbMgr == null){
            dbMgr = new DemoDBManager();
        }
        return dbMgr;
    }
    
    /**
     * save contact list
     * 
     * @param contactList
     */
    synchronized public void saveContactList(List<EaseUser> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(com.example.nanchen.aiyaschoolpush.db.UserDao.TABLE_NAME, null, null);
            for (EaseUser user : contactList) {
                ContentValues values = new ContentValues();
                values.put(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_ID, user.getUsername());
                if(user.getNick() != null)
                    values.put(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_NICK, user.getNick());
                if(user.getAvatar() != null)
                    values.put(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
                db.replace(com.example.nanchen.aiyaschoolpush.db.UserDao.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * get contact list
     * 
     * @return
     */
    synchronized public Map<String, EaseUser> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + com.example.nanchen.aiyaschoolpush.db.UserDao.TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_AVATAR));
                EaseUser user = new EaseUser(username);
                user.setNick(nick);
                user.setAvatar(avatar);
                if (username.equals(Constant.NEW_FRIENDS_USERNAME) || username.equals(Constant.GROUP_USERNAME)
                        || username.equals(Constant.CHAT_ROOM)|| username.equals(Constant.CHAT_ROBOT)) {
                        user.setInitialLetter("");
                } else {
                    EaseCommonUtils.setUserInitialLetter(user);
                }
                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }
    
    /**
     * delete a contact
     * @param username
     */
    synchronized public void deleteContact(String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete(com.example.nanchen.aiyaschoolpush.db.UserDao.TABLE_NAME, com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }
    
    /**
     * save a contact
     * @param user
     */
    synchronized public void saveContact(EaseUser user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_ID, user.getUsername());
        if(user.getNick() != null)
            values.put(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_NICK, user.getNick());
        if(user.getAvatar() != null)
            values.put(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
        if(db.isOpen()){
            db.replace(com.example.nanchen.aiyaschoolpush.db.UserDao.TABLE_NAME, null, values);
        }
    }
    
    public void setDisabledGroups(List<String> groups){
        setList(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_DISABLED_GROUPS, groups);
    }
    
    public List<String>  getDisabledGroups(){       
        return getList(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_DISABLED_GROUPS);
    }
    
    public void setDisabledIds(List<String> ids){
        setList(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_DISABLED_IDS, ids);
    }
    
    public List<String> getDisabledIds(){
        return getList(com.example.nanchen.aiyaschoolpush.db.UserDao.COLUMN_NAME_DISABLED_IDS);
    }
    
    synchronized private void setList(String column, List<String> strList){
        StringBuilder strBuilder = new StringBuilder();
        
        for(String hxid:strList){
            strBuilder.append(hxid).append("$");
        }
        
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(column, strBuilder.toString());

            db.update(com.example.nanchen.aiyaschoolpush.db.UserDao.PREF_TABLE_NAME, values, null,null);
        }
    }
    
    synchronized private List<String> getList(String column){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + column + " from " + com.example.nanchen.aiyaschoolpush.db.UserDao.PREF_TABLE_NAME,null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        String strVal = cursor.getString(0);
        if (strVal == null || strVal.equals("")) {
            return null;
        }
        
        cursor.close();
        
        String[] array = strVal.split("$");
        
        if(array.length > 0){
            List<String> list = new ArrayList<String>();
            Collections.addAll(list, array);
            return list;
        }
        
        return null;
    }
    
    /**
     * save a message
     * @param message
     * @return  return cursor of the message
     */
    public synchronized Integer saveMessage(InviteMessage message){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_GROUP_Name, message.getGroupName());
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_STATUS, message.getStatus().ordinal());
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_GROUPINVITER, message.getGroupInviter());
            db.insert(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME, null, values);
            
            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME,null);
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            
            cursor.close();
        }
        return id;
    }
    
    /**
     * update message
     * @param msgId
     * @param values
     */
    synchronized public void updateMessage(int msgId,ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            db.update(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME, values, com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
        }
    }
    
    /**
     * get messges
     * @return
     */
    synchronized public List<InviteMessage> getMessagesList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from " + com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME + " desc",null);
            while(cursor.moveToNext()){
                InviteMessage msg = new InviteMessage();
                int id = cursor.getInt(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_ID));
                String from = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_FROM));
                String groupid = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_GROUP_ID));
                String groupname = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_GROUP_Name));
                String reason = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_REASON));
                long time = cursor.getLong(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_TIME));
                int status = cursor.getInt(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_STATUS));
                String groupInviter = cursor.getString(cursor.getColumnIndex(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_GROUPINVITER));
                
                msg.setId(id);
                msg.setFrom(from);
                msg.setGroupId(groupid);
                msg.setGroupName(groupname);
                msg.setReason(reason);
                msg.setTime(time);
                msg.setGroupInviter(groupInviter);
                
                if(status == InviteMesageStatus.BEINVITEED.ordinal())
                    msg.setStatus(InviteMesageStatus.BEINVITEED);
                else if(status == InviteMesageStatus.BEAGREED.ordinal())
                    msg.setStatus(InviteMesageStatus.BEAGREED);
                else if(status == InviteMesageStatus.BEREFUSED.ordinal())
                    msg.setStatus(InviteMesageStatus.BEREFUSED);
                else if(status == InviteMesageStatus.AGREED.ordinal())
                    msg.setStatus(InviteMesageStatus.AGREED);
                else if(status == InviteMesageStatus.REFUSED.ordinal())
                    msg.setStatus(InviteMesageStatus.REFUSED);
                else if(status == InviteMesageStatus.BEAPPLYED.ordinal())
                    msg.setStatus(InviteMesageStatus.BEAPPLYED);
                else if(status == InviteMesageStatus.GROUPINVITATION.ordinal())
                    msg.setStatus(InviteMesageStatus.GROUPINVITATION);
                else if(status == InviteMesageStatus.GROUPINVITATION_ACCEPTED.ordinal())
                    msg.setStatus(InviteMesageStatus.GROUPINVITATION_ACCEPTED);
                else if(status == InviteMesageStatus.GROUPINVITATION_DECLINED.ordinal())
                    msg.setStatus(InviteMesageStatus.GROUPINVITATION_DECLINED);
                
                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
    }
    
    /**
     * delete invitation message
     * @param from
     */
    synchronized public void deleteMessage(String from){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME, com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_FROM + " = ?", new String[]{from});
        }
    }
    
    synchronized int getUnreadNotifyCount(){
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select " + com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME, null);
            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
            cursor.close();
        }
         return count;
    }
    
    synchronized void setUnreadNotifyCount(int count){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);

            db.update(com.example.nanchen.aiyaschoolpush.db.InviteMessgeDao.TABLE_NAME, values, null,null);
        }
    }
    
    synchronized public void closeDB(){
        if(dbHelper != null){
            dbHelper.closeDB();
        }
        dbMgr = null;
    }
    
    
}
