package com.tedu.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 响应对象
 * 该类的每个实例用于表示一个服务端发送给客户端的
 * 响应内容
 * @author adminitartor
 *
 */
public class HttpResponse {
	private Socket socket;
	private OutputStream out;
	
	/*
	 * 状态行相关信息定义
	 */
	//状态代码
	private int statusCode;
	
	
	
	/*
	 * 响应正文相关信息定义
	 */
	//要响应的实体文件
	private File entity;
	
	public HttpResponse(Socket socket){
		try {
			this.socket = socket;
			this.out = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 将响应内容按照HTTP协议格式发送给客户端
	 */
	public void flush(){
		/*
		 * 响应客户端做三件事
		 * 1:发送状态行
		 * 2:发送响应头
		 * 3:发送响应正文
		 */
		sendStatusLine();
		sendHeaders();
		sendContent();
	}	
	/**
	 *	发送状态行
	 */
	private void sendStatusLine(){
		try {
			String line = "HTTP/1.1"+" "+statusCode+" "+HttpContext.getStatusReason(statusCode);
			println(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 发送响应头
	 */
	private void sendHeaders(){
		try {
			String line = "Content-Type: text/html";
			println(line);
			
			line = "Content-Length: "+entity.length();
			println(line);
			
			//表示响应头部分发送完毕
			println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 发送响应正文
	 */
	private void sendContent(){
		try(
			FileInputStream fis 
				= new FileInputStream(entity);
		){
			byte[] data = new byte[1024*10];
			int len = -1;
			while((len = fis.read(data))!=-1){
				out.write(data, 0, len);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将给定字符串按行发送给客户端(以CRLF结尾)
	 * @param line
	 */
	private void println(String line){
		try {
			out.write(line.getBytes("ISO8859-1"));
			out.write(13);//written CR
			out.write(10);//written LF
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public File getEntity() {
		return entity;
	}


	public void setEntity(File entity) {
		this.entity = entity;
	}
	
	
	
	
}





