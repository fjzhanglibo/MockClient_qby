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

/**
 * 鉴权绑卡
 */
public class TestQuickPay {
	public static void main(String[] args) throws Exception {
		// 易通私钥
		// final PrivateKey hzfPriKey =
		// CryptoUtil.getRSAPrivateKeyByFileSuffix("E:/密钥/易通百通网络科技股份有限公司/pkcs8_rsa_private_key_2048.pem",
		// "pem", null, "RSA");
//		final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix(
//				"F:/key/test/test_rsa_public_key_2048.pem", "pem", "RSA");
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix(
//				"F:/key/test/test_pkcs8_rsa_private_key_2048.pem", "pem", null,
//				"RSA");
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//		final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
		PublicKey yhPubKey = TestUtil.getPublicKey();
		PrivateKey hzfPriKey = TestUtil.getPrivateKey();
		// final PublicKey yhPubKey =
		// CryptoUtil.getRSAPublicKeyByFileSuffix("E:/密钥/生成证书/rsa_public_key_2048.pem",
		// "pem", "RSA");
		// final PublicKey yhPubKey =
		// CryptoUtil.getRSAPublicKeyByFileSuffix("E:/密钥/易通百通网络科技股份有限公司/rsa_public_key_2048.pem",
		// "pem", "RSA");
		try {
			HttpClient4Util httpClient4Util = new HttpClient4Util();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>2.0.0</version>");
			sBuilder.append("<merchantId>"+TestUtil.merchantId+"</merchantId>");
            sBuilder.append("<childMerchantId>"+TestUtil.merchantId+TestUtil.childMerchantId+"</childMerchantId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>IFP00B</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqMsgId>");
//			sBuilder.append("<reqMsgId>kkjjhhhhuuhhgghhgggghhhhjjjjjjhhhhhhh</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			//sBuilder.append("<terminalId>20000021</terminalId>");
			sBuilder.append("<terminalId>"+TestUtil.terminalId+"</terminalId>");
			//信用卡
//			sBuilder.append("<bindId>1466592553uW</bindId>");
			//借记卡
			sBuilder.append("<bindId>1509649350l1</bindId>");
			sBuilder.append("<userId>"+TestUtil.userId+"</userId>");
			//sBuilder.append("<bankCardNo>4367420060540647660</bankCardNo>");
			sBuilder.append("<oriPayMsgId>20160802021804</oriPayMsgId>");
			sBuilder.append("<productCategory>14</productCategory>");
			sBuilder.append("<notify_url>http://localhost:8081/quickInter1/channel/commonInter.do</notify_url>");
			//sBuilder.append("<currency>HKD</currency>");
			sBuilder.append("<currency>CNY</currency>");
			sBuilder.append("<reckonCurrency>CNY</reckonCurrency>");
			sBuilder.append("<amount>1</amount>");
			
			sBuilder.append("<mobilePhone>13288821832</mobilePhone>");
			sBuilder.append("<sendReqMsgId>20160802033141</sendReqMsgId>");
			sBuilder.append("<validateCode>152481</validateCode>");
//            sBuilder.append("<fcCardNo >6217003320058490338</fcCardNo >");
//            sBuilder.append("<userFee>10</userFee>");
            sBuilder.append("<valid>0421</valid>");
            sBuilder.append("<cvn2>741</cvn2>");
            sBuilder.append("<bankCardType>02</bankCardType>");
            sBuilder.append("<certificateNo>350303199208300034</certificateNo>");
            sBuilder.append("<accountName>张立博</accountName>");
            sBuilder.append("<certificateType>ZR01</certificateType>");
            sBuilder.append("<bankCardNo>6258091656742561</bankCardNo>");
			
			//sBuilder.append("<productCategory>01</productCategory>");
			sBuilder.append("<productName>Mac笔记\n\r本</productName>");
			sBuilder.append("<productDesc>笔记\r本电脑</productDesc>");
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
			nvps.add(new BasicNameValuePair("tranCode", "IFP00B"));
			nvps.add(new BasicNameValuePair("callBack","http://localhost:801/callback/ghtBindCard.do"));

//			 byte[] retBytes =
//			 httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonSyncInter.do",
//			 null, nvps, null);
			//byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter/channel/commonSyncInter.do",null, nvps, null);
			byte[] retBytes = httpClient4Util.doPost(TestUtil.url,null, nvps, null);
			// byte[] retBytes =
//			byte[] retBytes = httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
			String response = new String(retBytes, "UTF-8");
			System.out.println("回调结果： " + new String(retBytes, "UTF-8"));
			TestEncype t = new TestEncype();
			String mw = t.respDecryption(response);
			System.out.println("明文结果: " + mw);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
