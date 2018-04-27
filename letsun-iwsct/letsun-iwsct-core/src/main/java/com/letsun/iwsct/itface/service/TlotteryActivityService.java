/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service;

import java.util.List;

import com.letsun.iwsct.itface.domain.TlotteryActivity;
import com.letsun.frame.core.service.BaseService;

/**
 * Service接口
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
public interface TlotteryActivityService extends BaseService<TlotteryActivity>{
	public List<TlotteryActivity> queryActivityList(Long corpid);
}
