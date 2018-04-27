package com.letsun.iwsct.itface.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片压缩  旋转 处理工具类
 * @author wayne
 * @createtime 2015-8-18
 */
public class RotateImage {

	@SuppressWarnings("finally")
	public static BufferedImage Rotate(String imgFilePath,Image src, int angel) throws ImageFormatException, IOException {
		BufferedImage res = null;
		try {
			int src_width = src.getWidth(null);
			int src_height = src.getHeight(null);
			
			// 根据缩放比率大的进行缩放控制
			int newWidth = src_width;
			int newHeight = src_height;
			
			// 为等比缩放计算输出的图片宽度及高度
			if(src_width>640){
				double rate = ((double) src_width)/640;
				// 根据缩放比率大的进行缩放控制
				newWidth = (int) (src_width / rate);
				newHeight = (int) (src_height/ rate);
				
				File targetFile = new File(imgFilePath);
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(targetFile);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				
				src_width = tag.getWidth(null);
				src_height = tag.getHeight(null);
				src=tag;
				
				out.close();
			}
			
			//判断是否旋转图片
	    	if(angel>0){
//	    		src_width = newWidth;
//				src_height = newWidth;
				
	    		//图片旋转处理
				Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
						src_width, src_height)), angel);
	
				res = new BufferedImage(rect_des.width, rect_des.height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = res.createGraphics();
				// transform
				g2.translate((rect_des.width - src_width) / 2,
						(rect_des.height - src_height) / 2);
				g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
	
				//g2.drawImage(src, 0, 0, newWidth,newHeight, 0, 0, src_width, src_height, null);// 长和宽缩小10倍
				g2.drawImage(src, null, null);
				g2.dispose();
	    	}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			return res;
		}
	}

	public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angel >= 90) {
			if(angel / 90 % 2 == 1){
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}
	
	
	/**  
     * 截取一个图像的中央区域  
     * @param image 图像File  
     * @param w 需要截图的宽度  
     * @param h 需要截图的高度  
     * @return 返回一个  
     * @throws IOException  
     */  
    public static void cutImage(File image, int w, int h,String disUrl) throws IOException {   
           
        // 判断参数是否合法   
        if (null == image || 0 == w || 0 == h) {   
            new Exception ("哎呀，截图出错！！！");   
        }  
        
        InputStream inputStream = new FileInputStream(image);   
        // 用ImageIO读取字节流   
        BufferedImage bufferedImage = ImageIO.read(inputStream);   
        BufferedImage distin = null;   
        // 返回源图片的宽度。   
        int srcW = bufferedImage.getWidth();   
        // 返回源图片的高度。   
        int srcH = bufferedImage.getHeight();   
        int x = 0, y = 0;   
        // 使截图区域居中   
        x = srcW / 2 - w / 2;   
        y = srcH / 2 - h / 2;   
        srcW = srcW / 2 + w / 2;   
        srcH = srcH / 2 + h / 2;   
        // 生成图片   
        distin = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);   
        Graphics g = distin.getGraphics();   
        g.drawImage(bufferedImage, 0, 0, w, h, x, y, srcW, srcH, null);   
        ImageIO.write(distin, "jpg", new File(disUrl));
        
    } 
    
	/**  
     * 截取一个图像的中央区域    当前图片的宽高截取
     * @param image 图像File  
     * @return 返回一个  
     * @throws IOException  
     */  
    public static void cutImage(File image,String disUrl) throws IOException {   
           
        // 判断参数是否合法   
        if (null == image) {   
            new Exception ("哎呀，参数出错！！！");   
        }   
        InputStream inputStream = new FileInputStream(image);   
        // 用ImageIO读取字节流   
        BufferedImage bufferedImage = ImageIO.read(inputStream);   
        // 返回源图片的宽度。   
        int srcW = bufferedImage.getWidth();   
        // 返回源图片的高度。   
        int srcH = bufferedImage.getHeight();   
        int x = 0, y = 0;   
        int xx = 0, yy = 0;  
        int disw=0;
        // 使截图区域居中 
        if(srcW>=srcH){
        	x=(srcW-srcH)/2;
        	xx=x+srcH;
        	y=0;
        	disw=yy=srcH;
        }else{
        	x=0;
        	disw=xx=srcW;
        	y=(srcH-srcW)/2;
        	yy=y+srcW;
        }
//        x = srcW / 2 - w / 2;   
//        y = srcH / 2 - h / 2;   
//        srcW = srcW / 2 + w / 2;   
//        srcH = srcH / 2 + h / 2;   
        // 生成图片   
        BufferedImage distin = new BufferedImage(disw, disw, BufferedImage.TYPE_INT_RGB);   
        Graphics g = distin.getGraphics();   
        g.drawImage(bufferedImage, 0,0,disw,disw,x, y, xx, yy, null);   
        ImageIO.write(distin, "jpg", new File(disUrl));   //"D:\\pic\\33.jpg"
    }   
    
    public static void main(String[] args) throws Exception {   
        File file = new File("c:\\1.jpg");   
        cutImage(file,"c:\\1.jpg");
//        cutImage(file,200,200,"c:\\3.jpg");  
        
//        readUsingImageReader("c://1.jpg", "c://4.jpg", 227, 163);
    }   
}
