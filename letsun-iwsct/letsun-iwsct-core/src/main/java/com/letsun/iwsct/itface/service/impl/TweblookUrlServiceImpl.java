/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.TweblookUrl;
import com.letsun.iwsct.itface.mapper.TweblookUrlMapper;
import com.letsun.iwsct.itface.service.TweblookUrlService;

/**
 * Service实现类
 * 
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Service
public class TweblookUrlServiceImpl extends
		BaseServiceImpl<TweblookUrlMapper, TweblookUrl> implements
		TweblookUrlService {

	protected final Logger logger = LoggerFactory
			.getLogger(TweblookUrlServiceImpl.class);

	/**
	 * 判断微刊URL是否存在
	 * */
	public TweblookUrl getUrlForUrl(Long corpid, String url) {
		Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("corpid", corpid);
		paramsMap.put("url", url);
		paramsMap.put("status", 1);

		List<TweblookUrl> list = this.selectByMap(paramsMap);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addShareSumUrl(Long corpid, String url) {
		// TODO Auto-generated method stub
		TweblookUrl weblook = null;

		Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("corpid", corpid);
		paramsMap.put("url", url);
		paramsMap.put("status", 1);

		List<TweblookUrl> list = (List<TweblookUrl>) this
				.selectByMap(paramsMap);
		if (!list.isEmpty()) {
			weblook = list.get(0);
		}

		if (weblook == null) {
			logger.error("微刊访问统计：此企业的微刊链接不存在！！");
			return;
		} else {
			// 分享次数+1
			weblook.setSharesum(weblook.getSharesum() + 1);
			this.updateById(weblook);
		}
	}

}
