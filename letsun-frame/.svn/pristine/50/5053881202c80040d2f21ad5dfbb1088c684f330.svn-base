package com.letsun.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * @Desc ERP工程生成入口函数  
 * @author YY  
 * @date 2018年4月12日
 */
public class ProjectErpMain {

	private static final String PROJECT_NAME = "erp";

	public static void main(String[] args) throws IOException {
		String templatePath = System.getProperty("user.dir") + "\\templateErpProject";
		String outPath = "E:\\temp\\erpProject\\";
		File outFile = new File(outPath);
		if (outFile.exists()) {
			outFile.delete();
		}
		copyDir(templatePath, outPath);
		Runtime.getRuntime().exec("cmd.exe /c start " + outPath);

	}
	
	/**
	 * 递归复制目录
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyDir(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		String[] filePath = oldFile.list();

		File newFile = new File(newPath);
		if (!newFile.exists()) {
			newFile.mkdir();
		}
		for (String oldFilepath : filePath) {
			String newFilePath = oldFilepath;
			if (oldFilepath.indexOf("${project}") != -1) {
				newFilePath = oldFilepath.replace("${project}", PROJECT_NAME);
			}
			if ((new File(oldPath + File.separator + oldFilepath)).isDirectory()) {
				copyDir(oldPath + File.separator + oldFilepath, newPath + File.separator + newFilePath);
			}
			
			//如果是文件
			File myFile = new File(oldPath + File.separator + oldFilepath);
			if (myFile.isFile()) {
				
				String srcFile = oldPath + File.separator + oldFilepath;
				String destFile =  newPath + File.separator + newFilePath;
				//xml和java才需要替换
				String extension = FilenameUtils.getExtension(myFile.getName());
				if("xml".equals(extension) || "java".equals(extension)){
					copyFile(srcFile,destFile);
				}else{
					FileUtils.copyFile(new File(srcFile), new File(destFile));
				}
				
			}
		}
	}
	
	/**
	 * 复制文件
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyFile(String oldPath, String newPath) throws IOException {
		// filePath 要读取的文件 savePath 要写入的文件
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// 以下读取和写入都转成UTF-8 防止乱码
			br = new BufferedReader(new InputStreamReader(new FileInputStream(oldPath), "UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newPath), "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.indexOf("${project}") != -1) {
					line = line.replace("${project}", PROJECT_NAME);
				}
				bw.write(line+"\r\n");
			}
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null){
					br.close();
				}
				if (bw != null){
					bw.close();
				}
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
