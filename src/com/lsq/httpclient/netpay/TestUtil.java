package com.lsq.httpclient.netpay;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TestUtil {
	
//	static PublicKey getPublicKey() throws Exception{
//		return CryptoUtil.getRSAPublicKeyByFileSuffix(
//				"F:/key/test/test_rsa_public_key_2048.pem", "pem", "RSA");
//	}
//	static PrivateKey getPrivateKey() throws Exception{
//		return CryptoUtil.getRSAPrivateKeyByFileSuffix(
//				"F:/key/test/test_pkcs8_rsa_private_key_2048.pem", "pem", null,
//				"RSA");
//	}
//	static String url = "http://120.31.132.114:8081/quickInter1/channel/commonSyncInter.do";
	
	
	
//	static String userId = "56742564";
	//借记卡
//	static String userId = "zhangsan";
	
	static String userId = "03083831";
	
	static PublicKey getPublicKey() throws Exception{
		return CryptoUtil.getRSAPublicKeyByFileSuffix("F:/密钥/生成证书/rsa_public_key_2048.pem", "pem", "RSA");
	}
	static PrivateKey getPrivateKey() throws Exception{
		return CryptoUtil.getRSAPrivateKeyByFileSuffix("F:/密钥/test/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
	}
	static String url = "http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do";
	
	
}
