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


public class BusiInfo {

	public static void main(String[] args) throws Exception{
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
	//测试
//	final String url = "http://epay.gaohuitong.com:8083/interfaceWeb/busiInfo";
//	final String url = "https://portal.sicpay.com:8087/interfaceWeb/busiInfo";
//        final String url = "http://gpay.gaohuitong.com:8086/interfaceWeb2/busiInfo";
//	    final String url = "https://testapp.sicpay.com:11008/interfaceWeb/busiInfo";
//	    final String url = "https://testpay.sicpay.com/interfaceWeb/busiInfo";
	    final String url = TestUtil.interface_url+"busiInfo";
	    
	    PublicKey yhPubKey = TestUtil.getPublicKey();
	     PrivateKey hzfPriKey = TestUtil.getPrivateKey();
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/549440155510001/GHT_ROOT.pem", "pem", "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/549440155510001/549440155510001.pem", "pem", null, "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000153990021.pem", "pem", null, "RSA");
	//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000152110003.pem", "pem", null, "RSA");
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("D:/key/test_rsa_public_key_2048.pem", "pem", "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
	    
//	    final String url = "https://testapp.sicpay.com:11008/interfaceWeb/busiInfo";   
//	    final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:\\document\\key\\000158120120\\GHT_ROOT.pem", "pem", "RSA");
//	    final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:\\document\\key\\000000153990021\\000000153990021.pem", "pem", null, "RSA");

	//	PublicKey yhPubKey = TestUtil.getPublicKey();
//	PrivateKey hzfPriKey = TestUtil.getPrivateKey();
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
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>100003</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i+i
					+ "</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<merchantId>"+TestUtil.childMerchantId+"</merchantId>");
			sBuilder.append("<handleType>0</handleType>");  //0：新增,1：修改,2：关闭业务, 3：从新开通 ,默认为0
			sBuilder.append("<cycleValue>2</cycleValue>");  //1:T1 2:D0 
			sBuilder.append("<allotFlag>0</allotFlag>");    //0:不允许 1：允许
			sBuilder.append("<busiList>");
			sBuilder.append("<busiCode>B00114</busiCode>");
			sBuilder.append("<futureRateType>1</futureRateType>");//1：百分比；2：单笔
			sBuilder.append("<futureRateValue>0.8</futureRateValue>");//费率类型为1时填费率的百分数；为2是填单笔金额
//			sBuilder.append("<futureMinAmount>0.01</futureMinAmount>");
//			sBuilder.append("<futureMaxAmount>100</futureMaxAmount>");
//            sBuilder.append("<attachRateType>1</attachRateType>");
//            sBuilder.append("<attachRateValue>0.01</attachRateValue>");
			sBuilder.append("</busiList>");
//			sBuilder.append("<busiList>");
//		    sBuilder.append("<busiCode>B00302</busiCode>");
//			sBuilder.append("<futureRateType>2</futureRateType>");
//			sBuilder.append("<futureRateValue>2</futureRateValue>");
//			sBuilder.append("<futureMinAmount></futureMinAmount>");
//			sBuilder.append("<futureMaxAmount></futureMaxAmount>");
//			sBuilder.append("</busiList>");
//			sBuilder.append("<busiList>");
//		    sBuilder.append("<busiCode>B00110</busiCode>");
//			sBuilder.append("<futureRateType>1</futureRateType>");
//			sBuilder.append("<futureRateValue>1</futureRateValue>");
//			sBuilder.append("<futureMinAmount></futureMinAmount>");
//			sBuilder.append("<futureMaxAmount></futureMaxAmount>");
//			sBuilder.append("</busiList>");
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
			nvps.add(new BasicNameValuePair("tranCode", "100003"));
//			nvps.add(new BasicNameValuePair("callBack","http://localhost:801/callback/ghtBindCard.do"));
//			 byte[] retBytes = httpClient4Util.doPost("http://localhost:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);
			byte[] retBytes = httpClient4Util.doPost(url,null, nvps, null);
//			byte[] retBytes =httpClient4Util.doPost("http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do", null, nvps, null);
//		     byte[] retBytes = httpClient4Util.doPost("http://192.168.80.113:8080/quickInter/channel/commonSyncInter.do", null, nvps, null);

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
