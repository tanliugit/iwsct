/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.TcheckinRecord;
import com.letsun.iwsct.itface.mapper.TcheckinRecordMapper;
import com.letsun.iwsct.itface.service.TcheckinRecordService;

/**
 * Service实现类
 * @Author generator
 * @Date <2018年04月23日>
 * @version 1.0
 */
@Service
public class TcheckinRecordServiceImpl extends BaseServiceImpl<TcheckinRecordMapper,TcheckinRecord> implements TcheckinRecordService{

	@Override
	public List<TcheckinRecord> getListNewRecord(Long corpid, Long activityid,
			int status, Long recordid) {
		// TODO Auto-generated method stub
		
		/*StringBuffer hqlCondition = new StringBuffer();
		hqlCondition.append(" /~ and corpid = {corpid} ~/ ");
		hqlCondition.append(" /~ and activityid = {activityid} ~/");
		hqlCondition.append(" /~ and status = {status} ~/");
		hqlCondition.append(" /~ and id > {recordid} ~/");
		
		HashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("corpid", corpid);
		paramsMap.put("activityid", activityid);
		paramsMap.put("status", status);
		paramsMap.put("recordid", recordid);*/
		
		Wrapper<TcheckinRecord> wrapper = new EntityWrapper<TcheckinRecord>();
		
		wrapper.eq("corpid", corpid);
		wrapper.eq("activityid", activityid);
		wrapper.eq("status", status);
		wrapper.gt("id", recordid);
		
		wrapper.orderBy("id", false);
		return this.selectList(wrapper);
		
		
		//return (List<TcheckinRecord> )tcheckinRecordDao.findByHqlCondition(TcheckinRecord.class,hqlCondition.toString(),"order by id desc",paramsMap);

		//return null;
	}
	
	
}
