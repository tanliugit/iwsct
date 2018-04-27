package com.letsun.frame.core.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * @Desc 基础service  
 * @author YY  
 * @date 2018年4月12日  
 * @param <T>
 */
public interface BaseService<T> extends IService<T>{

	/**
	 * 分页查询  
	 * @param entity 实体对象
	 * @param pageIndex 起始页
	 * @param pageSize 每页显示条数
	 * @return  Page<T> 分页对象
	 * @author YY<2018年4月12日>
	 */
    Page<T> selectPage(T entity, Integer pageIndex, Integer pageSize);
    
    /**
     * 分页查询  
     * @param entity  实体对象
     * @param pageSize 起始页，默认 每页显示条数10
     * @return  Page<T> 分页对象
     * @author YY<2018年4月12日>
     */
    Page<T> selectPage(T entity,Integer pageSize);
    
    /**
     * 多表关联查询  
     * @param map 查询条件map
     * @param pageIndex 起始页
     * @param pageSize 每页显示条数
     * @return  Page<T> 分页对象
     * @author YY<2018年4月12日>
     */
    Page<T> selectComplex(Map<String,Object> map,int pageIndex,int pageSize);

}