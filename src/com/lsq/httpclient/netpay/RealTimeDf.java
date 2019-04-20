package com.lsq.httpclient.netpay;

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

public class RealTimeDf {

	public static void main(String[] args) throws Exception{
	
	 final String url = TestUtil.interface_url+"realTimeDF";
//	 final String url = "https://testapp.sicpay.com:11008/interfaceWeb/realTimeDF";
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/549440155510001/GHT_ROOT.pem", "pem", "RSA");
//
//	 final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/549440155510001/549440155510001.pem", "pem", null, "RSA");
	 PublicKey yhPubKey = TestUtil.getPublicKey();
     PrivateKey hzfPriKey = TestUtil.getPrivateKey();
	
	
	int i = 0;
//	int j = 0;
	while (i<1) {
		i++;
		try {
			HttpClient4Util httpClient4Util = new HttpClient4Util();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>2.0.0</version>");
			sBuilder.append("<agencyId>"+TestUtil.merchantId+"</agencyId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>200001</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i+i
					+ "</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			
			
			sBuilder.append("<business_code>B00302</business_code>");
			sBuilder.append("<user_id>"+TestUtil.childMerchantId+"</user_id>");
//			sBuilder.append("<DF_type>1</DF_type>");
			sBuilder.append("<bank_code>999</bank_code>");
			sBuilder.append("<account_type>00</account_type>");
			sBuilder.append("<account_no>000000000100741</account_no>");
			sBuilder.append("<account_name>张立博</account_name>");
			sBuilder.append("<amount>2</amount>");
			sBuilder.append("<terminal_no>"+TestUtil.terminalId+"</terminal_no>");
			sBuilder.append("<ID>350303199208300034</ID>");
//            sBuilder.append("<Query_sn>2018011200075311</Query_sn>");

			
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			

			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes("UTF-8");
			String keyStr = "4D22R4846VFJ8HH4";
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
			nvps.add(new BasicNameValuePair("agencyId", TestUtil.merchantId));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", "200001"));
//			nvps.add(new BasicNameValuePair("callBack","http://localhost:801/callback/ghtBindCard.do"));
//			 byte[] retBytes = httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);
			byte[] retBytes = httpClient4Util.doPost(url,null, nvps, null);
//			byte[] retBytes =httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
//		     byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);

			String response = new String(retBytes, "UTF-8");
			System.out.println("回调结果： ||" + new String(retBytes, "UTF-8")+"||");
			TestEncype t = new TestEncype();
			System.out.println("明文结果: ||" + t.respDecryption(response)+"||");
			System.out.println("i"+i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}

}
