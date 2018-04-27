package com.letsun.iwsct.itface.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.google.gson.Gson;
import com.letsun.iwsct.itface.common.CUtil;
import com.letsun.iwsct.itface.common.CommonUtil;
import com.letsun.iwsct.itface.common.EmojiFilter;
import com.letsun.iwsct.itface.common.FileUtil;
import com.letsun.iwsct.itface.common.GetWXJsApSign;
import com.letsun.iwsct.itface.common.LotteryUtil;
import com.letsun.iwsct.itface.common.RotateImage;
import com.letsun.iwsct.itface.common.ServiceConstants;
import com.letsun.iwsct.itface.common.TwoDimensionCode;
import com.letsun.iwsct.itface.domain.TactivityCheckin;
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
import com.letsun.iwsct.itface.domain.Tvote;
import com.letsun.iwsct.itface.domain.TweblookUrl;
import com.letsun.iwsct.itface.model.Gift;
import com.letsun.iwsct.itface.model.RankingManifestVo;
import com.letsun.iwsct.itface.model.Vote;
import com.letsun.iwsct.itface.service.TactivityCheckinService;
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
import com.letsun.iwsct.itface.service.TvoteService;
import com.letsun.iwsct.itface.service.TweblookPvuvService;
import com.letsun.iwsct.itface.service.TweblookUrlService;

@Controller
@RequestMapping("w/crossDomain")
public class WxCrossDomainController extends BaseController {
	
	protected final Logger logger = LoggerFactory
			.getLogger(WxCrossDomainController.class);
	
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
	private TweblookUrlService tweblookUrlService;
	
	@Autowired
	private TlotteryBuyerService tlotteryBuyerService;
	
	@Autowired
	private TlotteryRecordService tlotteryRecordService;
	
	@Autowired
	private TlotteryActivityService tlotteryActivityService;
	
	@Autowired
	private TlotteryPrizeService tlotteryPrizeService;
	
	@Value("${SYSURL}")
	private String SYSURL;
	
	//tactivityCheckinManager;
	@Autowired
	private TactivityCheckinService tactivityCheckinService;
	
	@Autowired
	private TcheckinRecordService tcheckinRecordService;
	
	@Autowired
	private TfansLaService tfansLaService;
	
