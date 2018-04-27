package com.letsun.iwsct.itface.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letsun.iwsct.itface.common.CommonUtil;
import com.letsun.iwsct.itface.common.EmojiFilter;
import com.letsun.iwsct.itface.common.TwoDimensionCode;
import com.letsun.iwsct.itface.domain.Tcorp;
import com.letsun.iwsct.itface.domain.Tfans;
import com.letsun.iwsct.itface.service.TcorpService;
import com.letsun.iwsct.itface.service.TfansService;

@Controller
@RequestMapping(value = "/pc/HRExtend")
public class PcHRExtendController {

	Log log = LogFactory.getLog(getClass());
	
	
	@Value("${SYSURL}")
	private String SYSURL;

	@Autowired
	private TcorpService tcorpService;
	
	@Autowired
	private TfansService tfansService;
	
	
	/***
	 * 检测企业账号信息
	**/
	private boolean chenckCorp(ModelMap model, java.lang.Long corpid) {
		Tcorp tcorp = tcorpService.selectById(corpid);
		
		if(null == tcorp){
			model.addAttribute("errormsg", "不存在此企业信息！");
			return false;
		}else{
			
			if(tcorp.getStatus() != 1){
		    	if(tcorp.getStatus()==2)
		    		model.addAttribute("errormsg","企业服务已被停用");
		    	else if(tcorp.getStatus()==3){
		    		model.addAttribute("errormsg","此企业账号已被锁定");
		    	}
		    	else{
		    		model.addAttribute("errormsg","此企业账号异常");
		    	}
		    	return false;
			}else if(tcorp.getEnddate()!=null && CommonUtil.compareDay(tcorp.getEnddate(),new Date())==0){
    			model.addAttribute("errormsg","企业服务已到期");
    			return false;
    		}
		}
		model.addAttribute("tcorp", tcorp);
		return true;
	}
	
	/**
	 * 生成二维码
	 */
	@RequestMapping(value = "/vote/qrcode/{corpid}/{sessionid}")
	public void validateCode(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("corpid") Long corpid,
			@PathVariable("sessionid") String sessionid) throws IOException {

		String downUrl = SYSURL + "/pc/HRExtend/vote/" + corpid + "/"
				+ sessionid;
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCode(downUrl, response.getOutputStream());

	}

	/**
	 * ---------------------------- -------- index --------
	 * ----------------------------
	 * 
	 * 2015-1-4 add by wayne 获取openid页面 获取分享页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vote/{corpid}/{sessionid}")
	public String getOpenid(ModelMap model,
			@PathVariable java.lang.Long corpid,
			@PathVariable("sessionid") String sessionid,
			HttpServletRequest request) throws Exception {
		model.addAttribute("SYSURL", SYSURL);
		model.addAttribute("corpid", corpid);
		model.addAttribute("sessionid", sessionid);

		if (CommonUtil.isNull(corpid)) {
			model.addAttribute("errormsg", "参数出错");
			return "/wxopenid/error";
		}

		// 检测企业账号信息
		if (!chenckCorp(model, corpid))
			return "/wxopenid/error";

		model.addAttribute("isNofan", "1");
		String wxno = request.getParameter("wxno");
		if (wxno != null) {

			model.addAttribute("wxno", wxno);

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
				String nickname = request.getParameter("nickname") != null ? java.net.URLDecoder
						.decode(request.getParameter("nickname"), "UTF-8") : "";
				if (CommonUtil.isNotNull(nickname)) {
					nickname = EmojiFilter.filterEmoji(nickname);
				}
				fans.setNickname(nickname);
				fans.setPhotourl(request.getParameter("headimgurl") != null ? request
						.getParameter("headimgurl") : "");
				tfansService.insert(fans);
				model.addAttribute("isNofan", "0");
				model.addAttribute("fans", fans);

			} else {
				Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
				paramsMap.put("corpid", corpid);
				paramsMap.put("idnumber", wxno);
				paramsMap.put("status", 1);

				// 授权登录
				request.getSession().getServletContext()
						.setAttribute(sessionid, fans);

				model.addAttribute("fans", fans);
				return "/pchr/index";
			}

			// 授权登录
			request.getSession().getServletContext()
					.setAttribute(sessionid, fans);

			HttpSession session = request.getSession();
			session.setAttribute("fans", fans);
		}
		return "/pchr/index";
	}

	@RequestMapping(value = "/vote/login/{corpid}/{sessionid}")
	public void login(@PathVariable("corpid") Long corpid, ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("sessionid") String sessionid) {
		// 检测企业账号信息
		try {

			String flag = request.getSession().getServletContext()
					.getAttribute(sessionid)
					+ "";
			if ("true".equals(flag)) {
				PrintWriter out = response.getWriter();

				out.write("true");

				out.flush();

				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** 退出 */
	@RequestMapping(value = "/vote/logout/${corpid}")
	public String logout(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("corpid") Long corpid)
			throws Exception {

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		request.getSession(true);
		return "redirect:/pc/HRExtend/vote/list/" + corpid;
	}
}
