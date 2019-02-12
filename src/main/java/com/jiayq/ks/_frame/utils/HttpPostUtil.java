package com.jiayq.ks._frame.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


/**
 * 功能: post请求接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月17日
 * 修改日期: 2015年3月17日
 * 修改列表:
 */
public class HttpPostUtil {
	
	public static final String CHARSET = "UTF-8";
	
	/**
	 * 功能：默认以UTF-8格式发送http请求
	 * @param token
	 * @param postUrl
	 * @return
	 */
	public static String doPost(String postUrl,Map<String, Object> param) throws Exception{
		return doPost(postUrl,param, CHARSET);
	}
	
	/**
	 * 功能：详细设置http请求参数
	 * @param token
	 * @param postUrl
	 * @param charset
	 * @param obj
	 * @return
	 */
	public static String doPost(String postUrl,Map<String, Object> param,String charset) throws Exception {
		String result="";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(postUrl); 
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");  
		method.addParameter("_clientType", "wap");
		if(param != null && !param.isEmpty()){
            for(Map.Entry<String,Object> entry : param.entrySet()){
                Object value = entry.getValue();
                if(value != null){
                	method.addParameter(entry.getKey(),value.toString());
                }
            }
        }			
		HttpMethodParams methodParam = method.getParams();
		methodParam.setContentCharset(charset);
		client.executeMethod(method);
		InputStream stream = method.getResponseBodyAsStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            //lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        // System.out.println(sb);
        result = sb.toString();
        reader.close();
        // 断开连接
        method.releaseConnection();
	
		return result;
	}
	
    
	
	/**
	 * 功能：默认以UTF-8格式发送http请求
	 * @param token
	 * @param postUrl
	 * @return
	 */
	public static String doGet(String getUrl) throws Exception{
		return doGet(getUrl, CHARSET);
	}
	
	/**
	 * 功能：详细设置http请求参数
	 * @param token
	 * @param postUrl
	 * @param charset
	 * @param obj
	 * @return
	 */
	public static String doGet(String getUrl,String charset) throws Exception {
		String result="";
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(getUrl); 
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");  
		HttpMethodParams methodParam = method.getParams();
		methodParam.setContentCharset(charset);
		client.executeMethod(method);
		InputStream stream = method.getResponseBodyAsStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            sb.append(lines);
        }
        System.out.println(sb);
        result = sb.toString();
        reader.close();
        // 断开连接
        method.releaseConnection();
	
		return result;
	}

}
