package com.letsun.frame.core.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @Desc 阿里云发短信工具类  
 * @author YY  
 * @date 2018年4月12日
 */
public class Alidayu {

	public static final String SMS_FREE_SING_NAME = "千店万众";
	/**
	 * 验证码短信通知
	 */
	public static final String SMS_TEMPLATE_CODE = "SMS_78730010";
	/**
	 * 审核通过通知
	 */
	public static final String SMS_TEMPLATE_CODE1 = "SMS_126610277";

	/**
	 *  产品名称:云通信短信API产品,开发者无需替换
	 */
	public static final String PRODUCT = "Dysmsapi";
	/**
	 *  产品域名,开发者无需替换
	 */
	public static final String DOMAIN = "dysmsapi.aliyuncs.com";

	/**
	 * 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	 */
	public static final String ACCESS_KEY_ID = "LTAIFpKHYjFmxtCP";
	/**
	 * 秘钥
	 */
	public static final String ACCESS_KEY_SECRET = "rGIQdtQri37TSdTKykSxNwWPVzB0DG";
	
	/**
	 * @Desc 发动短息验证码
	 * @param phone
	 * @param code
	 * @return
	 * @throws ClientException
	 * @author YY<2018年4月12日>
	 */
	public static SendSmsResponse sendSms(String phone, String code) throws ClientException {

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(SMS_FREE_SING_NAME);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(SMS_TEMPLATE_CODE);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"code\":\"" + code + "\"}");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

		return sendSmsResponse;
	}
	
	
	/**
	 * @Desc 审核通过通知
	 * 模板：您的${mtname}申请已于${submittime}审批通过，特此通知。
	 * @param phone 手机号码
	 * @param mtname 
	 * @param submittime
	 * @return
	 * @throws ClientException
	 */
	public static SendSmsResponse sendSms1(String phone,String mtname, String submittime) throws ClientException {

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(SMS_FREE_SING_NAME);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(SMS_TEMPLATE_CODE1);
		// 可选:您的${mtname}申请已于${submittime}审批通过，特此通知。
		request.setTemplateParam("{\"mtname\":\"" + mtname + "\",\"submittime\":\""+submittime+"\"}");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

		return sendSmsResponse;
	}
	
	
	/**
	 * @Desc 审核通过通知
	 * 模板：您的${mtname}申请已于${submittime}审批通过，特此通知。
	 * @param phone
	 * @param mtname
	 * @param submittime
	 * @return
	 */
	public static Map<String, Object> sendMessage1(String phone,String mtname, String submittime) {
		Map<String, Object> result = new HashMap<String, Object>(10);
		try {
			SendSmsResponse sendSmsResponse =sendSms1(phone, mtname,submittime);
			if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
				if ("OK".equals(sendSmsResponse.getMessage())) {
					result.put("code", "1");
					result.put("message", sendSmsResponse.getMessage()); 
					return result;
				}
			}
			result.put("code", "-1");
			result.put("message", sendSmsResponse.getMessage());
		} catch (ClientException e) {
			result.put("code", "-1");
			result.put("message", e.getMessage());
		}
		return result;
	}

	/**
	 * @Desc 调用阿里云API发送短息
	 * @param phone
	 * @return
	 * @author YY<2017年11月28日>
	 */
	public static Map<String, Object> sendMessage(String phone) {
		Map<String, Object> result = new HashMap<String, Object>(10);
		int random = new Random().nextInt(9999 - 1000 + 1) + 1000;
		String validateCode = String.valueOf(random);
		try {
			SendSmsResponse sendSmsResponse =sendSms(phone, validateCode);
			if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
				if ("OK".equals(sendSmsResponse.getMessage())) {
					result.put("code", "1");
					result.put("message", sendSmsResponse.getMessage());
					result.put("validateCode", validateCode);
					return result;
				}
			}
			result.put("code", "-1");
			result.put("message", sendSmsResponse.getMessage());
		} catch (ClientException e) {
			result.put("code", "-1");
			result.put("message", e.getMessage());
		}
		return result;
	}
}
