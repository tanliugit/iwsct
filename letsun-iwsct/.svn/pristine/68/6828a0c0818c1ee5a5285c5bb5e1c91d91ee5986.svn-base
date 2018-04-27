package com.letsun.iwsct.itface.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 文件操作工具
 * @author PanJianPing
 */
public class FileUtil {
	
	/**
	 * 复制文件
	 * @param destFile 新文件
	 * @param origFileStream 原文件输入流
	 * @throws IOException 
	 */
	public static void copy(File destFile, InputStream origFileStream) throws IOException{
		//保证文件所在目录一定存在
		destFile.getParentFile().mkdirs();
		
		//输入流
		InputStream is = new BufferedInputStream(origFileStream);
		//输出流
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile));
		//每次读取的大小
		byte[] size = new byte[1024];
		//流长度
		int len = 0;
		//循环读取
		while((len = is.read(size)) != -1){
			os.write(size, 0, len);
		}
		os.flush();
		os.close();
		is.close();
	}

	/**
	 * 复制文件
	 * @param destFilePath 新文件路径
	 * @param origFileStream 原文件输入流
	 */
	public static void copy(String destFilePath, InputStream origFileStream)  throws IOException{
		File destFile = new File(destFilePath);
		copy(destFile, origFileStream);
	}
	
	/**
	 * 复制文件
	 * @param destFile 新文件
	 * @param origFile 原文件
	 */
	public static void copy(File destFile, File origFile)  throws IOException{
		//保证文件所在目录一定存在
		destFile.getParentFile().mkdirs();
		
		//输入流
		InputStream is = new BufferedInputStream(new FileInputStream(origFile));
		//输出流
		OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile));
		//每次读取的大小
		byte[] size = new byte[1024];
		//流长度
		int len = 0;
		//循环读取
		while((len = is.read(size)) != -1){
			os.write(size, 0, len);
		}
		os.flush();
		os.close();
		is.close();
	}
	
	/**
	 * 复制文件
	 * @param destFilePath 新文件路径
	 * @param origFile 原文件
	 */
	public static void copy(String destFilePath, File origFile) throws IOException {
		File destFile = new File(destFilePath);
		copy(destFile, origFile);
	}
	
	/**
	 * 复制文件
	 * @param destFilePath 新文件路径
	 * @param origFilePath 原文件路径
	 */
	public static void copy(String destFilePath, String origFilePath)  throws IOException {
		File destFile = new File(destFilePath);
		File origFile = new File(origFilePath);
		copy(destFile, origFile);
	}
	
	/**
	 * 直接删除非空目录
	 * @param dir File对象
	 */
	public static void deleteDirectory(File dir){
		 if(dir == null || !dir.exists() || !dir.isDirectory())
	        return; // 检查参数
	    for (File file : dir.listFiles()) {
	        if (file.isFile())
	            file.delete(); // 删除所有文件
	        else if (file.isDirectory())
	        	deleteDirectory(file); // 递规的方式删除文件夹
	    }
	    dir.delete();// 删除目录本身 
	}
	
	/**
	 * 直接删除非空目录
	 * @param dirPath 要删除的目录的绝对路径
	 */
	public static void deleteDirectory(String dirPath){
		File dir = new File(dirPath);
		deleteDirectory(dir);
	}

	/**
	 * 允许上传的文件类型
	 * @param allowFileTypes
	 * @param origFileName
	 * @return
	 */
	private static boolean isAllowFileType(String[] allowFileTypes, String origFileName) {
		//对文件类型的判断
		String suffix = origFileName.substring(origFileName.lastIndexOf(".")+1).toLowerCase();
		if (CommonUtil.isNotNull(allowFileTypes)) {
			for (String type : allowFileTypes) {
				if (suffix.equals(type.toLowerCase())) {
					return true;
				}
			}
			return false;
		}else{
			//默认所有都可以上传
			return true;
		}
	}
	
	/**
	 * 上传文件(Spring上传）
	 * @param request
	 * @param uploadFilePath 上传保存的路径
	 * @param uploadFileFormName 文件域名
	 * @param allowFileTypes 所允许上传的文件类型（null时为全部）
	 * @throws Exception
	 */
	public static String uploadFile(DefaultMultipartHttpServletRequest request, String uploadFilePath, String uploadFileFormName, String[] allowFileTypes) throws Exception{
		DefaultMultipartHttpServletRequest mRequest = request; //new DefaultMultipartHttpServletRequest(request);
		MultipartFile uFile = mRequest.getFile(uploadFileFormName);
		//判断上传的文件是否有内容
		if (uFile == null || uFile.isEmpty()) {
			return null;
		}
		
		//原文件名
		String origFileName = uFile.getOriginalFilename();
		//是否允许上传
		boolean allowable  = isAllowFileType(allowFileTypes, origFileName);
		
		if (allowable) {
			uploadFilePath += origFileName.substring(origFileName.lastIndexOf("."));
			//开始上传
			InputStream origFileStream = uFile.getInputStream();
			copy(uploadFilePath, origFileStream);
			return origFileName;
		}else{
			throw new Exception("上传文件类型错误");
		}
		
	}

	/**
	 * 上传多个文件(Spring上传）
	 * @param request
	 * @param uploadFilePath 上传保存的路径
	 * @param uploadFileFormName 文件域名
	 * @param allowFileTypes 所允许上传的文件类型（null时为全部）
	 * @throws Exception
	 */
	public static void uploadFiles(HttpServletRequest request, String uploadFilePath, String uploadFileFormName, String[] allowFileTypes) throws Exception{
		DefaultMultipartHttpServletRequest mRequest = new DefaultMultipartHttpServletRequest(request);
		List<MultipartFile> uFiles = mRequest.getFiles(uploadFileFormName);
		
		for (MultipartFile uFile : uFiles) {
			//判断上传的文件是否有内容
			if (uFile == null || uFile.isEmpty()) {
				continue;
			}
			
			//原文件名
			String origFileName = uFile.getOriginalFilename();
			//是否允许上传
			boolean allowable  = isAllowFileType(allowFileTypes, origFileName);
			
			if (allowable) {
				//开始上传
				InputStream origFileStream = uFile.getInputStream();
				copy(uploadFilePath, origFileStream);
			}
		}
	}
	
	/**
	 * 上传文件(Struts2上传)
	 * @param origFile 要上传的文件
	 * @param uploadFilePath 上传保存的路径
	 * @param uploadFileFormName 文件域名
	 * @param allowFileTypes 所允许上传的文件类型（null时为全部）
	 * @throws Exception
	 */
	public static void uploadFile(File origFile, String uploadFilePath, String uploadFileFormName, String[] allowFileTypes) throws Exception{
		if (origFile == null) {
			return;
		}
		
		//原文件名
		String origFileName = origFile.getName();
		//是否允许上传
		boolean allowable  = isAllowFileType(allowFileTypes, origFileName);
		
		if (allowable) {
			//开始上传
			copy(uploadFilePath, origFile);
		}else{
			throw new Exception("上传文件类型错误");
		}
	}

	/**
	 * 上传多个文件(Struts2上传)
	 * @param origFile 要上传的文件
	 * @param uploadFilePath 上传保存的路径
	 * @param uploadFileFormName 文件域名
	 * @param allowFileTypes 所允许上传的文件类型（null时为全部）
	 * @throws Exception
	 */
	public static void uploadFiles(File[] origFiles, String uploadFilePath, String uploadFileFormName, String[] allowFileTypes) throws Exception{
		for (File origFile : origFiles) {
			if (origFile == null) {
				continue;
			}
			
			//原文件名
			String origFileName = origFile.getName();
			//是否允许上传
			boolean allowable  = isAllowFileType(allowFileTypes, origFileName);
			
			if (allowable) {
				//开始上传
				copy(uploadFilePath, origFile);
			}
		}
		
	}
	
	//测试
	public static void main(String[] args) {

	}
	
	/** 得到文件名后缀，即带.号的文件类型 */
	public static String getLowerCaseSuffix(String filename){
		if (CommonUtil.isNotNull(filename)) {
			int position = filename.lastIndexOf(".");
			if (position <= 0) {
				return null;
			} else {
				return filename.substring(position).toLowerCase();
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 对上传的图片进行缩放处理，只存储处理后得到的文件
	 * 如果是等比例缩放，那么处理后的图片的宽度很有可能与指定的新宽度和新高度不一致！
	 * 因此在名片制作流程中上传的图片，都要使用非等比例缩放。
	 * 
	 * @param sourceFile	Spring上传的源文件
	 * @param targetPath	目标全路径
	 * @param width			新宽度
	 * @param height		新高度
	 * @param proportion	是否等比例缩放 true是 false否
	 * @param smooth		是否平滑处理 gif格式时使用
	 */
	public static void compressThenCopy(MultipartFile sourceFile, String targetPath, int width, int height, boolean proportion, boolean smooth) throws Exception{
		
		System.out.println("--------------目标全路径:"+targetPath);
		
		String filename = sourceFile.getOriginalFilename();
		String filetype = getLowerCaseFileType(filename);
		if (CommonUtil.isNotNull(filetype)) {
			File targetFile = new File(targetPath);
			targetFile.getParentFile().mkdirs();
			
			if (width > 0 && height > 0) {
				if ("gif".equalsIgnoreCase(filetype)) {
					GifImage gifImage = GifDecoder.decode(sourceFile.getInputStream());
					int imageWideth = gifImage.getScreenWidth();
					int imageHeight = gifImage.getScreenHeight();
					int changeToWideth = width;
					int changeToHeight = height;
					if (proportion) {
						if (imageWideth > 0 && imageHeight > 0) {
							if (imageWideth / imageHeight >= width / height) {
								if (imageWideth > width) {
									changeToWideth = width;
									changeToHeight = (imageHeight * width) / imageWideth;
								} else {
									changeToWideth = imageWideth;
									changeToHeight = imageHeight;
								}
							} else {
								if (imageHeight > height) {
									changeToHeight = height;
									changeToWideth = (imageWideth * height) / imageHeight;
								} else {
									changeToWideth = imageWideth;
									changeToHeight = imageHeight;
								}
							}
						}
					}
					GifImage resizedGifImage2 = GifTransformer.resize(gifImage, changeToWideth, changeToHeight, smooth);
					GifEncoder.encode(resizedGifImage2, targetFile);
				} else {
					Image image = ImageIO.read(sourceFile.getInputStream());
					if (image.getWidth(null) == -1) {
						throw new Exception("图片格式错误");
					} else {
						int newWidth;
						int newHeight;
						if (proportion) {
							// 为等比缩放计算输出的图片宽度及高度
							double rate1 = ((double) image.getWidth(null)) / (double) width + 0.1;
							double rate2 = ((double) image.getHeight(null)) / (double) height + 0.1;
							// 根据缩放比率大的进行缩放控制
							double rate = rate1 > rate2 ? rate1 : rate2;
							newWidth = (int) (((double) image.getWidth(null)) / rate);
							newHeight = (int) (((double) image.getHeight(null)) / rate);
						} else {
							newWidth = width;
							newHeight = height;
						}
						BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

						tag.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
						FileOutputStream out = new FileOutputStream(targetFile);
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
						encoder.encode(tag);
						out.close();
					}
				}
			} else {
				BufferedInputStream in = new BufferedInputStream(sourceFile.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile));
				byte[] data = new byte[1024];
				while (in.read(data) != -1) {
					out.write(data);
				}
				out.flush();
				out.close();
				in.close();
			}
		} else {
			throw new Exception("上传图片文件类型错误");
		}
	}
	
	
	/** 得到小写的文件类型,不带.号 */
	public static String getLowerCaseFileType(String filename) {
		if (CommonUtil.isNotNull(filename)) {
			int position = filename.lastIndexOf(".");
			if (position <= 0) {
				return null;
			} else {
				return filename.substring(position + 1).toLowerCase();
			}
		} else {
			return null;
		}
	}


	/** 2014-10-11 add by wayne
	 * 等比缩放图片
	 * @param imageFile
	 * @param sourcePath
	 * @param width
	 * @param height
	 * @param proportion
	 * @param smooth
	 * @throws Exception
	 */
	public static List<Object> compressThenCopyNew(File imageFile, String sourcePath, 
			int width, int height, 
			boolean proportion, boolean smooth,
			boolean type,boolean isCompress) throws Exception{
		
		 String filetype = getLowerCaseFileType(sourcePath.toString());
		 List<Object> size = new ArrayList<Object>();	
		 if (CommonUtil.isNotNull(filetype)) {
				File targetFile = new File(sourcePath);
				targetFile.getParentFile().mkdirs();
				
				if (width > 0 && height > 0){
					
					//页面最大显示的宽、高
					int kWidth = 320;
					int kHeight = 200;
					
					if ("gif".equalsIgnoreCase(filetype)) {
						GifImage gifImage = GifDecoder.decode(imageFile);
						int imageWideth = gifImage.getScreenWidth();
						int imageHeight = gifImage.getScreenHeight();
						int changeToWideth = kWidth;
						int changeToHeight = kHeight;
						if (proportion){
							if(type){
								if (imageWideth > 0 && imageHeight > 0) {
									if (imageWideth / imageHeight >= kWidth / kHeight) {
										if (imageWideth > kWidth) {
											changeToWideth = kWidth;
											changeToHeight = (imageHeight * kWidth) / imageWideth;
										} else {
											changeToWideth = imageWideth;
											changeToHeight = imageHeight;
										}
									} else {
										if (imageHeight > kHeight) {
											changeToHeight = kHeight;
											changeToWideth = (imageWideth * kHeight) / imageHeight;
										} else {
											changeToWideth = imageWideth;
											changeToHeight = imageHeight;
										}
									}
								}
							}else{
								changeToWideth = width;
								changeToHeight = height;
							}						
						}
						
						if(isCompress){
							size.add(changeToWideth);
							size.add(changeToHeight);
							size.add(imageWideth);
							size.add(imageHeight);
							
							GifImage resizedGifImage2 = GifTransformer.resize(gifImage, imageWideth, imageHeight, smooth);
							GifEncoder.encode(resizedGifImage2, targetFile);
							return size;
							
						}else{
							GifImage resizedGifImage2 = GifTransformer.resize(gifImage, changeToWideth, changeToHeight, smooth);
							GifEncoder.encode(resizedGifImage2, targetFile);
							return null;
						}						
					} else {
						Image image = ImageIO.read(imageFile);
						if (image.getWidth(null) == -1) {
							throw new Exception("图片格式错误");
						} else {
							int newWidth = image.getWidth(null);
							int newHeight = image.getHeight(null);
							
							int orWidth = image.getWidth(null);
							int orHeight = image.getHeight(null);
							double rate =0;
							if (proportion) {
								if(type){
									if(image.getWidth(null)>kWidth || image.getHeight(null)>kHeight){
										// 为等比缩放计算输出的图片宽度及高度
										double rate1 = ((double) image.getWidth(null)) / (double) kWidth + 0.1;
										double rate2 = ((double) image.getHeight(null)) / (double) kHeight + 0.1;
										// 根据缩放比率大的进行缩放控制
										rate = rate1 > rate2 ? rate1 : rate2;
										newWidth = (int) (((double) image.getWidth(null)) / rate);
										newHeight = (int) (((double) image.getHeight(null)) / rate);
									}
								}else{
									newWidth = width;
									newHeight = height;
								}
								
							}
							if(isCompress){
								size.add(newWidth);
								size.add(newHeight);
								size.add(orWidth);
								size.add(orHeight);
								size.add(rate);
								
								BufferedImage tag = new BufferedImage((int) image.getWidth(null), (int) image.getHeight(null), BufferedImage.TYPE_INT_RGB);	
								tag.getGraphics().drawImage(image.getScaledInstance(image.getWidth(null), image.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
								FileOutputStream out = new FileOutputStream(targetFile);
								JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
								encoder.encode(tag);
								out.close();
								return size;
							}else{
								BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
								
								tag.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
								FileOutputStream out = new FileOutputStream(targetFile);
								JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
								encoder.encode(tag);
								out.close();
								return null;
							}
						}
					}
				}	                    		
			}else {
				throw new Exception("上传图片文件类型错误");
			}
		return null;
	}
}
