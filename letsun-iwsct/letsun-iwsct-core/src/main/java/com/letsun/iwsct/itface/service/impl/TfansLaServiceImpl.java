/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.TfansLa;
import com.letsun.iwsct.itface.mapper.TfansLaMapper;
import com.letsun.iwsct.itface.service.TfansLaService;

/**
 * Service实现类
 * @Author generator
 * @Date <2018年04月23日>
 * @version 1.0
 */
@Service
public class TfansLaServiceImpl extends BaseServiceImpl<TfansLaMapper,TfansLa> implements TfansLaService{

	@Override
	public List<TfansLa> getListfansLa(Long wxno, Long corpid) {
		// TODO Auto-generated method stub
		/*Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();;
		paramsMap.put("corpid", corpid);
		paramsMap.put("fansid", wxno);
		
		String hqlCondition = "/~ and corpid={corpid} and fansid={fansid} ~/";
		
		List<TfansLa> list =(List<fansLa>)tfansLaDao.findByHqlCondition(TfansLa.class,hqlCondition.toString()," order by id desc", paramsMap);*/
		
		
		Wrapper<TfansLa> wrapper = new EntityWrapper<TfansLa>();
		
		wrapper.eq("corpid", corpid);
		wrapper.eq("fansid", wxno);
		wrapper.orderBy("id", true);
		
		List<TfansLa> list = this.selectList(wrapper);
		
		if(null != list && list.size() > 0){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public TfansLa chenckTfansLa(Long wxno, Long openid, Long corpid) {
		// TODO Auto-generated method stub
		
		Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();;
		paramsMap.put("corpid", corpid);
		paramsMap.put("fansid", wxno);
		paramsMap.put("lafansid", openid);
		
		//List<TfansLa> list =(List<TfansLa>)tfansLaDao.findAllByPropertyes(TfansLa.class, paramsMap);
		List<TfansLa> list = this.selectByMap(paramsMap);
		
		if(null != list && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	
	
	
	
}
