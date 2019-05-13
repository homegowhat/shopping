package com.shopping.web.bg.bean;

import java.util.Random;

public class StringUtil {
	public static boolean isNotNull(String... strs) {
		for(String s : strs){
			if(s == null){
				return false;
			}
		}
		return true;
	}
	
	public static String strToMD5(String pwd) {
		byte[] source = pwd.getBytes();
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	
	public static String getRandomStr(int size) {
		String[] randomStr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < size; i++) {
			buf.append(randomStr[random.nextInt(36)]);
		}
		return buf.toString();
	}
	
	public static String isNull(String str, String d){
		if(str != null){
			return str;
		}else{
			return d;
		}
	}

	/**
	 * 可用於在串逗號時
	 * @param value 要檢查的值
	 * @param def 如果要檢查的值為null時, 直接回傳的值
	 * @param value2 間隔符號
	 * @return
	 */
	public static String chkNull4AppendMiddleStr(String value, String def, String value2) {
		if(value == null){
			return def;
		}else{
			return new StringBuffer().append(value).append(value2).append(def).toString();
		}
	}

	/**
	 * 可用於網址參數若為null就回傳空，不為空就串key=value回傳
	 * @param value 要檢查的值
	 * @param def  如果要檢查的值為null時回傳的值
	 * @param value2  如果要檢查的值不為null要加上去的值
	 */
	public static String chkNull4AppendFrontStr(String value, String def, String value2) {
		if(value == null){
			return def;
		}else{
			return new StringBuffer(value2).append(value).toString();
		}
	}
	public static String chkNull4AppendAfterStr(String value, String def, String value2) {
		if(value == null){
			return def;
		}else{
			return new StringBuffer(value).append(value2).toString();
		}
	}
}
