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

public class Transfer {

	public static void main(String[] args) throws Exception{
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
	//测试
//	final String url = "http://localhost:8080/interfaceWeb/basicInfo";
//	final String url = "http://120.31.132.119:8082/interfaceWeb/basicInfo";   
	

	//开发
	final String url = "http://localhost:8080/interfaceWeb/transfer";
	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/000000158120121/GHT_ROOT.pem", "pem", "RSA");
	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/000000158120121/000000158120121.pem", "pem", null, "RSA");
	//	PublicKey yhPubKey = TestUtil.getPublicKey();
//	PrivateKey hzfPriKey = TestUtil.getPrivateKey();
	
/*	 final String url = "http://gpay.gaohuitong.com:8086/interfaceWeb2/basicInfo";
	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/549440155510001/GHT_ROOT.pem", "pem", "RSA");

	 final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/549440155510001/549440155510001.pem", "pem", null, "RSA");
*/
	
	
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
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<agencyId>000000158120121</agencyId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>100012</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i+i
					+ "</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			
			
			sBuilder.append("<transferType>01</transferType>");
			sBuilder.append("<outMerchantId>19910000036</outMerchantId>");
			sBuilder.append("<inMerchantId></inMerchantId>");
			sBuilder.append("<terminalNo>20000125</terminalNo>");
			sBuilder.append("<amount>124.78</amount>");

			
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
			nvps.add(new BasicNameValuePair("agencyId", "000000158120121"));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", "100012"));
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
