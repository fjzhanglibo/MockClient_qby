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

public class TestUnBind {
	public static void main(String[] args) throws Exception{
		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("E:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
		//final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("E:/密钥/测试证书/test_rsa_public_key_2048.pem", "pem", "RSA");
		final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("E:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
		
		try {
			HttpClient4Util httpClient4Util = new HttpClient4Util();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<merchantId>"+TestUtil.merchantId+"</merchantId>");
            sBuilder.append("<childMerchantId>"+TestUtil.childMerchantId+"</childMerchantId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>IFP002</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<bindId>I00010010501621700278200056139</bindId>");
			sBuilder.append("<userId>"+TestUtil.userId+"</userId>");
//			sBuilder.append("<mobilePhone>18222920903</mobilePhone>");
			sBuilder.append("<sendReqMsgId>20160704170015</sendReqMsgId>");
			sBuilder.append("<validateCode>050644</validateCode>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");

			String plainXML = sBuilder.toString();
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
			nvps.add(new BasicNameValuePair("merchantId", TestUtil.merchantId));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", "IFP002"));
			nvps.add(new BasicNameValuePair("callBack",
					"http://localhost:801/callback/ghtBindCard.do"));

			// byte[] retBytes =
			// httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonSyncInter.do",
			// null, nvps, null);
			//byte[] retBytes = httpClient4Util.doPost("http://120.31.132.114:8081/quickInter/channel/commonSyncInter.do",null, nvps, null);
			byte[] retBytes =httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
			String response = new String(retBytes, "UTF-8");
			System.out.println("回调结果： " + new String(retBytes, "UTF-8"));
			TestEncype t = new TestEncype();
			System.out.println("明文结果: " + t.respDecryption(response));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
