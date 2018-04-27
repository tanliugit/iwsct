/*
 * Copyright (c) 2016, Letsun and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.letsun.iwsct.itface.model;

/**
 * 
 * @author shiw
 * @date 2017年5月25日
 */
public class RankingManifestVo {
	

	private String photourl, mbname;
	private Integer score;

	/**
	 * @return the photourl
	 */
	public String getPhotourl() {
		return photourl;
	}

	/**
	 * @param photourl the photourl to set
	 */
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	/**
	 * @return the mbname
	 */
	public String getMbname() {
		return mbname;
	}

	/**
	 * @param mbname the mbname to set
	 */
	public void setMbname(String mbname) {
		this.mbname = mbname;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

}
