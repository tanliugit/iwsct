/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.iwsct.itface.domain.Tfans;
import com.letsun.iwsct.itface.mapper.TfansMapper;
import com.letsun.iwsct.itface.model.RankingManifestVo;
import com.letsun.iwsct.itface.service.TfansService;

/**
 * Service实现类
 * 
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Service
public class TfansServiceImpl extends BaseServiceImpl<TfansMapper, Tfans>
		implements TfansService {
	
	@Autowired
	private TfansMapper tfansMapper;
	
	
	@Override
	public Integer getMyRankingForNum(Long corpid, Integer shopid,
			Integer helpnum) {
		// TODO Auto-generated method stub
		if (helpnum == null) {
			helpnum = 0;
		}

		Wrapper<Tfans> wrapper = new EntityWrapper<Tfans>();

		wrapper.ne("status", "0");
		wrapper.eq("corpid", corpid);
		wrapper.ge("helpnum", helpnum);

		if (shopid != 0) {
			wrapper.eq("shopid", shopid);
		}

		List<Tfans> list = this.selectList(wrapper);

		if (list != null && list.size() > 0) {

			return list.size();
		} else {

			return 0;
		}
	}

	@Override
	public Integer getMyRankingForCore(Long corpid, Integer shopid,
			Integer core, Boolean excludeZero) {
		// TODO Auto-generated method stub

		Wrapper<Tfans> wrapper = new EntityWrapper<Tfans>();

		wrapper.ne("status", "0");
		wrapper.eq("corpid", corpid);
		wrapper.ge("helpscore", core);
		if (excludeZero != null && excludeZero) {

			wrapper.isNotNull("helpscore");
			wrapper.gt("helpscore", 0);
		}

		if (shopid != 0) {
			wrapper.eq("shopid", shopid);
		}

		List<Tfans> list = this.selectList(wrapper);

		if (list != null && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}

	}

	@Override
	public Tfans chenckTfans(String wxno, Long corpid) {
		// TODO Auto-generated method stub

		Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		;
		paramsMap.put("corpid", corpid);
		paramsMap.put("wxno", wxno);

		List<Tfans> list = this.selectByMap(paramsMap);
		if (null != list && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Tfans> queryRankinglist(Long corpid, Integer shopid, int rows,
			Boolean excludeZero) {
		// TODO Auto-generated method stub
		/*
		 * StringBuffer sql =new StringBuffer();
		 * sql.append(" from Tfans t where 1=1 and t.status<>0 ");
		 * sql.append("  and corpid = "+corpid);
		 * 
		 * if(shopid!=0){ sql.append("  and shopid = "+shopid); } if
		 * (excludeZero != null && excludeZero) {
		 * sql.append("  and helpscore is not null and helpscore > 0 "); }
		 * sql.append("  order by helpscore desc ");
		 * 
		 * Query query =
		 * sessionFactory.getCurrentSession().createQuery(sql.toString());
		 * List<Tfans> list = (List<Tfans>)query.setMaxResults(rows).list();
		 * if(null!=list && list.size()>0) { // Collections.reverse(list); //
		 * Collections.sort(list,new Comparator<Tposition>(){ // public int
		 * compare(Tposition arg0, Tposition arg1) { // return
		 * arg1.getPositionid().compareTo(arg0.getPositionid()); // } // });
		 * 
		 * return list; } else { return null; }
		 */

		Wrapper<Tfans> wrapper = new EntityWrapper<Tfans>();
		wrapper.ne("status", 0);
		wrapper.eq("corpid", corpid);

		if (shopid != 0) {
			wrapper.eq("shopid", shopid);
		}

		if (excludeZero != null && excludeZero) {
			// sql.append("  and helpscore is not null and helpscore > 0 ");
			wrapper.isNotNull("helpscore");
			wrapper.gt("helpscore", 0);
		}

		wrapper.orderBy("helpscore", false);

		List<Tfans> list = this.selectList(wrapper);

		if (null != list && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	@Override
	public List<Tfans> queryRankinglistForNum(Long corpid, Integer shopid,
			int rows) {
		// TODO Auto-generated method stub
		/*StringBuffer sql =new StringBuffer();
		sql.append(" from Tfans t where 1=1 and t.status<>0 ");
		sql.append("  and corpid = "+corpid);
		
		if(shopid!=0){
			sql.append("  and shopid = "+shopid);
		}
		
		sql.append("  order by helpnum desc ");
		
		Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());*/
		
		Wrapper<Tfans> wrapper = new EntityWrapper<Tfans>();
		wrapper.ne("status", 0);
		wrapper.eq("corpid", corpid);

		if (shopid != 0) {
			wrapper.eq("shopid", shopid);
		}

		wrapper.orderBy("helpnum", false);
		List<Tfans> list = this.selectList(wrapper);
		if (null != list && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Long queryOneselfRanking(Long corpid, String openid) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("corpid", corpid);
		params.put("openid", openid);
		return this.tfansMapper.queryOneselfRanking(params);
	}

	@Override
	public List<RankingManifestVo> queryRanking(Long corpid, Integer limit) {
		// TODO Auto-generated method stub
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("corpid", corpid);
		params.put("limit", limit);
		
		return this.tfansMapper.queryRanking(params);
	}
	
	
	
	
	

}
