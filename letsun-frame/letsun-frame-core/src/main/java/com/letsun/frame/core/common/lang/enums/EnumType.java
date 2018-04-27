package com.letsun.frame.core.common.lang.enums;

/**
 * @Desc 枚举定义  
 * @author YY  
 * @date 2018年4月12日
 */
public class EnumType {

	/**
	 * @Desc 操作人类别
	 * @author YY<2017年10月17日>
	 */
	public enum OperatorType {
		/** 其它 */
		OTHER,
		/** 后台用户 */
		MANAGE,
		/** 手机端用户 */
		MOBILE
	}


	/**
	 * @Desc 业务操作类型
	 * @author YY<2017年10月17日>
	 */
	public enum BusinessType {
		/** 其它 */
		OTHER,
		/** 新增 */
		INSERT,
		/** 修改 */
		UPDATE,
		/** 删除 */
		DELETE,
		/** 授权 */
		GRANT,
		/** 导出 */
		EXPORT,
		/** 导入 */
		IMPORT,
		/** 登录 */
		LOGIN,
		/** 退出登录 */
		LOGOUT,
		/** 禁止访问 */
		FORBID
	}

	/**
	 * @Desc 操作状态
	 * @author YY<2017年10月17日>
	 */
	public enum BusinessStatus {
		/** 其它 */
		OTHER,
		/** 成功 */
		SUCCESS,
		/** 失败 */
		FAIL
	}
}
