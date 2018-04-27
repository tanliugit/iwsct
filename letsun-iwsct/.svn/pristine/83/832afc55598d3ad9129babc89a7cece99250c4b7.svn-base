package com.letsun.iwsct.itface.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParser;
import com.letsun.iwsct.itface.common.CommonUtil;
import com.letsun.iwsct.itface.common.GetWXJsApSign;
import com.letsun.iwsct.itface.domain.Tcorp;
import com.letsun.iwsct.itface.service.TcorpService;
import com.letsun.iwsct.itface.service.TweblookPvuvService;
import com.letsun.iwsct.itface.service.TweblookUrlService;

@Controller
@RequestMapping("w/wxutil")
public class WxUtilController extends BaseController{
	
	protected final Logger logger = LoggerFactory.getLogger(WxUtilController.class);
	
	@Autowired
	private TcorpService tcorpService;
	
	@Autowired
	private TweblookUrlService tweblookUrlService;
	
	@Autowired
	private TweblookPvuvService tweblookPvuvService;
	
	@Value("${SYSURL}")
	private String SYSURL;
	
	/** 验证微信号 */
	@RequestMapping(value = "/verifyWxNo")
	@ResponseBody
	public Map<String, String> verifyWxNo(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	
	/** 返回当前公众账号配置的appid */
	@RequestMapping(value="/getAppid")
	@ResponseBody
	public JSONObject getAppid(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String shopid = request.getParameter("shopid");
		if (CommonUtil.isNotNull(shopid)) {
			Long corpId = Long.valueOf(shopid);
			Tcorp tcorp = this.tcorpService.selectById(corpId);
		    //Tcorp tcorp = tcorpManager.getById(corpId);
			if (tcorp != null) {
				result.put("appid", tcorp.getWxappid());
			} else {
				result.put("errcode", "01");
				result.put("errmsg", "公众号未设置APPID和APPSECRET!");
			}
		} else {
			result.put("errcode", "01");
			result.put("errmsg", "参数错误!");
		}

		System.out.println("result="+result);
		
		return result;
	}
	
	/** 服务号网页授权获取openid */
	@SuppressWarnings("finally")
	@RequestMapping(value="/getOpenidByCode")
	@ResponseBody
	public JSONObject getOpenidByCode(HttpServletRequest request) {
		
		JSONObject result = new JSONObject();
		
		try {
			String code = request.getParameter("code");
			String shopid = request.getParameter("shopid");
			if (CommonUtil.isNull(code, shopid)) {
				result.put("errcode", "02");
				result.put("errmsg", "参数错误!");
				return result;
			}
			Long corpId = Long.valueOf(shopid);
		    //Tcorp tcorp = tcorpManager.getById(corpId);
		    Tcorp tcorp = this.tcorpService.selectById(corpId);
			if (tcorp == null) {
				result.put("errcode", "02");
				result.put("errmsg", "公众号未设置APPID和APPSECRET!");
				return result;
			}
			
			String appid = tcorp.getWxappid();
			String secret = tcorp.getWxappsecret();
			
			JSONObject token = GetWXJsApSign.getToken(appid, secret, code);
			if (token.get("errcode") != null) {
				return token; 
			} else {
				String openid = token.getString("openid");
				result.put("openid", openid);
				
				//是否只获取openid
				boolean isonly = false;
				if(CommonUtil.isNotNull(request.getParameter("isonly"))){
					isonly=Boolean.valueOf(request.getParameter("isonly")) ;
				}
				
				if(!isonly){
					//获取用户昵称 和图像
					//弹出用户微信授权界面
					String access_token = token.getString("access_token");
					JSONObject userMap = GetWXJsApSign.getUserInfo(access_token, openid);
					//关注用户不用微信授权 
					//JSONObject userMap = GetWXJsApSign.getUserInfo(openid, corpId);
					if (userMap !=null && token.get("errcode") != null) {
						return userMap; 
					}else {
						//openid = userMap.getString("openid");
					
						//昵称
						result.put("nickname", userMap.getString("nickname"));
						//图像
						result.put("headimgurl", userMap.getString("headimgurl"));
					}
				 }
				
			}
		} catch (Exception e) {
			System.out.println("getOpenidByCode 处理异常");
		}finally{		
			System.out.println("result="+result);
			return result;
		}
		
		
	}
	/** js使用get方式用于跨域的请求
	 *  2015-2-4 add by wayne
	 *  异步获取微信jS-SDK 参数
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/getJsApSign/{corpid}", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object> getJsApSign(HttpServletRequest request,HttpServletResponse response,
			@PathVariable java.lang.Long corpid)throws Exception{
		
		 //允许跨域访问  用于微刊分享方法      
		 response.setHeader("Access-Control-Allow-Origin", "*");
		
		 Map<String, Object> map =  new HashMap<String, Object>();
		 map.put("status", "0");
		 map.put("msg", "提交失败");
		 
		try {
			
			String reurl = request.getParameter("reurl");
			if(CommonUtil.isNull(corpid,reurl)){
				map.put("msg", "参数出错");
				
				return map;
			}
			
			//获取 wx.config参数 
			Map<String,Object> mapsing=GetWXJsApSign.getJsApSign(reurl,corpid);
			if(mapsing!=null){
				//mapsing.put("url", url);
//				map.put("jsapi_ticket", mapsing.get("jsapi_ticket"));
//				map.put("nonceStr", mapsing.get("nonceStr"));
//				map.put("timestamp",mapsing.get("timestamp").toString());
//				map.put("signature", mapsing.get("signature"));
				
				map.put("msg", "提交成功");
				map.putAll(mapsing);
				map.put("status", "1");
			}else{
				map.put("status", "-1"); 
				map.put("msg", "生成JS-SDK使用权限签名算法出错");
			 }
			
		} catch (Exception e) {
			map.put("msg", "处理异常");
		}finally{
			return map;
		}
	}
	
	@RequestMapping(value="/{corpid}")
	public String wxjssdk(ModelMap model,
			@PathVariable java.lang.Long corpid,
		HttpServletRequest request)throws Exception
	{
		model.addAttribute("corpid", corpid);
		model.addAttribute("SYSURL", SYSURL);
		
		if(CommonUtil.isNull(corpid)){
			model.addAttribute("errormsg", "参数出错");
			return "/waphr/error";
		}
		
		//Tcorp tcorp = tcorpManager.getById(corpid);
		Tcorp tcorp = this.tcorpService.selectById(corpid);
		model.addAttribute("tcorp", tcorp);
		
		return "/wapcheckin/wxjssdk";
		
		//获取 wx.config参数 
//		Map<String, Object> mapsing=GetWXJsApSign.getJsApSign(SYSURL+"/w/wxutil/"+corpid,corpid);
//		if(mapsing!=null){
//			//mapsing.put("url", url);
//			model.addAttribute("ticket",  mapsing.get("jsapi_ticket"));
//			model.addAttribute("nonceStr", mapsing.get("nonceStr"));
//			model.addAttribute("timestamp",mapsing.get("timestamp").toString());
//			model.addAttribute("signature", mapsing.get("signature"));
//			
//			return "/wapcheckin/wxjssdk";
//		}else{
//			model.addAttribute("errormsg", "生成JS-SDK使用权限签名算法出错");
//			return "/waphr/error";
//		 }
	}
	
	
	
	/**
	 * 统计类型：1:URL，2：点击，3:（其他）
	 * 访问流量 以及点击统计
	 * */
	@RequestMapping(value="/writePvuv/{corpid}")
	@ResponseBody
	public JSONObject writeProductPvuv(ModelMap model,
			@PathVariable java.lang.Long corpid,
			HttpServletRequest request) {

		//前端入口  $.get("${root}/w/wxutil/writePvuv/${corpid}",{type:type,orgid:orgid,url:url},function(data){},"json");
		String type = request.getParameter("type");
		//String orgid = request.getParameter("orgid");
		String url = request.getParameter("url");
		
		if(CommonUtil.isNull(corpid,type)){
			logger.error("参数出错！！！");
			return null;
		}
		
		//Tcorp tcorp = tcorpManager.getById(corpid);
		
		Tcorp tcorp = this.tcorpService.selectById(corpid);
		if(tcorp==null){
			logger.error("企业不存在！！！");
			return null;
		}
		
		//统计类型：1:URL，2：点击，3:（其他）
		if(Integer.valueOf(type).equals(1)){
			if(CommonUtil.isNull(url)){
				logger.error("统计的URL参数为空！！！！");
				return null;
			}
			tweblookPvuvService.savePvuvForUrl(getIpAddr(),corpid, url);
			
		}
		
//		JSONObject result = new JSONObject();
		return null;
	}
	
	/**
	 * 统计类型：1:URL，2：点击，3:（其他）
	 * 访问流量 以及点击统计
	 * */
	@RequestMapping(value="/writePvuv2/{corpid}")
	@ResponseBody
	public JSONObject writeProductPvuv2(ModelMap model,
			@PathVariable java.lang.Long corpid,
			HttpServletRequest request) {

		//前端入口  $.get("${root}/w/wxutil/writePvuv/${corpid}",{type:type,orgid:orgid,url:url},function(data){},"json");
		String type = request.getParameter("type");
		//String orgid = request.getParameter("orgid");
		String url = request.getParameter("url");
		
		if(CommonUtil.isNull(corpid,type)){
			logger.error("参数出错！！！");
			return null;
		}
		
		
		Tcorp tcorp =  this.tcorpService.selectById(corpid);
		if(tcorp==null){
			logger.error("企业不存在！！！");
			return null;
		}
		
		//统计类型：1:URL，2：点击，3:（其他）
		if(Integer.valueOf(type).equals(1)){
			if(CommonUtil.isNull(url)){
				logger.error("统计的URL参数为空！！！！");
				return null;
			}
			//tweblookPvuvManager.savePvuvForUrl2(request,corpid, url, "share");
			
			this.tweblookPvuvService.savePvuvForUrl2(getIpAddr(), corpid, url, "share");
		}
		
//		JSONObject result = new JSONObject();
		return null;
	}
	
	
	/** 2016-8-19 add by wayne 
	 * 13 用于外部跨域访问
	 * 异步提交  新增url的分享次数 get方式提交  
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/addShareSumUrl/{corpid}")
	@ResponseBody
	public String addShareSumUrl(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String status="false";
		String msg="提交失败";
		String callback=request.getParameter("addShareSumUrl");  
		
		String url = request.getParameter("url");
		
		if(CommonUtil.isNull(url)){
			logger.error("统计的URL参数为空！！！！");
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'统计的URL参数为空！！！！'})");    
			return null;  
			
		}
		
		//Tcorp tcorp = tcorpManager.getById(corpid);
		
		Tcorp tcorp = this.tcorpService.selectById(corpid);
		if(tcorp==null){
			logger.error("企业不存在！！！");
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'不存在此企业信息！'})");    
			return null;
		}
		
		try{
			tweblookUrlService.addShareSumUrl(corpid, url);
			
			msg="提交成功";
			status="true";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
			
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    

		}finally{
			return null;   
		}
	}
	
	/** js使用get方式用于跨域的请求
	 *  2015-2-4 add by wayne
	 *  异步获取微信jS-SDK 参数
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/getJsApSign2/{corpid}")
	@ResponseBody
	public String getJsApSign2(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		String status="false";
		String msg="JDK请求失败";
		String callback=request.getParameter("getJsApSign");  
		 
		try {
			
			String reurl = request.getParameter("reurl");
			if(CommonUtil.isNull(corpid,reurl)){
				response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错'})");  
				return null;
			}
			
			//获取 wx.config参数 
			Map<String,Object> wxmaps=GetWXJsApSign.getJsApSign(reurl,corpid);
			if(wxmaps!=null){
				//mapsing.put("url", url);
//				map.put("jsapi_ticket", mapsing.get("jsapi_ticket"));
//				map.put("nonceStr", mapsing.get("nonceStr"));
//				map.put("timestamp",mapsing.get("timestamp").toString());
//				map.put("signature", mapsing.get("signature"));
				
				
				response.getWriter().print(callback + "({'status':'1','msg':'请求成功','wxmaps':'"+JSON.toJSONString(wxmaps)+"'})");  
				return null;
			}else{
				response.getWriter().print(callback + "({'status':'-1','msg':'生成JS-SDK使用权限签名算法出错'})");  
				return null;
			 }
			
		} catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;
		}
	}
	
}
