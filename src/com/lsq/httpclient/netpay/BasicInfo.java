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

public class BasicInfo {

	public static void main(String[] args) throws Exception{
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
//	final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
	//测试
//	final String url = "http://localhost:8080/interfaceWeb/basicInfo";
//	final String url = "https://testapp.sicpay.com:11008/interfaceWeb/basicInfo";   
	

	//开发
//	final String url = "http://120.31.132.120:8082/interfaceWeb/basicInfo";
//	    final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:\\document\\key\\000158120120\\GHT_ROOT.pem", "pem", "RSA");
//        final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:\\document\\key\\000000153990021\\000000153990021.pem", "pem", null, "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/000000158120121/000000158120121.pem", "pem", null, "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/000000152110003.pem", "pem", null, "RSA");
//	final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("D:/key/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
	//	PublicKey yhPubKey = TestUtil.getPublicKey();
//	PrivateKey hzfPriKey = TestUtil.getPrivateKey();
	
//	 final String url = "http://epay.gaohuitong.com:8083/interfaceWeb/basicInfo";
	    
//        final String url = "http://gpay.gaohuitong.com:8086/interfaceWeb2/basicInfo";
//	    final PublicKey yhPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/549440155510001/GHT_ROOT.pem", "pem", "RSA");
//	 final PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/549440155510001/549440155510001.pem", "pem", null, "RSA");

//	 final String url = "https://portal.sicpay.com:8087/interfaceWeb/basicInfo";
	    
	    final String url = TestUtil.interface_url+"basicInfo";
	    
	 final PublicKey yhPubKey = TestUtil.getPublicKey();
   final PrivateKey hzfPriKey = TestUtil.getPrivateKey();

	
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
//			sBuilder.append("<agencyId>549440155510001</agencyId>");
			sBuilder.append("<agencyId>"+TestUtil.merchantId+"</agencyId>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<tranCode>100001</tranCode>");
			sBuilder.append("<reqMsgId>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+i+i
					+ "</reqMsgId>");
			sBuilder.append("<reqDate>"
					+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
					+ "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			
			
			sBuilder.append("<handleType>0</handleType>");
			sBuilder.append("<merchantName>LIBTEST1</merchantName>");
			sBuilder.append("<shortName>UNCLE HENRY(A)</shortName>");
			sBuilder.append("<city>344344</city>");
			sBuilder.append("<merchantAddress>UNIT C 15/FHUA CHIAO COMM CTR 678 NATHAN RD MONGKOK KL</merchantAddress>");
			sBuilder.append("<servicePhone>13250538964</servicePhone>");
			sBuilder.append("<orgCode></orgCode>");
			sBuilder.append("<merchantType>01</merchantType>");
			sBuilder.append("<category>5399</category>");
			sBuilder.append("<corpmanName>张立博</corpmanName>");
			sBuilder.append("<corpmanId>440103198112214218</corpmanId>");
			sBuilder.append("<corpmanPhone>13926015921</corpmanPhone>");
			sBuilder.append("<corpmanMobile>13926015921</corpmanMobile>");
			sBuilder.append("<corpmanEmail>>zhanglibo@sicpay.com</corpmanEmail>");
			sBuilder.append("<bankCode>105</bankCode>");
			sBuilder.append("<bankName>银行</bankName>");
			sBuilder.append("<bankaccountNo>6250502002603958</bankaccountNo>");
			sBuilder.append("<bankaccountName>张立博</bankaccountName>");
			sBuilder.append("<autoCus>0</autoCus>");
			sBuilder.append("<remark>备注</remark>");
			sBuilder.append("<licenseNo>91440184355798892G</licenseNo>");
//			sBuilder.append("<taxRegisterNo></taxRegisterNo>");
//			sBuilder.append("<subMerchantNo></subMerchantNo>");
//			sBuilder.append("<inviteMerNo></inviteMerNo>");
//			sBuilder.append("<county></county>");
//			sBuilder.append("<appid>wx04df48e919ef7b28</appid>");
//			sBuilder.append("<pid>2015062600009243</pid>");
//			sBuilder.append("<childEnter>1</childEnter>");
//			sBuilder.append("<ghtEnter>0</ghtEnter>");
//			sBuilder.append("<addrType>BUSINESS_ADDRESS</addrType>");
//			sBuilder.append("<contactType>LEGAL_PERSON</contactType>");
//			sBuilder.append("<mcc>200101110640354</mcc>");
//			sBuilder.append("<licenseType>NATIONAL_LEGAL_MERGE</licenseType>");
//			sBuilder.append("<authPayDir>http://epay.gaohuitong.com/channel/|http://test.pengjv.com/channel/</authPayDir>");
//			sBuilder.append("<scribeAppid>wx04df48e919ef7b28</scribeAppid>");
//			sBuilder.append("<contactMan>巫石</contactMan>");
//			sBuilder.append("<telNo>18675857506</telNo>");
//			sBuilder.append("<mobilePhone>18675857506</mobilePhone>");
//			sBuilder.append("<email>CSC@sicpay.com</email>");
//			sBuilder.append("<licenseBeginDate>20150826</licenseBeginDate>");
//			sBuilder.append("<licenseEndDate>20991231</licenseEndDate>");
//			sBuilder.append("<licenseRange>软件和信息技术服务业（具体经营项目请登录广州市商事主体信息公示平台查询。依法须经批准的项目，经相关部门批准后方可开展经营活动。）</licenseRange>");

			
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
//			nvps.add(new BasicNameValuePair("agencyId", "549440155510001"));
			nvps.add(new BasicNameValuePair("agencyId", TestUtil.merchantId));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", "100001"));
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
