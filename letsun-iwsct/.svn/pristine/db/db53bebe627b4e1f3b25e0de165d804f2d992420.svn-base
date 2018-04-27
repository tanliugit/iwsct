/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service;

import java.util.List;

import com.letsun.frame.core.service.BaseService;
import com.letsun.iwsct.itface.domain.Tfans;
import com.letsun.iwsct.itface.model.RankingManifestVo;

/**
 * Service接口
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
public interface TfansService extends BaseService<Tfans>{
	public Integer getMyRankingForNum(Long corpid,Integer shopid,Integer helpnum);
	
	
	public Integer getMyRankingForCore(Long corpid,Integer shopid,Integer core, Boolean excludeZero);
	
	
	public Tfans chenckTfans(String wxno,Long corpid);
	
	
	public List<Tfans> queryRankinglist(Long corpid,Integer shopid,int rows, Boolean excludeZero);
	
	
	public List<Tfans> queryRankinglistForNum(Long corpid,Integer shopid,int rows);
	
	
	public Long queryOneselfRanking(Long corpid, String openid);
	
	
	public List<RankingManifestVo> queryRanking(Long corpid, Integer limit);
}
