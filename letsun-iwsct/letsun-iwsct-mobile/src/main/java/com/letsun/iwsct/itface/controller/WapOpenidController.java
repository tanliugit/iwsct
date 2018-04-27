package com.letsun.iwsct.itface.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.letsun.iwsct.itface.common.CUtil;
import com.letsun.iwsct.itface.common.CommonUtil;
import com.letsun.iwsct.itface.common.FileUtil;
import com.letsun.iwsct.itface.common.RotateImage;
import com.letsun.iwsct.itface.common.ServiceConstants;

/** 2015-1-4 add by wayne
 *  珠宝业务  需要获取openid
 **/
@Controller
@RequestMapping("/w/Openid")
public class WapOpenidController {
	Log log = LogFactory.getLog(getClass());
	
	/*//外网访问地址
	private static String SYSURL = ApplicationPro.get("systemUrl");*/
	
	@Value("${SYSURL}")
	private String SYSURL;
	

	
	

	/** 
	 * 2015-1-4 add by wayne
	 * 异步提交地址   上传图片
	 * 返回图片路径
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/uploadfile",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, String>  uploadfile(ModelMap model,
			DefaultMultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", "500");
		map.put("msg", "上传图片失败");
		try{
			//处理上传的产品图片
			String uploadFilePath = "";
			MultipartFile upload = request.getFile("upload");
			String finame=CUtil.genUUID();
			uploadFilePath = CUtil.newCopypic(upload,finame,request,0L,ServiceConstants.UPLOAD_PIC,0,0);
			
			//处理图片旋转   压缩
			if(uploadFilePath!=null){
				if(upload.getSize() <= 0)return null;
				String sourceFilename = upload.getOriginalFilename();
				String suffix = FileUtil.getLowerCaseSuffix(sourceFilename);
				String filename = finame + suffix;
				
				String filePath = request.getSession().getServletContext().getRealPath(ServiceConstants.UPLOAD_PIC);
				filePath += File.separator + 0;
				
				if (CommonUtil.isNotNull(filename)) {
					filePath += File.separator + filename;
				}
				
				//处理图片 压缩 和  旋转
				proExifDirectory(filePath);
				
				/** 2016-6-16 add by wayne */
				//处理图片裁剪 ：以图片中心位置按长宽取最小值的正方形
				if(request.getParameter("isCutImage")!=null && request.getParameter("isCutImage").equals("1")){
					File imgFile = new File(filePath);
					RotateImage.cutImage(imgFile, filePath);
				}
			}
			
			map.put("status", "200");
			map.put("msg", "上传图片成功");
			map.put("result", uploadFilePath);
			return map;

		}catch (Exception e) {
			log.error("上传图片提交出错："+e.getMessage());
		}finally{
			return map;
		}
	}

	
//	 if (!_directory.containsTag(ExifDirectory.TAG_ORIENTATION)) return null;
//  int orientation = _directory.getInt(ExifDirectory.TAG_ORIENTATION);
//  switch (orientation) {
//      case 1: return "Top, left side (Horizontal / normal)";  --0
//      case 2: return "Top, right side (Mirror horizontal)";
//      case 3: return "Bottom, right side (Rotate 180)"; ----180
//      case 4: return "Bottom, left side (Mirror vertical)";
//      case 5: return "Left side, top (Mirror horizontal and rotate 270 CW)";
//      case 6: return "Right side, top (Rotate 90 CW)";  ---90
//      case 7: return "Right side, bottom (Mirror horizontal and rotate 90 CW)";
//      case 8: return "Left side, bottom (Rotate 270 CW)";  --270
//      default:
//          return String.valueOf(orientation);
//  }
	/** 2015-8-17 add by wayne 
	 * 读取图片的EXIF信息 图片旋转 处理
	 * */
	@SuppressWarnings("finally")
	public boolean proExifDirectory(String imgFilePath) throws Exception {
			
		boolean isPro = false;
		
    	File imgFile = new File(imgFilePath);
    	InputStream isImgFile = new FileInputStream(imgFile);
    	
	    try 
	    {
	    	//核心对象操作对象
	    	Metadata metadata = ImageMetadataReader.readMetadata(isImgFile);
	    	//metadata对象会根据不同类型的图片，生成不同的Directory
	    	//除了可以遍历所有的Directory来获取对象的tag信息，如果你只是需要其中部分tag信息，可以获取其中指定类型的Directory，如：ExifSubIFDDirectory
	    	Directory dr = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
	      
	    	int angel=0;
       	 	// 通过读取该图片的EXIF信息   图片旋转返回值
	    	if (dr.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
	    		System.out.println("->Pic TAG_ORIENTATION is " +dr.getInt(ExifIFD0Directory.TAG_ORIENTATION));
	    		
	    		switch (dr.getInt(ExifIFD0Directory.TAG_ORIENTATION)) {
		    		case 1: 
			       		angel = 0;
			       		break;
			         case 2: 
			        	 angel = 0;
			        	 break;
			         case 3: 
			        	 angel = 180;
			        	 break;
			         case 4: 
			        	 angel = 180;
			        	 break;
			         case 5: 
			        	 angel = 90;
			        	 break;
			         case 6: 
			        	 angel = 90;
			        	 break;
			         case 7: 
			        	 angel = 270;
			        	 break;
			         case 8: 
			        	 angel = 270;
			        	 break;
			         default:
			        	 angel = 0;
	    		}
	    	}
         
	    	System.out.println("angel="+angel);
	    	//压缩 旋转图片
 		    BufferedImage src = ImageIO.read(new File(imgFilePath));
 			BufferedImage des = RotateImage.Rotate(imgFilePath,src, angel);
 			
 			if(des!=null){
 				Assert.assertNotNull(des);
	 			Assert.assertTrue(ImageIO.write(des, "jpg", new File(imgFilePath)));
 			}
	 			
	        
	    } catch (ImageProcessingException e) {
		    	isPro = false;
	    } catch (IOException e) {
	    	isPro = false;
	    }finally{
	    	isImgFile.close();
	    	return isPro;
	    }
	}
}
