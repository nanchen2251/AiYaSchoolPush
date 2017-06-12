/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.nanchen.aiyaschoolpush.im;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	/**
	 * name of preference
	 */
	public static final String PREFERENCE_NAME = "saveInfo";
	private static SharedPreferences mSharedPreferences;
	private static PreferenceManager mPreferencemManager;
	private static SharedPreferences.Editor editor;

	private String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";
	private String SHARED_KEY_SETTING_SOUND = "shared_key_setting_sound";
	private String SHARED_KEY_SETTING_VIBRATE = "shared_key_setting_vibrate";
	private String SHARED_KEY_SETTING_SPEAKER = "shared_key_setting_speaker";

	private static String SHARED_KEY_SETTING_CHATROOM_OWNER_LEAVE = "shared_key_setting_chatroom_owner_leave";
    private static String SHARED_KEY_SETTING_DELETE_MESSAGES_WHEN_EXIT_GROUP = "shared_key_setting_delete_messages_when_exit_group";
    private static String SHARED_KEY_SETTING_AUTO_ACCEPT_GROUP_INVITATION = "shared_key_setting_auto_accept_group_invitation";
    private static String SHARED_KEY_SETTING_ADAPTIVE_VIDEO_ENCODE = "shared_key_setting_adaptive_video_encode";
	private static String SHARED_KEY_SETTING_OFFLINE_PUSH_CALL = "shared_key_setting_offline_push_call";

	private static String SHARED_KEY_SETTING_GROUPS_SYNCED = "SHARED_KEY_SETTING_GROUPS_SYNCED";
	private static String SHARED_KEY_SETTING_CONTACT_SYNCED = "SHARED_KEY_SETTING_CONTACT_SYNCED";
	private static String SHARED_KEY_SETTING_BALCKLIST_SYNCED = "SHARED_KEY_SETTING_BALCKLIST_SYNCED";

	private static String SHARED_KEY_CURRENTUSER_USERNAME = "SHARED_KEY_CURRENTUSER_USERNAME";
	private static String SHARED_KEY_CURRENTUSER_NICK = "SHARED_KEY_CURRENTUSER_NICK";
	private static String SHARED_KEY_CURRENTUSER_AVATAR = "SHARED_KEY_CURRENTUSER_AVATAR";

	private static String SHARED_KEY_REST_SERVER = "SHARED_KEY_REST_SERVER";
	private static String SHARED_KEY_IM_SERVER = "SHARED_KEY_IM_SERVER";
	private static String SHARED_KEY_ENABLE_CUSTOM_SERVER = "SHARED_KEY_ENABLE_CUSTOM_SERVER";
	private static String SHARED_KEY_ENABLE_CUSTOM_APPKEY = "SHARED_KEY_ENABLE_CUSTOM_APPKEY";
	private static String SHARED_KEY_CUSTOM_APPKEY = "SHARED_KEY_CUSTOM_APPKEY";

	@SuppressLint("CommitPrefEdits")
	private PreferenceManager(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
	}

	public static synchronized void init(Context cxt){
	    if(mPreferencemManager == null){
	        mPreferencemManager = new PreferenceManager(cxt);
	    }
	}

	/**
	 * get instance of PreferenceManager
	 *
	 * @param
	 * @return
	 */
	public synchronized static PreferenceManager getInstance() {
		if (mPreferencemManager == null) {
			throw new RuntimeException("please init first!");
		}

		return mPreferencemManager;
	}

	public void setSettingMsgNotification(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
		editor.apply();
	}

	public boolean getSettingMsgNotification() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION, true);
	}

	public void setSettingMsgSound(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_SOUND, paramBoolean);
		editor.apply();
	}

	public boolean getSettingMsgSound() {

		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_SOUND, true);
	}

	public void setSettingMsgVibrate(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_VIBRATE, paramBoolean);
		editor.apply();
	}

	public boolean getSettingMsgVibrate() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_VIBRATE, true);
	}

	public void setSettingMsgSpeaker(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_SPEAKER, paramBoolean);
		editor.apply();
	}

	public boolean getSettingMsgSpeaker() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_SPEAKER, true);
	}

	public void setSettingAllowChatroomOwnerLeave(boolean value) {
        editor.putBoolean(SHARED_KEY_SETTING_CHATROOM_OWNER_LEAVE, value);
        editor.apply();
    }

	public boolean getSettingAllowChatroomOwnerLeave() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_CHATROOM_OWNER_LEAVE, true);
    }

    public void setDeleteMessagesAsExitGroup(boolean value){
        editor.putBoolean(SHARED_KEY_SETTING_DELETE_MESSAGES_WHEN_EXIT_GROUP, value);
        editor.apply();
    }

    public boolean isDeleteMessagesAsExitGroup() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_DELETE_MESSAGES_WHEN_EXIT_GROUP, true);
    }

    public void setAutoAcceptGroupInvitation(boolean value) {
        editor.putBoolean(SHARED_KEY_SETTING_AUTO_ACCEPT_GROUP_INVITATION, value);
        editor.commit();
    }

    public boolean isAutoAcceptGroupInvitation() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_AUTO_ACCEPT_GROUP_INVITATION, true);
    }

    public void setAdaptiveVideoEncode(boolean value) {
        editor.putBoolean(SHARED_KEY_SETTING_ADAPTIVE_VIDEO_ENCODE, value);
        editor.apply();
    }

    public boolean isAdaptiveVideoEncode() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_ADAPTIVE_VIDEO_ENCODE, false);
    }

	public void setPushCall(boolean value) {
		editor.putBoolean(SHARED_KEY_SETTING_OFFLINE_PUSH_CALL, value);
		editor.apply();
	}

	public boolean isPushCall() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_OFFLINE_PUSH_CALL, false);
	}
    
	public void setGroupsSynced(boolean synced){
	    editor.putBoolean(SHARED_KEY_SETTING_GROUPS_SYNCED, synced);
        editor.apply();
	}

	public boolean isGroupsSynced(){
	    return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_GROUPS_SYNCED, false);
	}

	public void setContactSynced(boolean synced){
        editor.putBoolean(SHARED_KEY_SETTING_CONTACT_SYNCED, synced);
        editor.apply();
    }

    public boolean isContactSynced(){
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_CONTACT_SYNCED, false);
    }

    public void setBlacklistSynced(boolean synced){
        editor.putBoolean(SHARED_KEY_SETTING_BALCKLIST_SYNCED, synced);
        editor.apply();
    }

    public boolean isBacklistSynced(){
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_BALCKLIST_SYNCED, false);
    }

	public void setCurrentUserNick(String nick) {
		editor.putString(SHARED_KEY_CURRENTUSER_NICK, nick);
		editor.apply();
	}

	public void setCurrentUserAvatar(String avatar) {
		editor.putString(SHARED_KEY_CURRENTUSER_AVATAR, avatar);
		editor.apply();
	}

	public String getCurrentUserNick() {
		return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_NICK, null);
	}

	public String getCurrentUserAvatar() {
		return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_AVATAR, null);
	}

	public void setCurrentUserName(String username){
		editor.putString(SHARED_KEY_CURRENTUSER_USERNAME, username);
		editor.apply();
	}

	public String getCurrentUsername(){
		return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_USERNAME, null);
	}

	public void setRestServer(String restServer){
		editor.putString(SHARED_KEY_REST_SERVER, restServer).commit();
		editor.commit();
	}

	public String getRestServer(){
		return mSharedPreferences.getString(SHARED_KEY_REST_SERVER, null);
	}

	public void setIMServer(String imServer){
		editor.putString(SHARED_KEY_IM_SERVER, imServer);
		editor.commit();
	}

	public String getIMServer(){
		return mSharedPreferences.getString(SHARED_KEY_IM_SERVER, null);
	}

	public void enableCustomServer(boolean enable){
		editor.putBoolean(SHARED_KEY_ENABLE_CUSTOM_SERVER, enable);
		editor.apply();
	}

	public boolean isCustomServerEnable(){
		return mSharedPreferences.getBoolean(SHARED_KEY_ENABLE_CUSTOM_SERVER, false);
	}

	public void enableCustomAppkey(boolean enable) {
		editor.putBoolean(SHARED_KEY_ENABLE_CUSTOM_APPKEY, enable);
		editor.apply();
	}

	public boolean isCustomAppkeyEnabled() {
		return mSharedPreferences.getBoolean(SHARED_KEY_ENABLE_CUSTOM_APPKEY, false);
	}

	public String getCustomAppkey() {
		return mSharedPreferences.getString(SHARED_KEY_CUSTOM_APPKEY, "");
	}

	public void setCustomAppkey(String appkey) {
		editor.putString(SHARED_KEY_CUSTOM_APPKEY, appkey);
		editor.apply();
	}

	public void removeCurrentUserInfo() {
		editor.remove(SHARED_KEY_CURRENTUSER_NICK);
		editor.remove(SHARED_KEY_CURRENTUSER_AVATAR);
		editor.apply();
	}
}
