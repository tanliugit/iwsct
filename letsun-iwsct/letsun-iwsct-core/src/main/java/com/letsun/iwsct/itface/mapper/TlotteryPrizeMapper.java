/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.letsun.frame.core.mapper.BaseMapper;
import com.letsun.iwsct.itface.domain.TlotteryPrize;

/**
 * Mapper接口
 * 
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
public interface TlotteryPrizeMapper extends BaseMapper<TlotteryPrize> {
	List<TlotteryPrize> getlistPrize(@Param("lotteryid") Long lotteryid);
}