	@Autowired
	private TvoteService tvoteService;
	
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
			this.getPrintWriter(response).print(
					callback + "({'status':'" + status + "','msg':'" + msg
							+ "'})");
			return null;
		}

		
		Tposition tposition = tpositionService.selectById(positionid);
		if (tposition == null) {
			msg = "数据出错！";
			this.getPrintWriter(response).print(
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
				this.getPrintWriter(response).print(
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
						this.getPrintWriter(response).print(
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
			this.getPrintWriter(response).print(
					callback + "({'status':'" + status + "','msg':'" + msg
							+ "', 'count':'" + count + "'})");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getPrintWriter(response).print(
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
			
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno, mytoken)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//验证参数是否合法
		if(!CommonUtil.hash(wxno+"wnzlxcy").equals(mytoken)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
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
					this.getPrintWriter(response).print(callback + "({'status':'2','msg':'您已中过奖！','lotteryNum':'"+lotteryNum+"'})");  
					return null;
				}
				
				this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'已抽过奖！','lotteryNum':'"+lotteryNum+"'})"); 
				return null;
			}
			
			//奖项规则
			List<TlotteryActivity> activityList = this.tlotteryActivityService.queryActivityList(corpid);
			TlotteryActivity activity = new TlotteryActivity();
			
			//判断活动是否存在
			if(activityList.size() >0){
				activity = activityList.get(0);
			}else{
				this.getPrintWriter(response).print(callback + "({'status':'0','msg':'暂无抽奖活动！','lotteryNum':'"+lotteryNum+"'})"); 
				return null;
			}
			
			List<TlotteryPrize> prizeList = tlotteryPrizeService.getlistPrizeForlotteryid(activity.getId());
			if(prizeList==null || prizeList.size()==0){
				this.getPrintWriter(response).print(callback + "({'status':'0','msg':'暂无抽奖活动！','lotteryNum':'"+lotteryNum+"'})"); 
				return null;
			}
		
			msg="没有抽过奖！";
			status="1";
			
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"','lotteryNum':'"+lotteryNum+"'})"); 
			return null;
			
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"','lotteryNum':'"+lotteryNum+"'})"); 
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
			
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno, mytoken)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//验证参数是否合法
		if(!CommonUtil.hash(wxno+"wnzlxcy").equals(mytoken)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
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
				this.getPrintWriter(response).print(callback + "({'status':'0','msg':'暂无抽奖活动！'})");  
				return null;
			}
			
			List<TlotteryPrize> prizeList = this.tlotteryPrizeService.getlistPrizeForlotteryid(activity.getId());
			//List<TlotteryPrize> prizeList = tlotteryPrizeManager.getlistPrizeForlotteryid(activity.getId());
			if(prizeList==null || prizeList.size()==0){
				this.getPrintWriter(response).print(callback + "({'status':'0','msg':'暂无抽奖活动！'})");  
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
				
				this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+tlotteryRecord.getId()+"','level':'"+prize.getLevel()+"'})");    
				return null;  
			}else{
				this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'很遗憾，您没有中奖！'})");    
				return null;  
			}
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
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
			
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(recordid)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错'})");  
			return null;
		}
		
		//查询中奖记录信息	
		
		TlotteryRecord tRecord=this.tlotteryRecordService.selectById(recordid);
		//TlotteryRecord tRecord=recordManager.getById(recordid);
		if(tRecord==null || tRecord.getBuyerid()==null){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'没有您的中奖记录信息，请先参加我们的活动！'})");  
			return null;
		}
		
		//中奖后的表单提交
		String candidate = request.getParameter("candidate");
		String mobile = request.getParameter("mobile");
		String remark = request.getParameter("remark");
		String address = request.getParameter("address");
		
		if(CommonUtil.isNull(candidate,remark,mobile)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'输入数据不完整！'})");  
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
			
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'提交成功'})");    
			return null; 
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
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
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		Tfans fans= this.tfansService.selectById(fansid);
		//Tfans fans= tfansManager.getById(fansid);
		if(fans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
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
				
				this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'"+listResume.get(0).getRemarks()+"','fans':'"+ JSONObject.toJSONString(fans)+
						"','cityid':'"+positionId+"','city':'"+positionName+
						"','name':'"+listResume.get(0).getCandidate()+"','content': '"+ listResume.get(0).getContent() +"'})");  
				return null;
			}else{
				msg="查询成功:没有提交过资料！";
				status="1";
				this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"','fans':'"+ JSONObject.toJSONString(fans)+"'})");    
				return null;  
			}
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
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
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		//获取score
		if(CommonUtil.isNull(score)){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		Tfans fans = this.tfansService.selectById(fansid);
		//Tfans fans= tfansManager.getById(fansid);
		if(fans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
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
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"','myranking':'"+myranking+"','scoreranking':'"+scoreranking+"'})");    
			return null;   
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
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
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}


		//获取每人分值
		if(CommonUtil.isNull(avescore)){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//Tfans fans= tfansManager.getById(fansid);
		Tfans fans = this.tfansService.selectById(fansid);
		if(fans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
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
			//JSONObject fansjson = (JSONObject)JSON.toJSON(fans);
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
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+msg+"','cityid':'"+cityid+"','city':'"+city+"','sumscore':'"+sumscore+"','fans':'"+ JSON.toJSONString(fans)+"','list':'"+ fansLajson+"'})");  
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	/** 2016-10-1 add by wayne 
	 * 提交好友助力  get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/submitHelp/{corpid}/{fansid}/{lafansid}/{helpscore}/{cityid}")
	@ResponseBody
	public String submitHelp(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			@PathVariable Long lafansid,
			@PathVariable Integer helpscore,
			@PathVariable Integer cityid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="查询失败";
		String callback=request.getParameter("submitHelpback");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//好友助力分值
		if(CommonUtil.isNull(helpscore,cityid)){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//Tfans fans= tfansManager.getById(fansid);
		
		Tfans fans = this.tfansService.selectById(fansid);
		if(fans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		
		
		//Tfans lafans= tfansManager.getById(lafansid);
		
		Tfans lafans= this.tfansService.selectById(lafansid);
		
		if(lafans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		try{
			//判断好友是否助力
			TfansLa fansLa=tfansLaService.chenckTfansLa(fansid, lafansid, corpid);
			if(fansLa!=null){
				msg="好友已经助力过了！";
				this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'"+msg+"'})");  
				return null;
			}
			
			//保存好友助力数据
			TfansLa newla=new TfansLa();
			newla.setCorpid(corpid);
			newla.setFansid(fansid);
			newla.setLafansid(lafansid);
			newla.setStatus(1);
			newla.setCreateDate(new Date());
			newla.setRemark(helpscore.toString());
			newla.setShopid(cityid);
			
			tfansLaService.insert(newla);
			
			//更新游戏总分数
			fans.setHelpscore(fans.getHelpscore()==null?helpscore:(fans.getHelpscore()+helpscore));
			//更新好友助力人数
			fans.setHelpnum(fans.getHelpnum()==null?0:(fans.getHelpnum()+1));
			tfansService.updateById(fans);
			
			msg="提交成功！";
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+msg+"'})");  
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	
	
	/** 2016-10-1 add by wayne 
	 * 查询同城市的排行榜  按游戏总分查询
	 *   get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/queryRankinglist/{corpid}/{fansid}/{cityid}/{rows}")
	@ResponseBody
	public String queryRankinglist(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			@PathVariable Long cityid,
			@PathVariable Integer rows,
			Boolean excludeZero,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="查询失败";
		String callback=request.getParameter("queryRankinglist");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//好友助力分值
		if(CommonUtil.isNull(cityid,rows)){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//Tfans fans= tfansManager.getById(fansid);
		Tfans fans = this.tfansService.selectById(fansid);
		if(fans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//城市名称
		String city="";
		if(CommonUtil.isNotNull(fans.getShopid())){
			//Tposition position=tpositionManager.getById(Long.valueOf(fans.getShopid()));
			Tposition position= this.tpositionService.selectById(Long.valueOf(fans.getShopid()));
			if(position==null){
				msg="数据出错！";
				this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
				return null;
			}
			city=position.getPositionname();
		}
		
		try{
			//获取自己的 游戏分数名次
			Integer scoreranking=tfansService.getMyRankingForCore(corpid, cityid.intValue(), fans.getHelpscore(), excludeZero);
			
			//列表数据
			String listjson="";
			//同一城市下游戏分数排行榜
			List<Tfans> listfans=tfansService.queryRankinglist(corpid, cityid.intValue(),rows, excludeZero);
			if(listfans!=null && listfans.size()>0){
				//列表对象
				//listjson = JSONArray.fromObject(listfans).toString();
				listjson = JSONArray.toJSONString(listfans);
				
			}
			
			msg="查询成功！";
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+msg+"','scoreranking':'"+scoreranking+"','fans':'"+JSONObject.toJSONString(fans)+"','city':'"+city+"','list':'"+listjson+"'})");  
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	/** 2016-10-1 add by wayne 
	 * 查询同城市的排行榜  按好友助力总人数查询
	 *   get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/queryRankinglistForNum/{corpid}/{fansid}/{cityid}/{rows}")
	@ResponseBody
	public String queryRankinglistForNum(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			@PathVariable Long cityid,
			@PathVariable Integer rows,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="查询失败";
		String callback=request.getParameter("queryRankinglist");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//好友助力分值
		if(CommonUtil.isNull(cityid,rows)){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//Tfans fans= tfansManager.getById(fansid);
		Tfans fans= this.tfansService.selectById(fansid);
		if(fans==null){
			msg="数据出错！";
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
			return null;
		}
		
		//城市名称
		String city="";
		if(CommonUtil.isNotNull(fans.getShopid())){
			//Tposition position=tpositionManager.getById(Long.valueOf(fans.getShopid()));
			
			Tposition position = this.tpositionService.selectById(Long.valueOf(fans.getShopid()));
			if(position==null){
				msg="数据出错！";
				this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");  
				return null;
			}
			city=position.getPositionname();
		}
		
		try{
			//获取自己的  助力人数名次
			Integer myranking=tfansService.getMyRankingForNum(corpid, cityid.intValue(), fans.getHelpnum());
			
			//列表数据
			String listjson="";
			//同一城市下游戏分数排行榜
			List<Tfans> listfans=tfansService.queryRankinglistForNum(corpid, cityid.intValue(),rows);
			if(listfans!=null && listfans.size()>0){
				//列表对象
				//listjson = JSONArray.fromObject(listfans).toString();
				listjson = JSONArray.toJSONString(listfans);
			}
			
			msg="查询成功！";
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+msg+"','myranking':'"+myranking+"','fans':'"+JSONObject.toJSONString(fans)+"','city':'"+city+"','list':'"+listjson+"'})");  
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	
	/** 2016-10-1 add by wayne 
	 * 12. 生成二维码，返回二维码图片链接地址
	 *   get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/encoderQRCode/{corpid}/{fansid}")
	@ResponseBody
	public String encoderQRCode(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable Long fansid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="生成失败";
		String callback=request.getParameter("encoderQRCode");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		String content=request.getParameter("content");
		if(CommonUtil.isNull(content,fansid)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		try{
			/** 签到 生成二维码图片并返回链接地址*/
			//String filename = SYSURL+"/upload/checkinpic/"+tactivityCheckin.getId()+".png";
			//String filepath = FileUtil.getFilePath(request, "/upload", corpid, filename);
			
			String filename = "qrcode/code_"+fansid+".png";
			String filePath = request.getSession().getServletContext().getRealPath("/upload");
			if (CommonUtil.isNotNull(filename)) {
				filePath += File.separator + filename;
			}
			
			File file = new File(filePath);
			//判断文件是否存在,不存在则创建图片文件
			if(!file.exists()){
				file.getParentFile().mkdirs();
				TwoDimensionCode handler = new TwoDimensionCode();
				handler.encoderQRCode(content, filePath, "png",8);
			}
			
			//返回二维码图片路径
			String qrcodeUrl=SYSURL+"/upload/"+filename;
			
			msg="生成成功！";
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+msg+"','qrcodeUrl':'"+qrcodeUrl+"'})");  
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
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
	 * 2015-1-4 add by wayne
	 * 异步提交地址   上传图片
	 * 返回图片路径
	 * *//*
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
				
				*//** 2016-6-16 add by wayne *//*
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
			logger.error("上传图片提交出错："+e.getMessage());
		}finally{
			return map;
		}
	}
	

	*//** 2015-8-17 add by wayne 
	 * 读取图片的EXIF信息 图片旋转 处理
	 * *//*
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
	 			123
	        
	    } catch (ImageProcessingException e) {
		    	isPro = false;
	    } catch (IOException e) {
	    	isPro = false;
	    }finally{
	    	isImgFile.close();
	    	return isPro;
	    }
	}
	*/
	
	
	/**
	 * 18----PC签到页面的初始化 
	 * 2016-8-27 add by wayne 
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/queryActivity/{corpid}/{activityid}")
	@ResponseBody
	public String queryActivity(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long activityid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("queryActivity");  
		
		if(!chenckCorpforPc(mapmsg, request, corpid)) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		if(CommonUtil.isNull(activityid)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
//		TactivityCheckin tactivityCheckin = tactivityCheckinManager.getById(activityid);
		TactivityCheckin tactivityCheckin = tactivityCheckinService.selectById(activityid);
		if(null == tactivityCheckin || tactivityCheckin.getStatus().equals(0)){
			this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'不存在此签到活动信息！'})");  
			return null;
		}
		
		if(tactivityCheckin.getStatus().equals(2)){
			this.getPrintWriter(response).print(callback + "({'status':'-2','msg':'活动尚未开启，敬请期待！'})");  
			return null;
		}
		
		try{
			
			//将java对象转换为json对象
			//JSONObject activityjson = (JSONObject) JSONObject.toJSON(tactivityCheckin);
			
			this.getPrintWriter(response).print(callback + "({'status':'1','activity':'"+JSONObject.toJSONString(tactivityCheckin)+"'})"); 
			return null;
			
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	
	
	/**
	 * 19--- 获取最新的签到人
	 * 2016-8-27 add by wayne 
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/getlistRecord/{corpid}/{activityid}/{recordid}")
	@ResponseBody
	public String getlistRecord(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.Long activityid,
			@PathVariable java.lang.Long recordid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("getlistRecord");  
		
		if(!chenckCorpforPc(mapmsg, request, corpid)) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		if(CommonUtil.isNull(activityid)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//TactivityCheckin tactivityCheckin = tactivityCheckinManager.getById(activityid);
		
		TactivityCheckin tactivityCheckin = this.tactivityCheckinService.selectById(activityid);
		if(null == tactivityCheckin || tactivityCheckin.getStatus().equals(0)){
			this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'不存在此签到活动信息！'})");  
			return null;
		}
		
		if(tactivityCheckin.getStatus().equals(2)){
			this.getPrintWriter(response).print(callback + "({'status':'-2','msg':'活动尚未开启，敬请期待！'})");  
			return null;
		}
		
		try{
			
			//获取最新的 签到人 
			//List<TcheckinRecord> listRecord = (List<TcheckinRecord>)tcheckinRecordManager.getListNewRecord(corpid,activityid,1,recordid);
			List<TcheckinRecord> listRecord = tcheckinRecordService.getListNewRecord(corpid,activityid,1,recordid);
			if(listRecord!=null && listRecord.size()>0){
				
				//列表数据
				String listfans="";
				List<Tfans> listfan =new ArrayList<Tfans>();
				for (TcheckinRecord tcheckinRecord : listRecord) {
					Long fansId = tcheckinRecord.getFansid();
					Tfans tfans = this.tfansService.selectById(fansId);
					listfan.add(tfans);
					//listfans=JSONArray.fromObject(listfan).toString();
					listfans = JSONArray.toJSONString(listfan);
				}
				
				//最新一条记录的id
				this.getPrintWriter(response).print(callback + "({'status':'1','msg':'请求成功！','lastid':'"+listRecord.get(0).getId()+"','listfans':'"+listfans+"'})"); 
				return null;
				
			}else{
				this.getPrintWriter(response).print(callback + "({'status':'-3','msg':'没有新数据！'})"); 
				return null;
			}
			
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	/**
	 * 检查用户是否绑定 
	 * @param checkBind
	 * @param response
	 * @param wxno
	 * @param corpid
	 * @throws Exception
	 */
	@RequestMapping("bind/check/{corpid}")
	public void checkBind(ModelMap model, String checkBindCallback, HttpServletResponse response, String wxno, @PathVariable Long corpid, HttpServletRequest request) throws Exception {
		if (corpid == null || StringUtils.isBlank(wxno)) {
			this.getPrintWriter(response).print(checkBindCallback + "({'status':'-1','msg':'参数不能空'})");
			return ;
		}
		Tfans fans = this.updateFans(model, corpid, request, wxno);
		int playCount = 0;
		if (fans.getHelpnum() != null) {
			playCount = fans.getHelpnum();
		}
		String writeString = String.format("%s({'status':'1', 'name':'%s', 'photourl':'%s', 'playCount': %s, 'highestScore': %s})", checkBindCallback, fans.getNickname(), fans.getPhotourl(), playCount, fans.getHelpscore());
		this.getPrintWriter(response).print(writeString);
	}
	
	
	/**
	 * 保存游戏分数
	 * @param saveGameScoreCallback
	 * @param response
	 * @param wxno
	 * @param corpid
	 * @param score
	 * @throws Exception
	 */
	@RequestMapping("game/score/save/{corpid}")
	public void saveGameScore(String saveGameScoreCallback, HttpServletResponse response, String wxno, @PathVariable Long corpid, Integer score) throws Exception {
		PrintWriter out = this.getPrintWriter(response);
		try {
			Tfans fans = tfansService.chenckTfans(wxno, corpid);
			if (fans == null) {
				out.print(saveGameScoreCallback + "({'status':'0','msg':'用户未绑定'})");
				return ;
			}
			int playCount = fans.getHelpnum() == null ? 0 : fans.getHelpnum();
			fans.setHelpnum(++ playCount);
			score = score == null ? 0 : score;
			Integer orgiScore = fans.getHelpscore() == null ? 0 : fans.getHelpscore();
			if (orgiScore == 0 || score > orgiScore) {
				fans.setHelpscore(score);
			}
			this.tfansService.updateById(fans);
			out.print(saveGameScoreCallback + "({'status':'1','msg':'保存成功'})");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			out.print(saveGameScoreCallback + "({'status':'0','msg':'保存失败'})");
		}
		
	}

	/**
	 * 
	 * @param model
	 * @param corpid
	 * @param request
	 * @param wxno
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Tfans updateFans(ModelMap model, Long corpid, HttpServletRequest request, String wxno) {
		model.addAttribute("wxno", wxno);

		String nickname = null;
		try {
			nickname = request.getParameter("nickname") != null
					? java.net.URLDecoder.decode(request.getParameter("nickname"), "UTF-8") : "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNoneBlank(nickname)) {
			try {
				if (nickname.equals(new String(nickname.getBytes("ISO8859-1"), "ISO8859-1"))) {
					nickname = new String(nickname.getBytes("ISO8859-1"), "UTF-8");
				}
			} catch (UnsupportedEncodingException ignore) {}
		}
		if (CommonUtil.isNotNull(nickname)) {
			nickname = EmojiFilter.filterEmoji(nickname);
			// nickname=nickname.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]",
			// "*");
		}

		// 查询获取的openid 是否 保存过
		Tfans fans = tfansService.chenckTfans(wxno, corpid);
		if (null == fans) {
			// 没有关注过 可以参加 临时粉丝状态为2
			fans = new Tfans();
			fans.setWxno(wxno);
			fans.setCorpid(corpid);
			fans.setCreateDate(new Date());
			fans.setScore(0);
			fans.setStatus(2);

			fans.setNickname(nickname);
			fans.setPhotourl(request.getParameter("headimgurl") != null ? request.getParameter("headimgurl") : "");

			tfansService.insert(fans);
			//tfansManager.save(fans);
		} else {
			// 更新图像和昵称
			fans.setNickname(nickname);
			fans.setPhotourl(request.getParameter("headimgurl") != null ? request.getParameter("headimgurl") : "");
			
			tfansService.updateById(fans);
			//tfansManager.update(fans);
		}

		model.addAttribute("fans", fans);
		model.addAttribute("fansid", fans.getFansid());
		return fans;
	}
	
	/***
	 * 检测企业账号信息
	**/
	private boolean chenckCorpforPc(Map<Object,Object> mapmsg,HttpServletRequest request, java.lang.Long corpid) {
//		Tcorp tcorp = tcorpManager.getById(corpid);
		Tcorp tcorp = tcorpService.selectById(corpid);
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
		
		
		return true;
	}
	
	/**
	 * 17  手机页面 提交签到
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
			
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		//查询获取的fansid 是否  保存过
//		Tfans fans=tfansManager.getById(fansid);
		Tfans fans = tfansService.selectById(Long.valueOf(fansid));
		if(null == fans){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		if(CommonUtil.isNull(activityid)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
//		TactivityCheckin tactivityCheckin = tactivityCheckinManager.getById(activityid);
		TactivityCheckin tactivityCheckin = tactivityCheckinService.selectById(activityid);
		
		if(null == tactivityCheckin || tactivityCheckin.getStatus().equals(0)){
			this.getPrintWriter(response).print(callback + "({'status':'-2','msg':'不存在此签到活动信息！'})");  
			return null;
		}
		
		if(tactivityCheckin.getStatus().equals(2)){
			this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'活动尚未开启，敬请期待！'})");  
			return null;
		}
		
		try{
			
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", Long.valueOf(corpid));
			paramsMap.put("activityid", Long.valueOf(activityid));
			paramsMap.put("status", 1);
			paramsMap.put("fansid",fansid);
			
//			List<TcheckinRecord> listRecord = (List<TcheckinRecord>)tcheckinRecordManager.findAllByPropertyes(TcheckinRecord.class,paramsMap);
			List<TcheckinRecord> listRecord = tcheckinRecordService.selectByMap(paramsMap);
			if(listRecord!=null && listRecord.size()>0){
				this.getPrintWriter(response).print(callback + "({'status':'-3','msg':'您已经签到过了！'})");  
				return null;
			}
			
			//新增签到
			TcheckinRecord tRecord= new TcheckinRecord();
			tRecord.setCorpid(corpid);
			tRecord.setActivityid(activityid);
			tRecord.setFansid(fansid);
//			tRecord.setCreatetime(new Date());
			tRecord.setCreateDate(new Date());
			tRecord.setStatus(1);
			
//			tcheckinRecordManager.save(tRecord);
			tcheckinRecordService.insert(tRecord);
			
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'签到成功！'})"); 
			return null;
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	
	 /**
	 * 16  手机签到初始化页面-获取活动列表
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
			
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno, mytoken)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//验证参数是否合法
		if(!CommonUtil.hash(wxno+"wnzlxcy").equals(mytoken)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}

		//查询获取的fansid 是否  保存过
//		Tfans fans=tfansManager.getById(fansid);
		Tfans fans = tfansService.selectById(Long.valueOf(fansid));
		if(null == fans){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		
		try{
			//将java对象转换为json对象
			//JSONObject fansjson = (JSONObject) JSONObject.toJSON(fans);
			
			//活动列表数据
			String activityList="";
			//获取有效活动列表
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", corpid);
			paramsMap.put("status", 1);
//			List<TactivityCheckin> listActivity=(List<TactivityCheckin>)tactivityCheckinManager.findAllByPropertyes(TactivityCheckin.class, paramsMap);
			List<TactivityCheckin> listActivity = tactivityCheckinService.selectByMap(paramsMap);
			if(listActivity!=null && listActivity.size()>0){
				activityList= JSONArray.toJSONString(listActivity).toString();
				if(listActivity.size()==1){
					//只开启一个活动
					this.getPrintWriter(response).print(callback + "({'status':'1','activityid':'"+listActivity.get(0).getId()+"','activityList':'"+activityList+"','fans':'"+JSONObject.toJSONString(fans)+"','fansid':'"+fansid+"'})"); 
					return null;
				}else{
					//开启多个活动
					this.getPrintWriter(response).print(callback + "({'status':'1','activityid':'0','activityList':'"+activityList+"','fans':'"+JSONObject.toJSONString(fans)+"','fansid':'"+fansid+"'})"); 
					return null;
				}
			}else{
				this.getPrintWriter(response).print(callback + "({'status':'-1','msg':'活动尚未开启，敬请期待！'})");  
				return null;
			}
			
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	/** 2016-10-1 add by wayne 
	 * 14. 微刊链接后台信息	创建人	创建时间	访问总人数	访问总次数	分享总次数	
	 *   get方式提交  用于外部跨域访问
	 * */
	@SuppressWarnings("finally")
	@RequestMapping(value="/getweblook/{corpid}")
	@ResponseBody
	public String getweblookurl(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
//		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="false";
		String msg="查询失败";
		String callback=request.getParameter("getweblook");  
		
//		if(!chenckCorp(mapmsg, request, corpid)) {
//			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
//			return null;
//		}
		
		String weblookurl=request.getParameter("weblookurl");
		if(CommonUtil.isNull(weblookurl)){
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		try{
//			TweblookUrl weblook = tweblookUrlManager.getUrlForUrl(corpid, weblookurl);
			TweblookUrl weblook = tweblookUrlService.getUrlForUrl(corpid, weblookurl);
			if(weblook!=null){
				msg="查询成功！";
				this.getPrintWriter(response).print(callback + "({'status':'1','msg':'"+msg+"','weblook':'"+JSONObject.toJSONString(weblook)+"'})");  
				return null;
			}
			
			this.getPrintWriter(response).print(callback + "({'status':'1','msg':'暂无数据'})");  
			return null;
		}catch (Exception e) {
			this.getPrintWriter(response).print(callback + "({'status':'"+status+"','msg':'"+msg+"'})");    
		}finally{
			return null;   
		}
	}
	
	
	
	/**
	 * 获取游戏排行榜
	 * @param getRankingCallback
	 * @param response
	 * @param corpid
	 * @throws Exception
	 */
	@RequestMapping("ranking/get/{corpid}/{limit}")
	public void getRanking(String getRankingCallback, HttpServletResponse response, @PathVariable Long corpid, @PathVariable Integer limit, String openid) throws Exception { 
		PrintWriter out = this.getPrintWriter(response);
		try {
			Long rank_num = this.tfansService.queryOneselfRanking(corpid, openid);
			List<RankingManifestVo> list = this.tfansService.queryRanking(corpid, limit);
			out.print(String.format("%s({'status':'1','data':%s,'rank_num':%s})", getRankingCallback, new Gson().toJson(list), new Gson().toJson(rank_num)));
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			out.print(String.format("%s({'status':'0','data':%s})", getRankingCallback, "获取排行榜失败"));
		}
		
	}
	
	
	/**
	 * 获取投票活动问题列表
	 * @param model
	 * @param request
	 * @param corpid
	 * @param fansid
	 * @param wxno
	 * @param mytoken
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "finally" })
	@RequestMapping(value="/getVoteDetail/{corpid}/{wxno}")
	@ResponseBody
	public String getVoteDetail(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.String wxno,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("getAllVote");  
		
		if(!chenckCorp(mapmsg, request, corpid)) {
			
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		if(CommonUtil.isNull(wxno)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		//查询获取的fansid 是否  保存过
		Tfans fans=tfansService.chenckTfans(wxno,corpid);
		if(null == fans){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		
		try{
			//将java对象转换为json对象
			//JSONObject fansjson = JSONObject.parseObject(fans);
			//JSONObject fansjson = (JSONObject)JSONObject.toJSON(fans);
			
			//投票列表数据
			String questionList="";
			//获取有效活动列表
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", corpid);
			List<Vote> voteList = tvoteService.findCount(corpid);
			
			if(voteList!=null && voteList.size()>0){
				//questionList= JSONArray.fromObject(voteList).toString();
				
				questionList = JSONArray.toJSONString(voteList);
				
				response.getWriter().print(callback + "({'status':'1','voteList':'"+questionList+"','fans':'"+JSONObject.toJSONString(fans)+"'})"); 
				return null;
			}else{
				response.getWriter().print(callback + "({'status':'-1','msg':'投票尚未开启，敬请期待！'})");  
				return null;
			}
			
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	
	/**
	 * 提交投票
	 * @param model
	 * @param request
	 * @param corpid
	 * @param fansid
	 * @param voteid
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "finally"})
	@RequestMapping(value="/submitVote/{corpid}/{wxno}/{optid}")
	@ResponseBody
	public String submitVote(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.String wxno,
			@PathVariable java.lang.Long optid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("submitVote");  
		
//		if(!chenckCorp(mapmsg, request, corpid)) {
//			
//			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
//			return null;
//		}
		
		//检测企业账号信息
		if(!chenckCorp(mapmsg, corpid)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}
		
		//查询获取的fansid 是否  保存过
		//Tfans fans=tfansManager.chenckTfans(wxno,corpid);
		
		Tfans fans = tfansService.chenckTfans(wxno, corpid);
		if(null == fans){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'参数出错！'})");  
			return null;
		}
		if(!tvoteService.checkUserVote(wxno, corpid)){
			response.getWriter().print(callback + "({'status':'-3','msg':'您今天已进行过3次投票！'})");  
			return null;
		}
		if(!tvoteService.checkOptVote(wxno,optid, corpid)){
			response.getWriter().print(callback + "({'status':'-4','msg':'您今天已对该选手进行过投票！'})");  
			return null;
		}
		
		try{
			
			Tvote entity = new Tvote();
			entity.setCorpid(corpid);
			entity.setWxno(wxno);
			entity.setCreateDate(new Date());
			entity.setOptid(optid);
			tvoteService.insert(entity);
			
			response.getWriter().print(callback + "({'status':'1','msg':'投票成功！'})"); 
			return null;
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	
	
	/**
	 * 获取投票活动问题列表
	 * @param model
	 * @param request
	 * @param corpid
	 * @param fansid
	 * @param wxno
	 * @param mytoken
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "finally" })
	@RequestMapping(value="/pc/getVoteDetail/{corpid}")
	@ResponseBody
	public String getVoteDetailForPc(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="0";
		String msg="提交失败";
		String callback=request.getParameter("getVoteDetail");  
		
		//检测企业账号信息
		if(!chenckCorp(mapmsg, corpid)){
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			return null;
		}

		
		try{
			HttpSession session = request.getSession();
			
			if (session.isNew()){
//	        	session.invalidate();
//	        	session = request.getSession(true);
//				session.setMaxInactiveInterval(arg0);
				request.getSession().getServletContext().setAttribute(session.getId(), null);
	        }
			
			
			//投票列表数据
			String questionList="";
			//获取有效活动列表
			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("corpid", corpid);
			List<Vote> voteList = tvoteService.findCount(corpid);
			if(voteList!=null && voteList.size()>0){
				//questionList= JSONArray.fromObject(voteList).toString();
				questionList = JSONArray.toJSONString(voteList);
				response.getWriter().print(callback + "({'status':'1','voteList':'"+questionList+"','sessionid':'"+session.getId()+"'})"); 
				return null;
			}else{
				response.getWriter().print(callback + "({'status':'-1','msg':'投票尚未开启，敬请期待！'})");  
				return null;
			}
			
		}catch (Exception e) {
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+msg+"'})"); 
		}finally{
			return null;   
		}
	}
	

	/**
	 * 获取投票活动问题列表
	 * @param model
	 * @param request
	 * @param corpid
	 * @param fansid
	 * @param wxno
	 * @param mytoken
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pc/checkLogin/{corpid}/{sessionid}")
	public void checkLogin(ModelMap model, 
			HttpServletRequest request,
			@PathVariable java.lang.Long corpid,
			@PathVariable java.lang.String sessionid,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	  
		Map<Object,Object> mapmsg = new HashMap<Object, Object>();
		String status="-1";
		String msg="提交失败";
		String callback=request.getParameter("checkLogin");  
		String url = "";
		
		try{
			//检测企业账号信息
			if(!chenckCorp(mapmsg, corpid)){
				response.getWriter().print(callback + "({'status':'"+status+"','msg':'"+mapmsg.get("msg")+"'})");  
			}
			
			Object fansObj = request.getSession().getServletContext().getAttribute(sessionid);
			if(fansObj!=null){
				//将java对象转换为json对象
				//JSONObject fansjson = JSONObject.fromObject(fansObj);
				//JSONObject fansjson = (JSONObject)JSONObject.toJSON(fansObj);
				
				response.getWriter().print(callback + "({'status':'1','fans':'"+JSONObject.toJSONString(fansObj)+"'})"); 
			}else{
				//uuid = UUID.randomUUID().toString();
				url = SYSURL+"/pc/HRExtend/vote/qrcode/"+corpid+"/"+sessionid;
				response.getWriter().print(callback + "({'status':'"+status+"','msg':'当前用户未登录！','qrcode_url':'"+url+"'})");  
			}
			
		}catch (Exception e) {
			url = SYSURL+"/pc/HRExtend/vote/qrcode/"+corpid+"/"+sessionid;
			response.getWriter().print(callback + "({'status':'"+status+"','msg':'当前用户未登录！','qrcode_url':'"+url+"'})");  
		}
	}
	
	
	/***
	 * 检测企业账号信息
	**/
	private boolean chenckCorp(Map<Object,Object> mapmsg, java.lang.Long corpid) {
		//Tcorp tcorp = tcorpManager.getById(corpid);
		Tcorp tcorp = tcorpService.selectById(corpid);
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
		mapmsg.put("tcorp", tcorp);
		return true;
	}
	
	/**
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		return out;
	}
}
