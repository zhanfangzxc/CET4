package com.yingshibao.cet4.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
	/**
	 * 成功
	 */
	public static final int ERROR_NO=0;
	/**
	 * 失败
	 */
	public static final int ERROR_YES=1;
	
	/**
	 * 无效账号
	 */
	public static final int ERROR_USERNAME_INVALID=2;
	
	/**
	 * 账号被占用
	 */
	public static final int ERROR_USERNAME_USED=3;
	
	/**
	 * 无效邮箱
	 */
	public static final int ERROR_MAIL_INVALID=4;
	
	/**
	 * 邮箱被占用
	 */
	public static final int ERROR_MAIL_USED=5;
	
	/**
	 * 账号和密码一致
	 */
	public static final int ERROR_USERNAME_PASSWORD_SAME=6;
	
	/**
	 * 无效会话
	 */
	public static final int ERROR_TOKEN_INVALID=7;
	
	/**
	 * 解密数据出错
	 */
	public static final int ERROR_DECRYPT=8;
	/**
	 * 加密数据出错
	 */
	public static final int ERROR_ENCRYPT=9;
	/**
	 *	密码无效
	 */
	public static final int ERROR_PASSWORD_INVALID=10;
	/**
	 * 数据关系无效
	 */
	public static final int ERROR_DATA_RELATION_INVALID=11;
	/**
	 * 请求数据过多
	 */
	public static final int ERROR_REQUEST_OVERFLOW=12;
	
	protected static ErrorCodeParser parser;
	
	/**
	 * 解析错误码
	 * @param errorCode 错误码
	 * @return 错误描述信息
	 */
	public static String parse(int errorCode){
		if(parser==null){
			parser=new ErrorCodeParser();
		}
		return parser.parse(errorCode);
	}
}

class ErrorCodeParser{
	protected Map<Integer,String> errorCodeMap;
	
	public ErrorCodeParser(){
		errorCodeMap=new HashMap<Integer, String>();
		errorCodeMap.put(ErrorCode.ERROR_NO, "成功");
		errorCodeMap.put(ErrorCode.ERROR_YES, "失败");
		errorCodeMap.put(ErrorCode.ERROR_USERNAME_INVALID, "无效账号");
		errorCodeMap.put(ErrorCode.ERROR_USERNAME_USED, "账号被占用");
		errorCodeMap.put(ErrorCode.ERROR_MAIL_INVALID, "无效邮箱");
		errorCodeMap.put(ErrorCode.ERROR_MAIL_USED, "邮箱被占用");
		errorCodeMap.put(ErrorCode.ERROR_USERNAME_PASSWORD_SAME, "账号和密码一致");
		errorCodeMap.put(ErrorCode.ERROR_TOKEN_INVALID, "无效会话");
		errorCodeMap.put(ErrorCode.ERROR_DECRYPT, "解密数据出错");
		errorCodeMap.put(ErrorCode.ERROR_ENCRYPT, "解密数据错误");
		errorCodeMap.put(ErrorCode.ERROR_PASSWORD_INVALID, "密码无效");
		errorCodeMap.put(ErrorCode.ERROR_DATA_RELATION_INVALID,"数据关系无效");
		errorCodeMap.put(ErrorCode.ERROR_REQUEST_OVERFLOW, "请求数据过多");
	}
	
	public String parse(int errorCode){
		return this.errorCodeMap.get(errorCode);
	}
}
