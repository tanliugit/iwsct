package com.letsun.iwsct.itface.common;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.letsun.iwsct.itface.domain.Tcorp;
import com.letsun.iwsct.itface.service.TcorpService;

/**
 * 2015-2-4 add by wayne
 * 根据openid获取用户微信图像 ：数据存储access_token jsapi_ticket
 * 以及微信js-sdk业务逻辑：JS-SDK使用权限签名算法
 * */
@Component
public class GetWXJsApSign {

	static Log log = LogFactory.getLog(GetWXJsApSign.class);
	
	//private static TcorpManager tcorpManager=(TcorpManager)SpringContext.getServiceBean("tcorpManager");
	/*@Autowired
	private TcorpService tcorpService;*/
	
	private static TcorpService tcorpService;
	
	
	
    @Autowired
	public  void setTcorpService(TcorpService tcorpService) {
		GetWXJsApSign.tcorpService = tcorpService;
	}
	/** 
	 *  2015-2-4 add by wayne
	 *  JS-SDK使用权限签名算法  返回 wx.config参数 
	 * */
	@SuppressWarnings({ "finally", "unchecked" })
	public static Map<String, Object> getJsApSign(String reurl,Long corpid)throws Exception{
		
		Map<String, Object> map =  new HashMap<String, Object>();
		try {
			//获取getapi_ticket
			Map<String,Object> ticketMap=getapi_ticket(corpid);
			if(ticketMap!=null){
				 //随机字符串
		        String nonce_str = create_nonce_str();
		        //时间戳
		        String timestamp = create_timestamp();
		        String string1;
		        String signature = "";

		        //注意这里参数名必须全部小写，且必须有序
		        string1 = "jsapi_ticket=" + ticketMap.get("ticket").toString() +
		                  "&noncestr=" + nonce_str +
		                  "&timestamp=" + timestamp +
		                  "&url=" + reurl;
		        log.info("加密参数string1=："+string1);

		        /** 机密算法 */
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());

		        map.put("url", reurl);
		        map.put("jsapi_ticket", ticketMap.get("ticket").toString());
				map.put("nonceStr", nonce_str);
				map.put("timestamp",Long.valueOf(timestamp));
				map.put("signature", signature);
				
			}else{
				map=null;
				log.info("getJsApSign加密参数string1：获取jsapi_ticket出错");
			 }
			
		} catch (Exception e) {
			log.info("getJsApSign加密参数string1异常");
		}finally{
			return map;
		}
	}
	/** 2015-2-4 add by wayne   微信jS-SDK   end */

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    /** 2015-2-4 add by wayne   微信jS-SDK   开始 
	 * 生成签名之前必须先了解一下jsapi_ticket，jsapi_ticket是公众号用于调用微信JS接口的临时票据。
	 * 正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
	 * 由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，
	 * 开发者必须在自己的服务全局缓存jsapi_ticket 
	 * 1.参考以下文档获取access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）
	 * 2.用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）
	 * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
	 * @return
	 */
	private static String api_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	@SuppressWarnings("finally")
	public static JSONObject getapi_ticket(Long corpid)throws Exception{
		
		JSONObject ticketMap= new JSONObject();
		try {
			
			//获取access_token
			Map<String,Object> accessToken=getAccessToken(corpid);
			if(accessToken!=null && accessToken.get("access_token")!=null){
				//Tcorp tcorp = tcorpManager.getById(corpid);
				Tcorp tcorp =  tcorpService.selectById(corpid);
				
				if (tcorp != null) {
					//数据库中 的jsapi_ticket存在 并且没有超时  获取已经超时7200秒
					if(CommonUtil.isNotNull(tcorp.getWxjsapiticket()) && CommonUtil.isNotNull(tcorp.getWxticketstamp())
						&& ((System.currentTimeMillis()-tcorp.getWxticketstamp())/1000<7200)){
						
						//从数据库中获取jsapi_ticket
						ticketMap.put("ticket", tcorp.getWxjsapiticket());
					}else{
						
						//重新请求 jsapi_ticket
						String url = api_ticket_url.replace("ACCESS_TOKEN", accessToken.get("access_token").toString());
						String jsonStr = TrustSSL.sendNewRequest(url, null);

						log.info("采用http GET方式请求获得jsapi_ticket="+jsonStr);
						//ticketMap=JSONObject.fromObject(jsonStr);
						ticketMap = JSONObject.parseObject(jsonStr);
						/*2015-2-4  add by wayne 数据库缓存jsapi_ticket */
						if(ticketMap !=null && ticketMap.get("ticket")!=null){
							tcorp.setWxjsapiticket(ticketMap.get("ticket").toString());
							tcorp.setWxticketstamp(System.currentTimeMillis());
							
							//tcorpManager.update(tcorp);
							tcorpService.updateById(tcorp);
							
							
							ticketMap.put("status", "1");
						}else{
							log.info("getapi_ticket获取ticket:失败！！");
							ticketMap=null;
						}
					}
					
				}else{
					log.info("getapi_ticket获取tcorp失败！！");
					ticketMap=null;
				}
			}else{
				log.info("getapi_ticket获取access_token:失败！！");
				ticketMap=null;
			}
			
		} catch (Exception e) {
			ticketMap=null;
		}finally{
			return ticketMap;
		}
	}
    
    
    /**
	 * 网页授权token 通过code获取token
	 * 
	 **/
	private static String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	@SuppressWarnings("finally")
	public static JSONObject getToken(String appid,String secret,String code) throws Exception{
		JSONObject userMap= new JSONObject();
		try {
			String url = token_url.replace("CODE", code);
			url = url.replace("APPID", appid);
			url = url.replace("SECRET", secret);
			
			String jsonStr = TrustSSL.sendNewRequest(url, null);
			log.info("网页授权token 通过code获取token="+jsonStr);
			//userMap= JSONObject.fromObject(jsonStr);
			//userMap = JSONObject.parse(jsonStr);
			userMap = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			userMap = null;
		}finally{
			return userMap;
		}
	}
	
	/**
	 * 网页授权获取用户的基本信息
	 * @param String accessToken
	 * @param String openid
	 * @return
	 */
	private static String userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	@SuppressWarnings("finally")
	public static JSONObject  getUserInfo(String accessToken,String openid)throws Exception{
		JSONObject userMap= new JSONObject();
		try {
			String url = userinfo_url.replace("ACCESS_TOKEN", accessToken);
			url = url.replace("OPENID", openid);
			
			String jsonStr = TrustSSL.sendNewRequest(url, null);
			log.info("用户同意授权jsonStr="+jsonStr);
			//userMap= JSONObject.fromObject(jsonStr);
			userMap = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			userMap = null;
		}finally{
			return userMap;
		}
	}
	
	/**
	 * 获取用户基本信息（包括UnionID机制）
	 * @param String accessToken
	 * @param String openid
	 * @return
	 */
	private static String userinfo_url2 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	@SuppressWarnings("finally")
	public static JSONObject getUserInfo2(String accessToken,String openid)throws Exception{
		
		JSONObject userMap= new JSONObject();
		try {
			
			String url = userinfo_url2.replace("ACCESS_TOKEN", accessToken);
			url = url.replace("OPENID", openid);

			String jsonStr = TrustSSL.sendNewRequest(url, null);
			//log.info("用户同意授权url="+url);
			log.info("用户同意授权2jsonStr="+jsonStr);
			//userMap=JSONObject.fromObject(jsonStr);
			userMap = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			userMap = null;
		}finally{
			return userMap;
		}
	}
	
	
	/**
	 * 不需求用户网页授权  直接获取用户的基本信息图像和昵称
	 * @param String openid
	 * @param Long corpid
	 * @return
	 */
	@SuppressWarnings("finally")
	public static JSONObject getUserInfo(String openid,Long corpid)throws Exception{
		JSONObject userMap= new JSONObject();
		try {
			//获取access_token
			Map<String,Object> atMap=getAccessToken(corpid);
			if(atMap==null || CommonUtil.isNull (atMap.get("access_token"))){
				log.info("getUserInfo获取用户的基本信息图像和昵称: 获取access_token失败");
				return userMap;
			}
			
			String url = userinfo_url2.replace("ACCESS_TOKEN", String.valueOf(atMap.get("access_token")));
			url = url.replace("OPENID", openid);
	
			String jsonStr = TrustSSL.sendNewRequest(url, null);
			//log.info("用户同意授权url="+url);
			log.info("用户同意授权jsonStr="+jsonStr);
			//userMap = JSONObject.fromObject(jsonStr);
			userMap = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			userMap = null;
		}finally{
			return userMap;
		}
	}
	
	/**
	 * 再次  获取access_token  拉取用户昵称和图像   不需打开授权页面
	 * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
	 * 默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开授权页面
	 * @return
	 */
	@SuppressWarnings("finally")
	public static  Map<String, Object> getAccessToken(Long corpid)throws Exception{
		 
			Map<String, Object> tokenMap= new HashMap<String, Object>();
			try {
				
				Tcorp tcorp = tcorpService.selectById(corpid);
				//Tcorp tcorp = tcorpManager.getById(corpid);
				if (tcorp != null) {
					
					//数据库中 的access_token存在 并且没有超时  获取已经超时7200秒
					if(CommonUtil.isNotNull(tcorp.getWxaccesstoken()) && CommonUtil.isNotNull(tcorp.getWxtokenstamp())
						&& ((System.currentTimeMillis()-tcorp.getWxtokenstamp())/1000<7200)){
						
						//从数据库中获取access_token
						tokenMap.put("access_token", tcorp.getWxaccesstoken());
						
					}else{
						//重新请求 access_token
						String appid = tcorp.getWxappid();
						String appsecret=tcorp.getWxappsecret();
						
						String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";
						url = url.replace("{appid}", appid).replace("{secret}", appsecret);
						String requestMsg="获取access_token:";
						
						String jsonStr = TrustSSL.sendRequest(url, requestMsg);
						//tokenMap = JsonParser.json2Map(jsonStr);
						
						tokenMap = (Map)JSON.parse(jsonStr);
						
						/*2015-2-4  add by wayne 数据库缓存access_token */
						if(tokenMap !=null && tokenMap.get("access_token")!=null){
							tcorp.setWxaccesstoken(tokenMap.get("access_token").toString());
							tcorp.setWxtokenstamp(System.currentTimeMillis());
							
							tcorpService.updateById(tcorp);
							//tcorpManager.update(tcorp);
						}else{
							log.error("getAccessToken获取access_token:失败！！jsonStr="+jsonStr);
						}
						
					}
					
				} else {
					log.error("公众号未设置APPID和APPSECRET!");
					return null;
				}
				
			} catch (Exception e) {
				tokenMap = null;
			}finally{
				return tokenMap;
			}
	}
	/** 2015-1-5 add by wayne   关注时 加入微信号到企业粉丝表tfansManager   end */
	
	/**
	 * 2016-1-19 add by wayne
	 * 后台获取关注粉丝获取关注粉丝
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	private static String SubscribeUserList_url2 = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	@SuppressWarnings("finally")
	public static JSONObject getSubscribeUserList(String openid,Long corpid)throws Exception{
		JSONObject userMap= new JSONObject();
		try {
			//获取access_token
			Map<String,Object> atMap=getAccessToken(corpid);
			if(atMap==null || CommonUtil.isNull (atMap.get("access_token"))){
				log.info("getSubscribeUserList获取所有粉丝出错: 获取access_token失败");
				return userMap;
			}
			
			String url = SubscribeUserList_url2.replace("ACCESS_TOKEN", String.valueOf(atMap.get("access_token")));
			if(openid!=null ){
				//第一个拉取的OPENID，不填默认从头开始拉取
				url += "&next_openid="+openid;
			}
	
			String jsonStr = TrustSSL.sendNewRequest(url, null);
			log.info("getSubscribeUserList获取所有粉丝数据:jsonStr="+jsonStr);
			
			//userMap = JSONObject.fromObject(jsonStr);
			userMap = JSONObject.parseObject(jsonStr);
			if(userMap!=null && userMap.get("errcode") != null){
				//出错
				userMap = null;
			}
		} catch (Exception e) {
			userMap = null;
		}finally{
			return userMap;
		}
	}
	
	/**
	 * 2015-9-22 add by wayne
	 * 模板消息接口
	 * @param openid
	 * @param corpid
	 * @param setMsg
	 * @return
	 */
	private static String template_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	@SuppressWarnings("finally")
	public static JSONObject setTemplateMsg(Long corpid,String setMsg)throws Exception{
		JSONObject result = new JSONObject();
		try {	
			//获取access_token
			Map<String,Object> atMap=getAccessToken(corpid);
			if(atMap==null || CommonUtil.isNull (atMap.get("access_token"))){
				log.info("setTemplateMsg发送模板消息: 获取access_token失败");
				return result;
			}
			
			String url = template_url.replace("ACCESS_TOKEN", String.valueOf(atMap.get("access_token")));
			String jsonStr = TrustSSL.sendNewRequest(url, setMsg);
			//log.info("用户同意授权url="+url);
			log.info("发送模板消息jsonStr="+jsonStr);
			//result = JSONObject.fromObject(jsonStr);
			result = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			System.out.println("setTemplateMsg 处理异常");
		}finally{		
			System.out.println("result="+result);
			return result;
		}
	}
	
	
	/**
	 * 2016-7-16 add by wayne
	 * 判断获取的openid是否关注
 	 * @param Long corpid
	 * @param String openid
	 * */
	@SuppressWarnings("finally")
	public static boolean isSubscribe(Long corpid,String openid)throws Exception{
		boolean isSubscribe = false;
		try {
			
			//请求微信接口获取用户基本信息
			JSONObject userMap = GetWXJsApSign.getUserInfo(openid, corpid);
			if(CommonUtil.isNotNull(userMap) && CommonUtil.isNull(userMap.get("errcode"))){
				//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
				if(CommonUtil.isNotNull(userMap.get("subscribe")) && !userMap.get("subscribe").equals(0)){
					isSubscribe=true;
				}
			}
		} catch (Exception e) {
			System.out.println("isSubscribe 处理异常");
		}finally{		
			return isSubscribe;
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("接口开始----------------");
		
		Map<String, Object> templateMsgMap= new HashMap<String, Object>();
		templateMsgMap.put("touser", "oO1uYt3Rkx9gkUdJSYta3OLM8JZA");
		templateMsgMap.put("template_id", "9tf1No_OUKa50wSRi4IEcZsmSPsCHElKE5UxSlnehug");
		//templateMsgMap.put("url", "http://weixin.qq.com/download");
		templateMsgMap.put("topcolor", "#FF0000");
		
		Map<String, Object> dataMap= new HashMap<String, Object>();
		Map<String, Object> firstMap= new HashMap<String, Object>();
		firstMap.put("value", "老板！资源对接有消息提交");
		firstMap.put("color", "#173177");
		dataMap.put("first", firstMap);
		
		Map<String, Object> workMap= new HashMap<String, Object>();
		workMap.put("value", "提交人：wayne;联系电话：13554985804");
		workMap.put("color", "#173177");
		dataMap.put("work", workMap);
		
		Map<String, Object> remarkMap= new HashMap<String, Object>();
		remarkMap.put("value", "预祝您工作愉快！");
		remarkMap.put("color", "#173177");
		dataMap.put("remark", remarkMap);
		
		templateMsgMap.put("data", dataMap);
		
		//System.out.println("发送数据="+JsonParser.map2Json(templateMsgMap));
		System.out.println("发送数据="+JSON.toJSONString(templateMsgMap));
		try {
			setTemplateMsg(3L,JSON.toJSONString(templateMsgMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		Tcorp tcorp = tcorpManager.getById(3L);
//		if (tcorp != null) {
//			
//			//数据库中 的access_token存在 并且没有超时  获取已经超时7200秒
//			if(CommonUtil.isNotNull(tcorp.getWxaccesstoken()) && CommonUtil.isNotNull(tcorp.getWxtokenstamp())
//				&& ((System.currentTimeMillis()-tcorp.getWxtokenstamp())/1000<7200)){
//				
//				//从数据库中获取access_token
//				System.out.println("111================:"+tcorp.getWxaccesstoken());
//				
//			}else{
//				System.out.println("2222================:"+(System.currentTimeMillis()-tcorp.getWxtokenstamp())/1000);
//			}
//		}
	}
}
