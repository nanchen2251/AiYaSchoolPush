package com.example.nanchen.aiyaschoolpush.helper;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * 本地存储工具类
 *
 * @author Frank
 * @email frank@mondol.info
 * @create_date 2015年4月7日
 */
public class LocalStorageHelper {
	private static final String FILE_TAG_IMAGE = "image";
	private static final String FILE_TAG_AUDIO = "audio";
	private static final String FILE_TAG_VIDEO = "video";
	static File mFilesDir;
	static File mUsersDir;

	/**
	 * 创建一个空的临时文件，用后请删除
	 * 
	 * @param suffix
	 *            文件扩展名，传null则使用.tmp
	 * @return null则失败
	 */
	public static File createTempFile(String suffix) {
		return FileHelper.createTempFile(suffix);
	}

	/**
	 * 创建班级圈视频
	 * 
	 * 返回文件路径，不需要请删除
	 */
	public static File createGroupVideoFile(long groupId) {
		return createUserTempFile("circle_" + groupId, FILE_TAG_VIDEO, ".mp4");
	}

	/**
	 * 创建一个空的讨论组图标文件
	 */
	public static File createDiscussGroupIconFile(String jid) {
		return createUserTempFile("group_" + jid, null, ".jpg");
	}

	/**
	 * 创建一个空的用于聊天的图片文件
	 * 
	 * @param userId
	 *            用户ID
	 */
	public static File createChatImageFile(long userId) {
		return createUserTempFile("chat_" + userId, FILE_TAG_IMAGE, ".jpg");
	}

	/**
	 * 创建一个空的用于启动页广告图片文件
	 */
	public static File createAdImageFile() {
		return createUserTempFile("start_ad", FILE_TAG_IMAGE, ".jpg");
	}
	/**
	 * 创建一个空的用于聊天的图片文件
	 * 
	 * @param jid
	 *            讨论组ID
	 * @return
	 */
	public static File createChatImageFile(String jid) {
		return createUserTempFile("chat_" + jid, FILE_TAG_IMAGE, ".jpg");
	}

	/**
	 * 创建一个空的用于聊天的语音文件
	 * 
	 * @param userId
	 * @return
	 */
	public static File createChatAudioFile(long userId) {
		return createUserTempFile("chat_" + userId, FILE_TAG_AUDIO, ".amr");
	}

	/**
	 * 创建一个空的用于聊天的语音文件
	 * 
	 * @param jid
	 * @return
	 */
	public static File createChatAudioFile(String jid) {
		return createUserTempFile("chat_" + jid, FILE_TAG_AUDIO, ".amr");
	}

	private static File createUserTempFile(String uidOrJid, String fileTag, String suffix) {
		if (mFilesDir == null)
			mFilesDir = FileHelper.getFilesDir();
		if (mUsersDir == null) {
			mUsersDir = new File(mFilesDir, "users");
			mUsersDir.mkdir();
		}

		String userDir = TextUtils.isEmpty(uidOrJid) ? "empty" : StringHelper.getMD5String(uidOrJid);
		File fDir = new File(mUsersDir, userDir);
		fDir.mkdir();

		if (fileTag != null) {
			fDir = new File(fDir, fileTag);
			fDir.mkdir();
		}

		try {
			return File.createTempFile(fileTag != null ? fileTag : "file", suffix, fDir);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
