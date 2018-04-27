/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service;

import javax.servlet.http.HttpServletRequest;

import com.letsun.iwsct.itface.domain.TweblookPvuv;
import com.letsun.frame.core.service.BaseService;

/**
 * Service接口
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
public interface TweblookPvuvService extends BaseService<TweblookPvuv>{
	public long savePvuvForUrl2(String visitIp,Long corpid,String url, String name);
	
	public void savePvuvForUrl(String visitIp,Long corpid,String url);
}
