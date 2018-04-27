package com.letsun.iwsct.itface.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.iwsct.itface.common.CommonUtil;
import com.letsun.iwsct.itface.common.EmojiFilter;
import com.letsun.iwsct.itface.common.GetWXJsApSign;
import com.letsun.iwsct.itface.common.LotteryUtil;
import com.letsun.iwsct.itface.domain.TactivityCheckin;
import com.letsun.iwsct.itface.domain.TcheckinCommentRecord;
import com.letsun.iwsct.itface.domain.TcheckinRecord;
import com.letsun.iwsct.itface.domain.Tcorp;
import com.letsun.iwsct.itface.domain.TcorpArea;
import com.letsun.iwsct.itface.domain.Tfans;
import com.letsun.iwsct.itface.domain.TfansLa;
import com.letsun.iwsct.itface.domain.TlotteryActivity;
import com.letsun.iwsct.itface.domain.TlotteryBuyer;
import com.letsun.iwsct.itface.domain.TlotteryPrize;
import com.letsun.iwsct.itface.domain.TlotteryRecord;
import com.letsun.iwsct.itface.domain.Tposition;
import com.letsun.iwsct.itface.domain.Tresume;
import com.letsun.iwsct.itface.model.Gift;
import com.letsun.iwsct.itface.service.TactivityCheckinService;
import com.letsun.iwsct.itface.service.TcheckinCommentRecordService;
import com.letsun.iwsct.itface.service.TcheckinRecordService;
import com.letsun.iwsct.itface.service.TcorpAreaService;
import com.letsun.iwsct.itface.service.TcorpService;
import com.letsun.iwsct.itface.service.TfansLaService;
import com.letsun.iwsct.itface.service.TfansService;
import com.letsun.iwsct.itface.service.TlotteryActivityService;
import com.letsun.iwsct.itface.service.TlotteryBuyerService;
import com.letsun.iwsct.itface.service.TlotteryPrizeService;
import com.letsun.iwsct.itface.service.TlotteryRecordService;
import com.letsun.iwsct.itface.service.TpositionService;
import com.letsun.iwsct.itface.service.TresumeService;
import com.letsun.iwsct.itface.service.TweblookPvuvService;

@Controller
@RequestMapping("w/zp/crossDomain")
public class ZPwxCrossDomainController extends BaseController {
	
	private final ReentrantLock lock = new ReentrantLock();
	
	@Autowired
	private TcorpService tcorpService;
	
	@Autowired
	private TcorpAreaService tcorpAreaService;

	@Autowired
	private TpositionService tpositionService;

	@Autowired
	private TfansService tfansService;

	@Autowired
	private TresumeService tresumeService;

	@Autowired
	private TweblookPvuvService tweblookPvuvService;
	
	@Autowired
	private TlotteryBuyerService tlotteryBuyerService;
	
	@Autowired
	private TlotteryRecordService tlotteryRecordService;
	
	@Autowired
	private TlotteryActivityService tlotteryActivityService;
	
	@Autowired
	private TlotteryPrizeService tlotteryPrizeService;
	
	@Autowired
	private TcheckinCommentRecordService tcheckinCommentRecordService;
	
	@Value("${SYSURL}")
	private String SYSURL;
	
	//tactivityCheckinManager;
	@Autowired
	private TactivityCheckinService tactivityCheckinService;
	
	@Autowired
	private TcheckinRecordService tcheckinRecordService;
	
	@Autowired
	private TfansLaService tfansLaService;
	
