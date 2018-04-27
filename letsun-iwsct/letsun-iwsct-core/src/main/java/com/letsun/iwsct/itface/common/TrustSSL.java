package com.letsun.iwsct.itface.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrustSSL {
	private static Logger log = LoggerFactory.getLogger(TrustSSL.class);
	
    private static class TrustAnyTrustManager implements X509TrustManager {
    
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
    
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    
    /** 发送请求 
     * @throws Exception */
	public static String sendRequest(String url, String requestMsg) {
        
		try{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) console.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			connection.connect();
			
//			URL uri = new URL(url);
//			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			
			OutputStream out = connection.getOutputStream();
			out.write(requestMsg.getBytes());
			out.flush();
			out.close();
			
			InputStream in = connection.getInputStream();
			StringBuffer inBuf = new StringBuffer();
			int bufLen = 1024;
			int actual = 0;
			byte[] rcvBuf = new byte[bufLen]; 
			while (actual != -1) {
			   actual = in.read(rcvBuf, 0, bufLen);
			   if(actual > 0){
				   inBuf.append(new String(rcvBuf, 0, actual, "utf-8"));
	//			   inBuf.append(new String(rcvBuf, 0, actual, "gb2312"));
			   }               
			}			
			in.close();
			String resultMsg = inBuf.toString();
			connection.disconnect();
			return resultMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	   /** 发送请求 
     * @throws Exception */
	public static String sendNewRequest(String url, String requestMsg) {
        
		try{
			SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
			sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) console.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setDoOutput(true);
			connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			connection.connect();
			
//			URL uri = new URL(url);
//			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			System.out.println(requestMsg);
			
			// 当有数据需要提交时
			if (null != requestMsg) {
				OutputStream outputStream = connection.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(requestMsg.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}
			
			InputStream in = connection.getInputStream();
			StringBuffer inBuf = new StringBuffer();
			int bufLen = 1024;
			int actual = 0;
			byte[] rcvBuf = new byte[bufLen]; 
			while (actual != -1) {
			   actual = in.read(rcvBuf, 0, bufLen);
			   if(actual > 0){
				   inBuf.append(new String(rcvBuf, 0, actual, "utf-8"));
	//			   inBuf.append(new String(rcvBuf, 0, actual, "gb2312"));
			   }               
			}			
			in.close();
			in=null;
			String resultMsg = inBuf.toString();
			connection.disconnect();
			return resultMsg;
		} catch (Exception e) {
			log.error("Weixin https request error:{}", e);
			e.printStackTrace();
		}
		return null;
	}
	
}
