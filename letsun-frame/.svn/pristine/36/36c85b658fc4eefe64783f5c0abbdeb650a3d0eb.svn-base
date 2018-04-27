package com.letsun.frame.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsun.frame.core.common.utils.SslTools;
import com.letsun.frame.core.common.utils.XmlUtils;
import com.letsun.frame.core.domain.SendRedPack;
import com.letsun.frame.core.service.SendRedPackService;

/**
 * @Desc 发送红包service实现类  
 * @author YY  
 * @date 2018年4月12日
 */
@Service
public class SendRedPackServiceImpl implements SendRedPackService {
	
	Logger logger = LoggerFactory.getLogger(SendRedPackServiceImpl.class);
	
	@Autowired private XmlUtils xmlUtil;
	
	private static String api_send_redpack_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
	/**
	 * @Desc 发送红包 
	 * @param redPack 发红包需要的参数
	 * @param payKey 商户秘密
	 * @param filePath 证书路径
	 * @return
	 * @author YY<2017年11月17日>
	 */
	@Override
	public Map<String, String> sendRedPack(SendRedPack redPack,String payKey,String filePath){
		Map<String, String> result = new HashMap<String, String>(8);
		String  response = null;
		try {
			String sign = createSignature(redPack,payKey);
	        redPack.setSign(sign);
			xmlUtil.xstream().alias("xml", redPack.getClass());
		    String xml = xmlUtil.xstream().toXML(redPack);
		    response = SslTools.sendSSLPost(redPack.getMch_id(),filePath,api_send_redpack_url, xml);
		    result = xmlUtil.parseXml(response);
		} catch (Exception e) {
			logger.error("响应结果："+response);
			e.printStackTrace();
			result.put("errcode", "-1");
			String errormsg = "调用发红包API发生错误 ："+ e.getMessage();
			if(StringUtils.isNotBlank(response)){
				errormsg += ",响应结果："+response;
			}
			result.put("mesage",errormsg);
		}
		return result;
	}
	
	/**
	 * @Desc 创建签名 
	 * @param redPack
	 * @param payKey 商户秘钥
	 * @return
	 * @author YY<2017年11月17日>
	 */
	private String createSignature(SendRedPack redPack,String payKey){
        StringBuffer sign = new StringBuffer();
        sign.append("act_name=").append(redPack.getAct_name().trim());
        sign.append("&client_ip=").append(redPack.getClient_ip().trim());
        sign.append("&mch_billno=").append(redPack.getMch_billno().trim());
        sign.append("&mch_id=").append(redPack.getMch_id().trim());
        sign.append("&nonce_str=").append(redPack.getNonce_str().trim());
        sign.append("&re_openid=").append(redPack.getRe_openid().trim());
        sign.append("&remark=").append(redPack.getRemark().trim());
        sign.append("&send_name=").append(redPack.getSend_name().trim());
        sign.append("&total_amount=").append(redPack.getTotal_amount().trim());
        sign.append("&total_num=").append(redPack.getTotal_num().trim());
        sign.append("&wishing=").append(redPack.getWishing().trim());
        sign.append("&wxappid=").append(redPack.getWxappid().trim());
        sign.append("&key=").append(payKey.trim());
        return DigestUtils.md5Hex(sign.toString().trim()).toUpperCase();
    }
}
