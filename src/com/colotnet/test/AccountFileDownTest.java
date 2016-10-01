package com.colotnet.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.encoders.Base64;

import com.colotnet.util.ConfigUtils;
import com.colotnet.util.Gzip;
import com.colotnet.util.SSLClient;
import com.colotnet.util.SignUtils;
import com.colotnet.util.ZipUtil;
/**
 * 类AccountFileDownTest.java的实现描述：对账文件下载
 * @author XB 2016年6月13日 下午1:16:27
 */
public class AccountFileDownTest {
	public static void main(String[] args) throws Exception {
		 DefaultHttpClient httpClient = new SSLClient();
	        HttpPost postMethod = new HttpPost(ConfigUtils.getProperty("trans_url"));
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        	FileOutputStream out = null;
	        File file = null;
	        nvps.add(new BasicNameValuePair("version", "V1.0"));
	        nvps.add(new BasicNameValuePair("transId", "14"));
	        nvps.add(new BasicNameValuePair("merNo", ConfigUtils.getProperty("merchant_no")));
	        nvps.add(new BasicNameValuePair("startDate", "20160401"));
	        nvps.add(new BasicNameValuePair("endDate", "20160630"));
	        nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps)));
	        try {
	            postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	            HttpResponse resp = httpClient.execute(postMethod);
	            String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
	            int statusCode = resp.getStatusLine().getStatusCode();
	            if (200 == statusCode) {
	            	Map<String,String> map  = getAllParamters(str);
	            	String fileName = "d://"+map.get("fileName");

	            	file = new File(fileName);
	            	if(file.exists()){
	            		file.delete();
	            	}
	            	out = new FileOutputStream(file);
	            	Gzip zip = new Gzip();
	            	out.write(zip.unzip(Base64.decode(map.get("fileContentDetail").toString())));
	                boolean signFlag = SignUtils.verferSignData(str);
	                if (!signFlag) {
	                    System.out.println("验签失败");
	                    return;
	                }
	                System.out.println("验签成功");
	                return;
	            }
	            System.out.println("返回错误码:" + statusCode);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	out.flush();
	        	out.close();
	        	long endtime = System.currentTimeMillis();
	        }
	}
	
	public static Map<String, String> getAllParamters(String result) {
		Map<String, String> map = null;
		try {
			if (StringUtils.isNotBlank(result)) {
				if (result.startsWith("{") && result.endsWith("}")) {
					result = result.substring(1, result.length() - 1);
				}
				map = parseQString(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> parseQString(String str)
			throws UnsupportedEncodingException {

		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		boolean isOpen = false;//值里有嵌套
		char openName = 0;
		if(len>0){
			for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
				curChar = str.charAt(i);// 取当前字符
				if (isKey) {// 如果当前生成的是key
					
					if (curChar == '=') {// 如果读取到=分隔符 
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else  {// 如果当前生成的是value
					if(isOpen){
						if(curChar == openName){
							isOpen = false;
						}
						
					}else{//如果没开启嵌套
						if(curChar == '{'){//如果碰到，就开启嵌套
							isOpen = true;
							openName ='}';
						}
						if(curChar == '['){
							isOpen = true;
							openName =']';
						}
					}
					if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					}else{
						temp.append(curChar);
					}
				}
				
			}
			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}
	private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
			String key, Map<String, String> map)
			throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}
}
