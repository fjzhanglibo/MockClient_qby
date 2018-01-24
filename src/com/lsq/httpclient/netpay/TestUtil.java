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
	
	static String userId = "199101";
//    static String userId = "19910000036";
	
	/*static PublicKey getPublicKey() throws Exception{
		return CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/test/GHT_ROOT.pem", "pem", "RSA");
	}
	static PrivateKey getPrivateKey() throws Exception{
		return CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/test_125/test_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
	}*/
//	static String url = "http://localhost:8080/quickInter2.0/channel/commonSyncInter.do";
//  static String url = "http://120.31.132.119:8081/quickInter1/channel/commonSyncInter.do";
  
	/*static PublicKey getPublicKey() throws Exception{
        return CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/102100000125/GHT_ROOT.pem", "pem", "RSA");
    }
    static PrivateKey getPrivateKey() throws Exception{
        return CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/102100000125/102100000125.pem", "pem", null, "RSA");
    }*/
    
  static PublicKey getPublicKey() throws Exception{
        return CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/000000158120121/GHT_ROOT.pem", "pem", "RSA");
    }
    static PrivateKey getPrivateKey() throws Exception{
        return CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/000000158120121/000000158120121.pem", "pem", null, "RSA");
    } 
	   /*static PublicKey getPublicKey() throws Exception{
	        return CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/000158120120/GHT_ROOT.pem", "pem", "RSA");
	    }
	    static PrivateKey getPrivateKey() throws Exception{
	        return CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/000158120120/000158120120.pem", "pem", null, "RSA");
	    } */
    
    
   /* static PublicKey getPublicKey() throws Exception{
        return CryptoUtil.getRSAPublicKeyByFileSuffix("C:/document/key/549440155510001/GHT_ROOT.pem", "pem", "RSA");
    }
    static PrivateKey getPrivateKey() throws Exception{
        return CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/549440155510001/549440155510001.pem", "pem", null, "RSA");
    } */
    static String url = "http://120.31.132.119:8081/quickInter1/channel/commonSyncInter.do";

//  static String url = "http://localhost:8080/quickInter2.0/channel/commonSyncInter.do";
//  static String url = "http://epay.gaohuitong.com:8082/quickInter/channel/commonSyncInter.do";
//	    static String url = "http://192.168.10.20:8084/quickInter/channel/commonSyncInter.do";
  
  static PrivateKey getPtPrivateKey() throws Exception{
      return CryptoUtil.getRSAPrivateKeyByFileSuffix("C:/document/key/pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
  }
  
  /*public static String merchantId = "549440155510001";
  public static String childMerchantId = "18222920903";
  
  public static String terminalId = "20000277";*/
  
 public static String merchantId = "000000158120121";
  public static String childMerchantId = "19910000036";
  
  public static String terminalId = "20000125";
  
  /*public static String merchantId = "000158120120";
  public static String childMerchantId = "18085819914";
  
  public static String terminalId = "20000067";*/
  
	
	
}
