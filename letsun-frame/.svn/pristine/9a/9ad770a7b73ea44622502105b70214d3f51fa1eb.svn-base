package com.letsun.frame.core.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.letsun.frame.core.domain.BaseEntity;
import com.letsun.frame.core.mapper.BaseMapper;
import com.letsun.frame.core.service.BaseService;

/**
 * @Desc 基础service实现类  
 * @author YY  
 * @date 2018年4月12日  
 * @param <M>
 * @param <T>
 */
public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements BaseService<T>{
	
	@Autowired
    protected M baseMapper;
	
	@Override
	public Page<T> selectPage(T entity, Integer pageIndex, Integer pageSize) {
		return super.selectPage(new Page<T>(pageIndex,pageSize), new EntityWrapper<T>(entity));
	}

	@Override
	public Page<T> selectPage(T entity, Integer pageSize) {
		return selectPage(entity, 1, pageSize);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean insert(T entity) {
		if(entity!=null && entity instanceof BaseEntity){
			BaseEntity baseEntity = (BaseEntity)entity;
			if(StringUtils.isEmpty(baseEntity.getCreateDate())){
				baseEntity.setCreateDate(new Date());
			}
		}
		return super.insert(entity);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateById(T entity) {
		if(entity!=null && entity instanceof BaseEntity){
			BaseEntity baseEntity = (BaseEntity)entity;
			if(StringUtils.isEmpty(baseEntity.getModifyDate())){
				baseEntity.setModifyDate(new Date());
			}
		}
		return super.updateById(entity);
	}

	@Override
	public Page<T> selectComplex(Map<String,Object> map, int pageIndex, int pageSize) {
		 Page<T> page = new Page<T>(pageIndex,pageSize);
		 page.setRecords(baseMapper.selectComplex(map,page));
		return page;
	}
}