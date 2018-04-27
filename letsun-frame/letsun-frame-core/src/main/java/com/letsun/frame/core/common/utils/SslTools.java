package com.letsun.frame.core.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc SSL请求工具类
 * @author YY<2017年11月9日>
 */
public class SslTools {

	private static Logger logger = LoggerFactory.getLogger(SslTools.class);

	/**
	 * 忽视证书HostName
	 */
	private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
		@Override
		public boolean verify(String s, SSLSession sslsession) {
			logger.warn("WARNING: Hostname is not matched for cert.");
			return true;
		}
	};

	/**
	 * Ignore Certification
	 */
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {
		private X509Certificate[] certificates;
		
		@Override
		public void checkClientTrusted(X509Certificate certificates[], String authType) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = certificates;
			}
		}
		
		@Override
		public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = ax509certificate;
			}
		}
		
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[0];
		}
	};
	
	/**
	 * @Desc SSL GET请求 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 * @author YY<2017年11月9日>
	 */
	public static String sendSSLGet(String urlStr) throws Exception {
		String repString = null;
		InputStream is = null;
		HttpsURLConnection connection = null;
		try {

			URL url = new URL(urlStr);
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);
			if (connection.getResponseCode() != 200) {

			}
			is = connection.getInputStream();
			repString = IOUtils.toString(is, "UTF-8");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (null != is) {
				is.close();
				is = null;
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		return repString;
	}
	
	/**
	 * @Desc SSL POST请求 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 * @author YY<2017年11月9日>
	 */
	public static String sendSSLPost(String urlStr) throws Exception {
		return sendSSLPost(urlStr, null);
	}
	
	/**
	 * @Desc SSL POST请求 
	 * @param urlStr
	 * @param postData
	 * @return
	 * @throws Exception
	 * @author YY<2017年11月9日>
	 */
	public static String sendSSLPost(String urlStr, String postData) throws Exception {
		String repString = null;
		InputStream is = null;
		HttpsURLConnection connection = null;
		try {

			URL url = new URL(urlStr);
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("content-type", "text/json");
			if(StringUtils.isNotEmpty(postData)){
				byte[] bytes = postData.getBytes("UTF-8");
				connection.setRequestProperty("content-length", String.valueOf(bytes.length));
				OutputStream out = connection.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
			}
			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);
			if (connection.getResponseCode() != 200) {

			}
			is = connection.getInputStream();
			repString = IOUtils.toString(is, "UTF-8");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (null != is) {
				is.close();
				is = null;
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		return repString;
	}
	
	/**
	 * @Desc SSL POST请求  带证书
	 * @param mchId 商户号
	 * @param path 证书路径
	 * @param url  请求路径
	 * @param data 请求参数
	 * @return
	 * @throws Exception
	 * @author YY<2017年11月9日>
	 */
	public static String sendSSLPost(String mchId,String path,String url,String data) throws Exception{
        StringBuffer message = new StringBuffer();
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(path));
            keyStore.load(instream, mchId.toCharArray());
            // 相信自己的CA和所有自签名的证书  
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            // 只允许使用TLSv1协议  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            
            HttpPost httpost = new HttpPost(url);
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message.toString();
    }
	
	/**
	 * @Desc 上传文件到微信服务器
	 * @param urlString
	 * @param filePath
	 * @param formDataName
	 * @return
	 * @throws Exception
	 * @author YY<2017年11月9日>
	 */
	public static String sendSSLMutiPartFormData(String urlStr, String filePath, String formDataName) throws Exception {
		String repString = null;
		InputStream is = null;
		OutputStream out = null;
		HttpsURLConnection connection = null;
		final String bounDaryStr = "" + System.currentTimeMillis();
		final String bounDary = "--" + bounDaryStr + "\r\n";
		try {
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				String errorMsg = "文件[" + filePath + "]不存在。无法上传。";
				logger.error(errorMsg);
				throw new Exception(errorMsg);
			}
			URL url = new URL(urlStr);
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			// 设置请求头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");

			connection.setRequestProperty("Content-type", "multipart/form-data;boundary=" + bounDaryStr);
			StringBuilder sb = new StringBuilder();
			sb.append(bounDary);
			sb.append("Content-Disposition: form-data;name=\"" + formDataName + "\";filename=\"" + file.getName()+ "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			out = new DataOutputStream(connection.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = bis.read(bufferOut, 0, 1024)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			bis.close();
			// 定义最后数据分隔线
			byte[] foot = ("\r\n--" + bounDaryStr + "--\r\n").getBytes("utf-8");
			out.write(foot);
			out.flush();
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);
			is = connection.getInputStream();
			repString = IOUtils.toString(is, "UTF-8");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (null != is) {
				is.close();
				is = null;
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		return repString;
	}

	/**
	 * @Desc GET方式下载文件 
	 * @param urlStr
	 * @return
	 * @author YY<2017年11月9日>
	 */
	public static Map<String, Object> sendSSLGetDownloadMedia(String urlStr) {
		String fileName = null;
		byte[] repData = null;
		InputStream is = null;
		Map<String, Object> resultInfo = null;
		HttpsURLConnection connection = null;
		try {

			URL url = new URL(urlStr);
			/*
			 * use ignore host name verifier
			 */
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);

			/**
			 * 从以下头部数据解析出文件名 Content-disposition: attachment;
			 * filename="MEDIA_ID.jpg"
			 */
			String contentDisposition = connection.getHeaderField("Content-disposition");
			if (contentDisposition != null) {
				String[] contentDispositionArray = contentDisposition.split(";");
				for (String content : contentDispositionArray) {
					if (content.contains("filename")) {
						String[] contentArry = content.split("=");
						fileName = contentArry[1];
						fileName = fileName.replaceAll("\"", "");
					}
				}
			}
			is = connection.getInputStream();
			repData = IOUtils.toByteArray(is);
			resultInfo = new HashMap<String, Object>(8);
			resultInfo.put("fileName", fileName);
			resultInfo.put("data", repData);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		return resultInfo;
	}

	/**
	 * @Desc POST方式下载文件 
	 * @param urlString
	 * @param postData
	 * @return
	 * @author YY<2017年11月9日>
	 */
	public static Map<String, Object> sendSSLPostDownloadMedia(String urlString, String postData) {
		String fileName = null;
		byte[] repData = null;
		InputStream is = null;
		Map<String, Object> resultInfo = null;
		HttpsURLConnection connection = null;
		try {
			URL url = new URL(urlString);
			/*
			 * use ignore host name verifier
			 */
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("content-type", "text/json");
			connection.setRequestProperty("content-length", String.valueOf(postData.getBytes().length));
			connection.getOutputStream().write(postData.getBytes("utf-8"));
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);

			/**
			 * 从以下头部数据解析出文件名 Content-disposition: attachment;
			 * filename="MEDIA_ID.jpg"
			 */
			String contentDisposition = connection.getHeaderField("Content-disposition");
			String[] contentDispositionArray = contentDisposition.split(";");
			for (String content : contentDispositionArray) {
				if (content.contains("filename")) {
					String[] contentArry = content.split("=");
					fileName = contentArry[1];
					fileName = fileName.replaceAll("\"", "");
				}
			}
			is = connection.getInputStream();
			repData = IOUtils.toByteArray(is);
			resultInfo = new HashMap<String, Object>(8);
			resultInfo.put("fileName", fileName);
			resultInfo.put("data", repData);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		return resultInfo;
	}

}