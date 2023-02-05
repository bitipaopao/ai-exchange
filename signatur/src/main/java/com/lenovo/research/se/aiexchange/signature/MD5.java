package com.lenovo.research.se.aiexchange.signature;

import java.io.UnsupportedEncodingException;

/**
 * MD5加密
 */
public class MD5 {
	
	private static final String UTF8_CHART_SET = "UTF-8";

	public static String md5(String str, String chartSet) {
		String md5 = null;
		try {
			md5 = DigestUtils.md5DigestAsHex(str.getBytes(chartSet));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return md5;
	}


	public static String md5Res(String str, String chartSet) {
		String md5 = null;
		try {
			md5 = DigestUtils.md5DigestAsHex(str.getBytes(chartSet));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return md5;
	}

	public static void main(String[] args) {
		String md5str = MD5.md5("litianyou", UTF8_CHART_SET);
		String md5str2 = MD5.md5Res("litianyou", UTF8_CHART_SET);

		System.out.println(md5str);
		System.out.println(md5str2);
	}
}
