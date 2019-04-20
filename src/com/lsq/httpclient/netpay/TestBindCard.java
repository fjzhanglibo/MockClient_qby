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

public class TestBindCard {
	public static void main(String[] args) throws Exception{
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//		final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
		//测试
	
		
//		final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/key/test/test_rsa_public_key_2048.pem", "pem", "RSA");
//		final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/key/test/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
		PublicKey yhPubKey = TestUtil.getPublicKey();
		PrivateKey hzfPriKey = TestUtil.getPrivateKey();
		int i = 0;
		int j = 0;
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
                sBuilder.append("<childMerchantId>"+TestUtil.childMerchantId+"</childMerchantId>");
				sBuilder.append("<msgType>01</msgType>");
				sBuilder.append("<tranCode>IFP001</tranCode>");
				sBuilder.append("<reqMsgId>"
						+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i+i
						+ "</reqMsgId>");
				sBuilder.append("<reqDate>"
						+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
						+ "</reqDate>");
				sBuilder.append("</head>");
				sBuilder.append("<body>");
				sBuilder.append("<terminalId>"+TestUtil.terminalId+"</terminalId>");
				//sBuilder.append("<bindId>I00010030202622688380400053352</bindId>");
				sBuilder.append("<userId>"+TestUtil.userId+"</userId>");
				sBuilder.append("<currency>156</currency>");
//				sBuilder.append("<amount>10</amount>");
//				sBuilder.append("<valid>0121</valid>");
//				sBuilder.append("<cvn2>159</cvn2>");
				sBuilder.append("<bankCardType>01</bankCardType>");
				sBuilder.append("<certificateNo>341126197709218366</certificateNo>");
				sBuilder.append("<accountName>全渠道</accountName>");
				sBuilder.append("<certificateType>ZR01</certificateType>");
				sBuilder.append("<bankCardNo>6216261000000000011</bankCardNo>");
//				sBuilder.append("<bankCardNo>621700083000012"+(int)(1+Math.random()*(10000-1+1))+"</bankCardNo>");
				sBuilder.append("<mobilePhone>18222920903</mobilePhone>");
//				sBuilder.append("<refundReason>质量不好</refundReason>");
				//sBuilder.append("<oriPayMsgId>2015102800121943</oriPayMsgId>");
//				sBuilder.append("<productCategory>01</productCategory>");
//				sBuilder.append("<productName>Mac笔记本</productName>");
//				sBuilder.append("<productDesc>笔记本电脑</productDesc>");
//				sBuilder.append("<sendReqMsgId>20160704171934</sendReqMsgId>");
//				sBuilder.append("<validateCode>332856</validateCode>");
				
				
//				sBuilder.append("<currency>156</currency>");
//				sBuilder.append("<amount>100</amount>");
//				sBuilder.append("<refundReason>测试退款</refundReason>");
//				sBuilder.append("<oriPayMsgId>201604251954339119</oriPayMsgId>");
//				sBuilder.append("<oriReqMsgId>2016042520001011</oriReqMsgId>");
				
				
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
				nvps.add(new BasicNameValuePair("agencyId", TestUtil.merchantId));
				nvps.add(new BasicNameValuePair("signData", signData));
				nvps.add(new BasicNameValuePair("tranCode", "IFP001"));
				nvps.add(new BasicNameValuePair("callBack","http://localhost:801/callback/ghtBindCard.do"));
//				 byte[] retBytes = httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);
				byte[] retBytes = httpClient4Util.doPost(TestUtil.qby_url+"/channel/commonSyncInter.do",null, nvps, null);
//				byte[] retBytes =httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
//			     byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);
	
				String response = new String(retBytes, "UTF-8");
				System.out.println("回调结果： " + new String(retBytes, "UTF-8"));
				TestEncype t = new TestEncype();
				System.out.println("明文结果: " + t.respDecryption(response));
				System.out.println("i"+i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
