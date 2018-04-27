/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.Tvote;
import com.letsun.iwsct.itface.mapper.TvoteMapper;
import com.letsun.iwsct.itface.model.Vote;
import com.letsun.iwsct.itface.service.TvoteService;

/**
 * Service实现类
 * 
 * @Author generator
 * @Date <2018年04月25日>
 * @version 1.0
 */
@Service
public class TvoteServiceImpl extends BaseServiceImpl<TvoteMapper, Tvote>
		implements TvoteService {

	@Override
	public List<Vote> findCount(Long corpid) {
		// TODO Auto-generated method stub

		List<Vote> votes = new ArrayList<Vote>();
		for (int i = 1; i <= 30; i++) {

			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("optid", i);
			maps.put("corpid", corpid);
			// sql =
			// "select * from t_vote where optid=:optid and corpid=:corpid";

			List<Tvote> list = this.selectByMap(maps);
			// List<Tvote> list = (List<Tvote>) tvoteDao.findBySql(sql, maps);

			if (list != null) {
				Vote vote = new Vote();
				vote.setVoteid(i);
				vote.setCount(list.size());
				votes.add(vote);
			} else {
				Vote vote = new Vote();
				vote.setVoteid(i);
				vote.setCount(0);
				votes.add(vote);
			}
		}

		return votes;
	}

	@Override
	public boolean checkUserVote(String wxno, Long corpid) {
		// TODO Auto-generated method stub

		/*
		 * Map<String, Object> check_wxno = new HashMap<String, Object>();
		 * String sql = "";
		 */

		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);

		Date createtimeBegin = now.getTime();

		now.add(Calendar.DAY_OF_MONTH, 1);

		Date createtimeEnd = now.getTime();

		/*
		 * check_wxno.put("createtimeBegin", createtimeBegin);
		 * check_wxno.put("createtimeEnd", createtimeEnd);
		 * check_wxno.put("corpid", corpid); check_wxno.put("wxno", wxno); sql =
		 * "select * from t_vote where createtime>=:createtimeBegin and createtime<=:createtimeEnd and corpid=:corpid and wxno=:wxno"
		 * ;
		 */

		Wrapper<Tvote> wrapper = new EntityWrapper<Tvote>();
		wrapper.ge("createtime", createtimeBegin);
		wrapper.le("createtime", createtimeEnd);
		wrapper.eq("corpid", corpid);
		wrapper.eq("wxno", wxno);

		List<Tvote> wxno_list = this.selectList(wrapper);

		// List<Tvote> wxno_list = (List<Tvote>) tvoteDao.findBySql(sql,
		// check_wxno);
		if (wxno_list != null) {
			if (wxno_list.size() >= 3) {
				return false;
			}
		}

		return true;

	}

	@Override
	public boolean checkOptVote(String wxno, Long optid, Long corpid) {
		// TODO Auto-generated method stub
		/*Map<String, Object> check_wxno = new HashMap<String, Object>();
		String sql = "";*/
		
		Calendar now = Calendar.getInstance();  
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		
		Date createtimeBegin = now.getTime();
		
		now.add(Calendar.DAY_OF_MONTH, 1);
		
		Date createtimeEnd = now.getTime();
		
		/*check_wxno.put("createtimeBegin", createtimeBegin);
		check_wxno.put("createtimeEnd", createtimeEnd);
		check_wxno.put("corpid", corpid);
		check_wxno.put("optid", optid);
		check_wxno.put("wxno", wxno);
		sql = "select * from t_vote where createtime>=:createtimeBegin and createtime<=:createtimeEnd and corpid=:corpid and optid=:optid and wxno=:wxno";*/
		
		Wrapper<Tvote> wrapper = new EntityWrapper<Tvote>();
		wrapper.ge("createtime", createtimeBegin);
		wrapper.le("createtime", createtimeEnd);
		wrapper.eq("optid", optid);
		wrapper.eq("corpid", corpid);
		wrapper.eq("wxno", wxno);

		List<Tvote> wxno_list = this.selectList(wrapper);
		
		
		//List<Tvote> wxno_list = (List<Tvote>) tvoteDao.findBySql(sql, check_wxno);
		if(wxno_list!=null){
			if(wxno_list.size()>=1){
				return false;
			}
		}
		
		return true;
	}

}