	/***********************************************获取openid页面 ***************************************************/
	@SuppressWarnings("unchecked")
	/**
	 * 2016-3-23 add by wayne
	 * 获取openid页面   判断微信用户是否绑定
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{corpid}")
	public String getOpenid(ModelMap model,
			@PathVariable java.lang.Long corpid,
		HttpServletRequest request)throws Exception
	{
		model.addAttribute("SYSURL", SYSURL);
		model.addAttribute("corpid", corpid);
		
		if(CommonUtil.isNull(corpid)){
			model.addAttribute("errormsg", "参数出错");
			return "/wxopenid/error";
		}
		//检测企业账号信息
		if(!chenckCorp(model,request, corpid)) return "/wxopenid/error";
		
		//获取请求wk_requestUrl
		String wk_requestUrl = request.getParameter("wk_requestUrl");
		model.addAttribute("wk_requestUrl", wk_requestUrl);
		
		//签到活动id
		String signid= request.getParameter("signid");
		model.addAttribute("signid", signid);
		model.addAttribute("isSign", 0);
		
		String isSubscribe= request.getParameter("isSubscribe");
		model.addAttribute("isSubscribe", isSubscribe);
		
		//是否授权获取用户昵称
		model.addAttribute("isAuthorize", request.getParameter("isAuthorize")==null?1:0);
		
		//获取wxno
		String wxno = request.getParameter("wxno");
		
		Tfans fans = new Tfans();
		
		if(wxno!=null){
			model.addAttribute("wxno", wxno);
			
			//返回的验证令牌
			model.addAttribute("mytoken", CommonUtil.hash(wxno+"wnzlxcy"));
			
			String nickname = request.getParameter("nickname")!=null?java.net.URLDecoder.decode(request.getParameter("nickname"),"UTF-8"):"";
			if(CommonUtil.isNotNull(nickname)){
				nickname =EmojiFilter.filterEmoji(nickname);
			}
			
			model.addAttribute("nickname", nickname);
			model.addAttribute("headimgurl", request.getParameter("headimgurl")!=null?request.getParameter("headimgurl"):"");
			
			
			//
			if(CommonUtil.isNotNull(isSubscribe) && isSubscribe.equals("1")){
				model.addAttribute("isSubscribe", 0);
				if(GetWXJsApSign.isSubscribe(corpid, wxno)){
					model.addAttribute("isSubscribe", 1);
				}
				
			}
			
			
			//查询获取的openid 是否  保存过
			//Tfans fans=tfansManager.chenckTfans(wxno, corpid);
			fans = this.tfansService.chenckTfans(wxno, corpid);
			if(null == fans){
				//没有关注过   可以参加   临时粉丝状态为2
				fans=new Tfans();
				fans.setWxno(wxno);
				fans.setCorpid(corpid);
				fans.setCreateDate(new Date());
				fans.setScore(0);
				fans.setHelpscore(0);
				fans.setHelpnum(0);
				fans.setStatus(2);
			  	
				fans.setNickname(nickname);
				fans.setPhotourl(request.getParameter("headimgurl")!=null?request.getParameter("headimgurl"):"");
				
				this.tfansService.insert(fans);
				//this.tfansService.insertAllColumn(fans);
			}else{
				//更新图像和昵称
				if(CommonUtil.isNotNull(nickname,request.getParameter("headimgurl"))){
					fans.setNickname(nickname);
					fans.setPhotourl(request.getParameter("headimgurl")!=null?request.getParameter("headimgurl"):"");
					
					this.tfansService.updateById(fans);
				}
			}
			
			
			
			//签到
			if(CommonUtil.isNotNull(signid) && !StringUtils.equals("null", signid)){
				//TactivityCheckin tactivityCheckin = tactivityCheckinManager.getById(Long.valueOf(signid));
				TactivityCheckin tactivityCheckin =  this.tactivityCheckinService.selectById(Long.valueOf(signid));
				if(tactivityCheckin != null && tactivityCheckin.getStatus().equals(1)){
					//查询是否签到过
					Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
					paramsMap.put("corpid", Long.valueOf(corpid));
					paramsMap.put("activityid", Long.valueOf(Long.valueOf(signid)));
					paramsMap.put("status", 1);
					paramsMap.put("fansid",fans.getFansid());
					
					//List<TcheckinRecord> listRecord = (List<TcheckinRecord>)tcheckinRecordManager.findAllByPropertyes(TcheckinRecord.class,paramsMap);
					
					List<TcheckinRecord> listRecord = tcheckinRecordService.selectByMap(paramsMap);
					
					if(listRecord!=null && listRecord.size()>0){
						//已经签到的标志
						model.addAttribute("isSign", 2);
					}else{
						//新增签到
						TcheckinRecord tRecord= new TcheckinRecord();
						tRecord.setCorpid(Long.valueOf(corpid));
						tRecord.setActivityid(Long.valueOf(Long.valueOf(signid)));
						tRecord.setFansid(fans.getFansid());
						tRecord.setCreateDate(new Date());
						tRecord.setRemark("签到");
						tRecord.setStatus(1);
						
						this.tcheckinRecordService.insert(tRecord);
						//签到成功的标志
						model.addAttribute("isSign", 1);
					}
				}
			}
		}
		model.addAttribute("fans", fans);
		return "/wapwx/welcome";
	}
	
	
	
	/**
	 * 2018-4-1 add by tanliu 异步提交 投递简历 get方式提交 用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/submitData/{corpid}/{areaid}/{positionid}")
	@ResponseBody
	public String jsonpcallback(ModelMap model, HttpServletRequest request,
			@PathVariable java.lang.Long corpid, @PathVariable Long areaid,
			@PathVariable Long positionid, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String status = "false";
		String msg = "提交失败";
		String callback = request.getParameter("submitDataback");

		TcorpArea tArea = tcorpAreaService.selectById(areaid);
		if (tArea == null) {
			msg = "数据出错！";
			response.getWriter().print(
					callback + "({'status':'" + status + "','msg':'" + msg
							+ "'})");
			return null;
		}

		
		Tposition tposition = tpositionService.selectById(positionid);
		if (tposition == null) {
			msg = "数据出错！";
			response.getWriter().print(
					callback + "({'status':'" + status + "','msg':'" + msg
							+ "'})");
			return null;
		}

		String candidate = request.getParameter("candidate");
		String idnumber = request.getParameter("idnumber");
		String mobile = request.getParameter("mobile");

		// 备用字段
		String content = request.getParameter("content");
		String remarks = request.getParameter("remarks");

		// 专业
		String specialty = request.getParameter("specialty");
		// 年级
		String grade = request.getParameter("grade");
		// 学校
		String school = request.getParameter("school");
		// 籍贯
		String hail = request.getParameter("hail");
		// email
		String email = request.getParameter("email");
		String type = request.getParameter("type");
		String hunterOpenid = request.getParameter("hunterOpenid");
		String uncheckRepeat = request.getParameter("uncheckRepeat");

		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		String education = request.getParameter("education");

		Long ageLong = (age == null || "".contains(age)) ? 0L : Long
				.parseLong(age);

		// 更新粉丝关联数据
		String fansid = request.getParameter("fansid");
		if (StringUtils.isNotBlank(fansid) && !StringUtils.equals(type, "-1")) {
			Tfans fans = tfansService.selectById(Long.valueOf(fansid));
			if (fans != null) {
				fans.setShopid(positionid.intValue());
				// fans.setHelpscore(0);
				// fans.setHelpnum(0);
				tfansService.updateById(fans);
			}
		}
		if (StringUtils.equals(type, "-1")) {
			type = "";
		}

		if (!StringUtils.equals(uncheckRepeat, "2")) {

			if (StringUtils.isAllBlank(candidate, idnumber, mobile)) {
				response.getWriter().print(
						callback + "({'status':'" + status
								+ "','msg':'输入数据不完整！'})");
				return null;
			}
		}

		try {
			if (!StringUtils.equals(uncheckRepeat, "1")
					&& !StringUtils.equals(uncheckRepeat, "2")) {
				// 判断企业统一岗位 同一手机号是否已提交过
				if (mobile != null) {
					Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
					paramsMap.put("corpid", corpid);
					paramsMap.put("positionid", positionid);
					paramsMap.put("mobile", mobile);
					paramsMap.put("status", 1);

					List<Tresume> listResume = tresumeService
							.selectByMap(paramsMap);
					// List<Tresume> listResume = (List<Tresume>)
					// tresumeService.findAllByPropertyes(Tresume.class,
					// paramsMap);
					if (listResume.size() > 0) {
						// map.put("msg", "此岗位，同一身份证号已经提交了应聘信息");
						msg = "同一手机号已经提交过了";
						response.getWriter().print(
								callback + "({'status':'" + status
										+ "','msg':'" + msg + "'})");
						return null;
					}
				}
			}

			if (StringUtils.equals(uncheckRepeat, "2")) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("status", 1);
				paramsMap.put("corpid", corpid);
				paramsMap.put("fansid", Long.parseLong(fansid));
				// List<Tresume> resumeList = (List<Tresume>)
				// this.tresumeManager.findAllByPropertyes(Tresume.class,
				// paramsMap);
				List<Tresume> resumeList = (List<Tresume>) tresumeService
						.selectByMap(paramsMap);
				if (!CollectionUtils.isEmpty(resumeList)) {
					Tresume resumeUpdate = resumeList.get(0);
					String origContent = resumeUpdate.getContent();
					origContent = origContent == null ? "" : origContent;
					origContent += content;
					resumeUpdate.setContent(origContent);
					if (StringUtils.isNotBlank(remarks)) {
						resumeUpdate.setRemarks(encodingCharset(remarks));
					}
					this.tresumeService.updateById(resumeUpdate);
				} else {

					this.saveTresume(corpid, areaid, positionid, candidate,
							idnumber, mobile, content, remarks, specialty,
							grade, school, hail, email, type, fansid, ageLong,
							sex, education);
				}
			} else {
				this.saveTresume(corpid, areaid, positionid, candidate,
						idnumber, mobile, content, remarks, specialty, grade,
						school, hail, email, type, fansid, ageLong, sex,
						education);
			}

			long count = 0;
			if (StringUtils.isNotBlank(hunterOpenid)) {
				String ip = getIpAddr();
				count = this.tweblookPvuvService.savePvuvForUrl2(ip, corpid,
						hunterOpenid, "data");
			}

			msg = "提交成功";
			status = "true";
			response.getWriter().print(
					callback + "({'status':'" + status + "','msg':'" + msg
							+ "', 'count':'" + count + "'})");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(
					callback + "({'status':'" + status + "','msg':'" + msg
							+ "', 'count':'-1'})");
		} finally {
			return null;
		}
	}
	
	
	/** 2016-8-27 add by wayne 
	 * 异步提交   查询抽奖人信息--get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/querylottly/{corpid}/{wxno}/{mytoken}")
	@ResponseBody
	public String querylottly(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.String wxno,
			@PathVariable java.lang.String mytoken,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="提交失败";
		String callback=request.getParameter("querylottlyback");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno, mytoken)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//验证参数是否合法
		if(!CommonUtil.hash(wxno+"wnzlxcy").equals(mytoken)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//抽奖总人数
		int lotteryNum=0;
		
		try{
			//查询获抽奖总人数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			
			paramMap.put("corpid", corpid);
			List<TlotteryBuyer> listNum=tlotteryBuyerService.selectByMap(paramMap);
			
			/*List<TlotteryBuyer> listNum=tlotteryBuyerManager.findAllBy("corpid", corpid);*/
			if(listNum!=null && listNum.size()>0){
				lotteryNum=listNum.size();
			}
			
			
			//查询获取的openid 是否抽过奖
			TlotteryBuyer buyer=tlotteryBuyerService.getBuyer(wxno, corpid);
			if(buyer!=null && buyer.getTotal()>=1){
				//是否中奖
				
				Wrapper<TlotteryRecord> wrapper = new EntityWrapper<TlotteryRecord>();
				wrapper.eq("buyerid", buyer.getId());
				
				List<TlotteryRecord> listRecord = this.tlotteryRecordService.selectList(wrapper);
				//List<TlotteryRecord> listRecord = recordManager.findAllBy("buyerid", buyer.getId());
				if(listRecord!=null && listRecord.size()>0){
					//model.addAttribute("tRecord", listRecord.get(0));
					response.getWriter().print(callback + "({'status':'2','msg':'您已中过奖！','lotteryNum':'"+lotteryNum+"'})");  
					return null;
				}
				
				response.getWriter().print(callback + "({'status':'-1','msg':'已抽过奖！','lotteryNum':'"+lotteryNum+"'})"); 
				return null;
			}
			
