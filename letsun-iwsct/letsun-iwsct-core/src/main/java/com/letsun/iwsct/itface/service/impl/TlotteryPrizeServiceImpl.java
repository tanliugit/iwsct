/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.TlotteryPrize;
import com.letsun.iwsct.itface.mapper.TlotteryPrizeMapper;
import com.letsun.iwsct.itface.service.TlotteryPrizeService;

/**
 * Service实现类
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Service
public class TlotteryPrizeServiceImpl extends BaseServiceImpl<TlotteryPrizeMapper,TlotteryPrize> implements TlotteryPrizeService{
	
	@Autowired
	private TlotteryPrizeMapper tlotteryPrizeMapper;
	
	
	
	
	
	
	@Override
	public List<TlotteryPrize> getlistPrizeForlotteryid(Long lotteryid) {
		// TODO Auto-generated method stub
		Wrapper<TlotteryPrize> wrapper = new EntityWrapper<TlotteryPrize>();
		wrapper.eq("lotteryid", lotteryid);
		wrapper.orderBy("level", true);
		return this.selectList(wrapper);
		
	}

	@Override
	public List<TlotteryPrize> getlistPrize(Long lotteryid) {
		// TODO Auto-generated method stub
		List<TlotteryPrize> lotteryPrizeList =  this.tlotteryPrizeMapper.getlistPrize(lotteryid);
		
		return lotteryPrizeList;
	}
	
	
	
	
	
}
