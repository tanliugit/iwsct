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
import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.common.CommonUtil;
import com.letsun.iwsct.itface.domain.TlotteryActivity;
import com.letsun.iwsct.itface.mapper.TlotteryActivityMapper;
import com.letsun.iwsct.itface.service.TlotteryActivityService;

/**
 * Service实现类
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Service
public class TlotteryActivityServiceImpl extends BaseServiceImpl<TlotteryActivityMapper,TlotteryActivity> implements TlotteryActivityService{

	@Override
	public List<TlotteryActivity> queryActivityList(Long corpid) {
		// TODO Auto-generated method stub
		
		Wrapper<TlotteryActivity> wrapper = new EntityWrapper<TlotteryActivity>();
		wrapper.eq("status", 1);
		wrapper.eq("corpid", corpid);
		wrapper.le("starttime", CommonUtil.getCurrentDateTimeStr());
		wrapper.ge("endtime", CommonUtil.getCurrentDateTimeStr());
		
		return this.selectList(wrapper);
	}
	
}
