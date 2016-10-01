package com.colotnet.util;

import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;

public class SignUtils {

    public static String signData(List<BasicNameValuePair> nvps) throws Exception {
        TreeMap<String, String> tempMap = new TreeMap<String, String>();
        for (BasicNameValuePair pair : nvps) {
            if (StringUtils.isNotBlank(pair.getValue())) {
                tempMap.put(pair.getName(), pair.getValue());
            }
        }
        StringBuffer buf = new StringBuffer();
        for (String key : tempMap.keySet()) {
            buf.append(key).append("=").append((String) tempMap.get(key)).append("&");
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        /*KeyInfo keyInfo = RSAUtil.getPFXPrivateKey(ConfigUtils.getProperty("private_key_pfx_path"),
                                                   ConfigUtils.getProperty("private_key_pwd"));
        String signData = RSAUtil.signByPrivate(signatureStr, keyInfo.getPrivateKey(), "UTF-8");*/
        String signData = RSAUtil.signByPrivate(signatureStr, RSAUtil.readFile(ConfigUtils.getProperty("private_key_path"), "UTF-8"), "UTF-8");
        System.out.println("请求数据：" + signatureStr + "&signature=" + signData);
        return signData;
    }

    public static boolean verferSignData(String str) {
        System.out.println("响应数据：" + str);
        String data[] = str.split("&");
        StringBuffer buf = new StringBuffer();
        String signature = "";
        for (int i = 0; i < data.length; i++) {
            String tmp[] = data[i].split("=", 2);
            if ("signature".equals(tmp[0])) {
                signature = tmp[1];
            } else {
                buf.append(tmp[0]).append("=").append(tmp[1]).append("&");
            }
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        System.out.println("验签数据：" + signatureStr);
        return RSAUtil.verifyByKeyPath(signatureStr, signature, ConfigUtils.getProperty("public_key_path"), "UTF-8");
    }
    
    public static void main(String[] args) throws Exception {
    	String signatureStr = "merNo=850440058121001&orderDate=20160811&orderNo=20160811173955184336&productId=0104&respCode=0000&respDesc=交易成功&transAmt=3&transId=10";
    	String signStr = "Z+xiulxSF4kxYBMl9gnUuiWlGuNJB0SMJUXjytUpJ9toYoYN2cgXMdKgLdJ37aK10uMmjnq+Oli5D7xwS8Zybh44toURXl2OQ5HGlfBlpR8ofgaGx/HjM95GodI5z55O/je9pRERjsO5EVZV80IMwM0FbvpfepnyAgV4tCx2U6y7zSCpGQjtGaEpRNqDrQIKos7PBYk7tsx3pgeuktRkEip2McQi2wDeoGrZEpOw9QVAFRfvgAoQz2hQJMhDOoiBiGMnymnbvCX05hxePs5q93Sl3Rc8QcONKVhmcDhVmmPbWpOIbBzO7TFSrKaTQx+u3+5okdpoNamXB/CT8yjqSA==";
    	String signData = RSAUtil.signByPrivate(signatureStr, RSAUtil.readFile("C:/Users/Administrator/Desktop/1000001_prv.pem", "UTF-8"), "UTF-8");
    	System.out.println("签名结果=>" + signData);
    	System.out.println("签名比对结果=>" + signData.equals(signStr));
	}
    
}
