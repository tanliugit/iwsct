/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service;

import com.letsun.iwsct.itface.domain.TweblookUrl;
import com.letsun.frame.core.service.BaseService;

/**
 * Service接口
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
public interface TweblookUrlService extends BaseService<TweblookUrl>{
	public TweblookUrl getUrlForUrl(Long corpid, String url);
	
	
	public void addShareSumUrl(Long corpid,String url);
}
