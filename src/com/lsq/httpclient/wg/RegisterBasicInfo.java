package com.lsq.httpclient.wg;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.lsq.httpclient.netpay.CryptoUtil;
import com.lsq.httpclient.netpay.HttpClient4Util;
import com.lsq.httpclient.netpay.TestEncype;
import com.lsq.httpclient.netpay.TestUtil;

public class RegisterBasicInfo    {
//	private static  String publicKey="D:/key/GHT_ROOT.pem";
//	private static  String privateKey="D:/key/000000158120121.pem";
//	private static  String privateKey="D:/key/000000152110003.pem";
//	private static  String privateKey="D:/key/pkcs8_rsa_private_key_2048.pem";

    //开发环境
//	final  static String url = "http://120.31.132.120:8083/interfaceWeb/realTimeDF";
	//测试环境
	final  static String url = "http://120.31.132.119:8082/interfaceWeb/realTimeDF";
//	final  static String url = "http://epay.gaohuitong.com:8083/interfaceWeb/realTimeDF";
	
	public static void main(String[] args) throws Exception{
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
		
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
	//测试

		final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("D:/key/GHT_ROOT.pem", "pem", "RSA");
		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000158120121.pem", "pem", null, "RSA");
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000153990021.pem", "pem", null, "RSA");
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000152110003.pem", "pem", null, "RSA");
		
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//	PublicKey yhPubKey = TestUtil.getPublicKey(); 
//	PrivateKey hzfPriKey = TestUtil.getPrivateKey();
	int i = 0;
	int j = 0;
	while (i<1) {
		i++;
		try {
			String tel="19910000002";
			HttpClient4Util httpClient4Util = new HttpClient4Util();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>2.0.0</version>");
			sBuilder.append("<agencyId>000000158120121</agencyId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>200001</tranCode>");
			sBuilder.append("<reqMsgId>"
					+"WG00DF"+DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + tel.substring(tel.length()-4, tel.length())
					+ "</reqMsgId>");
//			sBuilder.append("<reqMsgId>123</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<business_code>B00302</business_code>");
			sBuilder.append("<user_id>00000145</user_id>");   //19910000008
			sBuilder.append("<DF_type>0</DF_type>");   //代付类型：0：一般代付  1：机构代付 2：其他代付  默认：0
			sBuilder.append("<e_user_code></e_user_code>");
			sBuilder.append("<bank_code>308</bank_code>");
			sBuilder.append("<account_type></account_type>"); //00银行卡，01存折。不填默认为银行卡00
			sBuilder.append("<account_no>6226090000000048</account_no>");   //6225800308365466
			sBuilder.append("<account_name>张三</account_name>");
//			sBuilder.append("<province>广东</province>");
//			sBuilder.append("<city>广州</city>");
			sBuilder.append("<allot_flag></allot_flag>"); //机构账户调拨标志：0：不支持  1：支持  默认：0
//			sBuilder.append("<bank_name>中国工商银行广州市骏景分理处</bank_name>");  
			sBuilder.append("<bank_type></bank_type>");  //305100001602， 105584001016， 308581002257
			sBuilder.append("<account_prop>0</account_prop>"); //0私人，1公司。不填时，默认为私人0
			sBuilder.append("<amount>202</amount>");
			sBuilder.append("<terminal_no>20000125</terminal_no>");
//			sBuilder.append("<currency>CNY</currency>");
//			sBuilder.append("<protocol>1</protocol>");
//			sBuilder.append("<protocol_userid>1</protocol_userid>");
//			sBuilder.append("<id_type>0</id_type>");
			sBuilder.append("<ID>37142819800508053x</ID>");//210304198503040045
//			sBuilder.append("<tel>19910000007</tel>");
//			sBuilder.append("<remark>网关代付测试</remark>");
			sBuilder.append("<extra_fee>10</extra_fee>");
			sBuilder.append("</body>"); 
			sBuilder.append("</merchant>");

			String plainXML = sBuilder.toString();
			System.out.println("请求报文："+plainXML);
			byte[] plainBytes = plainXML.getBytes("UTF-8");
			String keyStr = "1122334455667788";
			byte[] keyBytes = keyStr.getBytes("UTF-8");
			byte[] base64EncryptDataBytes = Base64.encodeBase64(CryptoUtil
					.AESEncrypt(plainBytes, keyBytes, "AES",
							"AES/ECB/PKCS5Padding", null));
			String encryptData = new String(base64EncryptDataBytes, "UTF-8");
			byte[] base64SingDataBytes = Base64.encodeBase64(CryptoUtil
					.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA"));
			String signData = new String(base64SingDataBytes, "UTF-8");
			byte[] base64EncyrptKeyBytes = Base64.encodeBase64(CryptoUtil
					.RSAEncrypt(keyBytes, yhPubKey, 2048, 11,
							"RSA/ECB/PKCS1Padding"));
			String encrtptKey = new String(base64EncyrptKeyBytes, "UTF-8");
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("agencyId", "000000158120121"));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", "200001"));
//			nvps.add(new BasicNameValuePair("callBack","http://localhost:801/callback/ghtBindCard.do"));
//			 byte[] retBytes = httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);
			byte[] retBytes = httpClient4Util.doPost(url,null, nvps, null);
//			byte[] retBytes =httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
//		     byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);

			String response = new String(retBytes, "UTF-8");
			System.out.println("回调结果： " + new String(retBytes, "UTF-8"));
			TestEncype t = new TestEncype();
			System.out.println("应答明文结果: " + t.respDecryption(response));
			System.out.println("i"+i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
	
	

}
