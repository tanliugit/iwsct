/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.iwsct.itface.domain.TlotteryBuyer;
import com.letsun.iwsct.itface.mapper.TlotteryBuyerMapper;
import com.letsun.iwsct.itface.service.TlotteryBuyerService;
import com.letsun.frame.core.service.impl.BaseServiceImpl;

/**
 * Service实现类
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Service
public class TlotteryBuyerServiceImpl extends BaseServiceImpl<TlotteryBuyerMapper,TlotteryBuyer> implements TlotteryBuyerService{

	@Override
	public TlotteryBuyer getBuyer(String wxNo, Long corpId) {
		// TODO Auto-generated method stub
		//Wrapper<TlotteryBuyer> wrapper = new Wrapper<TlotteryBuyer>();
		
		Wrapper<TlotteryBuyer> wrapper = new EntityWrapper<TlotteryBuyer>();
		wrapper.eq("wxno", wxNo);
		wrapper.eq("corpid", corpId);
		wrapper.ne("status", 0);
		
		List<TlotteryBuyer> buyers = this.selectList(wrapper);
		
		return (buyers != null && buyers.size() > 0 ) ? buyers.get(0) : null;
	}
	
}
