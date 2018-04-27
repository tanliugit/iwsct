package com.letsun.frame.core.common.utils;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

/**
 * @Desc 腾讯云文件上传工具类  
 * @author YY  
 * @date 2018年4月12日
 */
public class CosClient {

	/**
	 * 这些属性可以通过cos控制台获取(https://console.qcloud.com/cos)
	 */
	private final static String APP_ID = "1254182596";
	private final static String SECRET_ID = "AKIDswv6C6FKfoohVwoOJ2cKRNzTlrlS4GNI";
	private final static String SECRET_KEY = "YyCPUN5bx882oOmxwjKjvwpWVKxPfkd2";
	private final static String APP_URL = "http://biaoji-1254182596.cosgz.myqcloud.com";

	/**
	 * @Desc 获取客服端对象
	 * @return
	 * @author YY<2017年11月21日>
	 */
	private static COSClient getCOSClient() {
		// 设置秘钥
		COSCredentials cred = new BasicCOSCredentials(APP_ID, SECRET_ID, SECRET_KEY);
		// 设置区域, 这里设置为广州
		// (COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224)
		ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
		// 生成cos客户端对象
		return new COSClient(cred, clientConfig);
	}

	/**
	 * @Desc 腾讯云文件上传
	 * @param file
	 * @param bucketName
	 * @param projectName
	 * @return
	 * @author YY<2017年11月21日>
	 */
	public static String fileUpload(MultipartFile file, String bucketName, String projectName) {
		COSClient cosClient = null;
		try {
			// 生成cos客户端对象
			cosClient = getCOSClient();
			// 上传文件(推荐), 支持根据文件的大小自动选择单文件上传或者分块上传,同时支持同时上传不同的文件
			TransferManager transferManager = new TransferManager(cosClient);
			String filePath = DateUtils.formatDate(new Date(), "yyyyMMdd");
			String fileName = UUID.randomUUID().toString().replace("-", "");
			String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
			String key = "/" + projectName + "/" + filePath + "/" + fileName + "." + suffix;
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentEncoding("UTF-8");
			metadata.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			metadata.setContentLength(file.getSize());
			Upload upload = transferManager.upload(bucketName, key, file.getInputStream(), metadata);
			// transfermanger upload是异步上传 等待传输结束
			upload.waitForCompletion();
			transferManager.shutdownNow();
			return APP_URL + key;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cosClient != null) {
				cosClient.shutdown();
			}
		}
		return null;
	}

	/**
	 * @Desc 腾讯云文件上传
	 * @param file 
	 * @param bucketName
	 * @param projectName
	 * @return
	 * @author YY<2017年11月21日>
	 */
	public static String fileUpload(File file, String bucketName, String projectName) {
		COSClient cosClient = null;
		try {
			// 生成cos客户端对象
			cosClient = getCOSClient();
			// 上传文件(推荐), 支持根据文件的大小自动选择单文件上传或者分块上传,同时支持同时上传不同的文件
			TransferManager transferManager = new TransferManager(cosClient);
			// transfermanger upload是异步上传
			String suffix = FilenameUtils.getExtension(file.getName());
			String filePath = DateUtils.formatDate(new Date(), "yyyyMMdd");
			String fileName = UUID.randomUUID().toString().replace("-", "");
			String key = "/" + projectName + "/" + filePath + "/" + fileName + "." + suffix;
			Upload upload = transferManager.upload(bucketName, key, file);
			// 等待传输结束
			upload.waitForCompletion();
			transferManager.shutdownNow();
			return APP_URL + key;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cosClient != null) {
				cosClient.shutdown();
			}
		}
		return null;
	}

	/**
	 * @Desc 腾讯云文件下载
	 * @param bucketName
	 * @param fileName
	 * @param savePath
	 * @return
	 * @author YY<2017年11月25日>
	 */
	public static File downFile(String bucketName, String fileName) {
		COSClient cosClient = null;
		try {
			// 生成cos客户端对象
			cosClient = getCOSClient();
			if (fileName.indexOf("http://") != -1) {
				fileName = fileName.replace(APP_URL, "");
			}
			String suffix = FilenameUtils.getExtension(fileName);
			final File downFile = File.createTempFile("temp","."+suffix);
			GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
			cosClient.getObject(getObjectRequest, downFile);
			return downFile;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cosClient != null) {
				cosClient.shutdown();
			}
		}
		return null;
	}

	/**
	 * @Desc 腾讯云文件删除
	 * @param bucketName
	 * @param fileName
	 * @return
	 * @author YY<2017年11月21日>
	 */
	public static Boolean deleteFile(String bucketName, String fileName) {
		COSClient cosClient = null;
		try {

			if (fileName.indexOf("http://") > 0) {
				fileName = fileName.replace(APP_URL, "");
			}
			// 生成cos客户端对象
			cosClient = getCOSClient();
			// 删除刚上传的文件
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
			cosClient.deleteObject(deleteObjectRequest);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cosClient != null) {
				cosClient.shutdown();
			}
		}
		return false;
	}
}
