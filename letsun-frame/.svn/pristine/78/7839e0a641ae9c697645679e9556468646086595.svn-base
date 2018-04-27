package com.letsun.frame.core.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * @Desc Mapper通用继承
 * @author YY<2017年10月10日>
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {
	//特别注意，由于继承了通用BaseMapper,该接口不能被扫描到，否则会出错,MapperScan(value="com.letsun.**.mapper*",markerInterface=CommonMapper.class)
	
	/**
	 * 自定义多表复杂查询  
	 * @param map
	 * @param page
	 * @return List<T>
	 * @author YY<2018年4月12日>
	 */
	List<T>  selectComplex(Map<String,Object> map,Pagination page);
}
