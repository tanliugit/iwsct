package com.letsun.frame.core.domain;

import java.io.Serializable;

/**
 * 该对象需要转xml，字段命名一下划线形式
 * 固忽略P3C代码规约扫描
 * @author YY<2017年11月17日>
 */
public class SendRedPack implements Serializable{
	private static final long serialVersionUID = 1L;
	/**随机字符串*/
	private String nonce_str;
	/**签名算法*/
	private String sign;
	/**商户订单号*/
	private String mch_billno;
	/**微信支付分配的商户号*/
	private String mch_id;
	/**商户appid*/
	private String wxappid;
	/**红包发送者名称*/
	private String send_name;
	/**接受收红包用户在wxappid下的openid*/
	private String re_openid;
	/**付款金额，单位分*/
	private String total_amount;
	/**红包发放总人数total_num=1*/
	private String total_num;
	/**红包祝福语*/
	private String wishing;
	/**用接口的机器Ip地址*/
	private String client_ip;
	/**活动名称*/
	private String act_name;
	/**备注信息*/
	private String remark;
	
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getWxappid() {
		return wxappid;
	}
	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public String getRe_openid() {
		return re_openid;
	}
	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
