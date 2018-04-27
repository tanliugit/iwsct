package com.letsun.frame.core.service;

import java.util.Map;

import com.letsun.frame.core.domain.SendRedPack;

/**
 * @Desc 发送红包service接口  
 * @author YY  
 * @date 2018年4月12日
 */
public interface SendRedPackService{

	/**
	 * 发送红包 
	 * @param redPack 发红包需要的参数
	 * @param payKey 商户秘密
	 * @param filePath 证书路径
	 * @return Map<String, String>
	 * @author YY<2017年11月17日>
	 */
	public Map<String, String> sendRedPack(SendRedPack redPack,String payKey,String filePath);
}
