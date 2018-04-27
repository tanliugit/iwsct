package com.letsun.frame.core.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.letsun.frame.core.common.utils.WebUtils;
import com.letsun.frame.core.domain.ErrorLog;
import com.letsun.frame.core.domain.Message;
import com.letsun.frame.core.service.ErrorLogService;

/**
 * @Desc 异常统一处理
 * @author YY<2017年10月17日>
 */
@ControllerAdvice
public class ExceptionController  {
	
	@Autowired ErrorLogService errorLogService;
	
	@ExceptionHandler(value = Throwable.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request,HttpServletResponse response,Throwable ex) {
		PrintWriter writer = null;
		try {
			if (!WebUtils.isAjax(request)) {
				ModelAndView mav = new ModelAndView();
				mav.addObject("exception", ex);
				mav.addObject("url", request.getRequestURL());
				mav.setViewName("/common/error/500");
				return mav;
			} else {
				// 如果是ajax请求，JSON格式返回
				response.setContentType("application/json;charset=UTF-8");
				writer = response.getWriter();
				writer.write(JSON.toJSONString(Message.error("系统错误", ex)));
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//保存错误日志
			saveErrorLog(request,response,ex);
			if(writer!=null){
				writer.close();
			}
		}
		return null;
	}

	
	/**
	 * @Desc 错误日志记录 
	 * @param request
	 * @param handler
	 * @param ex
	 * @author YY<2017年10月17日>
	 */
	private void saveErrorLog(HttpServletRequest request, HttpServletResponse response,Throwable ex){
		String message  = StringUtils.isNotEmpty((String)request.getAttribute("message"))?request.getAttribute("message").toString():"系统错误";
		ErrorLog errorLog  = new ErrorLog();
		errorLog.setErrorCode("500");
		errorLog.setRequestUrl(request.getRequestURL().toString());
		errorLog.setParams(JSON.toJSONString(request.getParameterMap()));
		errorLog.setMessage(message);
		errorLog.setException(getDetailException(ex));
		errorLog.setCreator("system");
		errorLog.setCreateDate(new Date());
		errorLogService.insert(errorLog);
	}
	
	/**
	 * @Desc 获取异常详细信息 
	 * @param e
	 * @return
	 * @author YY<2017年10月17日>
	 */
	private String getDetailException(Throwable e){   
        StringWriter sw = new StringWriter();   
        PrintWriter pw = new PrintWriter(sw, true);   
        e.printStackTrace(pw);   
        pw.flush();   
        sw.flush();   
        return sw.toString();   
	}	
}
