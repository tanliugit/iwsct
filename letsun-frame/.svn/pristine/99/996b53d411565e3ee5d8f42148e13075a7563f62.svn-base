package com.letsun.frame.core.common.utils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * @Desc  MD5加密工具类  
 * @author YY  
 * @date 2018年4月12日
 */
public class Md5Utils {

	private static final String [] HEX_DIGITS = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * @Desc 字节数组转字符串  
	 * @param b
	 * @return
	 * @author YY<2018年4月12日>
	 */
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++){
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	/**
	 * @Desc 字节转字符串   
	 * @param b
	 * @return
	 * @author YY<2018年4月12日>
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0){
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}
	
	/**
	 * @Desc MD5加密  
	 * @param origin
	 * @param charsetname
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public static String md5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)){
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			}else{
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}

	/**
	 * @Desc 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。  
	 * @param packageParams
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String sign = md5Encode(sb.toString(),  "UTF-8").toUpperCase();
		return sign;

	}
	
	/**
	 * @Desc 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。  
	 * @param packageParams
	 * @param key
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@SuppressWarnings("rawtypes")
	public static String createSignAddKey(SortedMap<String, String> packageParams,String key) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		//拼接API密钥
		sb.append("key=" + key);
		String sign = md5Encode(sb.toString(),  "UTF-8").toUpperCase();
		return sign;
	}
}
