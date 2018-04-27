/**
 * 
 */
package com.letsun.iwsct.itface.common;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 业务层常量类
 */
public class ServiceConstants {

	/**
	 * 注:所有常量必须以按照如下方式进行注释
	 * */
	
	/** wap页面图片宽度压缩尺寸 */
	public static final int IMG_WIDTH_240 = 240;
	
	public static final int IMG_WIDTH_320 = 320;
	
	public static final int IMG_WIDTH_640 = 640;
	
	/** 下划线 */
	public static final String UNDER_LINE = "_";
	
	/** 文件后缀 */
	public static final String FILE_SUFFIX_JPG = ".jpg";
	public static final String FILE_SUFFIX_ZIP = ".zip";
	public static final String FILE_SUFFIX_TXT = ".txt";
	public static final String FILE_SUFFIX_PIC = ".pic";
	
	/** 上传路径 */
	public static final String UPLOAD = "/upload/";
	/** 上传图片 */
	public static final String UPLOAD_PIC = UPLOAD + "pic";
	/** 上传视频 */
	public static final String UPLOAD_VEDIO = UPLOAD + "vedio";
	
	/** 标签文件存放磁盘路径 */
	//public static final String DISC_LABLE_CODE_PATH = "D:/server/wsct/lable/";
	public static final String DISC_LABLE_CODE_PATH = File.separator + "file" + File.separator + "lable" + File.separator;
	
	/** 序列号文件存放磁盘路径 */
	public static final String DISC_FAKE_CODE_PATH = File.separator + "file" + File.separator + "fake" + File.separator;
//	public static final String DISC_FAKE_CODE_PATH = "D:/server/wsct/fake/";
	
	/** 企业取码全局变量  */
	public static ConcurrentHashMap<Long, GetCodeSyn> GETCODE_SYN = null;
	
	/** 企业防伪区间号表名 */
	public static final String T_CORP_AMONG = "t_corp_among_";
	/** 企业防伪号表名 */
	public static final String T_CORP_NUMBER = "t_corp_number_";
	/** 标签库分表 t_lable_lib_* */
	public static final String T_FAKE_LIB = "t_fake_lib";
	
	/** 企业产生的防伪号预警数量 八十万 */
	public static final int MIN_NUMBER_NUM = 800000;
	
	/** 每次取100个区间 */
	public static final int GET_AMONG_NUM = 100;
	/** 最小区间预警数量 五百个区间 */
	public static final int MIN_AMONG_NUM = 500;
	/** 区间大小 一万 */
	public static final int AMONG_SIZE = 10000;
	/** 企业产生的最大区间数量 一千个区间 */
	public static final int AMONG_GENERATE_NUM = 1000;
	
	/** 防伪号匹配位数 八位 */
	public static final String NUMBER_CODE_PATTERN = "00000000";
	
	/** 标签分表的数据量 每个表一百万 标签库索引的区间大小 */
	public static final long TB_PART_LABLELIB_RECORD_NUM = 1000000;
	
	/** 防伪批次号匹配位数 */
	public static final String LABLE_CODE_PATTERN = "000000";
	
	/** 产生防伪标签并发数量 默认1000个同时并发 */
	public static final int GETCODE_NUM = 1000;
	
	/** 产生防伪标签失效分钟数 120分钟 该时间理论上与 每日制作的总数量同步完成的时间,同时结合多个企业并发制作的时间 成正比 */
	public static final int GETCODE_INVALID_MIN = 120;
	
	/** 
	 * 微信关键字处理匹配
	 * */
	/** 产品查询 */
	public static final String KEYWORD_CP = "cp";
	/** 防伪认证查询 */
	public static final String KEYWORD_RZ = "rz";
	
	/** 防伪认证默认结果 */
	public static final String FIRST_APPROVE_VALUE = "亲爱的用户, 你购买的该产品信息如下, 企业名称：某某某企业、产品名称：某某某、产地：某某, 该产品于{认证时间}首次认证真伪，防伪认证序列号为{序列号}。如您所购买的产品与该信息不一致，谨防假冒。";
	
	public static final String REPEAT_APPROVE_VALUE = "亲爱的用户，该产品已经于{认证时间}认证真伪，此次是第{认证次数}认证，认证序列号为{序列号}。如您所购买的商品有任何疑问，请与某某某咨询联系。";
	
	public static final String FAIL_APPROVE_VALUE = "验证失败, 如您所获取的商品与该信息不一致，说明您购买了假冒伪劣商品，请及时拨打假冒伪劣商品投诉热线，热线电话：xxxxxxxx。";
	
	/** 标签制作提示信息 begin */
	public static final String NONSUPPORT = "当前状态不支持此操作";
	
	public static final String PASS_SUCCEED = "审核成功,正在进入生成状态";
	
	public static final String PASS_ERRORS = "审核失败,可能存在的原因, 1.账户余额不足, 2.未设置收费标准, 3.当前状态不支持此操作,如有疑问请联系运营商";
	
	public static final String PASS_SUCCEED_ERROR = "审核没有全部成功,部分状态不支持此操作或账户余额不足";
	
	public static final String PASS_ERROR = "已经通过审核,不能再次进行审核";
	
	public static final String DOWN_EEEOE = "未制作完成,暂不能进行下载";
	/** 标签制作提示信息 end */
	
}
