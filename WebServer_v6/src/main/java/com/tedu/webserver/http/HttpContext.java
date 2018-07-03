package com.tedu.webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类定义了HTTP协议相关信息
 * @author adminitartor
 *
 */
public class HttpContext {
	/**
	 * 状态代码与对应状态描述的映射关系
	 * key:状态代码
	 * value:状态描述
	 */
	private static Map<Integer,String> STATUS_REASON_MAPPING = new HashMap<Integer,String>();
	
	static{
		initStatusReasonMapping();
	}
	/**
	 * 初始化状态代码与描述的映射MAP
	 */
	private static void initStatusReasonMapping(){
		STATUS_REASON_MAPPING.put(200, "OK");
		STATUS_REASON_MAPPING.put(302, "Move Temporaily");
		STATUS_REASON_MAPPING.put(404, "Not Found");
		STATUS_REASON_MAPPING.put(500, "Internal Server Error");
	}
	
	/**
	 * 根据给定的状态代码获取对应的状态描述
	 * @param statusCode
	 * @return
	 */
	public static String getStatusReason(int statusCode){
		return STATUS_REASON_MAPPING.get(statusCode);
	}
	
	
	public static void main(String[] args) {
		String reason = getStatusReason(200);
		System.out.println(reason);
	}
}





