/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.TweblookPvuv;
import com.letsun.iwsct.itface.domain.TweblookUrl;
import com.letsun.iwsct.itface.mapper.TweblookPvuvMapper;
import com.letsun.iwsct.itface.service.TweblookPvuvService;
import com.letsun.iwsct.itface.service.TweblookUrlService;

/**
 * Service实现类
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Service
public class TweblookPvuvServiceImpl extends BaseServiceImpl<TweblookPvuvMapper,TweblookPvuv> implements TweblookPvuvService{
	
	protected final Logger logger = LoggerFactory.getLogger(TweblookPvuvServiceImpl.class);
	
	
	@Autowired
	private TweblookPvuvMapper  tweblookPvuvMapper;
	
	@Autowired
	private TweblookUrlService tweblookUrlService;
	/**
	 * 统计类型：1:URL，2：分行点击，3:（其他）
	 * 保存wap端用户访问日志
	 * @param request
	 */
	public long savePvuvForUrl2(String visitIp,Long corpid,String url, String name){
			
			TweblookUrl weblook=tweblookUrlService.getUrlForUrl(corpid,url);
			if(weblook == null){
				weblook = new TweblookUrl();
				weblook.setCorpid(corpid);
				weblook.setCreateDate(new Date());
				weblook.setUrl(url);
				weblook.setVisitsum(1l);
				weblook.setVisitpsum(1l);
				weblook.setStatus(1);
				weblook.setCreator("-1");
				weblook.setName(name);
				weblook.setModifier("-1");
				weblook.setModifyDate(new Date());
				this.tweblookUrlService.insert(weblook);
				return 1;
			}else{
				
				TweblookPvuv entity = getPvforUrl(corpid, visitIp,url);
				if(null == entity){
					//总人数+1
					weblook.setVisitpsum(weblook.getVisitpsum()+1);
				}
				//总次数+1
				long totalCount = weblook.getVisitsum()+1;
				weblook.setVisitsum(weblook.getVisitsum()+1);
				tweblookUrlService.updateById(weblook);
				
				
				//新增访问记录
				TweblookPvuv newpv = new TweblookPvuv();
				newpv.setCorpid(corpid);
				newpv.setUrl(url);
				newpv.setVisitip(visitIp);
				newpv.setVisitsum(1L);
				newpv.setVisittime(new Date());
				
				newpv.setType(1);
				
				this.insert(newpv);
				return totalCount;
			}
	}
	
	/**
	 * 统计类型：1:URL，2：分行点击，3:（其他） 查询ip访问 url记录
	 * */
	public TweblookPvuv getPvforUrl(Long corpid, String visitip, String url) {

		Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("corpid", corpid);
		paramsMap.put("visitip", visitip);
		paramsMap.put("url", url);

		List<TweblookPvuv> list = (List<TweblookPvuv>) this
				.selectByMap(paramsMap);

		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void savePvuvForUrl(String visitIp, Long corpid,
			String url) {
		// TODO Auto-generated method stub
		
		TweblookUrl weblook=tweblookUrlService.getUrlForUrl(corpid,url);
		if(weblook == null){
			logger.error("微刊访问统计：此企业的微刊链接不存在！！");
			return;
		}else{
			TweblookPvuv entity = getPvforUrl(corpid, visitIp,url);
			if(null == entity){
				//总人数+1
				weblook.setVisitpsum(weblook.getVisitpsum()+1);
			}
			//总次数+1
			weblook.setVisitsum(weblook.getVisitsum()+1);
			//tweblookUrlDao.update(weblook);
			this.tweblookUrlService.updateById(weblook);
			
			
			//新增访问记录
			TweblookPvuv newpv = new TweblookPvuv();
			newpv.setCorpid(corpid);
			newpv.setUrl(url);
			newpv.setVisitip(visitIp);
			newpv.setVisitsum(1L);
			newpv.setVisittime(new Date());
			
			newpv.setType(1);
			
			this.insert(newpv);
		}
	}
	
	
	
	
}
