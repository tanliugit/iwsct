package com.letsun.frame.core.domain;

import java.io.Serializable;

/**
 * 操作消息提醒
 * @author YY
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type {
		/** 成功 */SUCCESS,
		/** 警告 */WARN,
		/** 错误 */ERROR
	}

	/** 类型 */
	private Type type;

	/** 内容 */
	private String content;
	
	/** 数据对象 */
	private Object object;

	/**
	 * 初始化一个新创建的 Message 对象，使其表示一个空消息。
	 */
	public Message() {
	}

	/**
	 * 初始化一个新创建的 Message 对象
	 * @param type 类型
	 * @param content 内容
	 */
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * @param type 类型
	 * @param content 内容
	 * @param args 参数
	 */
	public Message(Type type, String content, Object object) {
		this.type = type;
		this.content = content;
		this.object = object;
	}
	

	/**
	 * 返回成功消息
	 * @author hardy<2015年9月24日>
	 * @return
	 */
	public static Message success() {
		return Message.success("操作成功");
	}
	
	/**
	 * 返回成功消息
	 * @param content内容
	 * @return 成功消息
	 */
	public static Message success(String content) {
		return Message.success(content, null);
	}
	
	/**
	 * 返回成功消息
	 * @param content内容
	 * @param args参数
	 * @return 成功消息
	 */
	public static Message success(String content, Object object) {
		return new Message(Type.SUCCESS, content, object);
	}
	
	/**
	 * 返回警告消息
	 * @param content内容
	 * @return 警告消息
	 */
	public static Message warn(String content) {
		return Message.warn(content, null);
	}
	
	/**
	 * 返回警告消息
	 * @param content内容
	 * @param args参数
	 * @return 警告消息
	 */
	public static Message warn(String content, Object object) {
		return new Message(Type.WARN, content, object);
	}
	

	/**
	 * 返回错误消息
	 * @author hardy<2015年9月24日>
	 * @return
	 */
	public static Message error() {
		return Message.error("操作失败");
	}
	
	/**
	 * 返回错误消息
	 * @param content内容
	 * @return 警告消息
	 */
	public static Message error(String content) {
		return  Message.error(content, null);
	}
	
	/**
	 * 返回错误消息
	 * @param content内容
	 * @param object对象
	 * @return 警告消息
	 */
	public static Message error(String content, Object object) {
		return new Message(Type.ERROR, content, object);
	}

	/**
	 * 获取类型
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * @param type类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * @param content内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return content;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
