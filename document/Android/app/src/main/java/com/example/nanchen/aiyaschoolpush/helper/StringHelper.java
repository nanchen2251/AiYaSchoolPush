package com.example.nanchen.aiyaschoolpush.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * 字符串相关的Helper方法
 * 
 * */
public final class StringHelper {
	public static final String EMPTY = "";
	private final static Pattern pattern_isEmail = Pattern.compile("^[a-z0-9]+[\\w\\-\\.]*@[a-z0-9\\-]+(?:\\.[a-z0-9\\-]+)+$", Pattern.CASE_INSENSITIVE);
	private final static Pattern pattern_isMobile = Pattern.compile("^1[3-9][0-9]{9}$");
	private final static Pattern pattern_isPassword = Pattern.compile(".{6,30}");

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNullOrWhiteSpace(String str) {
		return str == null || str.length() == 0 || str.trim().length() == 0;
	}

	public static boolean isEmail(String str) {
		return pattern_isEmail.matcher(str).matches();
	}

	/**
	 * 判断指定的字符串是否是手机号
	 * */
	public static boolean isMobile(String str) {
		return pattern_isMobile.matcher(str).matches();
	}

	/**
	 * 获取指定字符串中 {@code lastChar} 前面的部分 如果不包含{@code lastChar}则返回原{@code str}
	 * */
	public static String getLeftStringByLastChar(String str, char lastChar) {
		if (str == null)
			return null;
		int index = str.lastIndexOf(lastChar);
		if (index == -1)
			return str;

		return str.substring(0, index);
	}

	/**
	 * 获取指定字符串中 {@code lastChar} 后面的部分 如果不包含{@code lastChar}则返回原{@code str}
	 * */
	public static String getRightStringByLastChar(String str, char lastChar) {
		if (str == null)
			return null;
		int index = str.lastIndexOf(lastChar);
		if (index == -1)
			return str;

		return str.substring(index + 1);
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sbHex = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			if ((b & 0xFF) < 0x10)
				sbHex.append("0");
			sbHex.append(Integer.toHexString(b & 0xFF));
		}
		return sbHex.toString();
	}

	public static String getMD5String(String str) {
		try {
			return getMD5String(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getMD5String(String str, String charsetName) throws UnsupportedEncodingException {
		try {
			return getHashString("MD5", str, charsetName);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("当前环境不支持MD5算法！");
		}
	}

	public static String getSHA1String(String str) {
		try {
			return getSHA1String(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSHA1String(String str, String charsetName) throws UnsupportedEncodingException {
		try {
			return getHashString("SHA-1", str, charsetName);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("当前环境不支持SHA-1算法！");
		}
	}

	public static String getCRC32String(String str) {
		try {
			return getCRC32String(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCRC32String(String str, String charsetName) throws UnsupportedEncodingException {
		try {
			return getHashString("CRC32", str, charsetName);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("当前环境不支持CRC32算法！");
		}
	}

	private static String getHashString(String algorithm, String str, String charsetName) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		if ("CRC32".equals(algorithm)) {
			CRC32 crc = new CRC32();
			crc.update(str.getBytes(charsetName));
			long lVal = crc.getValue();
			return Long.toHexString(lVal).toLowerCase();
		} else {
			byte[] bysHash = MessageDigest.getInstance(algorithm).digest(str.getBytes(charsetName));
			return bytesToHexString(bysHash);
		}
	}

	public static boolean isValidUserName(String str) {
		final int len = str.length();
		boolean allNumber = true;
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if ('0' > c || c > '9') {
				allNumber = false;
				break;
			}
		}
		return allNumber;
	}

	public static boolean isValidPwd(String str) {
		return pattern_isPassword.matcher(str).matches();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * 
	 * @param str
	 * @return
	 * 
	 * @author WLF
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
	}

	/**
	 * 判断字符串不为空
	 * 
	 * 
	 * @param str
	 * @return
	 * 
	 * @author WLF
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 
	 * 
	 * @param str
	 * @return
	 * 
	 * @author WLF
	 */
	public static String trim(String str) {
		return str == null ? EMPTY : str.trim();
	}
}