			//奖项规则
			List<TlotteryActivity> activityList = this.tlotteryActivityService.queryActivityList(corpid);
			TlotteryActivity activity = new TlotteryActivity();
			
			//判断活动是否存在
			if(activityList.size() >0){
				activity = activityList.get(0);
			}else{
				response.getWriter().print(callback + "({'status':'0','msg':'暂无抽奖活动！','lotteryNum':'"+lotteryNum+"'})"); 
				return null;
			}
			
			List<TlotteryPrize> prizeList = tlotteryPrizeService.getlistPrizeForlotteryid(activity.getId());
			if(prizeList==null || prizeList.size()==0){
				response.getWriter().print(callback + "({'status':'0','msg':'暂无抽奖活动！','lotteryNum':'"+lotteryNum+"'})"); 
				return null;
			}
		
			msg="没有抽过奖！";
			status="1";
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"','lotteryNum':'"+lotteryNum+"'})"); 
			return null;
			
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"','lotteryNum':'"+lotteryNum+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	
	/** 2016-8-27 add by wayne 
	 * 异步提交   抽奖 --get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/lottly/{corpid}/{wxno}/{mytoken}")
	@ResponseBody
	public String lottly(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.String wxno,
			@PathVariable java.lang.String mytoken,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="提交失败";
		String callback=request.getParameter("lottlyDataback");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno, mytoken)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//验证参数是否合法
		if(!CommonUtil.hash(wxno+"wnzlxcy").equals(mytoken)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		try{
			lock.lock(); 
			
			
			//奖项规则
			List<TlotteryActivity> activityList = this.tlotteryActivityService.queryActivityList(corpid);
			/*List<TlotteryActivity> activityList = activityManager.queryActivityList(corpid);*/
			TlotteryActivity activity = new TlotteryActivity();
			
			//判断活动是否存在
			if(activityList.size() >0){
				activity = activityList.get(0);
			}else{
				response.getWriter().print(callback + "({'status':'0','msg':'暂无抽奖活动！'})");  
				return null;
			}
			
			List<TlotteryPrize> prizeList = this.tlotteryPrizeService.getlistPrizeForlotteryid(activity.getId());
			//List<TlotteryPrize> prizeList = tlotteryPrizeManager.getlistPrizeForlotteryid(activity.getId());
			if(prizeList==null || prizeList.size()==0){
				response.getWriter().print(callback + "({'status':'0','msg':'暂无抽奖活动！'})");  
				return null;
			}
			
			//判断该wxno 是否存在
			TlotteryBuyer buyer= this.tlotteryBuyerService.getBuyer(wxno, corpid);
			//TlotteryBuyer buyer=tlotteryBuyerManager.getBuyer(wxno, corpid);
			if(buyer!=null){
				buyer.setTotal(buyer.getTotal()+1);
				tlotteryBuyerService.updateById(buyer);
			}else{
				//保存抽奖人信息
				buyer = new TlotteryBuyer();
				buyer.setCorpid(corpid);
				buyer.setWxno(wxno);
				buyer.setStatus(1);
				buyer.setTotal(1);
				buyer.setCreateDate(new Date());
				tlotteryBuyerService.insert(buyer);
			}
			
			//抽奖方法
			TlotteryPrize prize=get_lotteryPrize(activity.getId());
			//抽奖记录对象
			TlotteryRecord tlotteryRecord = new TlotteryRecord();
			tlotteryRecord.setCorpid(corpid);
			//中奖
			if(prize!=null){
				//奖品   中奖数量+1   修改奖品数量
				prize.setTotal((prize.getTotal()!=null)?prize.getTotal()+1:1);
				//tlotteryPrizeManager.merge(prize);
				
				this.tlotteryPrizeService.updateById(prize);
				//新增中奖记录
				tlotteryRecord.setStatus(1);
				tlotteryRecord.setLotteryid(activity.getId());
				tlotteryRecord.setPrizeid(prize.getId());
				tlotteryRecord.setBuyerid(buyer.getId());
				tlotteryRecord.setCreateDate(new Date());
				
				this.tlotteryRecordService.insert(tlotteryRecord);
				
				response.getWriter().print(callback + "({'status':'1','msg':'"+tlotteryRecord.getId()+"','level':'"+prize.getLevel()+"'})");    
				return null;  
			}else{
				response.getWriter().print(callback + "({'status':'-1','msg':'很遗憾，您没有中奖！'})");    
				return null;  
			}
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
			//return null;   
		}finally{
			if (lock.isLocked()) {
				lock.unlock();
			}
			
			return null;   
		}
		
	}
	
	
	
	
	/** 2016-8-27 add by wayne 
	 * 异步提交   查询抽奖人信息--get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/submitBuyer/{corpid}/{recordid}")
	@ResponseBody
	public String submitBuyer(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long recordid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="提交失败";
		String callback=request.getParameter("submitBuyerback");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(recordid)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错'})");  
			return null;
		}
		
		//查询中奖记录信息	
		
		TlotteryRecord tRecord=this.tlotteryRecordService.selectById(recordid);
		//TlotteryRecord tRecord=recordManager.getById(recordid);
		if(tRecord==null || tRecord.getBuyerid()==null){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'没有您的中奖记录信息，请先参加我们的活动！'})");  
			return null;
		}
		
		//中奖后的表单提交
		String candidate = request.getParameter("candidate");
		String mobile = request.getParameter("mobile");
		String remark = request.getParameter("remark");
		String address = request.getParameter("address");
		
		if(CommonUtil.isNull(candidate,remark,mobile)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'输入数据不完整！'})");  
			return null;
		}
	
		try{
			//更新获奖人信息	
			//TlotteryBuyer buyer = tlotteryBuyerManager.getById(tRecord.getBuyerid());
			
			TlotteryBuyer buyer = this.tlotteryBuyerService.selectById(tRecord.getBuyerid());
			buyer.setName(candidate);
			buyer.setMobile(mobile);
			buyer.setRemark(remark);
			buyer.setAddress(address!=null?address:"");
			
			this.tlotteryBuyerService.updateById(buyer);
			//tlotteryBuyerManager.update(buyer);
			
			response.getWriter().print(callback + "({'status':'1','msg':'提交成功'})");    
			return null; 
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;  
		}
	}
	
	
	
	
	/** 2016-10-1 add by wayne 
	 * 异步查询是否提交过资料  get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping(value="/queryResume/{corpid}/{fansid}")
	@ResponseBody
	public String queryResume(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			HttpServletResponse response, String type) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="查询失败";
		String callback=request.getParameter("queryResumeback");  
		

		if(!chenckCorp(mapmsg, request, corpid)) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		Tfans fans= this.tfansService.selectById(fansid);
		//Tfans fans= tfansManager.getById(fansid);
		if(fans==null){
			msg="数据出错！";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		try{
			//判断fans是否已提交过
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", corpid);
			paramsMap.put("fansid", fansid);
			if (StringUtils.isNotBlank(type)) {
				paramsMap.put("education", type);
			}
			paramsMap.put("status", 1);
			//List<Tresume> listResume = (List<Tresume>) tresumeManager.findAllByPropertyes(Tresume.class, paramsMap);
			
			List<Tresume> listResume = this.tresumeService.selectByMap(paramsMap);
			if(listResume.size()>0){
				//map.put("msg", "此岗位，同一身份证号已经提交了应聘信息");
				msg="已经提交过了";
				
				Long positionId = listResume.get(0).getPositionid();
				
				Tposition position = this.tpositionService.selectById(positionId);
				
				String positionName = position != null ? position.getPositionname() : "";
				
				response.getWriter().print(callback + "({'status':'-1','msg':'"+listResume.get(0).getRemarks()+"','fans':'"+ JSONObject.toJSON(fans)+
						"','cityid':'"+positionId+"','city':'"+positionName+
						"','name':'"+listResume.get(0).getCandidate()+"','content': '"+ listResume.get(0).getContent() +"'})");  
				return null;
			}else{
				msg="查询成功:没有提交过资料！";
				status="1";
				response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"','fans':'"+ JSONObject.toJSON(fans)+"'})");    
				return null;  
			}
		}catch (Exception e) {
			// TODO: handle exception
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	
	/** 2016-10-1 add by wayne 
	 * 更新粉丝游戏分数  get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/submitGamescore/{corpid}/{fansid}/{score}")
	@ResponseBody
	public String submitGamescore(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			@PathVariable Integer score,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="提交失败";
		String callback=request.getParameter("submitGamescore");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		//获取score
		if(CommonUtil.isNull(score)){
			msg="数据出错！";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		Tfans fans = this.tfansService.selectById(fansid);
		//Tfans fans= tfansManager.getById(fansid);
		if(fans==null){
			msg="数据出错！";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		try{
			//更新游戏分数
			if(fans.getScore()==null || fans.getScore()<score){
				fans.setHelpscore(fans.getHelpscore()==null?score:(fans.getHelpscore()+score-fans.getScore()));
				fans.setScore(score);
				//tfansManager.update(fans);
				this.tfansService.updateById(fans);
			}
			
			int myranking=0;
			int scoreranking=0;
			if(CommonUtil.isNotNull(fans.getShopid())){
				//获取自己的  助力人数名次
				//myranking=tfansManager.getMyRankingForNum(corpid, fans.getShopid(), fans.getHelpnum());
				myranking=this.tfansService.getMyRankingForNum(corpid, fans.getShopid(), fans.getHelpnum());
				//获取自己的 游戏分数名次
				//scoreranking=tfansManager.getMyRankingForCore(corpid,fans.getShopid(), fans.getHelpscore(), null);
				scoreranking =this.tfansService.getMyRankingForCore(corpid,fans.getShopid(), fans.getHelpscore(), null);
			}else{
				//获取自己的  助力人数名次
				myranking=this.tfansService.getMyRankingForNum(corpid, 0, fans.getHelpnum());
				//获取自己的 游戏分数名次
				scoreranking=this.tfansService.getMyRankingForCore(corpid,0, fans.getHelpscore(), null);
			}
			
			msg="提交成功";
			status="1";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"','myranking':'"+myranking+"','scoreranking':'"+scoreranking+"'})");    
			return null;   
		}catch (Exception e) {
			// TODO: handle exception
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	
	/** 2016-10-1 add by wayne 
	 * 查询个人信息以及好友助力列表  get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/queryFans/{corpid}/{fansid}/{avescore}")
	@ResponseBody
	public String queryFans(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			@PathVariable Integer avescore,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="查询失败";
		String callback=request.getParameter("queryFansback");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}


		//获取每人分值
		if(CommonUtil.isNull(avescore)){
			msg="数据出错！";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//Tfans fans= tfansManager.getById(fansid);
		Tfans fans = this.tfansService.selectById(fansid);
		if(fans==null){
			msg="数据出错！";
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		
			
		
		//城市名称
		String city="";
		Long cityid=null;
		if(CommonUtil.isNotNull(fans.getShopid())){
			//Tposition position=tpositionManager.getById(Long.valueOf(fans.getShopid()));
			
			Tposition position=this.tpositionService.selectById(Long.valueOf(fans.getShopid()));
			city=position.getPositionname();
			cityid=position.getPositionid();
		}
		
		
		try{
			//总积分数据
			Integer sumscore=fans.getHelpscore();
			//将java对象转换为json对象
			//JSONObject fansjson = JSONObject.fromObject(fans);
			JSONObject fansjson = (JSONObject)JSON.toJSON(fans);
			//好友助力列表
			List<TfansLa> fansLa=tfansLaService.getListfansLa(fansid, corpid);
			//列表数据
			String fansLajson="";
			if(fansLa!=null && fansLa.size()>0){
				//sumscore+=fansLa.size()*avescore;
				//列表对象
				fansLajson = JSONArray.toJSONString(fansLa);
			}
			
			msg="查询成功！";
			response.getWriter().print(callback + "({'status':'1','msg':'"+msg+"','cityid':'"+cityid+"','city':'"+city+"','sumscore':'"+sumscore+"','fans':'"+ fansjson.toString()+"','list':'"+ fansLajson+"'})");  
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	

	/***
	 * 检测企业账号信息
	**/
	private boolean chenckCorp(ModelMap model,HttpServletRequest request,java.lang.Long corpid) {
		//Tcorp tcorp = tcorpManager.getById(corpid);
		Tcorp tcorp = this.tcorpService.selectById(corpid);
		if(null == tcorp){
			model.addAttribute("errormsg", "不存在此企业信息！");
			return false;
		}else{
			
			if(tcorp.getStatus() != 1){
		    	if(tcorp.getStatus()==2)
		    		model.addAttribute("errormsg","企业服务已被停用");
		    	else if(tcorp.getStatus()==3)
		    		model.addAttribute("errormsg","此企业账号已被锁定");
		    	else
		    		model.addAttribute("errormsg","此企业账号异常");

		    	return false;
			}else if(tcorp.getEnddate()!=null && CommonUtil.compareDay(tcorp.getEnddate(),new Date())==0){
    			model.addAttribute("errormsg","企业服务已到期");
    			return false;
    		}
		}
		
		model.addAttribute("tcorp", tcorp);
		//检测是否微信浏览器
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		}else{
			model.addAttribute("errormsg","请使用微信访问!");
			return false;
		}
	}
	
	/**
	 * 
	 * @param nickname
	 * @return
	 */
	public static String encodingCharset(String nickname) {
		if (StringUtils.isNoneBlank(nickname)) {
			try {
				if (nickname.equals(new String(nickname.getBytes("ISO8859-1"),
						"ISO8859-1"))) {
					nickname = new String(nickname.getBytes("ISO8859-1"),
							"UTF-8");
				}
			} catch (UnsupportedEncodingException ignore) {
			}
		}
		return nickname;
	}

	/**
	 * 
	 * @param corpid
	 * @param areaid
	 * @param positionid
	 * @param candidate
	 * @param idnumber
	 * @param mobile
	 * @param content
	 * @param remarks
	 * @param specialty
	 * @param grade
	 * @param school
	 * @param hail
	 * @param email
	 * @param type
	 * @param fansid
	 * @param age
	 *            年龄
	 * @param sex
	 *            性别
	 * @param education
	 *            最高学历
	 * @throws UnsupportedEncodingException
	 */
	private void saveTresume(java.lang.Long corpid, Long areaid,
			Long positionid, String candidate, String idnumber, String mobile,
			String content, String remarks, String specialty, String grade,
			String school, String hail, String email, String type,
			String fansid, Long age, String sex, String education)
			throws UnsupportedEncodingException {
		// 保存
		Tresume resume = new Tresume();
		resume.setCorpid(corpid);
		resume.setAreaid(Long.valueOf(areaid));
		resume.setPositionid(Long.valueOf(positionid));
		resume.setStatus(1);
		resume.setSchedule(1);

		// resume.setTinvtPosition(new TinvtPosition(Long.valueOf(positionid)));
		resume.setCandidate(candidate);
		resume.setMobile(mobile);
		resume.setIdnumber(idnumber);

		resume.setCreateDate(new Date());

		// 备用字段
		resume.setContent(content == null ? "" : content);
		resume.setRemarks(remarks == null ? "" : encodingCharset(remarks));
		resume.setSpecialty(specialty == null ? "" : specialty);
		resume.setGrade(grade == null ? "" : grade);
		resume.setSchool(school == null ? "" : school);
		resume.setHail(hail == null ? "" : hail);
		resume.setEmail(email == null ? "" : email);

		// 年龄性别最高学历
		resume.setAge(age);
		resume.setSex(sex);
		resume.setEducation(education);

		// 关联的粉丝id
		resume.setFansid(fansid == null ? null : Long.valueOf(fansid));
		if (StringUtils.isNotBlank(type)) {
			resume.setEducation(type);
		}

		this.tresumeService.insert(resume);
		// tresumeManager.save(resume);
	}
	
	

	/***
	 * 跨域检测企业账号信息
	**/
	private boolean chenckCorp(Map<Object,Object> mapmsg,HttpServletRequest request, java.lang.Long corpid) {
		Tcorp tcorp = this.tcorpService.selectById(corpid);
		if(null == tcorp){
			mapmsg.put("msg", "不存在此企业信息！");
			return false;
		}else{
			
			if(tcorp.getStatus() != 1){
		    	if(tcorp.getStatus()==2)
		    		mapmsg.put("msg","企业服务已被停用");
		    	else if(tcorp.getStatus()==3)
		    		mapmsg.put("msg","此企业账号已被锁定");
		    	else
		    		mapmsg.put("msg","此企业账号异常");

		    	return false;
			}else if(tcorp.getEnddate()!=null && CommonUtil.compareDay(tcorp.getEnddate(),new Date())==0){
				mapmsg.put("msg","企业服务已到期");
    			return false;
    		}
		}
		
		//检测是否微信浏览器
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		}else{
			mapmsg.put("msg","请使用微信访问!");
			return false;
		}
	}
	
	
	/** 2014-11-7 add by wayne
	 * 抽奖方法返回抽奖对象
	 * */
	private TlotteryPrize get_lotteryPrize(Long lotteryid) {
		//抽奖方法
		
		List<TlotteryPrize> prizeList = this.tlotteryPrizeService.getlistPrize(lotteryid);
		//List<TlotteryPrize> prizeList = tlotteryPrizeManager.getlistPrize(lotteryid);
		if(prizeList.size()>0){
			 List<Gift> gifts = new ArrayList<Gift>();
		    // 序号==物品Id==物品名称==概率
		    //gifts.add(new Gift(1, "P1", "物品1", 1d/100));
		    for (TlotteryPrize prize : prizeList) {
		    	 double probability = prize.getProbability()/100;
		    	 if (probability < 0) {
		    		 probability=0;
		    	 }
		    	 gifts.add(new Gift(prize.getLevel(), 
							prize.getLevel().toString(),
							prize.getName(), 
							probability));
			}
		    
		    List<Double> orignalRates = new ArrayList<Double>(gifts.size());
		    for (Gift gift : gifts) {
		        orignalRates.add(gift.getProbability());
		    }
		    
		    int levelIndex =-1;
		    levelIndex = LotteryUtil.lottery(orignalRates);
		    
		    if(levelIndex==-1) return null;
		    else return prizeList.get(levelIndex);
		}else {
			return null;
		}
	}
	
	
	
	/**
	 * 2.数据提交 
	 * 2014-12-4 add by wayne 
	 * */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="/submitData/{corpid}")
	@ResponseBody
	public String jsonpcallback(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("submitDataback");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		
		String candidate = request.getParameter("candidate");
		String idnumber = request.getParameter("idnumber");
		String mobile = request.getParameter("mobile");
		
		//备用字段
		String content = request.getParameter("content");
		String remarks = request.getParameter("remarks");
		
		//专业
		String specialty = request.getParameter("specialty");
		//年级
		String grade = request.getParameter("grade");
		//学校
		String school = request.getParameter("school");
		//籍贯
		String hail = request.getParameter("hail");
		//email
		String email = request.getParameter("email");
		
		
		

		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		String education = request.getParameter("education");
		
		Long ageLong =  (age==null||"".equals(age))?0L:Long.parseLong(age);
		
		
		if(CommonUtil.isNull(candidate,idnumber,mobile)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'输入数据不完整！'})");  
			return null;
		}
		
		try{
			//判断企业统一岗位 同一手机号是否已提交过
			if(mobile!=null){
				Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
				paramsMap.put("corpid", corpid);
				paramsMap.put("mobile", mobile);
				paramsMap.put("status", 1);
				List<Tresume> listResume = (List<Tresume>) tresumeService.selectByMap(paramsMap);
				if(listResume.size()>0){
					//map.put("msg", "此岗位，同一身份证号已经提交了应聘信息");
					msg="同一手机号已经提交过了";
					response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
					return null;
				}
			}
			
			//保存
			Tresume resume=new Tresume();
			resume.setCorpid(corpid);
			resume.setStatus(1);
			resume.setSchedule(1);
			
//			resume.setTinvtPosition(new TinvtPosition(Long.valueOf(positionid)));
			resume.setCandidate(candidate);
			resume.setMobile(mobile);
			
			resume.setCreateDate(new Date());

			//备用字段
			resume.setContent(content==null?"":content);
			resume.setRemarks(remarks==null?"":remarks);
			resume.setSpecialty(specialty==null?"":specialty);
			resume.setGrade(grade==null?"":grade);
			resume.setSchool(school==null?"":school);
			resume.setHail(hail==null?"":hail);
			resume.setEmail(email==null?"":email);
			resume.setAge(ageLong);
			resume.setSex(sex);
			resume.setEducation(education);
			
			//关联的粉丝wxno
			resume.setIdnumber(idnumber);
			
			tresumeService.insert(resume);
			
			msg="提交成功";
			response.getWriter().print(callback + "({'status':'1','msg':'"+msg+"'})");    
			return null;   
		}catch (Exception e) {
			// TODO: handle exception
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}	
	
	
	/**
	 * 3  手机签到初始化页面-获取活动列表
	 * 2016-8-27 add by wayne 
	 * */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="/getAllActivity/{corpid}/{fansid}/{wxno}/{mytoken}")
	@ResponseBody
	public String getAllActivity(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long fansid,
			@PathVariable java.lang.String wxno,
			@PathVariable java.lang.String mytoken,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("getAllActivity");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno, mytoken)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//验证参数是否合法
		if(!CommonUtil.hash(wxno+"wnzlxcy").equals(mytoken)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}

		//查询获取的fansid 是否  保存过
		Tfans fans=tfansService.selectById(fansid);
		if(null == fans){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		
		try{
			//将java对象转换为json对象
			JSONObject fansjson = (JSONObject)JSON.toJSON(fans);
			
			//活动列表数据
			String activityList="";
			//获取有效活动列表
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", corpid);
			paramsMap.put("status", 1);
			List<TactivityCheckin> listActivity=(List<TactivityCheckin>)tactivityCheckinService.selectByMap(paramsMap);
			
			if(listActivity!=null && listActivity.size()>0){
				activityList= JSONArray.toJSONString(listActivity);
				if(listActivity.size()==1){
					//只开启一个活动
					response.getWriter().print(callback + "({'status':'1','activityid':'"+listActivity.get(0).getId()+"','activityList':'"+activityList+"','fans':'"+fansjson.toString()+"','fansid':'"+fansid+"'})"); 
					return null;
				}else{
					//开启多个活动
					response.getWriter().print(callback + "({'status':'1','activityid':'0','activityList':'"+activityList+"','fans':'"+fansjson.toString()+"','fansid':'"+fansid+"'})"); 
					return null;
				}
			}else{
				response.getWriter().print(callback + "({'status':'-1','msg':'活动尚未开启，敬请期待！'})");  
				return null;
			}
			
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	/**
	 * 4  手机页面 提交签到
	 * 2016-8-27 add by wayne 
	 * */
	@SuppressWarnings({ "finally", "unchecked" })
	@RequestMapping(value="/submitCheckin/{corpid}/{fansid}/{activityid}")
	@ResponseBody
	public String submitCheckin(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long fansid,
			@PathVariable java.lang.Long activityid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("submitCheckin");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//查询获取的fansid 是否  保存过
		Tfans fans=tfansService.selectById(fansid);
		if(null == fans){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		if(CommonUtil.isNull(activityid)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		TactivityCheckin tactivityCheckin = tactivityCheckinService.selectById(activityid);
		if(null == tactivityCheckin || tactivityCheckin.getStatus().equals(0)){
			response.getWriter().print(callback + "({'status':'-2','msg':'不存在此签到活动信息！'})");  
			return null;
		}
		
		if(tactivityCheckin.getStatus().equals(2)){
			response.getWriter().print(callback + "({'status':'-1','msg':'活动尚未开启，敬请期待！'})");  
			return null;
		}
		
		try{
			
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", Long.valueOf(corpid));
			paramsMap.put("activityid", Long.valueOf(activityid));
			paramsMap.put("status", 1);
			paramsMap.put("fansid",fansid);
			
			List<TcheckinRecord> listRecord = (List<TcheckinRecord>)tcheckinRecordService.selectByMap(paramsMap);
			if(listRecord!=null && listRecord.size()>0){
				response.getWriter().print(callback + "({'status':'-3','msg':'您已经签到过了！'})");  
				return null;
			}
			
			//新增签到
			TcheckinRecord tRecord= new TcheckinRecord();
			tRecord.setCorpid(corpid);
			tRecord.setActivityid(activityid);
			tRecord.setFansid(fansid);
			tRecord.setCreateDate(new Date());
			tRecord.setStatus(1);
			
			tcheckinRecordService.insert(tRecord);
			
			response.getWriter().print(callback + "({'status':'1','msg':'签到成功！'})"); 
			return null;
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	
	/**
	 * 5  手机页面  发言页面获取用户和活动信息
	 * 2016-8-27 add by wayne 
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/getComment/{corpid}/{fansid}/{activityid}")
	@ResponseBody
	public String getComment(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long fansid,
			@PathVariable java.lang.Long activityid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("getComment");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//查询获取的fansid 是否  保存过
		Tfans fans=tfansService.selectById(fansid);
		if(null == fans){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		if(CommonUtil.isNull(activityid)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		TactivityCheckin tactivityCheckin = tactivityCheckinService.selectById(activityid);
		if(null == tactivityCheckin || tactivityCheckin.getStatus().equals(0)){
			response.getWriter().print(callback + "({'status':'-2','msg':'不存在此签到活动信息！'})");  
			return null;
		}
		
		if(tactivityCheckin.getStatus().equals(2)){
			response.getWriter().print(callback + "({'status':'-1','msg':'活动尚未开启，敬请期待！'})");  
			return null;
		}
		
		try{
			//将java对象转换为json对象
			JSONObject fansjson = (JSONObject) JSON.toJSON(fans);
			response.getWriter().print(callback + "({'status':'1','activityid':'"+activityid+"','fans':'"+fansjson.toString()+"','fansid':'"+fansid+"'})");
			return null;
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	/**
	 * 6 手机页面 提交发言评论
	 * 2016-8-27 add by wayne 
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/submitComment/{corpid}/{fansid}/{activityid}")
	@ResponseBody
	public String submitComment(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long fansid,
			@PathVariable java.lang.Long activityid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("submitComment");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//查询获取的fansid 是否  保存过
		Tfans fans=tfansService.selectById(fansid);
		if(null == fans){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		if(CommonUtil.isNull(activityid)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		TactivityCheckin tactivityCheckin = tactivityCheckinService.selectById(activityid);
		if(null == tactivityCheckin || tactivityCheckin.getStatus().equals(0)){
			response.getWriter().print(callback + "({'status':'-2','msg':'不存在此签到活动信息！'})");  
			return null;
		}
		
		if(tactivityCheckin.getStatus().equals(2)){
			response.getWriter().print(callback + "({'status':'-1','msg':'活动尚未开启，敬请期待！'})");  
			return null;
		}
		
		try{
			
			String remark= request.getParameter("remark");
			
			//新增评论
			TcheckinCommentRecord tRecord= new TcheckinCommentRecord();
			tRecord.setCorpid(corpid);
			tRecord.setActivityid(activityid);
			tRecord.setFansid(fansid);
			tRecord.setCreateDate(new Date());
			tRecord.setLastedittime(new Date());
			
			//评论是否审核上墙
			if(tactivityCheckin.getIsflag().equals(1)){
				//审核中
				tRecord.setStatus(2);
			}else{
				//直接上墙
				tRecord.setStatus(1);
			}
			
			//活动评论
			tRecord.setRemark(remark);
			
			tcheckinCommentRecordService.insert(tRecord);
			
			response.getWriter().print(callback + "({'status':'1','msg':'提交成功！'})"); 
			return null;
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
}
