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


public class BankInfo {

	public static void main(String[] args) throws Exception{
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");

    final String url = TestUtil.interface_url+"bankInfo";	
		//测试

//	final String url = "http://120.31.132.119/interfaceWeb/bankInfo";
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("D:/key/GHT_ROOT.pem", "pem", "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000158120121.pem", "pem", null, "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000153990021.pem", "pem", null, "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
		PublicKey yhPubKey = TestUtil.getPublicKey();
	PrivateKey hzfPriKey = TestUtil.getPrivateKey();
    
//    final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/549440155510001/GHT_ROOT.pem", "pem", "RSA");
//
//    final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/549440155510001/549440155510001.pem", "pem", null, "RSA");
   
    
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
			sBuilder.append("<tranCode>100002</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i+i
					+ "</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<merchantId>"+TestUtil.childMerchantId+"</merchantId>");
//			sBuilder.append("<mobileNo2>18222920903</mobileNo2>");  //持卡人手机号，如果是实体商户,则该参数必传
			sBuilder.append("<handleType>0</handleType>");  //0：新增 1：删除。默认为“0：新增”
			sBuilder.append("<bankCode>999</bankCode>");
			sBuilder.append("<bankaccProp>1</bankaccProp>"); //0-对私，1-对公
			sBuilder.append("<name>高汇通</name>");   //
			sBuilder.append("<bankaccountNo>000000000102525</bankaccountNo>");//4367420060540647482  ，6222800105016832046
			sBuilder.append("<bankaccountType>1</bankaccountType>");  //1-借记卡;2-贷记卡;3-存折
			sBuilder.append("<certCode>1</certCode>");
			sBuilder.append("<certNo>440102199311034015</certNo>");
//			sBuilder.append("<bankbranchNo></bankbranchNo>");
			sBuilder.append("<defaultAcc>1</defaultAcc>");  //是否为默认账户：0:否 1：是,默认为0：否
//			sBuilder.append("<province></province>");
//			sBuilder.append("<city>长沙市长沙市长沙市长沙市长沙市长</city>");
//			sBuilder.append("<bankBranchName>芙蓉区南星路88号芙蓉区南星路88号芙蓉区南星路88号芙蓉区南星路</bankBranchName>");
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
			
			System.out.println("============="+encryptData);
			
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
			nvps.add(new BasicNameValuePair("tranCode", "100002"));
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
