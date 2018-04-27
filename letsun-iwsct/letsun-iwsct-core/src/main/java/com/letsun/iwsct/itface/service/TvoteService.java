/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service;

import java.util.List;

import com.letsun.frame.core.service.BaseService;
import com.letsun.iwsct.itface.domain.Tvote;
import com.letsun.iwsct.itface.model.Vote;

/**
 * Service接口
 * @Author generator
 * @Date <2018年04月25日>
 * @version 1.0
 */
public interface TvoteService extends BaseService<Tvote>{
	public List<Vote> findCount(Long corpid);
	
	
	public boolean checkUserVote(String wxno,Long corpid);
	
	
	public boolean checkOptVote(String wxno,Long optid,Long corpid);
	
	
	
}
