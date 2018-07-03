package com.tedu.webserver.http;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpRequest表示一个Http协议要求的请求信息
 * 一个请求包含三部分:
 * 请求行，消息头，消息正文
 * 
 * @author adminitartor
 *
 */
public class HttpRequest {
	//对应客户端的Socket
	private Socket socket;
	//通过Socket获取的输入流，用于读取客户端发送的请求
	private InputStream in;
	
	
	/*
	 * 请求行相关信息定义
	 */	
	//请求方式
	private String method;
	
	//资源路径
	private String url;
	
	//请求使用的协议版本
	private String protocol;
	
	/*
	 * 消息头相关信息
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	/**
	 * 实例化HttpRequest使用的构造方法，需要将对应
	 * 客户端的Socket传入，以便读取该客户端发送过来
	 * 的请求内容
	 * @param socket
	 */
	public HttpRequest(Socket socket){
		System.out.println("HttpRequest:开始解析请求");
		try{
			this.socket = socket;
			this.in = socket.getInputStream();
			/*
			 * 1:解析请求行
			 * 2:解析消息头
			 * 3:解析消息正文
			 */		
			//1
			parseRequestLine();
			//2
			parseHeaders();
			//3
			parseContent();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 解析请求行
	 */
	private void parseRequestLine(){
		System.out.println("解析请求行...");
		/*
		 * 大致流程:
		 * 1:通过输入流读取第一行字符串
		 * 2:将请求行按照空格拆分为三项
		 * 3:将拆分的三项分别设置到method,url,
		 *   protocol三个属性上
		 *   
		 * 解析请求行时，在获取拆分后的数组元素时
		 * 可能会引发数组下标越界，这是由于HTTP协
		 * 议允许客户端发送一个空请求过来导致的。
		 * 我们后面解决。  
		 */
		String line = readLine();
		String[] data = line.split("\\s");
		this.method = data[0];
		this.url = data[1];
		this.protocol = data[2];
		
		System.out.println("method:"+method);//  GET
		System.out.println("url:"+url);//  /index.html
		System.out.println("protocol:"+protocol);// HTTP/1.1
		System.out.println("请求行解析完毕");
	}
	/**
	 * 解析消息头
	 */
	private void parseHeaders(){
		System.out.println("解析消息头...");
		/*
		 * 大致步骤:
		 * 1:继续使用readLine方法读取若干行内容
		 *   每一行应该都是一个消息头
		 * 2:当readLine方法返回值为空字符串时则
		 *   停止循环读取工作(单独读取到了CRLF时
		 *   readLine方法返回值应当为空字符串)
		 * 3:每当读取一个消息头信息时应当按照": "
		 *   拆分为两项，第一项为消息头名字，第二项
		 *   为消息头对应的值，将名字作为key，将
		 *   值作为value存入到属性headers这个Map中。    
		 */
		while(true){
			String line = readLine();
			//判断是否单独读取到了CRLF
			if("".equals(line)){
				break;
			}
			String[] data = line.split(":\\s");
			headers.put(data[0], data[1]);
		}
		System.out.println("headers:"+headers);
		System.out.println("消息头解析完毕");
	}
	/**
	 * 解析消息正文
	 */
	private void parseContent(){
		System.out.println("解析消息正文...");
		
		
		System.out.println("消息正文解析完毕");
	}
		
	/**
	 * 通过给定的输入流读取一行字符串(以CRLF结尾)
	 * @param in
	 * @return
	 */
	private String readLine(){
		try {
			StringBuilder builder = new StringBuilder();
			int d = -1;
			//c1表示上次读到的字符，c2表示本次读到的字符
			char c1 ='a',c2 = 'a';
			while((d = in.read())!=-1){
				c2 = (char)d;
				/*
				 * 在ASC编码中CR的编码对应的数字为13
				 * LF编码对应的数字为10
				 * 就好比字符a的编码对应的数字为97
				 */
				if(c1==13&&c2==10){
					break;
				}
				builder.append(c2);
				c1 = c2;
			}
			return builder.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getHeader(String name) {
		return headers.get(name);
	}
	
	
	
	
}













